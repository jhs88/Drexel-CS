package main;

import java.io.FileInputStream;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        System.out.println("WELCOME.");
        if (args.length != 1) {
            System.out.println("Incorrect args.");
            System.exit(1);
        }

        // Create needed pipes for Filters
        Pipe circularShifterInput = new Pipe();
        Pipe alphabetizerInput = new Pipe();
        Pipe alphabetizerOutput = new Pipe();

        // Create all Filters and pass required Pipes.
        FileInputStream inputFile = new FileInputStream(args[0]);
        Input input = new Input(inputFile, circularShifterInput);
        CircularShifter shift = new CircularShifter(circularShifterInput, alphabetizerInput);
        Alphabetizer alphabetize = new Alphabetizer(alphabetizerInput, alphabetizerOutput);
        Output output = new Output(alphabetizerOutput);

        // Run all filters in order.
        input.run();
        shift.run();
        alphabetize.run();
        output.run();

        inputFile.close();
    }

}
