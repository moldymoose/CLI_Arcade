package Server.Chat;

import Server.ClientHandler;
import Server.User;
import Server.LocationResources.Panel;

import java.util.ArrayList;
import java.util.List;

public class ChatLog implements Panel {
    private List<Message> chatLog;

    public ChatLog() {
        chatLog = new ArrayList<>();
    }

    public void addMessage(String msg, User sender) {
        chatLog.add(new Message(msg, sender));
    }

    /**
     * Returns the most recent messages
     * @param n number of messages to be returned
     * @return
     */
    public String getMessages(int n) {
        StringBuilder chat = new StringBuilder();

        // Print the most recent messages
        for (int i = Math.max(0, chatLog.size() - n); i < chatLog.size(); i++) {
            chat.append(chatLog.get(i)).append(System.lineSeparator());
        }
        return chat.toString();
    }

    public String getContents(ClientHandler client) {
        return getMessages(chatLog.size());
    }
}