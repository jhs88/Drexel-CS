import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("WELCOME.");
        if (args.length > 2 || args.length == 0) {
            System.out.println("Incorrect args.");
            System.exit(1);
        }

        Lines stopwords = new Lines();
        if (args.length == 2) {
            System.out.println("Stopwords file detected.");
            Input in = new Input(args[1]);
            stopwords = new Lines(in.readAll());
        }

        Input in = new Input(args[0]);
        System.out.println("Reading " + args[0] + "...");
        Lines lines = new Lines(in.readAll());
        System.out.println("Lines Read.");

        System.out.println("Shifting Lines...");
        Shifter shifter = new Shifter(lines.getLines());
//        if (args.length == 2) shifter.rmStopWords(stopwords.getLines());
        System.out.println("Shifted.");

        System.out.println("Alphabetizing...");
        Alphabetizer alphabetizer = new Alphabetizer(shifter.getLines());
        System.out.println("Sorted.");

        System.out.println("Writing to out.txt...");
        Output output = new Output("out.txt");
        output.writeLines(alphabetizer.getLines());
        output.close();
        System.out.println("DONE.");
    }
}

