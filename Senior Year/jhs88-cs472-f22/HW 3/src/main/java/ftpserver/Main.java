/*
 *  CS472 – Homework #3
 *  Joseph Scherreik
 *  Main.java
 *
 *  This module is the major module of the ftp server, with the main processing loop.
 *  Processes filename for path and data port number.
 *  Manages multiple connections from clients with Thread.
 */
package ftpserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    private int port;
    private ServerSocket socket;
    boolean serverRunning = true;

    public Main(String log, int p) {
        try {
            Log serLog = new Log(log);
            this.port = p;
            socket = new ServerSocket(port);
        } catch (IOException e) {
            System.out.println("Socket creation failed.");
            System.exit(-1);
        }
        System.out.println("SUCCESS: Server listening on port " + port);
        
        // Number of current connections to server
        int theads = 0;
        while (serverRunning) {
            try {
                Socket client = socket.accept();
                int dataPort = port + theads + 1;

                FTPserver server = new FTPserver(client, dataPort);
                System.out.println("New connection received");

                theads++;
                server.start();
            } catch (IOException e) {
                System.out.println("Failed to create connection");
                e.printStackTrace();
            }
        }
        try {
            socket.close();
            System.out.println("Stopping Server...");

        } catch (IOException e) {
            System.out.println("Stopping server failure");
            System.exit(-1);
        }
    }

    public static void main(String[] args) {
        new Main(args[0],Integer.parseInt(args[1]));
    }

}
