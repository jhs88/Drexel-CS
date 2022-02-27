package main;

import java.io.IOException;
import java.util.ArrayList;

// Shifts Input from Alphabetizer to Output & supporting classes.
public class CircularShifter extends Filter {
    public CircularShifter(Pipe sInput, Pipe sOutput){
        super(sInput, sOutput);
    }

    private String[] shift(String[] line) {
        String[] shiftLine = line.clone();

        String shiftWord = shiftLine[0];
        for(int i = 0; i < shiftLine.length - 1; ++i)
            shiftLine[i] = shiftLine[i + 1];

        shiftLine[shiftLine.length - 1] = shiftWord;
        return shiftLine;
    }

    private String[] shiftTimes(String[] line, int times) {
        String[] shiftedTimes = line.clone();
        while(times > 0) {
            shiftedTimes = shift(shiftedTimes);
            --times;
        }
        return shiftedTimes;
    }

    private ArrayList<String[]> shiftLine(String[] line){
        ArrayList<String[]> shiftedLine = new ArrayList<>();
        for(int i = 0; i < line.length; ++i) {
            String[] shifted = shiftTimes(line, i);
            shiftedLine.add(shifted);
        }
        return shiftedLine;
    }

    private ArrayList<String[]> shiftLines(String[] lines){
        // Split lines by words
        ArrayList<String[]> linesByWords = new ArrayList<>();
        for (String s : lines) {
            String[] line = s.split(" ");
            linesByWords.add(line);
        }

        // Shift Lines
        ArrayList<String[]> shiftedLines = new ArrayList<>();
        for(String[] line : linesByWords) {
            ArrayList<String[]> shiftedLine = shiftLine(line);
            shiftedLines.addAll(shiftedLine);
        }
        return shiftedLines;
    }

    @Override
    public void run() {
        System.out.println("Shifting...");
        try {
            String[] lines = sInput.read().trim().split("\\n");
            for(int i = 0; i < lines.length; ++i) {
                String s = lines[i];
                s = s.replaceAll("[\r\n]+$", "");
                lines[i] = s;
            }
            ArrayList<String[]> shiftedLines = shiftLines(lines);

            StringBuilder s = new StringBuilder();
            for(String[] line : shiftedLines) {
                for(int j = 0; j < line.length - 1; ++j) {
                    s.append(line[j]);
                    s.append(" ");
                }
                s.append(line[line.length - 1]);
                s.append("\n");
            }
            String shifted = s.toString();
            sOutput.write(shifted);
            System.out.println("Shifted.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
