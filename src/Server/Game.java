package Server;

import java.util.ArrayList;
import java.util.List;

public abstract class Game {
    protected final String name;                                // Name of the game
    protected final int maxPlayers;                             // The maximum number of players
    protected final List<Player> players;                       // Array of players in game lobby
    protected final List<Message> chatLog;                      // Chat log is unique to each game

    public Game(String name, int maxPlayers) {
        this.name = name;
        this.maxPlayers = maxPlayers;
        this.players = new ArrayList<>();
        this.chatLog = new ArrayList<>();
    }

    public String getName() {
        return name;
    }
    public int getMaxPlayers() {
        return maxPlayers;
    }
    public List<Player> getPlayers() {
        return players;
    }
    public void addPlayer(Player player) {
        this.players.add(player);
    }
    public Boolean notFull() {
        return (players.size() < maxPlayers);
    }

    public void addMessage(String msg, Player sender) {
        chatLog.add(new Message(msg, sender));
    }

    public void displayChat(int count) {
        System.out.println("=== Chat Log ===");
        int start = Math.max(0, chatLog.size() - count);
        int actualMessages = chatLog.size() - start;

        // Print blank lines first if not enough messages
        for (int i = 0; i < count - actualMessages; i++) {
            System.out.println();
        }

        // Print the most recent messages
        for (int i = start; i < chatLog.size(); i++) {
            System.out.println(chatLog.get(i));
        }
        System.out.println("================");
    }


    public abstract boolean getInput(Player player);
}