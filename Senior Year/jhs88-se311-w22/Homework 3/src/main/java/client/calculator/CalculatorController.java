package client.calculator;

import client.Client;

import javax.swing.*;
import java.awt.*;
import java.util.Scanner;

public class CalculatorController {
    private Calculator calcModel;
    private CalculatorView view;
    private static Client client;

    public CalculatorController(Calculator model, CalculatorView view) {
        this.calcModel = model;
        this.view = view;
        client = new Client();
    }

    public void run() {
        try {
            JPanel panel = view.buidUI();
            calcModel.getFrame().getContentPane().add(panel, BorderLayout.CENTER);
            calcModel.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            client.connect();
            while (true) {
                Scanner in = new Scanner(System.in);
                System.out.println("Close server connection?\n (Type y/n)");
                if (in.nextLine().equals("y")) {
                    client.close();
                    System.out.println("Server closed.");
                    break;
                } else if (in.nextLine().equals("n")) {
                    System.out.println("Server still up.");
                } else {
                    System.out.println("Error: Invalid command.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String evaluate(String input) {
        char[] arr = input.toCharArray();
        StringBuilder operand1 = new StringBuilder();
        StringBuilder operand2 = new StringBuilder();
        StringBuilder operator = new StringBuilder();
        double result = 0;
        String answer = null;

        for (char c : arr) {
            if (c >= '0' && c <= '9' || c == '.') {
                if (operator.length() == 0) {
                    operand1.append(c);
                } else {
                    operand2.append(c);
                }
            }
            if (c == '+' || c == '-' || c == '/' || c == 'x') {
                operator.append(c);
            }
        }

        switch (operator.toString()) {
            case "+":
                result = (Double.parseDouble(operand1.toString()) + Double.parseDouble(operand2.toString()));
                break;
            case "-":
                result = (Double.parseDouble(operand1.toString()) - Double.parseDouble(operand2.toString()));
                break;
            case "/":
                result = (Double.parseDouble(operand1.toString()) / Double.parseDouble(operand2.toString()));
                break;
            default:
                result = (Double.parseDouble(operand1.toString()) * Double.parseDouble(operand2.toString()));
                break;
        }
        answer = operand1 + operator.toString() + operand2 + "=" + result;
        client.sendLog(answer);
        return answer;
    }
}
