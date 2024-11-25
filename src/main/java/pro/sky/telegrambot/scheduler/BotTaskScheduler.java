package pro.sky.telegrambot.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pro.sky.telegrambot.service.NotificationService;

@Component
public class BotTaskScheduler {
     private final NotificationService service;

    public BotTaskScheduler(NotificationService service) {
        this.service = service;
    }

    @Scheduled(cron = "0 0/1 * * * *")
    public void execute() {
        service.sendReminder();
    }
}
