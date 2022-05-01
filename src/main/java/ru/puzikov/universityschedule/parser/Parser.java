package ru.puzikov.universityschedule.parser;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import ru.puzikov.universityschedule.persistance.model.*;
import ru.puzikov.universityschedule.persistance.service.*;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
    ScheduleService scheduleService;
    final
    SubjectService subjectService;
    final
    TeacherService teacherService;

    public Parser(BuildingService buildingService, DayService dayService, GroupService groupService, LessonService lessonService, PairService pairService, RoomService roomService, ScheduleService scheduleService, SubjectService subjectService, TeacherService teacherService) {
        this.buildingService = buildingService;
        this.dayService = dayService;
        this.groupService = groupService;
        this.lessonService = lessonService;
        this.pairService = pairService;
        this.roomService = roomService;
        this.scheduleService = scheduleService;
        this.subjectService = subjectService;
        this.teacherService = teacherService;
    }

    @SneakyThrows
    public List<String> parseGroups(Site site) {
        Document document = Jsoup.connect(site.getGroupsUrl()).get();
        List<String> groupNames = new ArrayList<>();
        Elements groups = document.select("div.gr");
        for (Element group : groups) {
            groupNames.add(group.text());

        }
        return groupNames;
    }

    private Pair parsePair(Element pairElem) {
        Elements infoElems = pairElem.select("td");
        Element timeAndWeekType = infoElems.get(0);
        String time = timeAndWeekType.text().replace("Верхняя неделя", "").replace("Нижняя неделя", "").trim();
        String weekType = timeAndWeekType.select("span").text().trim();
        String buildingRoom = infoElems.get(1).text();
        Element subjAndTypeElem = infoElems.get(2);
        String type = subjAndTypeElem.select("span").text().trim();
        String subj = subjAndTypeElem.text().replace(type, "").trim();
        String teacher = infoElems.get(3).text().trim();
        char buildLetter = buildingRoom.trim().toLowerCase(Locale.ROOT).charAt(0);
        String room = buildingRoom.substring(2).trim();
        String[] s = teacher.split(" ");
//        log.info("Time: "+time);
//        log.info("Week type: "+weekType);
//        log.info("Building: "+buildLetter);
//        log.info("Room: "+room);
//        log.info("Subject: "+subj);
//        log.info("Type: "+type);
//        log.info("Teacher: "+teacher);
        String firstTime = time.substring(0, 5);
        LocalTime localTime = LocalTime.parse(firstTime);
        //log.info("Start time: "+localTime);
        Building building = Building.builder().letter(buildLetter).build();
        building = buildingService.saveOrGet(building);
        Room roomToCreate = Room.builder().building(building).number(room).build();
        roomToCreate = roomService.saveOrGet(roomToCreate);
        Teacher teacher1;
        if (!teacher.isEmpty()) {
            teacher1 = Teacher.builder().surname(s[0]).name(s[1]).patronymic(s[2]).build();
        } else {
            teacher1 = Teacher.builder().surname("").name("").patronymic("").build();
        }
        teacher1 = teacherService.saveOrGet(teacher1);
        Subject subject = Subject.builder().name(subj).build();
        subject = subjectService.saveOrGet(subject);
        Lesson lesson = Lesson.builder().room(roomToCreate).subject(subject).teacher(teacher1)
                .type(LessonType.getFromRepresentation(type)).build();
        lesson = lessonService.saveOrGet(lesson);
        if (weekType.equals("")) {
            return new Pair(lesson, localTime);
        }
        return new Pair(lesson, null, localTime);
    }

    @SneakyThrows
    public Group parseScheduleForGroup(Site site, int groupNumber) {
        Document document = Jsoup.connect(site.getUrlForGroup(groupNumber)).get();
        Elements daysElems = document.select("tbody");
        Group group=new Group(groupNumber);
        Schedule schedule = Schedule.builder().days(new ArrayList<Day>()).build();
        for (int j = 0; j < daysElems.size(); j++) {
            Element dayElem = daysElems.get(j);
            if (dayElem.text().equals("")) continue;
            Day day = Day.builder().dayOfWeek(DayOfWeek.getDayOfWeek(j)).pairs(new ArrayList<Pair>()).build();
            //log.info(dayElem.text());
            Elements pairElems = dayElem.select("tr");
            //  for (Element pairElem:pairElems){
            for (int i = 0; i < pairElems.size(); i++) {
                Pair pair = parsePair(pairElems.get(i));
                pair = pairService.saveOrGet(pair);
                log.info(pair.toString());
                day.getPairs().add(pair);


            }
            day=dayService.save(day);
            schedule.getDays().add(day);

        }
        schedule=scheduleService.save(schedule);
        group.setSchedule(schedule);
        group=groupService.saveOrGet(group);
        return group;
    }
}
