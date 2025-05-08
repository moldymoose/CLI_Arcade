package Server;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Message {
    private final String contents;
    private final Player sender;
    private final String timestamp;

    public Message(String contents, Player sender) {
        this.contents = contents;
        this.sender = sender;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        this.timestamp = LocalDateTime.now().format(formatter);
    }

    public String getContents() {
        return contents;
    }

    public Player getSender() {
        return sender;
    }

    @Override
    public String toString() {
        return timestamp + " - " + sender + ": " + contents;
    }
}
