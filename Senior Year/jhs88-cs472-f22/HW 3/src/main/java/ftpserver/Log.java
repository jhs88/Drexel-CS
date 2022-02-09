
/*  CS472 – Homework #3
 *  Joseph Scherreik
 *
 *  Log.java
 *
 * Main logging file. Handles setting and
 * writing to the log file.
 */
package ftpserver;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Log {
    private static String path;

    public Log(String p) {
        path = p;
    }

    /*
     * write2F(String t)
     * Writes text to log file.
     * Checks to see if log exists first.
     *t: String - text to write to file.
     */
    public static void write2F(String t) {
        try {
            File f = new File(path);
            LocalDateTime T = LocalDateTime.now();
            DateTimeFormatter tFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            String currentT = T.format(tFormat);

            if (f.createNewFile()) {
                FileWriter out = new FileWriter(f);
                out.write(currentT + " " + t + "\n");
                out.close();
            } else {
                FileWriter out = new FileWriter(f,true);
                out.write(currentT + " " + t + "\n");
                out.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
