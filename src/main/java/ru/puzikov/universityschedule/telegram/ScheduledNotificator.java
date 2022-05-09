package ru.puzikov.universityschedule.telegram;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ScheduledNotificator {

    final
    ScheduleBot bot;

    public ScheduledNotificator(ScheduleBot bot) {
        this.bot = bot;
    }

    @Scheduled(cron = "0 0/10 * * * ?")
    public void notifyStudents() {
        bot.notifyUsers();
    }
}
