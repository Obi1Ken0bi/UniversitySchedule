package ru.puzikov.universityschedule.persistance.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.puzikov.universityschedule.parser.Parser;
import ru.puzikov.universityschedule.parser.Site;
import ru.puzikov.universityschedule.persistance.model.Group;

import java.util.List;
@Slf4j
@Service
public class ParserService {
    private final GroupService groupService;

    final
    Parser parser;

    public ParserService(GroupService groupService, Parser parser) {
        this.groupService = groupService;
        this.parser = parser;
    }

    public void refresh(){
        groupService.deleteAll();
        Site site = Site.builder()
                .groupClass("gr")
                .groupsUrl("https://www.smtu.ru/ru/listschedule/")
                .scheduleUrl("https://www.smtu.ru/ru/viewschedule")
                .build();
        List<String> groups = parser.parseGroups(site);
        for (int i=0;i<groups.size();i++) {
            Group group = parser.parseScheduleForGroup(site, Integer.parseInt(groups.get(i)));
            log.info(group.toString());
        }

    }
}
