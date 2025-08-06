package Server;



import Server.Games.MainMenu;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private static final int port = 6969;
    private static final List<User> serverUsers = new ArrayList<>(); // This is the list of all users ever logged in while the server has been active
    public static final MainMenu mainMenu = new MainMenu();

    public static List<User> getServerUsers() {
        return serverUsers;
    }

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