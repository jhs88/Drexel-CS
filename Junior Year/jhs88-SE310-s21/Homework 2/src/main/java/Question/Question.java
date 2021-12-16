package Question;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Serializable;

abstract public class Question implements Serializable {
    private static final long serialVersionUID = -4075963175656922914L;
    protected String query;

    public Question(String q) {
        this.query = q;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String s) {
        query = s;
    }

    public void modify(BufferedReader br) throws IOException {
        boolean valid = false;
        //loop for user to edit questions
        while(!valid){
            System.out.println(getQuery());
            System.out.println("Would you like to enter a new prompt?");
            System.out.println("1) Yes\n2) Go Back");
            String option = br.readLine();
            if(option.equals("1")){
                System.out.println("Enter the new prompt:");
                String prompt = br.readLine();
                setQuery(prompt); //setting new prompt
                System.out.println("New prompt set");
                valid = true;
            }else if(option.equals("2")){
                valid = true;
            }else{
                System.out.println("Input '" + option + "' is not a option number");
            }
        }
    }

    public Response take(BufferedReader br) throws IOException {
        display();
        System.out.println("");
        String ans = br.readLine(); //getting answer
        return new Response(ans);
    }

    abstract public void display();

}
