/*
 *  CS472 – Homework #4
 *  Joseph Scherreik
 *  Config.java
 *
 *  This module is for handling config file logic.
 *  Processes filename and config properties.
 *  Eventually is passed to the server to activate/deactivate PORT & PASV.
 */
package ftpserver;

import java.io.BufferedReader;
import java.io.FileReader;

import static ftpserver.Log.write2F;

public class Config {
    private boolean port;
    private boolean pasv;

    public Config() {
        try {
            loadConfig();
        } catch (Exception e) {
            System.out.println("Config file error");
            write2F("Config file error");
            e.printStackTrace();
            System.exit(-1);
        }
    }

    public boolean getPort() {
        return port;
    }

    public boolean getPasv() {
        return pasv;
    }

    private void loadConfig() throws Exception {
        String line;
        BufferedReader configFile = new BufferedReader(
                new FileReader("ftpserver.conf"));
        while ((line = configFile.readLine()) != null) {
            if (!line.startsWith("#")) {
                if (line.startsWith("port_mode")) {
                    if (line.contains("NO")) port = false;
                    else if (line.contains("YES")) port = true;
                    else throw new IllegalArgumentException("Incorrect PORT config");
                } else if (line.startsWith("pasv_mode")) {
                    if (line.contains("NO")) pasv = false;
                    else if (line.contains("YES")) pasv = true;
                    else throw new IllegalArgumentException("Incorrect PASV config");
                } else {
                    throw new Exception("Incorrect config formatting");
                }
            }
        }
        if (!port && !pasv) throw new Exception("Both configs cannot be false");
    }

}
