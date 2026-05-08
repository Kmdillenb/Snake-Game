package model;

import java.text.DecimalFormat;

public class CalculatorModel {
    private static final DecimalFormat DISPLAY_FORMAT = new DecimalFormat("0.############");

    private String currentInput = "0";
    private Double accumulator;
    private Operation pendingOperation;
    private Operation visualActiveOperation;
    private boolean awaitingNextOperand;
    private boolean lastValueIsExecutedResult;
    private double memoryValue;

    public String getDisplayText() {
        return currentInput;
    }

    public Operation getVisualActiveOperation() {
        return visualActiveOperation;
    }

    public void inputDigit(char digit) {
        if (!Character.isDigit(digit)) {
            return;
        }

        if (awaitingNextOperand) {
            currentInput = String.valueOf(digit);
            awaitingNextOperand = false;
            visualActiveOperation = null;
            lastValueIsExecutedResult = false;
            return;
        }

        if (lastValueIsExecutedResult && pendingOperation == null) {
            currentInput = String.valueOf(digit);
            accumulator = null;
            lastValueIsExecutedResult = false;
            return;
        }

        if ("0".equals(currentInput)) {
            currentInput = String.valueOf(digit);
        } else {
            currentInput += digit;
        }

        lastValueIsExecutedResult = false;
    }

    public void inputDecimal() {
        if (awaitingNextOperand) {
            currentInput = "0.";
            awaitingNextOperand = false;
            visualActiveOperation = null;
            lastValueIsExecutedResult = false;
            return;
        }

        if (lastValueIsExecutedResult && pendingOperation == null) {
            currentInput = "0.";
            accumulator = null;
            lastValueIsExecutedResult = false;
            return;
        }

        if (!currentInput.contains(".")) {
            currentInput += ".";
            lastValueIsExecutedResult = false;
        }
    }

    public void setOperation(Operation operation) throws CalculatorException {
        double inputValue = parseInput();

        if (accumulator == null) {
            accumulator = inputValue;
        } else if (!awaitingNextOperand && pendingOperation != null) {
            accumulator = executeBinary(accumulator, inputValue, pendingOperation);
            currentInput = format(accumulator);
            lastValueIsExecutedResult = true;
        }

        pendingOperation = operation;
        visualActiveOperation = operation;
        awaitingNextOperand = true;
    }

    public void calculateResult() throws CalculatorException {
        if (pendingOperation == null) {
            return;
        }

        double rightOperand = awaitingNextOperand ? accumulator : parseInput();
        accumulator = executeBinary(accumulator, rightOperand, pendingOperation);
        currentInput = format(accumulator);
        pendingOperation = null;
        visualActiveOperation = null;
        awaitingNextOperand = false;
        lastValueIsExecutedResult = true;
    }

    public void square() throws CalculatorException {
        double value = parseInput();
        double result = checked(value * value);
        currentInput = format(result);
        accumulator = result;
        pendingOperation = null;
        visualActiveOperation = null;
        awaitingNextOperand = false;
        lastValueIsExecutedResult = true;
    }

    public void squareRoot() throws CalculatorException {
        double value = parseInput();
        if (value < 0) {
            throw new CalculatorException("Error: sqrt of negative value");
        }

        double result = checked(Math.sqrt(value));
        currentInput = format(result);
        accumulator = result;
        pendingOperation = null;
        visualActiveOperation = null;
        awaitingNextOperand = false;
        lastValueIsExecutedResult = true;
    }

    public void memoryAdd() throws CalculatorException {
        requireExecutedResultForMemory();
        memoryValue = checked(memoryValue + parseInput());
    }

    public void memorySubtract() throws CalculatorException {
        requireExecutedResultForMemory();
        memoryValue = checked(memoryValue - parseInput());
    }

    public void memoryRecall() {
        currentInput = format(memoryValue);
        awaitingNextOperand = false;
        visualActiveOperation = null;
        lastValueIsExecutedResult = true;
    }

    public void memoryClear() {
        memoryValue = 0.0;
    }

    public void deleteLast() {
        if (awaitingNextOperand || lastValueIsExecutedResult) {
            return;
        }

        if (currentInput.length() <= 1) {
            currentInput = "0";
            return;
        }

        currentInput = currentInput.substring(0, currentInput.length() - 1);
        if (currentInput.equals("-") || currentInput.equals("-0")) {
            currentInput = "0";
        }
    }

    public void clearAll() {
        currentInput = "0";
        accumulator = null;
        pendingOperation = null;
        visualActiveOperation = null;
        awaitingNextOperand = false;
        lastValueIsExecutedResult = false;
        memoryValue = 0.0;
    }

    public void setError(String message) {
        currentInput = message;
        accumulator = null;
        pendingOperation = null;
        visualActiveOperation = null;
        awaitingNextOperand = false;
        lastValueIsExecutedResult = false;
    }

    private void requireExecutedResultForMemory() throws CalculatorException {
        if (!lastValueIsExecutedResult) {
            throw new CalculatorException("Error: memory accepts executed results only");
        }
    }

    private double executeBinary(double left, double right, Operation operation) throws CalculatorException {
        return switch (operation) {
            case ADD -> checked(left + right);
            case SUBTRACT -> checked(left - right);
            case MULTIPLY -> checked(left * right);
            case DIVIDE -> {
                if (right == 0.0) {
                    throw new CalculatorException("Error: division by zero");
                }
                yield checked(left / right);
            }
        };
    }

    private double parseInput() throws CalculatorException {
        try {
            return Double.parseDouble(currentInput);
        } catch (NumberFormatException ex) {
            throw new CalculatorException("Error: invalid number");
        }
    }

    private double checked(double value) throws CalculatorException {
        if (!Double.isFinite(value)) {
            throw new CalculatorException("Error: out of range");
        }
        return value;
    }

    private String format(double value) {
        return DISPLAY_FORMAT.format(value);
    }
}
