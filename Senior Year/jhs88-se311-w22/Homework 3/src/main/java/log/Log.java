package log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Log {
    private static File log;

    public Log(String path) {
        log = new File(path);
    }

    public static void log(String line) {
        try {
            LocalDateTime T = LocalDateTime.now();
            DateTimeFormatter tFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            String currentT = T.format(tFormat);
            System.out.println(line);
            if (log.createNewFile()) {
                FileWriter out = new FileWriter(log);
                out.write(currentT + " " + line + "\n");
                out.close();
            } else {
                FileWriter out = new FileWriter(log,true);
                out.write(currentT + " " + line + "\n");
                out.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
