package Server.Locations.MainMenu;

import Server.Chat.ChatLog;
import Server.ClientHandler;
import Server.User;
import Server.LocationResources.Display;
import Server.LocationResources.Location;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainMenu implements Location {
    List<ClientHandler> connectedClients;
    ChatLog chatLog;
    MainMenuPanel mainMenuPanel;
    Display display;

    public MainMenu() {
        connectedClients = new ArrayList<>();
        chatLog = new ChatLog();
        mainMenuPanel = new MainMenuPanel();
        display = new Display(70, 20);
        
        display.addPanel(mainMenuPanel);
        display.addPanel(chatLog);
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
        for (ClientHandler client : connectedClients) {
            pushDisplayUpdates(client);
        }
    }
    
    @Override
    public void pushDisplayUpdates(ClientHandler client) {
       client.out.println(display.displayString(client));
    }

    @Override
    public void acceptInput(ClientHandler client) {

        try {
            String input[] = client.in.readLine().split(" ", 2);
            String command = input[0];
            String content = input.length > 1 ? input[1] : "";
            
            switch (command) {
                case "/msg":
                    chatLog.addMessage(content, client.getCurrentUser());
                    break;
            
                default:
                    client.out.println("Invalid message");
                    break;
            }
            pushDisplayUpdates();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}