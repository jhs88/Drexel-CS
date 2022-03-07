package client;

import client.calculator.Calculator;
import client.calculator.CalculatorController;
import client.calculator.CalculatorView;

public class Main {
    public static void main(String[] args) {
        Calculator model = new Calculator();
        CalculatorView view = new CalculatorView();
        CalculatorController controller = new CalculatorController(model, view);

        controller.run();
    }
}
