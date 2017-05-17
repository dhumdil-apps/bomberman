import java.awt.*;
import javax.swing.*;

// gui

// core
import gui.screen.Screen;

public class Main extends JFrame {

    public static void main(String[] args) {

        // http://docs.oracle.com/javase/6/docs/api/java/awt/DisplayMode.html
        DisplayMode dm = new DisplayMode(1366, 768, DisplayMode.BIT_DEPTH_MULTI, DisplayMode.REFRESH_RATE_UNKNOWN);
        Main game = new Main();

        game.run(dm);

        // int size = selectSize("xlg");
        // int lvl = selectLevel("insane");

        // new Board(size, lvl);

    }

    // gui
    private void run(DisplayMode dm) {

        setBackground(Color.BLACK);
        setForeground(Color.cyan);
        setFont( new Font("Arial", Font.PLAIN, 24) );

        Screen s = new Screen();

        try {

            s.setFullScreen(dm);

            // visualize and get back to normal
            try {
                Thread.sleep(5000);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

        } finally {
            s.restoreScreen();
        }

    }
    public void paint(Graphics g) {

        if (g instanceof Graphics2D) {
            Graphics2D g2 = (Graphics2D)g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        }

        g.drawString("Test", 200, 200);

    }

    // core
    private static int selectSize(String size) {

        switch (size) {
            case "xsm": return 1;
            case "sm": return 2;
            case "md": return 3;
            case "lg": return 4;
            case "xlg": return 5;
            default: return 2;
        }

    }
    private static int selectLevel(String level) {

        switch (level) {
            case "basic": return 0;
            case "easy": return 1;
            case "medium": return 2;
            case "hard": return 3;
            case "insane": return 4;
            default: return 2;
        }

    }

}
