package Server.Chat;

import Server.ClientHandler;
import Server.User;

import java.util.ArrayList;
import java.util.List;

public class ChatLog {
    List<Message> chatLog;

    public ChatLog() {
        chatLog = new ArrayList<>();
    }

    public void addMessage(String msg, User sender) {
        chatLog.add(new Message(msg, sender));
    }

    //For printing entire chat
    public void displayChat(ClientHandler client) {
        client.out.println("=== Chat Log ===");
        for (Message msg : chatLog) client.out.println(msg);
        client.out.println("================");
    }

    //For printing specific number of most recent messages
    public void displayChat(ClientHandler client, int count) {
        client.out.println("=== Chat Log ===");
        int start = Math.max(0, chatLog.size() - count);
        int actualMessages = chatLog.size() - start;

        // Print blank lines first if not enough messages
        for (int i = 0; i < count - actualMessages; i++) {
            client.out.println();
        }

        // Print the most recent messages
        for (int i = start; i < chatLog.size(); i++) {
            client.out.println(chatLog.get(i));
        }
        client.out.println("================");
    }
}