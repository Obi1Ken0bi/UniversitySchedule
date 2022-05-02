package ru.puzikov.universityschedule.persistance.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.puzikov.universityschedule.parser.Parser;
import ru.puzikov.universityschedule.parser.Site;
import ru.puzikov.universityschedule.persistance.model.Group;

import java.util.List;

@Slf4j
@Service
public class ParserService {
    final
    Parser parser;
    private final GroupService groupService;

    public ParserService(GroupService groupService, Parser parser) {
        this.groupService = groupService;
        this.parser = parser;
    }

    public void refresh() {
        groupService.deleteAll();
        Site site = Site.builder()
                .groupClass("gr")
                .groupsUrl("https://www.smtu.ru/ru/listschedule/")
                .scheduleUrl("https://www.smtu.ru/ru/viewschedule")
                .build();
        List<String> groups = parser.parseGroups(site);
        for (String s : groups) {
            Group group = parser.parseScheduleForGroup(site, Integer.parseInt(s));
            log.info(group.toString());
        }

    }
}
