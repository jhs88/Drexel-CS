package Question;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

public class MultipleChoice extends Question{
    private static final long serialVersionUID = 5963857954743786587L;
    protected ArrayList<String> options;

    public MultipleChoice(String q) {
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

        //enter choices for the question
        while(!validInput) {
            System.out.println("Please enter a number for the amount of choices");
            String num = br.readLine();
            try {
                numChoices = Integer.parseInt(num);
                validInput = true;
            } catch (Exception e) {
                System.out.println(num + " is not a valid number");
            }
        }

        //user input
        while (numChoices != 0){
            System.out.println("Enter an option");
            String option = br.readLine();
            options.add(option);
            numChoices--;
        }

    }

    @Override
    public void modify(BufferedReader br) throws IOException{
        boolean valid = false;
        //loop for UI for edit questions and options
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
                    System.out.println("Enter which option number to edit");
                    String num = br.readLine();
                    try {
                        int optnum = Integer.parseInt(num);
                        System.out.println(options.get(optnum-1));
                        System.out.println("Enter the new option");
                        String newOpt = br.readLine();
                        options.set(optnum-1, newOpt); //set new option
                        System.out.println("New option set");
                        valid = true;
                    } catch (Exception e) {
                        System.out.println("Input '" + num + "' is not as a valid option number");
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
    public Response take(BufferedReader br) throws IOException{
        return super.take(br);
    }

    @Override
    public void display(){
        //print out query, loop through options
        System.out.println(query);
        if(options != null) {
            for(int i = 0; i < options.size(); i++){
                System.out.println("\t" + (char)('A' + i) + ") " + options.get(i));
            }
        }
    }

}
