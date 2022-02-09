/*
 *  CS472 – Homework #3
 *  Joseph Scherreik
 *  FTPserver.java
 *
 *  This class is the main file for managing connections for the FTPserver.
 *  It runs an FTP server to receive commands to be processed.
 *  The server will then process the commands and return a response to the FTP client.
 *  It will also handle logging to the programs UI and log file.
 */
package ftpserver;

import static ftpserver.Log.write2F;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class FTPserver extends Thread {
    private String ip;
    private File accounts;

    // Control port
    private Socket socket;
    private BufferedReader reader;
    private BufferedWriter writer;

    // Data Port
    private int dataPort;
    private ServerSocket data;
    private Socket dataConnection;
    private BufferedReader dReader;
    private BufferedWriter dWriter;

    private String currUser;
    private String state;
    private String cwd;

    private boolean ipV6 = false;
    private boolean quit = false;
    private boolean port = false;
    private boolean pasv = true;

    /*
     *FTPserver(String ip, int port)
     * Default constructor.
     *port : int - Open port of server location (usually 21 or 2121).
     *client: Socket - socket created from connection from client.
     *config: config file class handler.
     */
    public FTPserver(Socket client, int port, Config config) {
        super();
        try {
            this.port = config.getPort();
            this.pasv = config.getPasv();
        } catch (Exception e) {
            System.out.println("Error: Incorrect Config");
            write2F("Error: Incorrect Config");
            e.printStackTrace();
        }
        try {
            this.ip = InetAddress
                    .getLocalHost()
                    .getHostAddress();
            if (ip.contains(":")) this.ipV6 = true;
        } catch (UnknownHostException e) {
            System.out.println("Error: Could not find IP");
            write2F("Error: Could not find IP");
            e.printStackTrace();
        }
        try {
            this.accounts = new File("accounts");
        } catch (Exception e) {
            System.out.println("Error: Could not find accounts");
            write2F("Error: Could not find accounts");
            e.printStackTrace();
        }
        this.socket = client;
        this.dataPort = port;
        this.cwd = System.getProperty("user.dir");
        this.state = "IDLE";
        write2F("State: IDLE");
    }

    /*
     * run()
     * Required function for Thread.
     * Reads IO streams from control socket
     * & sends a welcome message.
     * Creates command loop that reads commands from input stream.
     * Closes connections when user disconnects.
     */
    public void run() {
        try {
            this.reader = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            this.writer = new BufferedWriter(
                    new OutputStreamWriter(socket.getOutputStream()));
            sendResponse("220 Welcome to FTPServer");

            // Get new command from client
            while (!quit) {
                readCommand(reader.readLine());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
                writer.close();
                socket.close();
                System.out.println("Closed all socket connections...");
                write2F("Closed all socket connections...");
            } catch (IOException e) {
                System.out.println("Socket closure failure");
                write2F("Socket closure failure");
                e.printStackTrace();
            }
        }
    }

    /*
     * user(String username)
     * Logic for USER command.
     * Checks to see if username is in accounts file.
     * Then sets the currUser, state and sends OK response.
     * Returns error on failure.
     *username: String - username send by client
     */
    public void user(String username) {
        if (isUser(username)) {
            this.currUser = username;
            sendResponse("331 User name okay, need password");
            this.state = "WAITING4PASS";
            write2F("State: WATING4PASS");
        } else if (this.state.equals("LOGGEDIN")) {
            sendResponse("530 User already logged in");
        } else {
            sendResponse("530 Not logged in");
            this.state = "IDLE";
            write2F("STATE: IDLE");
        }
    }

    /*
     * pass(String username)
     * Logic for PASS command.
     * Checks to see if password & username is in accounts file.
     * Also checks for proper state after USER command.
     * Then sets the currUser, state and sends OK response.
     * Returns error on failure.
     *username: String - username send by client
     */
    public void pass(String password) {
        if (this.state.equals("WAITING4PASS") && isPassword(password)) {
            this.state = "LOGGEDIN";
            write2F("State: LOGGEDIN");
            sendResponse("230 Welcome your logged in");
        } else if (this.state.equals("LOGGEDIN")) {
            sendResponse("530 User already logged in");
        } else {
            sendResponse("530 Not logged in");
            this.state = "IDLE";
            write2F("State: IDLE");
        }
    }

    /*
     * cwd(String dir)
     * Logic for CWD command.
     * Checks to make sure the user is
     * not trying to cd up or to the current directory.
     * Then checks to see if directory requested exists and
     * changes cwd to new directory.
     * Returns error on failure and keeps original value.
     *dir: String - requested directory to change to
     */
    public void cwd(String dir) {
        String filename = this.cwd;
        if (dir.equals("..")) {
            int location = filename.lastIndexOf("/");
            if (location > 0) {
                filename = filename.substring(0, location);
            }
        } else if (!dir.equals(".")) {
            filename = filename + "/" + dir;
        }

        // check if file exists and if it is a directory
        File f = new File(filename);
        if (f.exists() && f.isDirectory()) {
            this.cwd = filename;
            sendResponse("250 The current directory changed to " + this.cwd);
        } else {
            sendResponse("550 File unavailable");
        }
    }

    /*
     * quit()
     * Logic for QUIT command.
     * Sends response to server then changes
     * quit boolean to break command loop.
     */
    public void quit() {
        sendResponse("221 Closing connection");
        this.state = "QUIT";
        quit = true;
    }

    /*
     * pasv()
     * Logic for PASV command.
     * Splits up server's current IP and
     * converts port into p1,p2 format.
     * Then sends response to server letting it know
     * it will attempt to open a data port.
     * Then sets the state to LISTEN.
     * Returns error on failure.
     */
    public void pasv() {
        String myIp[] = this.ip.split("\\.");
        int p1 = dataPort / 256;
        int p2 = dataPort % 256;

        sendResponse("227 Entering Passive Mode (" + myIp[0] + "," + myIp[1]
                + "," + myIp[2] + "," + myIp[3] + "," + p1 + "," + p2 + ")");
        try {
            data = new ServerSocket(dataPort);
            this.state = "LISTENING";
            System.out.println("SUCCESS: Passive Data connection running");
            write2F("SUCCESS: Passive Data connection running");
            write2F("State: LISTENING");
            dataConnection = data.accept();
            dWriter = new BufferedWriter(
                    new OutputStreamWriter(dataConnection.getOutputStream()));
        } catch (IOException e) {
            System.out.println("Data connection failure");
            write2F("Data connection failure");
            this.state = "IDLE";
            write2F("State: IDLE");
            e.printStackTrace();
        }
    }

    /*
     * epsv()
     * Logic for EPSV command.
     * Parses data port into EPSV format.
     * Then sends response to server letting it know
     * it will attempt to open a data port.
     * Then sets the state to LISTEN.
     * Returns error on failure.
     */
    public void epsv() {
        sendResponse("229 Entering Extended Passive Mode (|||" + dataPort + "|)");
        try {
            data = new ServerSocket(dataPort);
            dataConnection = data.accept();
            dWriter = new BufferedWriter(
                    new OutputStreamWriter(dataConnection.getOutputStream()));
            this.state = "LISTENING";
            System.out.println("SUCCESS: Passive Data connection running");
            write2F("SUCCESS: Passive Data connection running");
            write2F("State: LISTENING");
        } catch (IOException e) {
            System.out.println("Data connection failure");
            write2F("Data connection failure");
            this.state = "IDLE";
            write2F("State: IDLE");
            e.printStackTrace();
        }
    }

    /*
     * port(String args)
     * Logic for PORT command.
     * Parses out the IP & port from the client's request.
     * Then opens an active connection and sends an OK response.
     * Active connection logic for PORT command.
     * Opens up the data connection socket on specified
     * ip and port from PORT command.
     * Returns error on failure.
     */
    public void port(String args) {
        String[] parse = args.split(",");
        String ip = parse[0] + "." + parse[1] + "." + parse[2] + "." + parse[3];
        int p = Integer.parseInt(parse[4]) * 256 + Integer.parseInt(parse[5]);
        try {
            this.dataConnection = new Socket(ip, p);
            this.dWriter = new BufferedWriter(
                    new OutputStreamWriter(dataConnection.getOutputStream()));
            this.state = "LISTENING";
            System.out.println("SUCCESS: Active Data connection running");
            write2F("SUCCESS: Active Data connection running");
            write2F("State: LISTENING");
        } catch (IOException e) {
            System.out.println("Could not connect to client data socket");
            write2F("Could not connect to client data socket");
            this.state = "IDLE";
            write2F("State: IDLE");
            e.printStackTrace();
        }
        sendResponse("200 Command OK");
    }

    /*
     * eprt(String args)
     * Logic for EPRT command, adds IPv6 support.
     * Parses out the IP & port from the client's request.
     * Then opens an active connection and sends an OK response.
     * Active connection logic for PORT command.
     * Opens up the data connection socket on specified
     * ip and port from PORT command.
     * Returns error on failure.
     */
    public void eprt(String args) {
        String[] parse = args.split("\\|");
        if (parse[1].equals("2")) {
            ipV6 = true;
        } else if (parse[1].equals("1")) {
            ipV6 = false;
        } else {
            write2F("IP version not known");
            throw new IllegalArgumentException("IP version not known");
        }
        int p = Integer.parseInt(parse[3]);
        try {
            this.dataConnection = new Socket("127.0.0.1", p);
            this.dWriter = new BufferedWriter(
                    new OutputStreamWriter(dataConnection.getOutputStream()));
            this.state = "LISTENING";
            System.out.println("SUCCESS: Active Data connection running");
            write2F("SUCCESS: Active Data connection running");
            write2F("State: LISTENING");
        } catch (IOException e) {
            System.out.println("Could not connect to client data socket");
            write2F("Could not connect to client data socket");
            this.state = "IDLE";
            write2F("State: IDLE");
            e.printStackTrace();
        }
        sendResponse("200 Command OK");
    }

    /*
     * retr(String filename)
     * Logic for RETR command.
     * Opens file to send to client.
     * Sends response over control port to confirm transfer,
     * Sends file over data port then closes connection.
     *filename: String - name of file to be transferred
     */
    public void retr(String filename) {
        File f = new File(this.cwd + "/" + filename);
        if (f.exists()) {
            sendResponse("150 File status okay; about to open data connection");
            try {
                this.state = "READING";
                write2F("State: READING");
                BufferedReader br = new BufferedReader(
                        new FileReader(f));
                String l;
                String lines = "";
                while ((l = br.readLine()) != null) {
                    lines+=(l + "\n");
                }
                System.out.println(lines);
                this.dWriter.write(lines);
                this.dWriter.flush();
                this.dWriter.close();
                this.dataConnection.close();
                System.out.println("SUCCESS: File Retrieved");
                write2F("SUCCESS: File Retrieved");
                this.state = "IDLE";
                write2F("State: IDLE");
            } catch (Exception e) {
                System.out.println("Could not connect to client data socket");
                write2F("Could not connect to client data socket");
                this.state = "IDLE";
                write2F("State: IDLE");
                e.printStackTrace();
            }
        } else {
            sendResponse("450 Requested file action not taken, can't find file");
        }
    }

    /*
     * stor(String filename)
     * Logic for STOR command.
     * Opens mew file to server.
     * Sends response over control port to confirm transfer,
     * Recieves file over data port from client then closes connection.
     *filename: String - name of file to be transferred
     */
    public void stor(String filename) {
        File f = new File(this.cwd + "/" + filename);
        sendResponse("150 File status okay; about to open data connection");
        try {
            this.dReader = new BufferedReader(
                    new InputStreamReader(dataConnection.getInputStream()));
            this.state = "WRITING";
            write2F("State: WRITING");
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
            dataConnection.close();
            dReader.close();
        } catch (Exception e) {
            sendResponse("450 Requested file action not taken, can't find file");
            System.out.println("Could not connect to client data socket");
            write2F("Could not connect to client data socket");
            this.state = "IDLE";
            write2F("State: IDLE");
            e.printStackTrace();
        }
    }

    /*
     * pwd()
     * Send response to client of path of current working directory.
     */
    public void pwd() {
        sendResponse("257 \"" + this.cwd + "\"");
        this.state = "IDLE";
        write2F("State: IDLE");
    }

    /*
     * syst()
     * Send response to client of path of current working directory.
     */
    public void syst() {
        sendResponse("215 FTP Server for CS472");
        this.state = "IDLE";
        write2F("State: IDLE");
    }

    /*
     * list(String args)
     * Parse requested directory. If it is blank select current
     * working directory. Enter PASV mode to establish data port
     * connection. Send parsed directory over data port and change
     * state to writing and then back to idle after complete.
     */
    public void list(String args) {
        File dir = null;
        String resp = "";
        if(args.equals("\r\n")) {
            dir = new File(this.cwd);
        } else {
            dir = new File(args);
        }
        if(dir.isDirectory()) {
            File[] filesList = dir.listFiles();
            for (File f : filesList) {
                resp+=(f.getName()) + " ";
            }
            sendResponse("150 File status okay; about to open data connection");
        } else {
            sendResponse("450 Requested file action not taken");
        }
        try {
            this.state = "WRITING";
            write2F("State: WRITING");
            dWriter.write(resp);
            dWriter.flush();
            dataConnection.close();
            this.state = "IDlE";
            write2F("State: IDLE");
        } catch (IOException e) {
            System.out.println("Can't write to data socket");
            write2F("Can't write to data socket");
            this.state = "IDlE";
            write2F("State: IDLE");
            e.printStackTrace();
        }
    }

    /*
     * cdup()
     * cd's up a directory.
     */
    public void cdup() {
        String filename = this.cwd;
        int location = filename.lastIndexOf("/");
        if (location > 0) {
            filename = filename.substring(0, location);
            this.cwd = filename;
            sendResponse("250 The current directory changed to " + this.cwd);
            this.state = "IDLE";
            write2F("State: IDLE");
        } else {
            sendResponse("550 File unavailable");
            this.state = "IDLE";
            write2F("State: IDLE");
        }
    }

    /*
     *sendResponse(String response)
     * Send a raw command in bytes to the client using the BufferedWriter.
     * Ends line with proper Tenet carriage return and new line.
     *response : String - FTP response code & info to send
     */
    public void sendResponse(String response) {
        try {
            writer.write(response + "\r\n");
            writer.flush();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    /*
     *readCommand(String command)
     * Parse and read command from client.
     * Check proper command and execute.
     * Handles disabled PORT & PASV logic
     * and sends response if disabled.
     *command: String - FTP command sent by client
     */
    public void readCommand(String command) {
        write2F(command);
        String[] parse = command.split(" ");
        command = parse[0];
        String args = "\r\n";
        if (parse.length > 1) args = parse[1];
        System.out.println(command + " " +  args);
        switch (command) {
            case "USER":
                user(args);
                break;
            case "PASS":
                pass(args);
                break;
            case "CWD":
                cwd(args);
                break;
            case "QUIT":
                quit();
                break;
            case "PASV":
                if (!this.pasv) {
                    sendResponse("451 PASV command disabled");
                    break;
                }
                pasv();
                break;
            case "EPSV":
                if (!this.port) {
                    sendResponse("451 EPSV command disabled");
                    break;
                }
                epsv();
                break;
            case "PORT":
                if (!this.port) {
                    sendResponse("451 PORT command disabled");
                    break;
                }
                port(args);
                break;
            case "EPRT":
                if (!this.port) {
                    sendResponse("451 EPRT command disabled");
                    break;
                }
                eprt(args);
                break;
            case "RETR":
                retr(args);
                break;
            case "STOR":
                stor(args);
                break;
            case "PWD":
                pwd();
                break;
            case "SYST":
                syst();
                break;
            case "LIST":
                list(args);
                break;
            case "CDUP":
                cdup();
                break;
            default:
                sendResponse("501 Unknown command");
                break;
        }
    }

    /*
     * isUser(String username)
     *
     * Checks to see if username is in accounts list.
     * Returns true if so.
     *username: String - username requested
     */
    private boolean isUser(String username) {
        Scanner accounts = null;
        try {
            accounts = new Scanner(this.accounts);
        } catch (FileNotFoundException e) {
            System.out.println("Error: Could not find account file");
            e.printStackTrace();
        }
        while(accounts.hasNextLine()) {
            if(accounts.nextLine().contains(username)) {
                return true;
            }
        }
        return false;
    }

    /*
     * isPassword(String password)
     *
     * Checks to see if username & password is in accounts list.
     * Returns true if so.
     *password: String - password requested
     */
    private boolean isPassword(String password) {
        Scanner accounts = null;
        try {
            accounts = new Scanner(this.accounts);
        } catch (FileNotFoundException e) {
            System.out.println("Error: Could not find account file");
            e.printStackTrace();
        }
        while (accounts.hasNextLine()) {
            String line = accounts.nextLine();
            if (line.contains(this.currUser)
                    && line.contains(password)) {
                return true;
            }
        }
        return false;
    }

}
