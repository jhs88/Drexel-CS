package Question;

import java.io.BufferedReader;
import java.io.IOException;

public class ValidDate extends Question {
    private static final long serialVersionUID = -1803842649322908253L;

    public ValidDate(String q) {
        super(q);
    }

    //checks date formatting
    public boolean checkDate(String date){
        boolean valid = false;
        char[] chars = date.toCharArray();
        //check length & formatting
        if(chars.length == 10) {
            //check date format
            if (Character.isDigit(chars[0]) && Character.isDigit(chars[1]) &&
                    Character.isDefined(chars[2]) && Character.isDigit(chars[3]) && Character.isDigit(chars[4]) &&
                    Character.isDefined(chars[5]) && Character.isDigit(chars[6]) && Character.isDigit(chars[7]) &&
                    Character.isDigit(chars[8]) && Character.isDigit(chars[9])) {
                valid = true;
            }
        }
        return valid;
    }

    @Override
    public void modify(BufferedReader br) throws IOException {
        super.modify(br);
    }

    @Override
    public Response take(BufferedReader br) throws IOException {
        display(); //display question
        System.out.println("");
        String ans = br.readLine(); //get answer
        //check if user input is valid
        while (!checkDate(ans)){
            System.out.println("Date format not valid");
            display();
            System.out.println("");
            ans = br.readLine();
        }
        return new Response(ans);
    }

    @Override
    public void display() {
        System.out.println(query);
        System.out.println("(Answer in MM/DD/YYYY format)");
    }

}
