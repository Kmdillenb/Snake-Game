# Lab7_CarsonCooper

This folder contains a standalone Java MVC scientific calculator project added separately from the Snake game.

## Project layout

```text
Lab7_CarsonCooper/
├── pom.xml
├── README.md
├── src/
│   ├── app/CalculatorApp.java
│   ├── controller/CalculatorController.java
│   ├── model/
│   │   ├── CalculatorException.java
│   │   ├── CalculatorModel.java
│   │   └── Operation.java
│   └── view/CalculatorView.java
└── test/
    ├── model/CalculatorModelTest.java
    └── ui/
        ├── CalculatorSeleniumUiTest.java
        └── resources/calculator_ui_harness.html
```

## Requirements covered

- MVC architecture (model, view, controller packages)
- GUI interactions only (Swing desktop UI)
- Digits, decimal, + - * /, square, square root
- Memory buttons: M+, M-, MR, MC
- Delete and Clear support
- Operation buttons keep active/depressed visuals until next operand/result transition
- Display shows operands/results only (never operation symbols)
- Division by zero and out-of-range handling
- Memory add/subtract allowed only for successfully executed results
- Clear resets computation, memory, and GUI state

## Build and run

From `Lab7_CarsonCooper`:

```bash
mvn test
mvn -q -DskipTests compile
java -cp target/classes app.CalculatorApp
```

## Testing

- **Model-level tests**: `test/model/CalculatorModelTest.java`
- **GUI/UI automation tests (Selenium)**: `test/ui/CalculatorSeleniumUiTest.java`
  - Includes 6 GUI function tests
  - Includes 3 display-behavior tests
  - Includes 3 active-button visual-state tests

The Selenium UI tests target the included calculator UI harness (`test/ui/resources/calculator_ui_harness.html`) to automate required UI behavior checks.

## Screenshot

A calculator UI screenshot is generated at:

`docs/calculator-ui.png`
