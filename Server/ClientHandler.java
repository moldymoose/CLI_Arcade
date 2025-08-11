package Server;

import Server.Games.Location;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * ClientHandler runs as a thread on the server program to manage a client connected via a socket.
 * Each object has a final socket object created when the ClientHandler is constructed.
 * The socket input and output stream are handled by final in/out objects
 * The each ClientHandler also has a User and Location (that may change if a User logs in/out or moves to a different game/menu)
 */
public class ClientHandler implements Runnable {
    private final Socket socket;                    // The socket connection between our server and the client
    private User currentUser;                       // The player of the client
    private Location currentLocation;               // The current location of the player;
    public final BufferedReader in;                 // Input stream from client program
    public final PrintWriter out;                   // Writer to send information to client program

    /**
     * Client handler constructor.  Takes socket connection to initialize input/output for communication with client program
     * @param socket socket accepted by server main method
     * @throws IOException
     */
    public ClientHandler(Socket socket) throws IOException {
        this.socket = socket;
        this.in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
        this.out = new PrintWriter(this.socket.getOutputStream(), true);
    }

    /**
     * Continuously gets input from client program and processes it using the provided InputParser
     * @param <T> The data type expected from the input parser based on the provided input
     * @param userPrompt The text prompt displayed to the user before geting their input
     * @param inputParser The object that processes the user's input and returns an object of data type <T>
     * @return method returns the result from inputParser
     */
    public <T> T getInput(String userPrompt, InputParser<T> inputParser) {
        while (true) {                              // Loop until valid input is received
            out.println(userPrompt);                // Print prompt to user
            try {
                String input = in.readLine();       // Get input from user by reading line from buffered reader
                return inputParser.parse(input);    // Passes input into parser and returns result
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage()); // Prints illegal argument message if input was not valid
            } catch (IOException e) {
                System.out.println("There was an IO exception trying to read a line from the client.");
            }
        }
    }

    /**
     * Creates a new user object by prompting the user
     * @return
     */
    public User assignUser() {
        String userPrompt = "Please enter your name: ";

        // Anonymous input parser that returns a user based on the name they provide
        InputParser<User> inputParser = new InputParser<User>() {
            @Override
            public User parse(String input) throws IllegalArgumentException {

                // Searches existing users for username.  if it exists returns existing user
                for (User user : ServerProgram.getServerUsers()) {
                    if (user.getName().equals(input.trim())) {
                        return user;
                    }
                }
                // If user does not exist, a new one is created with provided username
                return new User(input.trim());
            }
        };

        // return calls getinput using anonymous input parser and prompt.
        return getInput(userPrompt, inputParser);
    }

    /**
     * Gets user object associated with client handler
     * @return user asssocieted with client hander
     */
    public User getCurrentUser() {
        return currentUser;
    }

    /**
     * Assigns user associated with client handler
     * @param currentUser
     */
    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    /**
     * returns location of user associated with client handler
     * @return
     */
    public Location getCurrentLocation() {
        return currentLocation;
    }

    /**
     * sets client handler user's location
     */
    public void setCurrentLocation(Location currentLocation) {
        this.currentLocation = currentLocation;
    }
    
    public void endMessage() {
        out.println("%END%");
    }

    /**
     * Run method for thread.  gets username and assigns it to current user.  Starts user in mainMenu.  Adds this clienthandler to main menu of server.
     * Continuously causes the current location of this user to send the menu info to all users connected and causes the current location of this user to accept input.
     */
    @Override
    public void run() {
        currentUser = assignUser();
        currentLocation = ServerProgram.mainMenu;
        ServerProgram.mainMenu.addClient(this);
        endMessage();
        while (true) {
            this.currentLocation.pushDisplayUpdates();
            this.currentLocation.acceptInput(this);
        }
    }
}
