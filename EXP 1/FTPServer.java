import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class FTPServer {
    public static void main(String[] args) {
        try (ServerSocket ss = new ServerSocket(1010)) { // Use try-with-resources to auto-close
            System.out.println("Server socket is created. Waiting for client...");

            while (true) {
                try (Socket sl = ss.accept()) { // Accept client connections and close automatically
                    System.out.println("Client connected.");

                    DataInputStream fromClient = new DataInputStream(sl.getInputStream());
                    BufferedReader reader = new BufferedReader(new InputStreamReader(fromClient));
                    String option = reader.readLine(); // Use BufferedReader for reading lines

                    if (option.equalsIgnoreCase("upload")) {
                        System.out.println("Upload option selected.");
                        String fileName = reader.readLine(); // Get the file name
                        File clientFile = new File(fileName);
                        try (FileOutputStream fout = new FileOutputStream(clientFile)) {
                            // Read file data from the client
                            int ch;
                            while ((ch = fromClient.read()) != -1) {
                                fout.write((char) ch);
                            }
                        }
                        System.out.println("File " + fileName + " uploaded successfully.");
                    }

                    if (option.equalsIgnoreCase("download")) {
                        System.out.println("Download option selected.");
                        String fileName = reader.readLine(); // Get the file name
                        File clientFile = new File(fileName);

                        if (clientFile.exists()) {
                            try (FileInputStream fis = new FileInputStream(clientFile);
                                 PrintStream out = new PrintStream(sl.getOutputStream())) {
                                // Send file data to the client
                                int n;
                                while ((n = fis.read()) != -1) {
                                    out.print((char) n);
                                }
                            }
                            System.out.println("File " + fileName + " downloaded successfully.");
                        } else {
                            System.out.println("File " + fileName + " does not exist.");
                        }
                    }
                } // Socket will be closed here
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
