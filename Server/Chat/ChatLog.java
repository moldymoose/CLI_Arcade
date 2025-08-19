package Server.Chat;

import Server.User;
import Server.LocationResources.Panel;

import java.util.ArrayList;
import java.util.List;

public class ChatLog implements Panel {
    private List<Message> chatLog;
    private String contents;

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
    
    @Override
    public void updateContents() {
        contents = getMessages(chatLog.size());
    }

    @Override
    public String toString() {
        updateContents();
        return contents;
    }
}