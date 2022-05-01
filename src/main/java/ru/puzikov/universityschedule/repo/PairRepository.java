package ru.puzikov.universityschedule.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.puzikov.universityschedule.model.Pair;

public interface PairRepository extends JpaRepository<Pair, Long> {
}