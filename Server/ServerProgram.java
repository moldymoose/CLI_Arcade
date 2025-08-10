package Server;



import Server.Games.MainMenu;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerProgram {
    // Server will only ever opperate on one port, have one list of users, and have one main menu
    private static final int port = 6969; // Port is hardcoded for now
    private static final List<User> serverUsers = new ArrayList<>(); // This is the list of all users ever logged in while the server has been active
    public static final MainMenu mainMenu = new MainMenu(); // Initialize main menu

    /**
     * Returns list of users connected to server.
     * @return list of users connected to server.
     */
    public static List<User> getServerUsers() {
        return serverUsers;
    }

    /**
     * Server main method.  accepts socket connections and spins off client handler threads.
     * @param args
     */
    public static void main(String[] args) {

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            //accepts incoming connections
            while (true) {
                Socket socket = serverSocket.accept();
                //starts new thread with accepted socket
                Thread clientThread = new Thread(new ClientHandler(socket));
                clientThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}