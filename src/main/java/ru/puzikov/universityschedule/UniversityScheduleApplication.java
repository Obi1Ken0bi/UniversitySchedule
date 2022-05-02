package ru.puzikov.universityschedule;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ru.puzikov.universityschedule.parser.Parser;
import ru.puzikov.universityschedule.parser.Site;
import ru.puzikov.universityschedule.persistance.model.Group;
import ru.puzikov.universityschedule.persistance.repo.GroupRepository;

import java.util.List;

@SpringBootApplication
@Slf4j
public class UniversityScheduleApplication {
    @Autowired
    Parser parser;
    @Autowired
    GroupRepository groupRepository;
    @Value("${university.update}")
    private Boolean needToUpdate;


    public static void main(String[] args) {
        SpringApplication.run(UniversityScheduleApplication.class, args);
    }

    @Bean

    public CommandLineRunner runner() {
        return args -> {
            if (needToUpdate) {
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
            } else
                log.info("No update needed");
            groupRepository.deleteAll();

        };
    }
}
