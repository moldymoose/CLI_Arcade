package Server;

import Server.Games.Location;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private final Socket socket;                        // The socket connection between our server and the client
    private User currentUser;                          // The player of the client
    private Location currentLocation;                   // The current location of the player;
    public final BufferedReader in;
    public final PrintWriter out;

    public ClientHandler(Socket socket) throws IOException {
        this.socket = socket;
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.out = new PrintWriter(socket.getOutputStream(), true);
    }

    public <T> T getInput(String userPrompt, InputParser<T> inputParser) {
        while (true) {                          // Loop until valid input is received
            out.println(userPrompt);       // Print prompt to user
            try {
                String input = in.readLine();       // Get input from user
                return inputParser.parse(input);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            } catch (IOException e) {
                System.out.println("There was an IO exception trying to read a line from the client.");
            }
        }
    }

    public User assignUser() {
        String userPrompt = "Please enter your name: ";

        InputParser<User> inputParser = new InputParser<User>() {
            @Override
            public User parse(String input) throws IllegalArgumentException {

                for (User user : Server.getServerUsers()) {
                    if (user.getName().equals(input.trim())) {
                        return user;
                    }
                }
                return new User(input.trim());
            }
        };

        return getInput(userPrompt, inputParser);
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public Location getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(Location currentLocation) {
        this.currentLocation = currentLocation;
    }

    @Override
    public void run() {
        currentUser = assignUser();
        currentLocation = Server.mainMenu;
        Server.mainMenu.addClient(this);
        while (true) {
            this.currentLocation.drawMenu();
            this.currentLocation.acceptInput(this);
        }
    }
}
