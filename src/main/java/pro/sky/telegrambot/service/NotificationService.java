package pro.sky.telegrambot.service;

import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.listener.TelegramBotUpdatesListener;
import pro.sky.telegrambot.model.NotificationTask;
import pro.sky.telegrambot.repository.TaskRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class NotificationService {

    private final TelegramBotUpdatesListener listener;

    private final TaskRepository repository;

    public NotificationService(TelegramBotUpdatesListener listener, TaskRepository repository) {
        this.listener = listener;
        this.repository = repository;
    }

    public void sendReminder() {
        List<NotificationTask> tasks = repository.findByNotificationDateBetween(LocalDateTime.now().minusSeconds(60),
                LocalDateTime.now());
        Set<SendMessage> messages = tasks.stream()
                .map(t -> new SendMessage(t.getChatId(), t.getText()))
                .collect(Collectors.toSet());
        listener.execute(messages);
    }
}
