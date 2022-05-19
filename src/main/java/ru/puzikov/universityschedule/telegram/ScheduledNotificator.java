package ru.puzikov.universityschedule.telegram;

//@Service
public class ScheduledNotificator {

    final
    ScheduleBot bot;

    public ScheduledNotificator(ScheduleBot bot) {
        this.bot = bot;
    }

    //@Scheduled(cron = "0 0/10 * * * ?")
    public void notifyStudents() {
        bot.notifyUsers();
    }
}
