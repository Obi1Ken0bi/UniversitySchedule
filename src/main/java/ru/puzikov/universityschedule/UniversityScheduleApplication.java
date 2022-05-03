package ru.puzikov.universityschedule;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.PostConstruct;
import java.time.ZoneId;
import java.util.Date;
import java.util.TimeZone;

@SpringBootApplication
@Slf4j
@EnableScheduling
public class UniversityScheduleApplication {
    public static void main(String[] args) {
        SpringApplication.run(UniversityScheduleApplication.class, args);
    }

    @PostConstruct
    public void init() {
        log.info(new Date().toString());
        TimeZone.setDefault(TimeZone.getTimeZone(ZoneId.of("Europe/Moscow")));
        log.info(new Date().toString());
    }


}
