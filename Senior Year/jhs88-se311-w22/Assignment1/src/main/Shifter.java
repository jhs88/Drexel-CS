import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Shifter {
    private List<String> lines;

    public Shifter(List<String> lines) {
        this.lines = shift(lines);
    }

    public List<String> getLines() {
        return this.lines;
    }

    public List<String> shift(List<String> lines){
        List<String> shifted = new LinkedList<>();
        for (String line: lines) {
            List<String> words = new ArrayList<>(Arrays.asList(line.split(" ")));
            int last = words.size() - 1;
            for (int i = 0; i < words.size() ; i++) {
                words.add(0,words.remove(last));
                shifted.add(arr2str(words));
            }
        }
        return shifted;
    }

//    public void rmStopWords(List<String> sw) {
//        int i = 0;
//        List<String> lines = this.lines;
//        for (String line: lines) {
//            List<String> words = new ArrayList<>(Arrays.asList(line.split(" ")));
//            for (String word : words) { System.out.print(word + " "); }
//            System.out.println(words.get(0));
//            for (String s: sw) {
//                if (words.get(0).equals("the")) {
//                    lines.remove(i);
//                }
//            }
//            System.out.println(i);
//            i++;
//        }
//    }

    public String arr2str(List<String> arr){
        StringBuilder b = new StringBuilder();
        for (String n : arr) {
            b.append(n);
            b.append(" ");
        }
        b.deleteCharAt(b.length() - 1);
        return b.toString();
    }

}
