import java.io.IOException;
import java.nio.file.*;
import java.util.List;

public class Input {
    private String file;

    public Input(String filename) {
        this.file = filename;
    }

    public List<String> readAll() throws IOException {
        return Files.readAllLines(Paths.get(this.file));
    }

}
