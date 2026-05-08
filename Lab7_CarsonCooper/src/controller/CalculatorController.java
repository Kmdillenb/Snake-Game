package controller;

import model.CalculatorException;
import model.CalculatorModel;
import model.Operation;
import view.CalculatorView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CalculatorController implements ActionListener {
    private final CalculatorModel model;
    private final CalculatorView view;

    public CalculatorController(CalculatorModel model, CalculatorView view) {
        this.model = model;
        this.view = view;
        this.view.addActionListener(this);
        refreshView();
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        String command = event.getActionCommand();

        try {
            if (command.startsWith("DIGIT_")) {
                model.inputDigit(command.charAt(command.length() - 1));
            } else {
                switch (command) {
                    case "DECIMAL" -> model.inputDecimal();
                    case "ADD" -> model.setOperation(Operation.ADD);
                    case "SUBTRACT" -> model.setOperation(Operation.SUBTRACT);
                    case "MULTIPLY" -> model.setOperation(Operation.MULTIPLY);
                    case "DIVIDE" -> model.setOperation(Operation.DIVIDE);
                    case "EQUALS" -> model.calculateResult();
                    case "SQUARE" -> model.square();
                    case "SQRT" -> model.squareRoot();
                    case "MEM_ADD" -> model.memoryAdd();
                    case "MEM_SUBTRACT" -> model.memorySubtract();
                    case "MEM_RECALL" -> model.memoryRecall();
                    case "MEM_CLEAR" -> model.memoryClear();
                    case "DELETE" -> model.deleteLast();
                    case "CLEAR" -> model.clearAll();
                    default -> {
                    }
                }
            }
        } catch (CalculatorException ex) {
            model.setError(ex.getMessage());
        }

        refreshView();
    }

    private void refreshView() {
        view.updateDisplay(model.getDisplayText());
        view.setActiveOperation(model.getVisualActiveOperation());
    }
}
