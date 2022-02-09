/*
 *  CS472 – Homework #2
 *  Joseph Scherreik
 *  FTPclient.java
 *
 *  This class is the main file for managing connections for the FTPclient.
 *  It allows a user to connect to a server, submit commands, and quit from the session.
 *  It will also handle logging to the programs UI and log file.
 */
package ftpclient;

import java.io.*;
import java.net.Socket;

import static ftpclient.Main.write2F;

public class FTPclient {
    private final String ip;
    private final int port;
    private Socket socket;
    private BufferedReader reader;
    private BufferedWriter writer;

    private String dataIP;
    private int dataPort;
    private Socket data;
    private BufferedInputStream dReader;
    private BufferedOutputStream dWriter;

    private boolean ipV6;
    private boolean isPORT;

    /*
     *FTPclient(String ip, int port)
     * Default constructor.
     *ip : String - IP address of server.
     *port : int - Open port of server location (usually 21 or 2121).
     */
    public FTPclient(String ip, int port) {
        this.ip = ip;
        this.port = port;
        this.ipV6 = false;
        this.isPORT = false;
    }

    // Only for testing verification.
    public boolean isConnected() {
        return socket.isConnected();
    }

    // Only for testing verification.
    public boolean isClosed() {
        return socket.isClosed();
    }

    /*
     *connect(String username, String password)
     * Create a new socket and buffered io to send/receive commands.
     * Create a new socket and buffered io to send/receive on data port.
     * Check for correct responses for server on login.
     * Use commands USER & PASS to create a valid session with the server.
     * Receive a response from the server and checks for the correct reply code.
     *username : String - Username of user on server.
     *password : String - Password of user on server.
     */
    public void connect(String username, String password) {
        try {
            this.socket = new Socket(ip, port);
            this.reader = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            this.writer = new BufferedWriter(
                    new OutputStreamWriter(socket.getOutputStream()));
            if (ip.contains(":")) this.ipV6 = true;
        } catch (IOException e) {
            System.out.println("Error: Could not init socket and IO stream");
            e.printStackTrace();
        }

        /*
         * 220 Service is ready for new user.
         */
        String response = readResponse();
        System.out.println(response);
        write2F(response); // Not good implementation but ran out of time.
        try {                   // This should be in the main method.
            if (response.startsWith("220 ")) {
                sendCommand("USER " + username );
            }
        } catch (Exception e) {
            System.out.println("Error: " + response);
            e.printStackTrace();
        }

        /*
         * 331 Username okay, need password.
         */
        response = readResponse();
        System.out.println(response);
        write2F(response);
        try {
            if (response.startsWith("331 ")) {
                sendCommand("PASS " + password);
            }
        } catch (Exception e) {
            System.out.println("Error: " + response);
            e.printStackTrace();
        }

        /*
         * 230 User logged in, proceed.
         */
        response = readResponse();
        System.out.println(response);
        write2F(response);
        try {
            if (response.startsWith("230 ")) {
                System.out.println("User Connected");
            }
        } catch (Exception e) {
            System.out.println("Error: " + response);
            e.printStackTrace();
        }
        /*
         * user now successfully logged in.
         */
    }

    /*
     *cwd(String dir)
     * Send changes current working directory command.
     * Check for incorrect reply code. Returns boolean on
     * success or failure.
     *dir : String - path to desired directory.
     *      Leave empty for current working directory.
     */
    public String cwd(String dir) {
        try {
            sendCommand("CWD " + dir);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String response = readResponse();
        if (!response.startsWith("250 ")) {
            response = "Error:\n\t" + response;
        }
        /*
         * 250 Requested file action okay, completed.
         */
        return response;
    }

    /*
     *quit()
     * Closes connection to server using QUIT command.
     * socket is closed to insure connection is terminated.
     */
    public void quit() {
        try {
            sendCommand("QUIT");
            socket.close();
            System.out.println("Disconnected");
            write2F("Disconnected");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     *pasv()
     * Set Passive Mode attributes: (h1,h2,h3,h4,p1,p2)
     * p1 and p2 need to be converted into proper decimal.
     * This is the data ip and port information: dataIP, dataPort.
     * Check for incorrect reply code and return proper response.
     */
    public String pasv() {
        try {
            sendCommand("PASV ");
        } catch (Exception e) {
            e.printStackTrace();
        }

        String response = readResponse();
        if (response.startsWith("500 ")) {
            response = "Error:\n\t" + response;
        } else if (response.startsWith("227 ")) {
            try {
                int start = response.indexOf("(");
                int end = response.indexOf(")", start + 1);
                String dParse = response.substring(start + 1, end);
                String[] dataInfo = dParse.split(",");

                this.dataIP = dataInfo[0] + "." + dataInfo[1] + "." + dataInfo[2] + "." + dataInfo[3];
                this.dataPort = Integer.parseInt(dataInfo[4]) * 256 + Integer.parseInt(dataInfo[5]);
                this.isPORT = false;
            } catch (Exception e) {
                System.out.println("Error: Can't parse PASV response");
                e.printStackTrace();
            }
        }
        /*
         * 227 Entering Passive Mode (h1,h2,h3,h4,p1,p2)
         */
        return response;
    }

    /*
     *epsv()
     * Set Extended Passive Mode attributes.
     * This is the data ip and port information: dataIP, dataPort.
     * Parse the port out of the response.
     * Check for incorrect reply code and return proper response.
     */
    public String epsv() {
        try {
            sendCommand("EPSV ");
        } catch (Exception e) {
            e.printStackTrace();
        }
        String response = readResponse();
        if (response.startsWith("500 ")) {
            response = "Error:\n\t" + response;
        } else if (response.startsWith("229 ")) {
            int start = response.indexOf("(");
            int end = response.indexOf(")", start + 4);
            String port = response.substring(start + 4, end);

            this.dataIP = this.ip;
            this.dataPort = Integer.parseInt(port);
            this.isPORT = false;
        }
        /*
         * 229 Entering Extended Passive Mode (|||port|)
         */
        return response;
    }

    /* ~~~BROKEN~~~
     *port(String address, String port)
     * Send server port command.
     * Parse ip and port to correct format: (h1,h2,h3,h4,p1,p2).
     * Convert port into proper hex.
     * Check for incorrect reply code and return proper response.
     * Sets dataIP and dataPort vars if correct response.
     *address : String - IP address.
     *port : String - data port.
     */
    public String port(String ip, String port) {
        String[] parse = ip.split("\\.");
        String p = parseHex(port);
//        System.out.println("PORT " + parse[0] + "," + parse[1]
//                + "," + parse[2] + "," + parse[3] + "," + p);
        try {
            sendCommand("PORT " + parse[0] + "," + parse[1]
                    + "," + parse[2] + "," + parse[3] + "," + p);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String response = readResponse();
        if (!response.startsWith("200 ")) {
            response = "Error:\n\t" + response;
        }
        isPORT = true;
        dataIP = ip;
        dataPort = Integer.parseInt(port);
        /*
         * 200 OK.
         */
        return response;
    }

    /*
     *eprt(String address, String port)
     * Send server extended port command.
     * Send different command, dependent on IPv type.
     * Check for incorrect reply code and return proper response.
     * Set dataIP and dataPort vars if correct response.
     *address : String - IP address.
     *port : String - data port.
     */
    public String eprt(String address, String port) {
        try {
            if (ipV6) {
                sendCommand("EPRT |2|" + address + "|" + port + "|");
            } else {
                sendCommand("EPRT |1|" + address + "|" + port + "|");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        String response = readResponse();
        if (!response.startsWith("522 ")) {
            response = "Error:\n\t" + response;
        }
        isPORT = true;
        dataIP = ip;
        dataPort = Integer.parseInt(port);
        /*
         * 200 OK
         */
        return response;
    }

    /*
     *retr(String filename)
     * Receive specified file from server
     * Open up data socket and write file to local file system.
     * Check for incorrect reply code and return proper response.
     *filename : String - name of file to be received to server.
     */
    public String retr(String filename) {
        try {
            if(!isPORT && !ipV6) {
                pasv();
            } else if(!isPORT && ipV6) {
                epsv();
            }
            sendCommand("RETR " + filename);
            this.data = new Socket(dataIP, dataPort);
            this.dReader = new BufferedInputStream(data.getInputStream());
            this.dWriter = new BufferedOutputStream(new FileOutputStream(filename));
        } catch (Exception e) {
            e.printStackTrace();
        }
        String response = readResponse();
        FileOutputStream writeF = null;
        if (!response.startsWith("150 ")) {
            response = "Error:\n\t" + response;
        } else {
            try {
                byte[] buffer = new byte[4096];
                int bytesRead = 0;
                while ((bytesRead = dReader.read(buffer)) != -1) {
                    dWriter.write(buffer, 0, bytesRead);
                }
                dWriter.flush();
                dWriter.close();
                dReader.close();
                data.close();

                response = readResponse();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        /*
         * 266 Closing data connection. Success.
         */
        return response;
    }

    /*
     *stor(String filename)
     * Sends server file to store.
     * Open up data socket and write file to server.
     * Check for incorrect reply code and return proper response.
     *filename : String - name of file to be sent to server.
     */
    public String stor(String filename) {
        try {
            if(!isPORT && !ipV6) {
                pasv();
            } else if(!isPORT && ipV6) {
                epsv();
            }
            sendCommand("STOR " + filename);
            this.data = new Socket(dataIP, dataPort);
            this.dReader = new BufferedInputStream(new FileInputStream(filename));
            this.dWriter = new BufferedOutputStream(data.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
        String response = readResponse();
        /*
         * 150 Okay to send data.
         */
        if (!response.startsWith("150 ")) {
            response = "Error:\n\t" + response;
        } else {
            try {
                byte[] buffer = new byte[4096];
                int bytesRead = 0;
                while ((bytesRead = dReader.read(buffer)) != -1) {
                    dWriter.write(buffer, 0, bytesRead);
                }
                dWriter.flush();
                dWriter.close();
                dReader.close();
                data.close();
                response = readResponse();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        /*
         * 226 Transfer complete.
         */
        return response;
    }

    /*
     *pwd()
     * Return path current working directory.
     * Check for incorrect reply code and return proper response.
     */
    public String pwd() {
        try {
            sendCommand("PWD ");
        } catch (Exception e) {
            e.printStackTrace();
        }
        String response = readResponse();
        if (!response.startsWith("257 ")) {
            response = "Error:\n\t" + response;
        }
        /*
         * 257 <directory> is the current directory
         */
        return response;
    }

    /*
     *syst()
     * Return type of OS of the server's system.
     * Check for incorrect reply code and return proper response.
     */
    public String syst() {
        try {
            sendCommand("SYST ");
        } catch (Exception e) {
            e.printStackTrace();
        }
        String response = readResponse();
        if (!response.startsWith("215 ")) {
            response = "Error:\n\t" + response;
        }
        /*
         * 215 <os> system type.
         */
        return response;
    }

    /*
     *list(String dir)
     * Return list of files in the directory specified.
     * This list is not a java.util.List, but a String.
     * Check for incorrect reply code and return proper response.
     *dir : String - path to desired directory.
     *      Leave empty for current working directory.
     */
    public String list(String dir) {
        BufferedReader input = null;
        try {
            if(!isPORT && !ipV6) {
                pasv();
            } else if(!isPORT && ipV6) {
                epsv();
            }
            sendCommand("LIST " + dir);
            this.data = new Socket(dataIP, dataPort);
            input = new BufferedReader(new InputStreamReader(data.getInputStream()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        String response = readResponse();
        String list = null;
        if (!response.startsWith("150 ")) {
            response = "Error:\n\t" + response;
        } else if (input != null) {
            try {
                int bytes;
                while((bytes = input.read()) != -1) {
                    list+= (char) bytes;
                }
                data.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        /*
         * 150 File status okay; about to open data connection.
         */
        return response + "\n\t" + list;
    }

    /*
     *help(String command)
     * Request help information for specified command.
     * Check for incorrect reply code and return proper response
     *command : String - desired command.
     *          Leave empty for list of all possible commands.
     */
    public String help(String command) {
        try {
            sendCommand("HELP " + command);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String response = readResponse();
        try {
            response += "\n\t" + reader.readLine();
            response += "\n\t" + reader.readLine();
            response += "\n\t" + reader.readLine();
            response += "\n\t" + reader.readLine();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!response.startsWith("214 ")) {
            response = "Error:\n\t" + response;
        }
        /*
         * 214 <help message>.
         */
        return response;
    }

    /*
     *sendCommand(String command)
     * Send a raw command in bytes to the server using the BufferedWriter.
     * Ends line with proper Tenet carriage return and new line.
     *command : String - FTP command to send
     */
    public void sendCommand(String command) {
        try {
            writer.write(command + "\r\n");
            writer.flush();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    /*
     *readResponse()
     * Read response from server.
     * Check proper response and return it.
     */
    public String readResponse() {
        String response = null;
        try {
            response = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    /*
     *parseHex(String port)
     * Convert port to hex.
     * Create p1 & p2.
     * Return as string for end of port command.
     * port : String - port to converted.
     */
    public String parseHex(String port) {
        String hexP = Integer.toHexString(Integer.parseInt(port)).toUpperCase();
        String p1 = null;
        String p2 = null;
        if (hexP.length() <= 2) {
            p1 = "0";
            p2 = hexP;
        } else if (hexP.length() == 3) {
            hexP = "0" + hexP;
            p1 = hexP.substring(1,2);
            p2 = hexP.substring(2,4);
        } else {
            p1 = hexP.substring(0,2);
            p2 = hexP.substring(2,4);
        }
        if (p2.contains("0")) {
            p2 = p2.substring(1,2);
        }

        return p1 + "," + p2;
    }

}
