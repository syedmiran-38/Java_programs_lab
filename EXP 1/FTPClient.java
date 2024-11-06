import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;

class One extends JFrame implements ActionListener {
    public JButton b, b1; 
    public JLabel l; 
    public JLabel l1, lmsg1, lmsg2; 

    One() {
        b = new JButton("Upload"); 
        l = new JLabel("Upload a file: ");  
        lmsg1 = new JLabel(""); 

        b1 = new JButton("Download"); 
        l1 = new JLabel("Download a file: "); 
        lmsg2 = new JLabel(""); 

        setLayout(new GridLayout(2, 3, 10, 10)); 
        add(l); 
        add(b); 
        add(lmsg1); 
        add(l1); 
        add(b1); 
        add(lmsg2); 

        b.addActionListener(this); 
        b1.addActionListener(this); 

        setVisible(true); 
        setSize(600, 500); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Ensure the application exits
    }

    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == b) { // Check if upload button was pressed
                Socket s = new Socket("localhost", 1010); 
                System.out.println("Client connected to server"); 
                JFileChooser j = new JFileChooser(); 
                int val = j.showOpenDialog(One.this); 

                if (val == JFileChooser.APPROVE_OPTION) {
                    String filename = j.getSelectedFile().getName(); 
                    String path = j.getSelectedFile().getPath(); 

                    PrintStream out = new PrintStream(s.getOutputStream()); 
                    out.println("Upload"); 
                    out.println(filename); 

                    FileInputStream fis = new FileInputStream(path); 
                    int n; 
                    while ((n = fis.read()) != -1) {
                        out.print((char) n); 
                    } 
                    fis.close(); 
                    out.close(); 
                    lmsg1.setText(filename + " is uploaded"); 
                }
                s.close(); // Close socket after uploading
            }

            if (e.getSource() == b1) { // Check if download button was pressed
                Socket s = new Socket("localhost", 1010); 
                System.out.println("Client connected to server"); 
                String remoteadd = s.getRemoteSocketAddress().toString(); 
                System.out.println(remoteadd); 

                JFileChooser j1 = new JFileChooser(); 
                int val = j1.showOpenDialog(One.this); 

                if (val == JFileChooser.APPROVE_OPTION) {
                    String filename = j1.getSelectedFile().getName(); 
                    String filepath = j1.getSelectedFile().getPath(); 

                    System.out.println("File name: " + filename); 
                    PrintStream out = new PrintStream(s.getOutputStream()); 
                    out.println("Download"); 
                    out.println(filepath); 

                    FileOutputStream fout = new FileOutputStream(filename); 
                    DataInputStream fromserver = new DataInputStream(s.getInputStream()); 
                    int ch; 
                    while ((ch = fromserver.read()) != -1) {
                        fout.write((char) ch); 
                    } 
                    fout.close(); 
                    s.close(); // Close socket after downloading
                    lmsg2.setText(filename + " is downloaded"); 
                }
            }
        } catch (Exception ee) { 
            System.out.println(ee); 
        } 
    } 
}

public class FTPClient { 
    public static void main(String[] args) { 
        new One(); 
    } 
}
