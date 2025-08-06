package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

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
     * Reads line of input and sends through socket via client's outputwriter
     * @param input scanner object that gets line of user input
     */
    public static void sendInput(Scanner input) {
        String send = input.nextLine();
        out.println(send);
    }

    /*
     * Updates client window with new content from server.
     * Clears the console of old content and reads new content through socket connection buffered reader
     * @throws IOException
    private static void getFromServer() throws IOException {
        clearConsole();
        while (in.ready()) {
            System.out.println(in.readLine());
        }
    }
    */

    /**
     * Clears screen of previous text.
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
                // could possibly replace with unused getFromServer() method
                while (true) {
                    if (in.ready()) {
                        clearConsole();
                        while (in.ready()) {
                            System.out.println(in.readLine());
                        }
                    }
                }
            } catch (IOException e) {
                System.out.println("Connection closed by server.");
            }
        }).start();

        while (true) {
            if (scanner.hasNextLine()) {
                sendInput(scanner);
            }
        }
    }
}