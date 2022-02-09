/*
 *  CS472 – Homework #2
 *  Joseph Scherrik
 *  Main.java
 *
 *  This module is the major module of the ftp client, with the main processing loop.
 */
package ftpclient;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import static java.lang.System.exit;

public class Main {
    private static FTPclient ftpclient;

    /*
     *write2F(String t)
     * The main function to write to log.
     * t - String : Text to log.
     */
    public static void write2F(String t) {
        try {
            File f = new File("log.txt");
            LocalDateTime T = LocalDateTime.now();
            DateTimeFormatter tFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            String currentT = T.format(tFormat);

            if (f.createNewFile()) {
                FileWriter out = new FileWriter("log.txt");
                out.write(currentT + " " + t + "\n");
                out.close();
            } else {
                FileWriter out = new FileWriter("log.txt",true);
                out.write(currentT + " " + t + "\n");
                out.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
     *main()
     * The main programming loop of the FTP client.
     * It takes user input for commands and displays the results.
     */
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String message = "Welcome to FTPclient!!\nEnter IP and port number in format \"<ip> <port>\". \n";
        System.out.print(message);
        write2F(message);

        String[] infoArgs = in.nextLine().split(" ");
        if (infoArgs.length == 2) {
            write2F(infoArgs[0] + " " + infoArgs[1]);
            ftpclient = new FTPclient(infoArgs[0], Integer.parseInt(infoArgs[1]));
            message = "Made Connection on " + infoArgs[0] + " on port " + infoArgs[1]
                    + "\nPlease use \"<username> <password>\" to login.\n";
            System.out.print(message);
            write2F(message);
        } else {
            System.out.println("ERROR... Please Restart.");
            write2F("ERROR... Please Restart.");
            exit(0);
        }

        infoArgs = in.nextLine().split(" ");
        if (infoArgs.length == 2) {
            write2F(infoArgs[0] + " " + infoArgs[1]);
            ftpclient.connect(infoArgs[0], infoArgs[1]);
        } else {
            System.out.println("ERROR... Please Restart.");
            write2F("ERROR... Please Restart.");
            exit(0);
        }

        while(true) {
            System.out.print("ftp> ");
            String[] arguments = in.nextLine().toLowerCase().split(" ");
            write2F("ftp> " + arguments[0]);
            switch (arguments[0]) {
                case "cwd":
                    if (arguments.length != 2) {
                        System.out.println("Error: Invalid Command");
                        break;
                    }
                    String cwd = ftpclient.cwd(arguments[1]);
                    System.out.println(cwd);
                    write2F(cwd);
                    break;
                case "quit":
                    if (arguments.length != 1) {
                        System.out.println("Error: Invalid Command");
                        write2F("Error: Invalid Command");
                        break;
                    }
                    ftpclient.quit();
                    exit(0);
                case "port":
                    if (arguments.length != 3) {
                        System.out.println("Error: Invalid Command");
                        write2F("Error: Invalid Command");
                        break;
                    }
                    String port = ftpclient.port(arguments[1], arguments[2]);
                    System.out.println(port);
                    write2F(port);
                case "eprt":
                    if (arguments.length != 3) {
                        System.out.println("Error: Invalid Command");
                        write2F("Error: Invalid Command");
                        break;
                    }
                    String eprt = ftpclient.eprt(arguments[1], arguments[2]);
                    System.out.println(eprt);
                    write2F(eprt);
                    break;
                case "get":
                    if (arguments.length != 2) {
                        System.out.println("Error: Invalid Command");
                        write2F("Error: Invalid Command");
                        break;
                    }
                    String retr = ftpclient.retr(arguments[1]);
                    System.out.println(retr);
                    write2F(retr);
                    break;
                case "put":
                    if (arguments.length != 2) {
                        System.out.println("Error: Invalid Command");
                        write2F("Error: Invalid Command");
                        break;
                    }
                    String stor = ftpclient.stor(arguments[1]);
                    System.out.println(stor);
                    write2F(stor);
                    break;
                case "pwd":
                    if (arguments.length != 1) {
                        System.out.println("Error: Invalid Command");
                        write2F("Error: Invalid Command");
                        break;
                    }
                    String pwd = ftpclient.pwd();
                    System.out.println(pwd);
                    write2F(pwd);
                    break;
                case "syst":
                    if (arguments.length != 1) {
                        System.out.println("Error: Invalid Command");
                        write2F("Error: Invalid Command");
                        break;
                    }
                    String syst = ftpclient.syst();
                    System.out.println(syst);
                    write2F(syst);
                    break;
                case "dir":
                    if (arguments.length == 1) {
                        String list = ftpclient.list("");
                        System.out.println(list);
                        write2F(list);
                        break;
                    }
                    if (arguments.length == 2) {
                        String list = ftpclient.list(arguments[1]);
                        System.out.println(list);
                        write2F(list);
                        break;
                    }
                    System.out.println("Error: Invalid Command");
                    write2F("Error: Invalid Command");
                    break;
                case "help":
                    if (arguments.length == 1) {
                        String help = ftpclient.help("");
                        System.out.println(help);
                        write2F(help);
                        break;
                    } else if (arguments.length == 2) {
                        String help = ftpclient.help(arguments[1]);
                        System.out.println(help);
                        write2F(help);
                        break;
                    }
                    System.out.println("Error: Invalid Command");
                    write2F("Error: Invalid Command");
                    break;
                default:
                    System.out.println("Error: Invalid command");
                    write2F("Error: Invalid Command");
                    break;
            }
        }
    }
}
