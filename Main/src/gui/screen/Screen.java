package gui.screen;

import java.awt.*;
import java.awt.image.*;
import javax.swing.JFrame;

public class Screen {

    // video card
    private GraphicsDevice vc;

    public Screen() {

        // get access to monitor
        GraphicsEnvironment e = GraphicsEnvironment.getLocalGraphicsEnvironment();
        vc = e.getDefaultScreenDevice();

    }

    public DisplayMode getDisplayMode() {

        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] gs = ge.getScreenDevices();

        if (gs.length > 0) {
            return gs[0].getDisplayMode();
        }

        return null;
    }

    public void setFullScreen(DisplayMode dm) {

        // basic config for the frame
        JFrame frame = new JFrame();
        frame.setUndecorated(true);
        frame.setIgnoreRepaint(true);
        frame.setResizable(false);

        vc.setFullScreenWindow(frame);

        if (dm != null && vc.isDisplayChangeSupported()) {
            try {

                vc.setDisplayMode(dm);

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        // use two buffers
        frame.createBufferStrategy(2);

    }

    public void restoreScreen() {

        Window w = vc.getFullScreenWindow();

        if (w != null) {
            w.dispose();
        }

        vc.setFullScreenWindow(null);

    }

    public Graphics2D getGraphics() {

        Window w = vc.getFullScreenWindow();

        if (w != null) {
            BufferStrategy s = w.getBufferStrategy();
            return (Graphics2D)s.getDrawGraphics();
        }

        return null;

    }

    public void update() {

        Window w = vc.getFullScreenWindow();

        if (w != null) {

            BufferStrategy s = w.getBufferStrategy();

            if (!s.contentsLost()) {
                s.show();
            }

        }

    }

    public Window getFullScreen() {

        // get screen window
        return vc.getFullScreenWindow();

    }

}
