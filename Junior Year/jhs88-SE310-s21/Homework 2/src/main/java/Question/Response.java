package Question;

import java.io.Serializable;

public class Response implements Serializable {
    private static final long serialVersionUID = -4990548855019261396L;
    protected String answer;

    public Response(String ans) {
        this.answer = ans;
    }

    public String getAnswer() {
        return answer;
    }

    public void modify(String ans) {
        answer = ans;
    }

    public void display() {
        System.out.println(answer);
    }

}
