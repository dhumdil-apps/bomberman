package gui.screen;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.BufferStrategy;
import java.lang.reflect.InvocationTargetException;
import java.nio.Buffer;
import javax.swing.JFrame;

public class Screen {

    // video card
    private GraphicsDevice vc;

    public Screen() {

        // get access to monitor
        GraphicsEnvironment e = GraphicsEnvironment.getLocalGraphicsEnvironment();
        vc = e.getDefaultScreenDevice();

    }

    public DisplayMode findCompatibleMode(DisplayMode[] modes) {

        DisplayMode[] supportedModes = vc.getDisplayModes();

        for(DisplayMode mode: modes) {
            for (DisplayMode supported: supportedModes) {
                if (isModeMatch(mode, supported)) {
                    return mode;
                }
            }
        }

        return null;

    }

    private boolean isModeMatch(DisplayMode m1, DisplayMode m2) {

        // validate width & height
        if (m1.getWidth() != m2.getWidth()) {
            return false;
        }
        if (m1.getHeight() != m2.getHeight()) {
            return false;
        }

        // validate bit depth
        if (m1.getBitDepth() != m2.getBitDepth()) {
            return false;
        }
        if (m1.getBitDepth() != DisplayMode.BIT_DEPTH_MULTI) {
            return false;
        }
        if (m2.getBitDepth() != DisplayMode.BIT_DEPTH_MULTI) {
            return false;
        }

        // validate refresh date
        if (m1.getRefreshRate() != m2.getRefreshRate()) {
            return false;
        }
        if (m1.getRefreshRate() != DisplayMode.REFRESH_RATE_UNKNOWN) {
            return false;
        }
        if (m2.getRefreshRate() != DisplayMode.REFRESH_RATE_UNKNOWN) {
            return false;
        }

        return true;

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

    public int getWidth() {

        Window w = vc.getFullScreenWindow();

        if (w != null) {
            return w.getWidth();
        }

        return 0;

    }

    public int getHeight() {

        Window w = vc.getFullScreenWindow();

        if (w != null) {
            return w.getHeight();
        }

        return 0;

    }

    public Window getFullScreen() {

        // get screen window
        return vc.getFullScreenWindow();

    }

    public DisplayMode[] getCompatibleDMs() {

        // get compatible display modes
        return vc.getDisplayModes();

    }

    public DisplayMode getCurrentDM() {

        // the current dm
        return vc.getDisplayMode();

    }

    public BufferedImage createImage(int w, int h, int t) {

        Window window = vc.getFullScreenWindow();

        if (window != null) {
            GraphicsConfiguration gc = window.getGraphicsConfiguration();
            return gc.createCompatibleImage(w, h, t);
        }

        return null;

    }

}
