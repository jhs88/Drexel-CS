import Question.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Driver {

    public static void main(String[] args) throws IOException {
        System.out.println("Survey Maker\n1) Create\n2) Display\n3) Load\n4) Save\n5) Take\n6) Edit\n7) Tabulate\n8) Grade\nEnter 'q' to quit");

        Survey survey = null; //initial survey
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in)); //buffered reader for getting inputs
        System.out.println("Please enter an option number");
        String selectedOp = br.readLine();

        //loop to keep program from stopping unless q is entered
        while (!selectedOp.equals("q")) {

            switch (selectedOp) {

                case "1": //create new survey
                    //saving survey and clearing it if one exists
                    if(survey != null){
                        System.out.print("Existing ");
                        survey.save(survey);
                        survey = null;
                    }

                    //making new survey
                    if(survey == null) {
                        String name = "";
                        boolean valid = false;
                        while(!valid){
                            System.out.println("Would you like to make a Survey or Test?");
                            System.out.println("1) Survey\n2) Test");
                            String type = br.readLine();
                            //checking whether should create survey or test
                            if(type.equals("1")){
                                System.out.println("Please enter a name for the new Survey:");
                                name = br.readLine();
                                survey = new Survey(name);
                                valid = true;
                            }else if(type.equals("2")){
                                System.out.println("Please enter a name for the new Test:");
                                name = br.readLine();
                                survey = new Test(name);
                                valid = true;
                            }else{
                                System.out.println("Input '" + type + "' not a valid option");
                            }
                        }

                        System.out.println("Please fill in questions");
                        boolean quit = false;

                        //loop to keep entering question until go back is selected
                        while (!quit) {
                            System.out.println("\nPick a question type");
                            System.out.println("1) True/False\n2) Multiple Choice\n3) Short Answer\n4) Essay\n5) Matching\n6) Valid Date\n7) Go Back");
                            String qType = br.readLine();
                            String prompt;
                            //switching between question types and adding the prompt and options if needed and adding question to survey
                            switch (qType) {
                                case "1": // true/false
                                    System.out.println("Enter the True/False prompt");
                                    prompt = br.readLine();
                                    TrueFalse tf = new TrueFalse(prompt);
                                    //have user give correct answer if making test
                                    if(survey instanceof Test){
                                        tf.display();
                                        System.out.println("Enter the answer for the question (Enter A for true, B for false)");
                                        String correctAns = br.readLine();
                                        ((Test) survey).addAnswer(correctAns);
                                    }
                                    survey.addQuestion(tf);
                                    break;

                                case "2": // multiple choice
                                    System.out.println("Enter the Multiple Choice prompt");
                                    prompt = br.readLine();
                                    MultipleChoice mc = new MultipleChoice(prompt);
                                    mc.setOptions(br);
                                    if(survey instanceof Test){
                                        mc.display();
                                        System.out.println("Enter the answer for the question (Enter the option letter)");
                                        String correctAns = br.readLine();
                                        ((Test) survey).addAnswer(correctAns);
                                    }
                                    survey.addQuestion(mc);
                                    break;

                                case "3": // short answer
                                    System.out.println("Enter the Short Answer prompt");
                                    prompt = br.readLine();
                                    ShortAnswer sa = new ShortAnswer(prompt);
                                    if(survey instanceof Test){
                                        sa.display();
                                        System.out.println("Enter the answer for the question");
                                        String correctAns = br.readLine();
                                        ((Test) survey).addAnswer(correctAns);
                                    }
                                    survey.addQuestion(sa);
                                    break;

                                case "4": // essay
                                    System.out.println("Enter the Essay prompt");
                                    prompt = br.readLine();
                                    Essay e = new Essay(prompt);
                                    //for tests essays cant be graded automatically
                                    if(survey instanceof Test){
                                        ((Test) survey).addAnswer("Essays must be reviewed");
                                    }
                                    survey.addQuestion(e);
                                    break;

                                case "5": // matching
                                    System.out.println("Enter the Matching prompt");
                                    prompt = br.readLine();
                                    Matching m = new Matching(prompt);
                                    m.setOptions(br);
                                    if(survey instanceof Test){
                                        m.display();
                                        System.out.println("Enter the answer for the question");
                                        String correctAns = br.readLine();
                                        ((Test) survey).addAnswer(correctAns);
                                    }
                                    survey.addQuestion(m);
                                    break;

                                case "6": //valid date
                                    System.out.println("Enter the Valid Date prompt");
                                    prompt = br.readLine();
                                    ValidDate vd = new ValidDate(prompt);
                                    if(survey instanceof Test){
                                        vd.display();
                                        System.out.println("Enter the answer for the question");
                                        String correctAns = br.readLine();
                                        //checking if date is in valid format
                                        while (!vd.checkDate(correctAns)){
                                            System.out.println("Date format not valid");
                                            System.out.println("");
                                            correctAns = br.readLine();
                                        }
                                        ((Test) survey).addAnswer(correctAns);
                                    }
                                    survey.addQuestion(vd);
                                    break;

                                case "7": //go back
                                    quit = true;
                                    break;

                                default: //invalid input
                                    System.out.println("Input '" + qType + "' is not a valid option number");
                                    break;
                            }

                        }
                        System.out.println(survey.getClass().getName() + " " + name + " has been created \n");
                    }
                    break;

                case "2": //display survey
                    if(survey != null) {
                        if(survey instanceof Test){
                            boolean valid = false;
                            while(!valid) {
                                System.out.println("Enter an option to print");
                                System.out.println("1) with Answers\n2) without Answers");
                                String printOpt = br.readLine();
                                if(printOpt.equals("1")){
                                    ((Test) survey).displayWithAnswers(); //displaying questions and answers
                                    valid = true;
                                }else if(printOpt.equals("2")){
                                    survey.display(); //displaying questions
                                    valid = true;
                                }else{
                                    System.out.println("Input '" + printOpt + "' not a valid option");
                                }
                            }
                        }else{
                            survey.display(); //displaying questions
                        }
                    }else{
                        System.out.println("No survey or test to display");
                    }
                    break;

                case "3": //load survey
                    //saving survey and clearing it if one exists
                    if(survey != null){
                        System.out.print("Existing ");
                        survey.save(survey);
                        survey = null;
                    }

                    if(survey == null) {
                        boolean valid = false;
                        while(!valid){
                            System.out.println("Would you like to load a Survey or Test?");
                            System.out.println("1) Survey\n2) Test");
                            String type = br.readLine();
                            //checking whether to load survey or test
                            if(type.equals("1")){
                                System.out.println("Please enter the name of the Survey:");
                                survey = new Survey("");
                                survey = survey.load(br);
                                valid = true;
                            }else if(type.equals("2")){
                                System.out.println("Please enter the name of the Test:");
                                survey = new Test("");
                                survey = survey.load(br);
                                valid = true;
                            }else{
                                System.out.println("Input '" + type + "' not a valid option");
                            }
                        }
                    }
                    break;

                case "4": //save survey
                    if(survey != null) {
                        survey.save(survey);
                    }else{
                        System.out.println("No survey or test to save");
                    }
                    break;

                case "5": //take survey
                    if(survey != null) {
                        System.out.println("Starting " + survey.getClass().getName() + ": " + survey.getName());
                        ArrayList<Response> responseSet = survey.take(br); //responses to taking the survey get saved in the Response ArrayList responseSet
                        survey.addResponse(responseSet);
                        System.out.println("Response set saved");
                    }else{
                        System.out.println("No survey or test to take");
                    }
                    break;

                case "6": //modify survey
                    if(survey != null) {
                        survey.modify(br);
                    }else{
                        System.out.println("No survey or test to edit");
                    }
                    break;

                case "7": //tabulate
                    if(survey != null) {
                        survey.tabulate();
                    }else{
                        System.out.println("No survey or test to tabulate");
                    }
                    break;

                case "8": //graded
                    //checking if a test is currently loaded and if there are response sets
                    if(survey instanceof Test && !survey.responses.isEmpty()){
                        int i;
                        //list currently loaded response sets
                        for(i = 0; i < survey.responses.size(); i++){
                            System.out.println((i+1) + ") " + survey.getName() + "Response" + (i+1));
                        }

                        boolean valid = false;
                        int resNum = 0;
                        //getting which set number to grade from user
                        while(!valid) {
                            System.out.println("Enter the number of the response set to grade:");
                            String num = br.readLine();
                            try {
                                resNum = Integer.parseInt(num);
                                //checking if giving number is a response set number
                                if(resNum <= i && resNum > 0) {
                                    valid = true;
                                }
                            } catch (Exception e) {
                                System.out.println(num + " is not a valid option number");
                            }
                        }
                        ((Test) survey).grade(resNum);
                    }else{
                        System.out.println("This can only be used on a Test and if there are response sets");
                    }
                    break;

                default:
                    System.out.println("Input '" + selectedOp + "' is not a valid option number");
                    break;
            }
            System.out.println("\nPlease enter an option number");
            System.out.println("1) Create\n2) Display\n3) Load\n4) Save\n5) Take\n6) Edit\n7) Tabulate\n8) Grade\nEnter 'q' to quit");
            selectedOp = br.readLine();
        }
    }
}


