package ru.puzikov.universityschedule.parser;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import ru.puzikov.universityschedule.persistence.model.*;
import ru.puzikov.universityschedule.persistence.service.*;

import java.time.LocalTime;
import java.util.*;

@Component
@Slf4j
public class Parser {
    final
    BuildingService buildingService;
    final
    DayService dayService;
    final
    GroupService groupService;
    final
    LessonService lessonService;
    final
    PairService pairService;
    final
    RoomService roomService;
    final
    SubjectService subjectService;
    final
    TeacherService teacherService;

    public Parser(BuildingService buildingService, DayService dayService, GroupService groupService, LessonService lessonService, PairService pairService, RoomService roomService, SubjectService subjectService, TeacherService teacherService) {
        this.buildingService = buildingService;
        this.dayService = dayService;
        this.groupService = groupService;
        this.lessonService = lessonService;
        this.pairService = pairService;
        this.roomService = roomService;
        this.subjectService = subjectService;
        this.teacherService = teacherService;
    }

    @SneakyThrows
    public List<String> parseGroups(Site site) {
        Document document = Jsoup.connect(site.getGroupsUrl()).get();
        List<String> groupNames = new ArrayList<>();
        Elements groups = document.select("div.gr");
        for (Element group : groups) {
            groupNames.add(getText(group));
        }
        return groupNames;
    }

    private Pair parsePair(Element pairElem) {
        Elements infoElems = pairElem.select("td");
        Element timeAndWeekType = infoElems.get(0);
        String time = parseTime(timeAndWeekType);
        String weekType = parseSpanElem(timeAndWeekType);
        String buildingRoom = getText(infoElems.get(1));
        Element subjAndTypeElem = infoElems.get(2);
        String type = parseSpanElem(subjAndTypeElem);
        String subj = trim(getText(subjAndTypeElem).replace(type, ""));
        String teacher = trim(getText(infoElems.get(3)));
        char buildLetter = getBuildLetter(buildingRoom);
        String room = trim(buildingRoom.substring(2));
        String[] s = teacher.split(" ");
        String firstTime = time.substring(0, 5);
        LocalTime localTime = LocalTime.parse(firstTime);
        Building building = Building.builder().letter(buildLetter).build();
        building = buildingService.saveOrGet(building);
        Room roomToCreate = Room.builder().building(building).number(room).build();
        roomToCreate = roomService.saveOrGet(roomToCreate);
        Teacher teacher1;
        teacher1 = buildTeacherName(teacher, s);
        teacher1 = teacherService.saveOrGet(teacher1);
        Subject subject = Subject.builder().name(subj).build();
        subject = subjectService.saveOrGet(subject);
        Lesson lesson = Lesson.builder().room(roomToCreate).subject(subject).teacher(teacher1)
                .type(LessonType.getFromRepresentation(type)).build();
        lesson = lessonService.saveOrGet(lesson);
        if (weekType.equals("")) {
            return new Pair(lesson, localTime);
        } else if (weekType.equals("Нижняя неделя")) {
            return new Pair(null, lesson, localTime);
        }
        return new Pair(lesson, null, localTime);
    }

    private Teacher buildTeacherName(String teacher, String[] s) {
        Teacher teacher1;
        if (s.length == 2) {
            teacher1 = Teacher.builder().surname(s[0]).name(s[1]).build();
        } else if (!teacher.isEmpty()) {
            teacher1 = Teacher.builder().surname(s[0]).name(s[1]).patronymic(s[2]).build();
        } else {
            teacher1 = Teacher.builder().surname("").name("").patronymic("").build();
        }
        return teacher1;
    }

    private String trim(String buildingRoom) {
        return buildingRoom.trim();
    }

    private char getBuildLetter(String buildingRoom) {
        return trim(buildingRoom).toLowerCase(Locale.ROOT).charAt(0);
    }

    private String getText(Element infoElems) {
        return infoElems.text();
    }

    private String parseSpanElem(Element timeAndWeekType) {
        return trim(timeAndWeekType.select("span").text());
    }

    private String parseTime(Element timeAndWeekType) {
        return trim(getText(timeAndWeekType)
                .replace("Верхняя неделя", "")
                .replace("Нижняя неделя", ""));
    }

    @SneakyThrows
    public Group parseScheduleForGroup(Site site, int groupNumber) {
        Document document = Jsoup.connect(site.getUrlForGroup(groupNumber)).get();
        Elements daysElems = document.select("tbody");
        Group group = new Group(groupNumber);
        Map<Integer, Day> schedule = new HashMap<>(7, 1f);
        parseSchedule(daysElems, schedule);
        group.setSchedule(schedule);
        group = groupService.saveOrGet(group);
        return group;
    }

    private void parseSchedule(Elements daysElems, Map<Integer, Day> schedule) {
        for (int j = 0; j < daysElems.size(); j++) {
            Element dayElem = daysElems.get(j);
            if (getText(dayElem).equals("")) {

                continue;
            }
            Day day = Day.builder().dayOfWeek(j).pairs(new ArrayList<>()).build();
            Elements pairElems = dayElem.select("tr");
            parseDay(day, pairElems);
            day.getPairs().sort(Comparator.comparingInt(x -> x.getTime().getHour()));
            day = dayService.save(day);
            schedule.put(j, day);

        }
    }

    private void parseDay(Day day, Elements pairElems) {
        for (int i = 0; i < pairElems.size() - 1; i++) {
            Pair pair = parsePair(pairElems.get(i));
            Pair nextPair = parsePair(pairElems.get(i + 1));
            if (pair.getTime().equals(nextPair.getTime())) {
                pair.setDownLesson(nextPair.getUpperLesson());
                if(pair.getUpperLesson() == null){
                    pair.setUpperLesson(nextPair.getUpperLesson());
                }else{
                    pair.setDownLesson(nextPair.getDownLesson());
                }
                i++;
            } else {
                if (i + 1 == pairElems.size() - 1) {
                    nextPair = pairService.saveOrGet(nextPair);
                    day.getPairs().add(nextPair);
                }
            }
            pair = pairService.saveOrGet(pair);
            day.getPairs().add(pair);
        }
    }
}
