package Question;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

public class Matching extends Question {
    private static final long serialVersionUID = 7174702343936591327L;
    protected ArrayList<String> options;

    public Matching(String q) {
        super(q);
        this.options = new ArrayList<>();
    }

    public void addOption(String opt) {
        options.add(opt);
    }

    public void removeOption(int i) {
        options.remove(i);
    }

    public void setOptions(BufferedReader br) throws IOException {
        boolean validInput = false;
        int numChoices = 0;
        //user input for num of choices
        while(!validInput) {
            System.out.println("Enter number for the amount of choices:");
            String num = br.readLine();
            try {
                numChoices = Integer.parseInt(num);
                validInput = true;
            } catch (Exception e) {
                System.out.println(num + " is not a valid number");
            }
        }
        //add options
        while (numChoices != 0){
            System.out.println("Enter an option:");
            String option = br.readLine();
            options.add(option);
            numChoices--;
        }

    }

    @Override
    public void modify(BufferedReader br) throws IOException {
        boolean valid = false;
        //loop for user input to edit questions
        while(!valid) {
            System.out.println(getQuery());
            System.out.println("Would you like to edit \n1) Prompt\n2) Options\n3) Go Back");
            String choice = br.readLine();
            switch (choice) {
                case "1": //edit prompt
                    System.out.println("Enter the new prompt");
                    String prompt = br.readLine();
                    setQuery(prompt); //set new prompt
                    System.out.println("New prompt set");
                    valid = true;
                    break;
                case "2": //edit options
                    System.out.println("Enter which option number to edit:");
                    String optNum = br.readLine();
                    try {
                        int optnum = Integer.parseInt(optNum);
                        System.out.println(options.get(optnum-1));
                        System.out.println("Enter the new option:");
                        String newOpt = br.readLine();
                        options.set(optnum-1, newOpt); //set new option
                        System.out.println("New option set");
                        valid = true;
                    } catch (Exception e) {
                        System.out.println("Input '" + optNum + "' is not as a valid option number");
                    }
                    break;
                case "3": //go back
                    valid = true;
                    break;
                default:
                    System.out.println("Input '" + choice + "' is not a option number");
                    break;
            }
        }
    }

    @Override
    public Response take(BufferedReader br) throws IOException {
        return super.take(br);
    }

    @Override
    public void display(){
        System.out.println(query);
        System.out.println("(Answer by putting a group of choices followed by a comma)");
        System.out.println("(ex: A1, B2)");
        //print 2 col of options
        if(options != null) {
            int c = 1;
            for(int i = 0; i < options.size(); i = i + 2){
                System.out.print("\t" + (char)('A' + c-1) + ") " + options.get(i));
                if(i + 1 < options.size()){
                    System.out.print("\t" + c + ") " + options.get(i+1));
                    c++;
                }
                System.out.println("");
            }
        }
    }

}
