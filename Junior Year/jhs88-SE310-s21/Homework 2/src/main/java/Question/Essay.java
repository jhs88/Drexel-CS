package Question;

import java.io.BufferedReader;
import java.io.IOException;

public class Essay extends Question {
    private static final long serialVersionUID = -4186961665903765571L;

    public Essay(String q) {
        super(q);
    }

    @Override
    public void modify(BufferedReader br) throws IOException {
        super.modify(br);
    }

    @Override
    public Response take(BufferedReader br) throws IOException {
        return super.take(br);
    }

    @Override
    public void display() {
        System.out.println(query);
    }

}
