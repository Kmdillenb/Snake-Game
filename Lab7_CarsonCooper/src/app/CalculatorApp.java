package app;

import controller.CalculatorController;
import model.CalculatorModel;
import view.CalculatorView;

import javax.swing.*;
import java.io.IOException;

public class CalculatorApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CalculatorModel model = new CalculatorModel();
            CalculatorView view = new CalculatorView();
            new CalculatorController(model, view);
            view.show();

            if (args.length == 2 && "--screenshot".equals(args[0])) {
                try {
                    view.saveScreenshot(args[1]);
                } catch (IOException ignored) {
                    // Screenshot is optional utility output for lab artifacts.
                }
            }
        });
    }
}
