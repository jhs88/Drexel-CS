package Question;

import java.io.BufferedReader;
import java.io.IOException;

public class ShortAnswer extends Essay {
    private static final long serialVersionUID = 2857898503175512716L;
    public final int limit = 250;

    public ShortAnswer(String q) {
        super(q);
    }

    public int getLimit() {
        return limit;
    }

    public boolean checkLength(String ans) {
        return ans.length() <= 199;
    }

    @Override
    public void modify(BufferedReader br) throws IOException {
        super.modify(br);
    }

    @Override
    public Response take(BufferedReader br) throws IOException {
        display(); //display question
        System.out.println("Response limit is 200 characters");
        System.out.println("");
        String ans = br.readLine(); //get ans
        //check for valid user input
        while (!checkLength(ans)){
            System.out.println("Answer is too long");
            display();
            System.out.println("");
            ans = br.readLine();
        }
        return new Response(ans);
    }

    @Override
    public void display() {
        System.out.println(query);
    }

}
