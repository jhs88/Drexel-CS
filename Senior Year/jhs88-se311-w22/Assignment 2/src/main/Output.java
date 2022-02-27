package main;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

// Takes in and writes file output
public class Output extends Filter {

    public Output(Pipe sInput){
        super(sInput);
    }

    @Override
    public void run() {
        System.out.println("Writing to out.txt...");
        try {
            String orderedLines = sInput.read();
            String[] lines = orderedLines.split("\\n");

            BufferedWriter writer = new BufferedWriter(new FileWriter("out.txt"));
            for(String line : lines) {
                writer.write(line + '\n');
            }
            writer.close();

            System.out.println("DONE.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
