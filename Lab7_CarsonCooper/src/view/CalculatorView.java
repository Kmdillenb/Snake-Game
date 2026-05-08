package view;

import model.Operation;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class CalculatorView {
    public static final Color IDLE_COLOR = new Color(240, 240, 240);
    public static final Color ACTIVE_COLOR = new Color(255, 201, 107);

    private final JFrame frame;
    private final JTextField display;
    private final Map<String, JButton> buttons = new LinkedHashMap<>();

    public CalculatorView() {
        frame = new JFrame("Lab7_CarsonCooper Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout(8, 8));

        display = new JTextField("0");
        display.setHorizontalAlignment(SwingConstants.RIGHT);
        display.setEditable(false);
        display.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 26));
        display.setBorder(new LineBorder(Color.GRAY, 1));

        JPanel root = new JPanel(new BorderLayout(8, 8));
        root.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        root.add(display, BorderLayout.NORTH);
        root.add(buildButtonPanel(), BorderLayout.CENTER);

        frame.add(root, BorderLayout.CENTER);
        frame.pack();
        frame.setMinimumSize(new Dimension(420, 520));
        frame.setLocationRelativeTo(null);
    }

    private JPanel buildButtonPanel() {
        JPanel panel = new JPanel(new GridLayout(6, 4, 6, 6));

        addButton(panel, "MC", "MEM_CLEAR");
        addButton(panel, "MR", "MEM_RECALL");
        addButton(panel, "M+", "MEM_ADD");
        addButton(panel, "M-", "MEM_SUBTRACT");

        addButton(panel, "Del", "DELETE");
        addButton(panel, "C", "CLEAR");
        addButton(panel, "x²", "SQUARE");
        addButton(panel, "√", "SQRT");

        addButton(panel, "7", "DIGIT_7");
        addButton(panel, "8", "DIGIT_8");
        addButton(panel, "9", "DIGIT_9");
        addButton(panel, "÷", "DIVIDE");

        addButton(panel, "4", "DIGIT_4");
        addButton(panel, "5", "DIGIT_5");
        addButton(panel, "6", "DIGIT_6");
        addButton(panel, "×", "MULTIPLY");

        addButton(panel, "1", "DIGIT_1");
        addButton(panel, "2", "DIGIT_2");
        addButton(panel, "3", "DIGIT_3");
        addButton(panel, "-", "SUBTRACT");

        addButton(panel, "0", "DIGIT_0");
        addButton(panel, ".", "DECIMAL");
        addButton(panel, "=", "EQUALS");
        addButton(panel, "+", "ADD");

        return panel;
    }

    private void addButton(JPanel panel, String text, String command) {
        JButton button = new JButton(text);
        button.setActionCommand(command);
        button.setFocusPainted(false);
        button.setOpaque(true);
        button.setBackground(IDLE_COLOR);
        button.setBorder(new LineBorder(Color.GRAY, 1));
        panel.add(button);
        buttons.put(command, button);
    }

    public void addActionListener(ActionListener actionListener) {
        for (JButton button : buttons.values()) {
            button.addActionListener(actionListener);
        }
    }

    public void updateDisplay(String text) {
        display.setText(text);
    }

    public void setActiveOperation(Operation operation) {
        resetOperationButtonStates();
        if (operation == null) {
            return;
        }

        String command = switch (operation) {
            case ADD -> "ADD";
            case SUBTRACT -> "SUBTRACT";
            case MULTIPLY -> "MULTIPLY";
            case DIVIDE -> "DIVIDE";
        };

        JButton button = buttons.get(command);
        if (button != null) {
            button.setBackground(ACTIVE_COLOR);
            button.setBorder(new LineBorder(Color.DARK_GRAY, 2));
        }
    }

    private void resetOperationButtonStates() {
        for (String key : new String[]{"ADD", "SUBTRACT", "MULTIPLY", "DIVIDE"}) {
            JButton button = buttons.get(key);
            if (button != null) {
                button.setBackground(IDLE_COLOR);
                button.setBorder(new LineBorder(Color.GRAY, 1));
            }
        }
    }

    public void show() {
        frame.setVisible(true);
    }

    public JFrame getFrame() {
        return frame;
    }

    public void saveScreenshot(String path) throws IOException {
        BufferedImage image = new BufferedImage(frame.getWidth(), frame.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = image.createGraphics();
        frame.paint(graphics);
        graphics.dispose();
        ImageIO.write(image, "png", new File(path));
    }
}
