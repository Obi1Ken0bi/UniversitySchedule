package ru.puzikov.universityschedule.persistence.repo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PairRepositoryTest {
    @Autowired
    private PairRepository pairRepository;

    @Test
    void findDistinctTimes() {
        System.out.println(pairRepository.findDistinctTimes());
    }
}