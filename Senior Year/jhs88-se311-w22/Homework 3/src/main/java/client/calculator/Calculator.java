package client.calculator;

import javax.swing.*;

public class Calculator {
    private JFrame frame;

    public Calculator() {
        frame = new JFrame("Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 300);
        frame.setLocationRelativeTo(null);
    }

    public JFrame getFrame() {
       return frame;
    }

    public void setVisible(boolean visible) {
        frame.setVisible(visible);
    }
}
