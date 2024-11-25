package pro.sky.telegrambot.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "task")
public class NotificationTask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String chatId;

    private String text;

    private LocalDateTime notificationDate;

    private LocalDateTime entryDate;

    public NotificationTask(String chatId, String text, LocalDateTime notificationDate) {
        this.chatId = chatId;
        this.text = text;
        this.notificationDate = notificationDate;
        this.entryDate = LocalDateTime.now();
    }

    public NotificationTask() {

    }

    public long getId() {
        return id;
    }

    public String getChatId() {
        return chatId;
    }

    public String getText() {
        return text;
    }

    public LocalDateTime getNotificationDate() {
        return notificationDate;
    }

    public LocalDateTime getEntryDate() {
        return entryDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NotificationTask that = (NotificationTask) o;
        return id == that.id && Objects.equals(chatId, that.chatId) && Objects.equals(text, that.text) && Objects.equals(notificationDate, that.notificationDate) && Objects.equals(entryDate, that.entryDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, chatId, text, notificationDate, entryDate);
    }

    @Override
    public String toString() {
        return "NotificationTask{" +
                "id=" + id +
                ", chatId='" + chatId + '\'' +
                ", text='" + text + '\'' +
                ", notificationDate=" + notificationDate +
                ", entryDate=" + entryDate +
                '}';
    }
}
