package client.calculator;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static client.calculator.CalculatorController.evaluate;

public class ButtonHandler implements ActionListener {
    public JTextField inputBox;

    public ButtonHandler(JTextField inputBox) {
        this.inputBox = inputBox;
    }

    public JTextField getInputBox() {
        return inputBox;
    }

    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command.charAt(0) == 'C') {
            inputBox.setText("");
        }else if (command.charAt(0) == '=') {
            inputBox.setText(evaluate(inputBox.getText()));
        }else {
            inputBox.setText(inputBox.getText() + command);
        }
    }
}
