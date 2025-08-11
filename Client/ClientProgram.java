package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * ClientProgram connects to the server via socket connection, sends user input, and displays server output.
 * It continuously reads output from the server in a separate thread to avoid being blocked by waiting for user input.
 */
public class ClientProgram {
    // Client program has a single socket, reader for input, and writer for output
    private static Socket socket;
    private static BufferedReader in;
    private static PrintWriter out;

    // Static initializer block creates socket connection and input/output reader/writer using try/catch 
    static {
        try {
            socket = new Socket("localhost", 6969);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            System.out.println("Unable to connect to server");
            System.err.println(e.getMessage());
        }
    }

    /**
     * Sends a line of user input read by scanner to the server
     * @param input Scanner object for reading user input
     */
    public static void sendInput(Scanner input) {
        String send = input.nextLine();
        out.println(send);
    }

    /**
     * Displays user information sent from the server.
     * Uses ANSI code to clear the screen before printing new info from input stream.
     * @throws IOException
     */ 
    private static void getFromServer() throws IOException {
        clearConsole();
        String line = in.readLine();
        while (!line.equals("%END%")) {
            String nextLine = in.readLine();
            if (!nextLine.equals("%END%")) {
                System.out.println(line);
            } else {
                System.out.print(line);
            }
            line = nextLine; 
        }
    }

    /**
     * Clears user console using ANSI escape codes.
     */
    public static void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    /**
     * Class main method.
     * Initializes new scanner for getting user input.
     * Creates a thread to continuously read new information from server
     * Uses while loop to continuously get new input from scanner
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Separate thread is required to get info from server because scanner.hasNextLine() will halt the program. 
        new Thread(() -> {
            try {
                while (true) {
                    // If there is new content from the server, read it and display it
                    if (in.ready()) {
                        getFromServer();
                    }
                }
            } catch (IOException e) {
                System.out.println("Connection closed by server.");
            }
        }).start();

        // Main thread continuously waits for user to provide new input.
        while (true) {
            if (scanner.hasNextLine()) {
                sendInput(scanner);
            }
        }
    }
}