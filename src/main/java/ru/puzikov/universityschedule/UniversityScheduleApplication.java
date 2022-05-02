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



    public static void main(String[] args) {
        SpringApplication.run(UniversityScheduleApplication.class, args);
    }


}
