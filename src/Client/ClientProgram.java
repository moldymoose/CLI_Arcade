package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientProgram {
    private static Socket socket;
    private static BufferedReader in;
    private static PrintWriter out;

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

    public static void sendInput(Scanner input) {
        String send = input.nextLine();
        out.println(send);
    }

    private static void getFromServer() throws IOException {
        clearConsole();
        while (in.ready()) {
            System.out.println(in.readLine());
        }
    }

    public static void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        new Thread(() -> {
            try {
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