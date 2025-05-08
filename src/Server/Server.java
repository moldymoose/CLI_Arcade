package Server;

import Server.Games.Arcade;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static final int port = 6969;

    public static void main(String[] args) {
        Game mainMenu = new Arcade();

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            //accepts incoming connections
            while (true) {
                Socket socket = serverSocket.accept();
                //starts new thread with accepted socket
                new Player(socket, mainMenu).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
