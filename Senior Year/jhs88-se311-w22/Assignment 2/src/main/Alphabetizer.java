package main;

import java.io.IOException;
import java.util.Arrays;

// Sorts Input by alphabetical order
public class Alphabetizer extends Filter {

    public Alphabetizer(Pipe sInput, Pipe sOutput){
        super(sInput, sOutput);
    }

    @Override
    public void run() {
        System.out.println("Alphabetizing...");
        try {
            String[] shiftedLines = sInput.read().trim().split("\\n");
            for(int i = 0; i < shiftedLines.length; ++i) {
                String s = shiftedLines[i];
                s = s.replaceAll("[\r\n]+$", "");
                shiftedLines[i] = s;
            }

            Arrays.sort(shiftedLines, String.CASE_INSENSITIVE_ORDER);
            StringBuilder sb = new StringBuilder();
            for(String str : shiftedLines) {
                sb.append(str).append("\n");
            }
            sOutput.write(sb.toString());
            System.out.println("Sorted.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
