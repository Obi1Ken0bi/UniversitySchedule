package ru.puzikov.universityschedule.misc;

import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


@Slf4j
public
class TimeTaskExecutor {
    ScheduledExecutorService executorService = Executors.newScheduledThreadPool(10);
    Runnable myTask;
    volatile boolean isStopIssued;

    public TimeTaskExecutor(Runnable myTask$) {
        myTask = myTask$;

    }

    public void startExecutionAt(int targetHour, int targetMin, int targetSec) {
        Runnable taskWrapper = () -> {
            myTask.run();
            startExecutionAt(targetHour, targetMin, targetSec);
        };
        long delay = computeNextDelay(targetHour, targetMin, targetSec);
        log.info("Delay: " + (delay));
        executorService.schedule(taskWrapper, delay, TimeUnit.SECONDS);
    }

    private long computeNextDelay(int targetHour, int targetMin, int targetSec) {
        LocalDateTime localNow = LocalDateTime.now();
        ZoneId currentZone = ZoneId.systemDefault();
        ZonedDateTime zonedNow = ZonedDateTime.of(localNow, currentZone);
        ZonedDateTime zonedNextTarget = zonedNow.withHour(targetHour).withMinute(targetMin).withSecond(targetSec);
        if (zonedNow.compareTo(zonedNextTarget) >= 0)
            zonedNextTarget = zonedNextTarget.plusDays(1);

        Duration duration = Duration.between(zonedNow, zonedNextTarget);
        return duration.getSeconds();
    }

    public void stop() {
        executorService.shutdown();
        try {
            executorService.awaitTermination(1, TimeUnit.DAYS);
        } catch (InterruptedException ex) {
            log.info("error");
        }
    }
}
