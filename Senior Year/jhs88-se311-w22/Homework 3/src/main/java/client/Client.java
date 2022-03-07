package client;

import log.Log;

import java.io.*;
import java.net.Socket;

import static log.Log.log;

public class Client {
    private final String ip;
    private final int port;
    private Socket socket;
    private BufferedReader reader;
    private BufferedWriter writer;

    public Client() {
        this.ip = "127.0.0.1";
        this.port = 400;
        Log log = new Log("client.log");
    }

    public void connect() {
        log("Starting server connection.");
        try {
            socket = new Socket(ip, port);
            reader = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            writer = new BufferedWriter(
                    new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException e) {
            log("Error: Could not init socket and IO stream.");
            e.printStackTrace();
        }

        String response = readResponse();
        System.out.println(response);
        log(response);
        try {
            if (response.startsWith("200 ")) {
                log("Connected.");
            }
        } catch (Exception e) {
            log("Error: " + response);
            e.printStackTrace();
        }

    }

    public void close() {
        try {
            log("Closing...");
            reader.close();
            writer.close();
            socket.close();
            log("BYE BYE.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendLog(String line) {
        try {
            writer.write(line + "\r\n");
            writer.flush();
            log("Calculation sent.");
        } catch (IOException e){
            log("Error: Calculation sending failure.");
            e.printStackTrace();
        }
    }

    public String readResponse() {
        String response = null;
        try {
            log("Reading Response... ");
            response = reader.readLine();
            log("     Respose: " + response);
        } catch (IOException e) {
            log("Error: Reading response failure.");
            e.printStackTrace();
        }
        return response;
    }
}
