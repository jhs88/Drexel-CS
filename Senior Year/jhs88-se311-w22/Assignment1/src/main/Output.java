import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class Output {
    private PrintWriter writer;

    public Output(String filename) throws IOException {
        this.writer = new PrintWriter(filename);
    }

    public void writeLines(List<String> lines) {
        PrintWriter output = this.writer;
        for (String line : lines) {
            output.println(line);
        }
    }

    public void close() {
        this.writer.close();
    }

}
