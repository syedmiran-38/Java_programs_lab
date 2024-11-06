import java.io.*;
import java.net.*;

public class ChatClient {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 1010);
             BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader serverIn = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            String message;
            while (true) {
                System.out.print("You: ");
                message = in.readLine();
                out.println(message); // Send message to server
                System.out.println(serverIn.readLine()); // Read response from server
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
