package Question;

import java.io.BufferedReader;
import java.io.IOException;

public class TrueFalse extends MultipleChoice {
    private static final long serialVersionUID = -1587215860838346326L;

    public TrueFalse(String q) {
        super(q);
        //Only options for TrueFalse
        this.options.add("T");
        this.options.add("F");
    }

    @Override
    public void modify(BufferedReader br) throws IOException {
        boolean valid = false;
        //user to edit questions
        while(!valid){
            System.out.println(getQuery());
            System.out.println("Would you like to enter a new prompt?");
            System.out.println("1) Yes\n2) Go Back");
            String option = br.readLine();
            switch (option) {
                case "1":
                    System.out.println("Enter the new prompt:");
                    String prompt = br.readLine();
                    setQuery(prompt); //set new prompt
                    System.out.println("New prompt set");
                    valid = true;
                    break;
                case "2":
                    valid = true;
                    break;
                default:
                    System.out.println("Input '" + option + "' is not a option number");
                    break;
            }
        }
    }

    @Override
    public Response take(BufferedReader br) throws IOException {
        return super.take(br);
    }

    @Override
    public void display() {
        System.out.println(query);
        System.out.println("\tA) T");
        System.out.println("\tB) F");
    }

}
