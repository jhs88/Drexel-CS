package client.calculator;

import javax.swing.*;
import java.awt.*;

public class CalculatorView {
    private JTextField inputBox;

    public CalculatorView() {
        inputBox = new JTextField(10);
        inputBox.setEditable(false);
    }

    public JPanel buidUI() {
        JPanel panel = new JPanel();
        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();

        JButton button0 = new JButton("0");
        JButton button1 = new JButton("1");
        JButton button2 = new JButton("2");
        JButton button3 = new JButton("3");
        JButton button4 = new JButton("4");
        JButton button5 = new JButton("5");
        JButton button6 = new JButton("6");
        JButton button7 = new JButton("7");
        JButton button8 = new JButton("8");
        JButton button9 = new JButton("9");

        JButton buttonPlus = new JButton("+");
        JButton buttonMinus = new JButton("-");
        JButton buttonDivide = new JButton("/");
        JButton buttonMultiply = new JButton("x");
        JButton buttonClear = new JButton("C");
        JButton buttonDot = new JButton(".");
        JButton buttonEquals = new JButton("=");

        ButtonHandler handler = new ButtonHandler(inputBox);

        panel.setLayout(layout);

        button1.addActionListener(handler);
        button2.addActionListener(handler);
        button3.addActionListener(handler);
        button4.addActionListener(handler);
        button5.addActionListener(handler);
        button6.addActionListener(handler);
        button7.addActionListener(handler);
        button8.addActionListener(handler);
        button9.addActionListener(handler);
        button0.addActionListener(handler);

        buttonPlus.addActionListener(handler);
        buttonMinus.addActionListener(handler);
        buttonDivide.addActionListener(handler);
        buttonMultiply.addActionListener(handler);
        buttonClear.addActionListener(handler);
        buttonDot.addActionListener(handler);
        buttonEquals.addActionListener(handler);

        inputBox = handler.getInputBox();

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0; gbc.gridy = 0; panel.add(button1, gbc);
        gbc.gridx = 1; gbc.gridy = 0; panel.add(button2, gbc);
        gbc.gridx = 2; gbc.gridy = 0; panel.add(button3, gbc);
        gbc.gridx = 3; gbc.gridy = 0; panel.add(buttonPlus, gbc);
        gbc.gridx = 0; gbc.gridy = 1; panel.add(button4, gbc);
        gbc.gridx = 1; gbc.gridy = 1; panel.add(button5, gbc);
        gbc.gridx = 2; gbc.gridy = 1; panel.add(button6, gbc);
        gbc.gridx = 3; gbc.gridy = 1; panel.add(buttonMinus, gbc);
        gbc.gridx = 0; gbc.gridy = 2; panel.add(button7, gbc);
        gbc.gridx = 1; gbc.gridy = 2; panel.add(button8, gbc);
        gbc.gridx = 2; gbc.gridy = 2; panel.add(button9, gbc);
        gbc.gridx = 3; gbc.gridy = 2; panel.add(buttonDivide, gbc);
        gbc.gridx = 0; gbc.gridy = 3; panel.add(buttonDot, gbc);
        gbc.gridx = 1; gbc.gridy = 3; panel.add(button0, gbc);
        gbc.gridx = 2; gbc.gridy = 3; panel.add(buttonClear, gbc);
        gbc.gridx = 3; gbc.gridy = 3; panel.add(buttonMultiply, gbc);
        gbc.gridwidth = 3;
        gbc.gridx = 0; gbc.gridy = 4; panel.add(inputBox, gbc);
        gbc.gridx = 3; gbc.gridy = 4; panel.add(buttonEquals, gbc);

        return panel;
    }
}
