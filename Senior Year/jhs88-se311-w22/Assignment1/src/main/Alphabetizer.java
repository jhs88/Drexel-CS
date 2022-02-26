import java.util.Collections;
import java.util.List;

public class Alphabetizer {
    private List<String> lines;

    public Alphabetizer(List<String> lines) {
        this.lines = sortAZ(lines);
    }

    public List<String> getLines() {
        return this.lines;
    }

    public List<String> sortAZ(List<String> lines){
        Collections.sort(lines);
        return lines;
    }

}
