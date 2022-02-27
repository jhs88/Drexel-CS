package main;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

// Takes in and reads file input
public class Input extends Filter {
    protected FileInputStream inputFile;

    public Input(FileInputStream inputFile, Pipe sOutput) {
        super(inputFile, sOutput);
        this.inputFile = inputFile;
    }

    @Override
    public void run(){
        System.out.println("Reading input file...");
        try {
            var bytes = inputFile.readAllBytes();
            String s = new String(bytes, StandardCharsets.UTF_8);
            sOutput.write(s);
            System.out.println("Lines Read.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
