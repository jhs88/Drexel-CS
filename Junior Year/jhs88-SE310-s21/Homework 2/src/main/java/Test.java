import Question.Question;
import Question.Response;

import java.io.*;
import java.util.ArrayList;

public class Test extends Survey {
    private static final long serialVersionUID = 6772581161049029820L;
    public ArrayList<Response> answers;

    public Test(String name) {
        super(name);
        this.answers = new ArrayList<>();
    }

    public Response getAnswer(int i){
        return answers.get(i);
    }

    public ArrayList<Response> getAnswers() {
        return answers;
    }

    public void addAnswer(String ans) {
        this.answers.add(new Response(ans));
    }

    public void addAnswers(ArrayList<Response> answers) {
        this.answers = answers;
    }

    @Override
    public ArrayList<Response> take(BufferedReader br) throws IOException {
        return super.take(br);
    }

    @Override
    public Test load(BufferedReader br) {
        Test loadSurvey = null;
        try {
            File[] tests = new File("./tests/").listFiles();
            if(tests != null){
                //loop to print out all the tests saved
                for(int i = 0; i < tests.length; i++){
                    if(!tests[i].getName().contains("Response")) {
                        System.out.println((i+1) + ") " + tests[i].getName());
                    }
                }
            }

            boolean valid = false;
            int optionNum = 0;
            //pick which survey to load
            while(!valid) {
                System.out.println("Enter the option number of the Test to load");
                String num = br.readLine();
                try {
                    optionNum = Integer.parseInt(num);
                    valid = true;
                } catch (Exception e) {
                    System.out.println(num + " is not a valid number");
                }
            }

            FileInputStream fisTest = new FileInputStream("./tests/" + tests[optionNum-1].getName() + "/" + tests[optionNum-1].getName());
            ObjectInputStream oisTest = new ObjectInputStream(fisTest);
            loadSurvey = new Test(tests[optionNum-1].getName()); // survey to build from loaded file
            loadSurvey.addQuestions((ArrayList<Question>) oisTest.readObject()); // cast read object as a Question Arraylist
            oisTest.close();                                                     // add it to survey

            File[] files = new File("./tests/" + tests[optionNum-1].getName() + "/").listFiles();
            //looping through files in test folder to load responseSet
            if (files != null) {
                for (File file : files) {
                    if (file.getName().contains("Response")) {
                        FileInputStream fisResp = new FileInputStream("./tests/" + tests[optionNum-1].getName() + "/" + file.getName());
                        ObjectInputStream oisResp = new ObjectInputStream(fisResp);
                        loadSurvey.addResponse((ArrayList<Response>) oisResp.readObject()); // cast read object as a String ArrayList
                    }                                                                       // add it to survey
                }
            }

            FileInputStream fisAns = new FileInputStream("./tests/" + tests[optionNum-1].getName() + "/" + tests[optionNum-1].getName() + "Answers");
            ObjectInputStream oisAns = new ObjectInputStream(fisAns);
            loadSurvey.addAnswers((ArrayList<Response>) oisAns.readObject());

        } catch (Exception e) {
            System.out.println("Error de-serializing Test");
        }

        System.out.println("Test Loaded");
        return loadSurvey; //return survey just loaded
    }

    //cast survey as a test
    @Override
    public void save(Survey survey){
        saveTest((Test) survey);
    }

    public void saveTest(Test test) {
        try {
            new File("./tests").mkdir(); // create directory for all tests
            new File("./tests/" + test.getName()).mkdir(); // create folder for individual test and responses
            FileOutputStream fosTest = new FileOutputStream("./tests/" + test.getName() + "/" + test.getName());
            ObjectOutputStream oosTest = new ObjectOutputStream(fosTest);
            oosTest.writeObject(test.getQuestions()); // writing object to file
            oosTest.close();
            // save response sets if they exist
            for (int i = 0; i < test.getResponses().size(); i++) {
                FileOutputStream fosResp = new FileOutputStream("./tests/" + test.getName() + "/" + test.getName() + "Response" + (i + 1));
                ObjectOutputStream oosResp = new ObjectOutputStream(fosResp);
                oosResp.writeObject(test.getResponse(i));
                oosResp.close();
            }
            // save answers to file
            FileOutputStream fosAns = new FileOutputStream("./tests/" + test.getName() + "/" + test.getName() + "Answers");
            ObjectOutputStream oosAns = new ObjectOutputStream(fosAns);
            oosAns.writeObject(test.getAnswers());
            oosAns.close();
            System.out.println("Test saved");
        } catch (Exception e) {
            System.out.println("Error serializing test");
        }
    }

    @Override
    public void display(){
        super.display();
    }

    public void displayWithAnswers() {
        //printing out all questions and answers
        System.out.println("Test: " + name);
        for (int i = 0; i < questions.size(); i++){
            System.out.print((i+1) + ") ");
            getQuestion(i).display();
            System.out.println("Correct Answer: " + answers.get(i).getAnswer());
            System.out.println("");
        }
    }

    @Override
    public void tabulate(){
        super.tabulate();
    }

    public void grade(int resNum) throws IOException {
        int numCorrect = 0;
        ArrayList<Response> setToGrade = getResponse(resNum-1);
        //grade selected response set
        for(int j = 0; j < setToGrade.size(); j++){
            if(answers.get(j).getAnswer().equalsIgnoreCase("Essays must be reviewed")){
                ; //exclude essay questions from correct answers
            }else if(setToGrade.get(j).getAnswer().equalsIgnoreCase(answers.get(j).getAnswer())){
                numCorrect++;
            }
        }
        System.out.println("");
        System.out.println(getName() + "Response" + resNum);
        System.out.println("Note: Essays are omitted from score and need to be manually graded");
        System.out.println("Score: " + ((double)numCorrect/(setToGrade.size()))*100);
    }

}
