package pro.sky.telegrambot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.model.NotificationTask;
import pro.sky.telegrambot.repository.TaskRepository;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;

import static pro.sky.telegrambot.util.BotUtil.VALID_DATE_PATTERN;

@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    private final Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    private final TelegramBot telegramBot;

    private final TaskRepository repository;

    public TelegramBotUpdatesListener(TelegramBot telegramBot, TaskRepository repository) {
        this.telegramBot = telegramBot;
        this.repository = repository;
    }

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            String text = update.message().text();
            logger.info("Processing update text: {}", text);
            if (update.message() == null && !text.isBlank()) {
                logger.warn("Not expected message: {}", text);
                return;
            }
            String chatId = getChatId(update);
            SendMessage sendMessage = null;
            Matcher matcher = VALID_DATE_PATTERN.matcher(text);
            if (text.equals("/start")) {
                sendMessage = new SendMessage(chatId, "Greetings");
            } else if (matcher.matches()) {
                try {
                    repository.save(new NotificationTask(
                                    chatId,
                                    matcher.group(3),
                                    LocalDateTime.parse(matcher.group(1), DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"))
                            )
                    );
                } catch (DateTimeParseException exception) {
                    logger.error("[{}]", exception.getMessage());
                    sendMessage = new SendMessage(chatId, "Not correct format of date");
                }
            }

            if (sendMessage != null) {
                execute(sendMessage);
            }
            // Process your updates here
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    public void execute(Collection<SendMessage> sendMessages) {
        sendMessages.forEach(telegramBot::execute);
    }

    public void execute(SendMessage sendMessage) {
        execute(List.of(sendMessage));
    }

    private String getChatId(Update update) {
        if (update.message().chat() == null) {
            throw new IllegalArgumentException("There is no chatId");
        }
        return update.message().chat().id().toString();
    }

}
