package ru.puzikov.universityschedule.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.puzikov.universityschedule.model.Day;
import ru.puzikov.universityschedule.model.DayOfWeek;
import ru.puzikov.universityschedule.model.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface DayRepository extends JpaRepository<Day, Long> {

}