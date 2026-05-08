package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CalculatorModelTest {
    private CalculatorModel model;

    @BeforeEach
    void setUp() {
        model = new CalculatorModel();
    }

    @Test
    void addsValues() throws Exception {
        model.inputDigit('2');
        model.setOperation(Operation.ADD);
        model.inputDigit('3');
        model.calculateResult();
        assertEquals("5", model.getDisplayText());
    }

    @Test
    void supportsDecimalMultiplication() throws Exception {
        model.inputDigit('2');
        model.inputDecimal();
        model.inputDigit('5');
        model.setOperation(Operation.MULTIPLY);
        model.inputDigit('4');
        model.calculateResult();
        assertEquals("10", model.getDisplayText());
    }

    @Test
    void divisionByZeroThrowsError() throws Exception {
        model.inputDigit('9');
        model.setOperation(Operation.DIVIDE);
        model.inputDigit('0');
        CalculatorException ex = assertThrows(CalculatorException.class, model::calculateResult);
        assertEquals("Error: division by zero", ex.getMessage());
    }

    @Test
    void squareRootOfNegativeThrowsError() throws Exception {
        model.inputDigit('9');
        model.setOperation(Operation.SUBTRACT);
        model.inputDigit('9');
        model.inputDigit('9');
        model.calculateResult();
        CalculatorException ex = assertThrows(CalculatorException.class, model::squareRoot);
        assertEquals("Error: sqrt of negative value", ex.getMessage());
    }

    @Test
    void deleteRemovesLastEnteredCharacter() {
        model.inputDigit('1');
        model.inputDigit('2');
        model.inputDecimal();
        model.inputDigit('3');
        model.deleteLast();
        assertEquals("12.", model.getDisplayText());
        model.deleteLast();
        assertEquals("12", model.getDisplayText());
    }

    @Test
    void memoryRequiresExecutedResult() {
        model.inputDigit('8');
        CalculatorException ex = assertThrows(CalculatorException.class, model::memoryAdd);
        assertEquals("Error: memory accepts executed results only", ex.getMessage());
    }

    @Test
    void memoryAddSubtractRecallAndClear() throws Exception {
        model.inputDigit('6');
        model.square();
        model.memoryAdd();
        model.memoryRecall();
        assertEquals("36", model.getDisplayText());

        model.memorySubtract();
        model.memoryRecall();
        assertEquals("0", model.getDisplayText());

        model.memoryClear();
        model.memoryRecall();
        assertEquals("0", model.getDisplayText());
    }

    @Test
    void clearResetsCalculatorAndMemory() throws Exception {
        model.inputDigit('4');
        model.square();
        model.memoryAdd();
        model.clearAll();
        model.memoryRecall();

        assertEquals("0", model.getDisplayText());
        assertNull(model.getVisualActiveOperation());
    }

    @Test
    void overflowThrowsOutOfRange() throws Exception {
        for (int i = 0; i < 309; i++) {
            model.inputDigit('9');
        }
        CalculatorException ex = assertThrows(CalculatorException.class, model::square);
        assertEquals("Error: out of range", ex.getMessage());
    }
}
