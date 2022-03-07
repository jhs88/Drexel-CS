package server;

import log.Log;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import static log.Log.log;

public class Server extends Thread {
    private String ip;
    private Socket socket;
    private Log log;
    private BufferedReader reader;
    private BufferedWriter writer;
    
    public Server(Socket client, Log log) {
        super();
        try {
            this.ip = InetAddress
                    .getLocalHost()
                    .getHostAddress();
        } catch (UnknownHostException e) {
            log("Error: Could not find host IP.");
            e.printStackTrace();
        }
        this.socket = client;
        this.log = log;
    }

    public void run() {
        try {
            this.reader = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            this.writer = new BufferedWriter(
                    new OutputStreamWriter(socket.getOutputStream()));
            sendResponse("200 Welcome to Server.");

            // Get new log line from client
            while (true) {
                try {
                    readLog();
                    sendResponse("200 Logged.");
                } catch (Exception e) {
                    sendResponse("404 Error.");
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
                writer.close();
                socket.close();
                log("Closed all socket connections...");
            } catch (IOException e) {
                log("Socket closure failure.");
                e.printStackTrace();
            }
        }
    }

    public void sendResponse(String response) {
        try {
            writer.write(response + "\r\n");
            writer.flush();
        } catch (IOException e){
            log("Error: Response sending failure.");
            e.printStackTrace();
        }
    }

    public void readLog() {
        try {
            log(reader.readLine());
        } catch (IOException e) {
            log("Error: Reading response failure.");
            e.printStackTrace();
        }
    }
}
