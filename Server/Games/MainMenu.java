package Server.Games;

import Server.Chat.ChatLog;
import Server.ClientHandler;
import Server.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainMenu implements Location {
    List<ClientHandler> connectedClients;
    ChatLog chatLog;
    // List<ClientHandler> clientsToUpdate;

    public MainMenu() {
        connectedClients = new ArrayList<>();
        chatLog = new ChatLog();
    }

    @Override
    public String getName() {
        return "Main Menu";
    }

    @Override
    public int getMaxPlayers() {
        return Integer.MAX_VALUE;
    }

    @Override
    public List<User> getPlayers() {
        List<User> players = new ArrayList<>();
        for (ClientHandler client : connectedClients) {
            players.add(client.getCurrentUser());
        }
        return players;
    }

    @Override
    public void addClient(ClientHandler client) {
        connectedClients.add(client);
    }

    @Override
    public void removeClient(ClientHandler client) {
        connectedClients.remove(client);
    }

    @Override
    public Boolean hasRoom() {
        return (this.getMaxPlayers() > this.getPlayers().size());
    }

    @Override
    public void pushDisplayUpdates() {
        for (ClientHandler client : this.connectedClients) {
            client.out.println("Hello " + client.getCurrentUser().getName() + "!");
            chatLog.displayChat(client);
            client.endMessage();
        }
    }

    @Override
    public void acceptInput(ClientHandler client) {
        try {
            chatLog.addMessage(client.in.readLine(), client.getCurrentUser());
            // clients to update should be set to all if a new line has successfully been read in

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
