package ru.puzikov.universityschedule.telegram;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.config.Task;
import ru.puzikov.universityschedule.misc.TimeTaskExecutor;
import ru.puzikov.universityschedule.persistence.service.PairService;

import java.util.Timer;

@Configuration
@Slf4j
public class ScheduledNotificator {

    final
    PairService pairService;
    final
    ScheduleBot bot;
    private final Timer timer = new Timer();

    public ScheduledNotificator(ScheduleBot bot, PairService pairService) {
        this.bot = bot;
        this.pairService = pairService;
    }

    @Bean
    public TimeTaskExecutor notifyStudents() {
        TimeTaskExecutor executor = new TimeTaskExecutor(new Task(bot::notifyUsers));
        executor.startExecutionAt(0, 1, 0);
        log.info("executor ready");
        return executor;
    }
}
