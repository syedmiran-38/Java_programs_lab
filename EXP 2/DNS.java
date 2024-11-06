import java.net.*;
import java.io.*;

public class DNS {
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            System.out.println("\n1. DNS Lookup\n2. Reverse DNS Lookup\n3. Exit\nChoose:");
            int choice = Integer.parseInt(in.readLine());
            switch (choice) {
                case 1: 
                    System.out.print("Enter Host Name: ");
                    lookupDNS(in.readLine());
                    break;
                case 2: 
                    System.out.print("Enter IP Address: ");
                    reverseDNS(in.readLine());
                    break;
                case 3: 
                    System.exit(0);
                default: 
                    System.out.println("Invalid option. Please choose again.");
            }
        }
    }

    static void lookupDNS(String host) {
        try {
            InetAddress address = InetAddress.getByName(host);
            System.out.println("Host: " + address.getHostName() + "\nIP: " + address.getHostAddress());
        } catch (UnknownHostException e) {
            System.out.println("Error: Unable to resolve host " + host);
        }
    }

    static void reverseDNS(String ip) {
        try {
            InetAddress address = InetAddress.getByName(ip);
            System.out.println("IP: " + ip + "\nHost: " + address.getHostName());
        } catch (UnknownHostException e) {
            System.out.println("Error: Unable to resolve IP " + ip);
        }
    }
}
