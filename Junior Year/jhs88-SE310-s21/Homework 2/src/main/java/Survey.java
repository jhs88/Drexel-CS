import Question.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Survey implements Serializable {
    private static final long serialVersionUID = -1373186766942031799L;
    protected String name;
    protected ArrayList<Question> questions;
    protected ArrayList<ArrayList<Response>> responses;

    public Survey(String n) {
        this.name = n;
        this.questions = new ArrayList<>();
        this.responses = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String n) {
        name = n;
    }

    public Question getQuestion(int q) {
        return questions.get(q);
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public void addQuestion(Question q) {
        questions.add(q);
    }

    public void addQuestions(ArrayList<Question> q) {
        questions.addAll(q);
    }

    public void removeQuestion(int q) {
        questions.remove(q);
    }

    public ArrayList<ArrayList<Response>> getResponses(){
        return responses;
    }

    public ArrayList<Response> getResponse(int i){
        return responses.get(i);
    }

    public void addResponse(ArrayList<Response> r){
        responses.add(r);
    }

    public void modify(BufferedReader br) throws IOException {
        System.out.println("Select what to edit:");
        System.out.println("1) Delete question\n2) Edit question\n3) Go back");
        boolean valid = false;
        while(!valid) {
            String choice = br.readLine();
            switch (choice) {
                case "1": //del question
                    System.out.println("Enter the question number to delete:");
                    String qDel = br.readLine();
                    try {
                        int qnum = Integer.parseInt(qDel);
                        removeQuestion(qnum-1);
                        System.out.println("Question has been removed");
                        valid = true;
                    }catch (Exception e) {
                        System.out.println("Input '" + qDel + "' is not a valid question number");
                    }
                    break;
                case "2": //edit question
                    System.out.println("Enter the question number to edit:");
                    String qEdit = br.readLine();
                    try {
                        int qnum = Integer.parseInt(qEdit);
                        getQuestion(qnum-1).modify(br);
                        valid = true;
                    }catch (Exception e) {
                        System.out.println("Input '" + qEdit + "' is not a valid question number");
                    }
                    break;
                case "3": //go back
                    valid = true;
                    break;
                default:
                    System.out.println("Input '" + choice + "' is not a option number");
            }
        }
    }

    // User response saved to new response
    public ArrayList<Response> take(BufferedReader br) throws IOException {
        ArrayList<Response> responseSet = new ArrayList<>();
        for (int i = 0; i < questions.size(); i++){
            System.out.print((i+1) + ") ");
            Response response = getQuestion(i).take(br); // user response gets saved to new response
            responseSet.add(response);
            System.out.println("");
        }

        return responseSet;
    }

    public Survey load(BufferedReader br) throws IOException {
        Survey loadSurvey = null;
        try {
            File[] surveys = new File("./surveys/").listFiles();
            if(surveys != null){
                //print all surveys in folder
                for(int i = 0; i < surveys.length; i++){
                    if(!surveys[i].getName().contains("Response")) {
                        System.out.println((i+1) + ") " + surveys[i].getName());
                    }
                }
            }

            boolean valid = false;
            int optionNum = 0;
            //User picks survey
            while(!valid) {
                System.out.println("Enter the option number of the survey to load");
                String num = br.readLine();
                try {
                    optionNum = Integer.parseInt(num);
                    valid = true;
                } catch (Exception e) {
                    System.out.println(num + " is not a valid number");
                }
            }

            FileInputStream fisSurvey = new FileInputStream("./surveys/" + surveys[optionNum-1].getName() + "/" + surveys[optionNum-1].getName());
            ObjectInputStream oisSurvey = new ObjectInputStream(fisSurvey);
            loadSurvey = new Survey(surveys[optionNum-1].getName()); // survey to build from loaded file
            loadSurvey.addQuestions((ArrayList<Question>) oisSurvey.readObject()); // casting the read object as a Question Arraylist and adding it to the survey
            oisSurvey.close();

        } catch (Exception e) {
            System.out.println("Error de-serializing object");
        }

        System.out.println("Survey Loaded");
        return loadSurvey; //return survey just loaded
    }

    public void save(Survey s) {
        try {
            new File("./surveys").mkdir(); // creating directory 
            new File("./surveys/" + s.getName()).mkdir(); // creating folder for individual survey
            FileOutputStream fosSurvey = new FileOutputStream("./surveys/" + s.getName() + "/" + s.getName());
            ObjectOutputStream oosSurvey = new ObjectOutputStream(fosSurvey);
            oosSurvey.writeObject(s.getQuestions()); // write object to file
            oosSurvey.close();
            System.out.println("Survey saved");
        } catch (Exception e) {
            System.out.println("Error serializing survey");
        }
    }

    public void display() {
        //print all questions
        System.out.println(getClass().getName() + ": " + name);
        for (int i = 0; i < questions.size(); i++) {
            System.out.print((i + 1) + ") ");
            getQuestion(i).display();
            System.out.println("");
        }
    }

    public void tabulate(){
        display();
        ArrayList<String> responses = new ArrayList<>();
        //collect all responses and print
        for(int i = 0; i < responses.size(); i++){
            for(int j = 0; j < getResponse(i).size(); j++){
                responses.add(getResponse(i).get(j).getAnswer());
                System.out.println(getResponse(i).get(j).getAnswer());
            }
            System.out.println("");
        }
        Set<String> diffResponses = new HashSet<String>(responses);
        System.out.println("Response : Response Amount");
        //print out how many of each type of response
        for(String res : diffResponses){
            System.out.println(res + " : " + Collections.frequency(responses, res));
        }
    }

}