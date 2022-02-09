package ftpserver;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.*;

public class FTPserverTest {
    private Socket socket;
    private BufferedReader reader;
    private BufferedWriter writer;

    private ServerSocket data;
    private Socket dataConnection;
    private BufferedReader dReader;
    private BufferedWriter dWriter;

    @BeforeEach
    void init() {
        try {
            this.socket = new Socket("127.0.0.1", 2121);
            this.reader = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            this.writer = new BufferedWriter(
                    new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException e) {
            System.out.println("Error: Could not init socket and IO stream");
            e.printStackTrace();
        }
        String response = readResponse();
        System.out.println(response);
    }

    public void sendCommand(String command) {
        try {
            writer.write(command + "\r\n");
            writer.flush();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public String readResponse() {
        String response = null;
        try {
            response = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

//    @Test
//    void user(){
//        sendCommand("USER joe");
//        String response = readResponse();
//        sendCommand("QUIT");
//        assertTrue(response.startsWith("331 "));
//    }

    @Test
    void pass(){
        sendCommand("USER joe");
        readResponse();
        sendCommand("PASS passwords");
        String response = readResponse();
        sendCommand("QUIT");
        assertTrue(response.startsWith("230 "));
    }

    @Test
    void quit() {
        sendCommand("USER joe");
        readResponse();
        sendCommand("PASS passwords");
        readResponse();
        sendCommand("QUIT ");
        String response = readResponse();
        assertTrue(response.startsWith("221 "));
    }

    @Test
    void syst() {
        sendCommand("USER joe");
        readResponse();
        sendCommand("PASS passwords");
        readResponse();
        sendCommand("SYST ");
        String response = readResponse();
        sendCommand("QUIT");
        assertTrue(response.startsWith("215 "));
    }

    @Test
    void pwd() {
        sendCommand("USER joe");
        readResponse();
        sendCommand("PASS passwords");
        readResponse();
        sendCommand("PWD ");
        String response = readResponse();
        sendCommand("QUIT");
        assertTrue(response.startsWith("257 "));
    }

    @Test
    void cwd(){
        sendCommand("USER joe");
        readResponse();
        sendCommand("PASS passwords");
        readResponse();
        sendCommand("CWD src");
        String response = readResponse();
        sendCommand("QUIT");
        assertTrue(response.startsWith("250 "));
    }

    @Test
    void cdup() {
        sendCommand("USER joe");
        readResponse();
        sendCommand("PASS passwords");
        readResponse();
        sendCommand("CDUP ");
        String response = readResponse();
        sendCommand("QUIT");
        assertTrue(response.startsWith("250 "));
    }

    @Test
    void port() {
        sendCommand("USER joe");
        readResponse();
        sendCommand("PASS passwords");
        readResponse();
        try {
            this.data = new ServerSocket(0);
            int port = data.getLocalPort();
            int p1 = port / 256;
            int p2 = port % 256;
            sendCommand("PORT 127,0,0,1," + p1 + "," + p2);
            this.dataConnection = data.accept();
            this.dReader = new BufferedReader(
                    new InputStreamReader(dataConnection.getInputStream()));
            data.close();
            dataConnection.close();
            dReader.close();
            sendCommand("QUIT");
        } catch (IOException e) {
            e.printStackTrace();
        }
        String response = readResponse();
        assertTrue(response.startsWith("200 "));
    }

    @Test
    void retr() {
        sendCommand("USER joe");
        readResponse();
        sendCommand("PASS passwords");
        readResponse();
        try {
            this.data = new ServerSocket(0);
            int port = data.getLocalPort();
            int p1 = port / 256;
            int p2 = port % 256;
            sendCommand("PORT 127,0,0,1," + p1 + "," + p2);
            System.out.println(readResponse());
            this.dataConnection = data.accept();
            this.dReader = new BufferedReader(
                    new InputStreamReader(dataConnection.getInputStream()));

            sendCommand("RETR Text.txt");
            System.out.println(readResponse());

            File f = new File( System.getProperty("user.dir")
                    + "/TESTING/Text.txt");
            BufferedWriter fWriter = new BufferedWriter(
                    new FileWriter(f));
            String l;
            String lines = "";
            while ((l = dReader.readLine()) != null) {
                lines+=(l + "\n");
            }
            fWriter.write(lines);
            fWriter.flush();
            fWriter.close();
            data.close();
            dataConnection.close();
            dReader.close();
            sendCommand("QUIT");
        } catch (IOException e) {
            e.printStackTrace();
        }
        String init = "";
        String copy = "";
        try {
            BufferedReader first = new BufferedReader(
                    new FileReader(
                            System.getProperty("user.dir")
                                    + "/Text.txt"
                    ));
            init = first.readLine();
            BufferedReader last = new BufferedReader(
                    new FileReader(
                            System.getProperty("user.dir")
                                    + "/TESTING/Text.txt"
                    ));
            copy = last.readLine();
            first.close();
            last.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertEquals(init, copy);
    }

    @Test
    void stor() {
        sendCommand("USER joe");
        readResponse();
        sendCommand("PASS passwords");
        readResponse();
        try {
            this.data = new ServerSocket(0);
            int port = data.getLocalPort();
            int p1 = port / 256;
            int p2 = port % 256;
            sendCommand("PORT 127,0,0,1," + p1 + "," + p2);
            System.out.println(readResponse());
            this.dataConnection = data.accept();
            this.dWriter = new BufferedWriter(
                    new OutputStreamWriter(dataConnection.getOutputStream()));

            sendCommand("STOR Text.txt");
            System.out.println(readResponse());

            File f = new File( System.getProperty("user.dir")
                    + "/TESTING/Text.txt");
            BufferedReader br = new BufferedReader(
                    new FileReader(f));
            String l;
            String lines = "";
            while ((l = br.readLine()) != null) {
                lines += (l + "\n");
            }
            System.out.println(lines);
            dWriter.write(lines);
            dWriter.flush();
            dWriter.close();
            dataConnection.close();
            sendCommand("QUIT");
        } catch (IOException e) {
            e.printStackTrace();
        }
        String init = "";
        String copy = "";
        try {
            BufferedReader first = new BufferedReader(
                    new FileReader(
                            System.getProperty("user.dir")
                                    + "/TESTING/Text.txt"
                    ));
            init = first.readLine();
            BufferedReader last = new BufferedReader(
                    new FileReader(
                            System.getProperty("user.dir")
                                    + "/Text.txt"
                    ));
            copy = last.readLine();
            first.close();
            last.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertEquals(init, copy);
    }

    @Test
    void list() {
        sendCommand("USER joe");
        readResponse();
        sendCommand("PASS passwords");
        readResponse();
        String list = "";
        try {
            this.data = new ServerSocket(0);
            int port = data.getLocalPort();
            int p1 = port / 256;
            int p2 = port % 256;
            sendCommand("PORT 127,0,0,1," + p1 + "," + p2);
            System.out.println(readResponse());
            this.dataConnection = data.accept();
            this.dReader = new BufferedReader(
                    new InputStreamReader(dataConnection.getInputStream()));

            sendCommand("LIST");
            System.out.println(readResponse());
            list = this.dReader
                    .readLine()
                    .replace(" ", "\n");
            data.close();
            dataConnection.close();
            dReader.close();
            sendCommand("QUIT");
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertTrue(list.contains("src\n"));
    }


    // doesn't quit session
    @Test
    void pasv() {
        sendCommand("USER joe");
        readResponse();
        sendCommand("PASS passwords");
        readResponse();
        sendCommand("PASV");
        String response = readResponse();
        assertTrue(response.startsWith("227 "));
    }


}
