package ru.puzikov.universityschedule;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ru.puzikov.universityschedule.parser.Parser;
import ru.puzikov.universityschedule.parser.Site;
import ru.puzikov.universityschedule.persistance.model.Group;

@SpringBootApplication
@Slf4j
public class UniversityScheduleApplication {
    @Autowired
    Parser parser;


    public static void main(String[] args) {
        SpringApplication.run(UniversityScheduleApplication.class, args);
    }

    @Bean

    public CommandLineRunner runner() {
        return args -> {
            Site site = Site.builder()
                    .groupClass("gr")
                    .groupsUrl("https://www.smtu.ru/ru/listschedule/")
                    .scheduleUrl("https://www.smtu.ru/ru/viewschedule")
                    .build();
            Group group = parser.parseScheduleForGroup(site, 20391);
            log.info(group.toString());

        };
    }
}
