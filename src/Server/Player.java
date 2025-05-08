package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Player extends Thread{
    private final Socket socket;                        // The socket connection between our server and the client
    private final String name;                          // The player of the client
    private Game currentGame;                           // The current game the player is in
    private final BufferedReader in;
    private final PrintWriter out;

    public Player(Socket socket, Game currentGame) throws IOException {
        this.socket = socket;
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.out = new PrintWriter(socket.getOutputStream(), true);
        this.name = in.readLine();
        this.currentGame = currentGame;
        currentGame.players.add(this);
    }

    public String getInput() {
        try {
            return in.readLine();
        } catch (IOException e) {
            return (e.getMessage());
        }
    }

    public PrintWriter getOut() {
        return out;
    }

    @Override
    public void run() {
        while (true) {
            currentGame.getInput(this);
        }
    }

    @Override
    public String toString() {
        return name;
    }
}
