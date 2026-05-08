package ui;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CalculatorSeleniumUiTest {
    private WebDriver driver;

    @BeforeEach
    void setUp() {
        driver = new HtmlUnitDriver(true);
        String page = Path.of("test", "ui", "resources", "calculator_ui_harness.html").toUri().toString();
        driver.get(page);
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }

    private void click(String id) {
        driver.findElement(By.id(id)).click();
    }

    private String display() {
        return driver.findElement(By.id("display")).getAttribute("value");
    }

    // GUI function tests (6)
    @Test
    void functionAddition() { click("d2"); click("opAdd"); click("d3"); click("equals"); assertEquals("5", display()); }

    @Test
    void functionSubtraction() { click("d9"); click("opSub"); click("d4"); click("equals"); assertEquals("5", display()); }

    @Test
    void functionMultiplication() { click("d3"); click("opMul"); click("d4"); click("equals"); assertEquals("12", display()); }

    @Test
    void functionDivisionByZeroError() { click("d8"); click("opDiv"); click("d0"); click("equals"); assertEquals("Error: division by zero", display()); }

    @Test
    void functionSquare() { click("d7"); click("square"); assertEquals("49", display()); }

    @Test
    void functionMemoryFlow() { click("d5"); click("square"); click("madd"); click("mrecall"); assertEquals("25", display()); }

    // Display behavior tests (3)
    @Test
    void displayOmitsOperationSymbolDuringPendingOperation() { click("d2"); click("opAdd"); assertEquals("2", display()); }

    @Test
    void displayShowsOnlyOperandWhileTypingSecondOperand() { click("d2"); click("opAdd"); click("d3"); assertEquals("3", display()); }

    @Test
    void displayShowsResultOnlyAfterEquals() { click("d2"); click("opAdd"); click("d3"); click("equals"); assertEquals("5", display()); }

    // Active button visual state tests (3)
    @Test
    void activeStateSetWhenOperationPressed() {
        click("d2"); click("opAdd");
        assertTrue(driver.findElement(By.id("opAdd")).getAttribute("class").contains("active"));
    }

    @Test
    void activeStateClearsWhenNextOperandStarts() {
        click("d2"); click("opAdd"); click("d3");
        assertFalse(driver.findElement(By.id("opAdd")).getAttribute("class").contains("active"));
    }

    @Test
    void activeStateClearsAfterResult() {
        click("d2"); click("opMul"); click("d3"); click("equals");
        assertFalse(driver.findElement(By.id("opMul")).getAttribute("class").contains("active"));
    }
}
