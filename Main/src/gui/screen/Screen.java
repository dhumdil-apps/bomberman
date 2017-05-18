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

        for (int i = 0; i < gs.length; i++) {

            DisplayMode dm = gs[i].getDisplayMode();

            int width = dm.getWidth();
            int height = dm.getHeight();
            System.out.println(width +"x"+ height);

            int refreshRate = dm.getRefreshRate();
            if (refreshRate == DisplayMode.REFRESH_RATE_UNKNOWN) {
                System.out.println("Unknown rate");
            } else {
                System.out.println("Refresh rate: " + refreshRate);
            }

            int bitDepth = dm.getBitDepth();
            if (bitDepth != DisplayMode.BIT_DEPTH_MULTI) {
                System.out.println("BIT DEPTH: MULTI");
            } else {
                System.out.println("BIT DEPTH: " + bitDepth);
            }

            return dm;

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
