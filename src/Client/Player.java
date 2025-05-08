package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Player {
    private final String name;
    private final Socket socket;
    private final BufferedReader in;
    private final PrintWriter out;

    public Player(String name, String host, int port) throws IOException {
        this.name = name;
        this.socket = new Socket(host, port);
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.out = new PrintWriter(socket.getOutputStream(), true);
    }

    public void sendPlayerDetails() {
        this.out.println(this.name);
    }

    public void sendInput(Scanner input) {
        this.out.println(input.nextLine());
    }

    private void getFromServer() throws IOException {
        while(this.in.ready()) {
            this.in.readLine();
        }
    }

    public static void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        clearConsole();
        // Get player name
        System.out.println("What is your name?");
        String playerName = scanner.nextLine();

        try {
            // Try getting connection to server
            Player clientPlayer = new Player(playerName, "localhost", 6969);
            // Send player details
            clientPlayer.sendPlayerDetails();
            // Continuously get/send input to server
            while (true) {
                clientPlayer.getFromServer();
                clientPlayer.sendInput(scanner);
            }
        } catch (IOException e) {
            System.out.println("Unable to connect to server");
            System.err.println(e.getMessage());
        }

        scanner.close();
    }
}