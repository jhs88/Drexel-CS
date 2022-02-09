package ftpclient;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FTPclientTest {
    String ip;
    int port;
    String username;
    String password;
    String logFile;
    FTPclient client;

    @BeforeEach
    void init() {
        this.username = "test";
        this.password = "test";
        this.logFile = "log.txt";
        this.client = new FTPclient(ip = "127.0.0.1", port = 2121);
//        this.username = "cs472";
//        this.password = "hw2ftp";
//        this.logFile = "log.txt";
//        this.client = new FTPclient(ip = "10.246.251.93", port = 21);
    }

    @Test
    void test_connect() {
        client.connect(username, password);
        assertTrue(client.isConnected());
    }

    @Test
    void test_quit() {
        client.connect(username, password);
        client.quit();
        assertTrue(client.isClosed());
    }

    @Test
    void test_pasv() {
        client.connect(username, password);
        String pasv = client.pasv();
        System.out.println(pasv);
        client.quit();
        assertTrue(pasv.contains("227 "));
    }

    @Test
    void test_port() {
        client.connect(username, password);
        String p = client.port(ip, "21000");
        System.out.println(p);
        client.quit();
        assertTrue(p.contains("200 "));
    }

    @Test
    void test_retr() {
        client.connect(username, password);
        client.cwd("/");
        String retr = client.retr("du.list");
        System.out.println(retr);
        client.quit();
        assertTrue(retr.contains("226 "));
    }

    @Test
    void test_stor() {
        client.connect(username, password);
        String stor = client.stor("kitty.jpg");
        System.out.println(stor);
        client.quit();
        assertTrue(stor.contains("226 "));
    }

    @Test
    void test_cwd() {
        client.connect(username, password);
        String cwd = client.cwd("/");
        System.out.println(cwd);
        client.quit();
        assertTrue(cwd.contains("250 "));
    }

    @Test
    void test_pwd() {
        client.connect(username, password);
        String wd = client.pwd();
        System.out.println(wd);
        client.quit();
        assertTrue(wd.contains("257 "));
    }

    @Test
    void test_syst() {
        client.connect(username, password);
        String syst = client.syst();
        System.out.println(syst);
        client.quit();
        assertEquals("215 UNIX Type: L8",syst);
    }

    @Test
    void test_list() {
        client.connect(username, password);
        String list = client.list("/");
        System.out.println(list);
        client.quit();
        assertTrue(list.contains("150 "));
    }

    @Test
    void test_help() {
        client.connect(username, password);
        String help = client.help("");
        System.out.println(help);
        client.quit();
        assertTrue(help.contains("214 "));
    }

}
