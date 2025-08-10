package Server.Games;

import Server.ClientHandler;
import Server.InputParser;
import Server.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.List;

public interface Location {
    public String getName();
    public int getMaxPlayers();

    public List<User> getPlayers();

    public void addClient(ClientHandler client);
    public void removeClient(ClientHandler client);

    public Boolean hasRoom();

    public void pushDisplayUpdates();

    public void acceptInput(ClientHandler client);
    public default <T> T processInput(String userPrompt, InputParser<T> inputParser, ClientHandler client) {
        while (true) {                          // Loop until valid input is received
            client.out.print(userPrompt);       // Print prompt to user
            try {
                String input = client.in.readLine();       // Get input from user
                return inputParser.parse(input);
            } catch (IllegalArgumentException e) {
                client.out.println(e.getMessage());
            } catch (IOException e) {
                System.out.println("There was an IO exception trying to read a line from the client.");
            }
        }
    }
}