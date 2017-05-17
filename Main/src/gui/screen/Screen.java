package gui.screen;

import java.awt.*;
import javax.swing.JFrame;

public class Screen {

    private GraphicsDevice vc;

    // pc gui.screen
    public Screen() {
        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        vc = env.getDefaultScreenDevice();
    }

    // make full gui.screen
    public void setFullScreen(DisplayMode displayMode, JFrame window) {

        // make full gui.screen window
        window.setUndecorated(true);
        window.setResizable(false);
        vc.setFullScreenWindow(window);

        // if supported
        if (displayMode != null && vc.isDisplayChangeSupported()) {
            try {

                vc.setDisplayMode(displayMode);

            } catch (Exception e) {
                System.out.println("...");
                System.out.println(e.getMessage());
            }
        }

    }

    public Window getFullScreenWindow() {
        return vc.getFullScreenWindow();
    }

    // restore gui.screen
    public void restoreScreen() {

        Window w = vc.getFullScreenWindow();

        if (w != null) {
            w.dispose();
        }

        vc.setFullScreenWindow(null);

    }


}
