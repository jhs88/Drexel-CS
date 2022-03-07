package server;

import log.Log;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static log.Log.log;

public class Main {
    public static void main(String[] args) {
        ServerSocket socket = null;
        Log log = new Log("server.log");
        int port = 400;
        boolean serverRunning = true;

        try {
            socket = new ServerSocket(port);
        } catch (IOException e) {
            log("Socket creation failed.");
            System.exit(-1);
        }
        log("SUCCESS: Server listening on port " + port);

        // Number of current connections to server
        int theads = 0;
        while (serverRunning) {
            try {
                Socket client = socket.accept();

                Server server = new Server(client, log);
                log("New connection received.");

                theads++;
                server.start();
            } catch (IOException e) {
                log("Error: Failed to create connection.");
                e.printStackTrace();
            }
        }
        try {
            socket.close();
            log("Stopping Server...");

        } catch (IOException e) {
            log("Error: Stopping server failure.");
            System.exit(-1);
        }
    }
}
