import java.awt.*;
import javax.swing.*;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

// gui
import gui.screen.*;
import gui.sprite.*;
import gui.animation.*;

// core
import core.board.*;

public class Main extends JFrame implements KeyListener {

    public static void main(String[] args) {
        new Main().run();
        // new Board();
    }

    // GUI
    private Animation animation;
    private Screen screen;
    private Window window;
    private Sprite sprite;

    // messages
    private String keyEventMessage;
    private String errMessage;
    private String helpMessage;

    // game
    private boolean running;

    // position
    private long x = 333;
    private long y = 33;

    // move
    private int stepY;
    private int stepX;

    // CORE
    private void run() {

        // init config
        setBackground(Color.BLACK);
        setForeground(Color.cyan);
        setFont( new Font("Arial", Font.PLAIN, 24) );

        // output messages
        keyEventMessage = "Navigate using arrow keys.";
        errMessage = "";
        helpMessage = "Press Esc to Exit.";

        running = true;

        screen = new Screen();

        try {

            // make full screen
            DisplayMode dm = screen.getDisplayMode();
            screen.setFullScreen(dm);

            // get window and add key event listener
            window = screen.getFullScreen();
            window.setFocusTraversalKeysEnabled(false);
            window.addKeyListener(this);

            // load images
            Image background = new ImageIcon(this.getClass().getResource("/resources/bg.jpg")).getImage();
            Image face1 = new ImageIcon(this.getClass().getResource("/resources/face1.png")).getImage();
            Image face2 = new ImageIcon(this.getClass().getResource("/resources/face2.png")).getImage();

            // add animation
            animation = new Animation();
            animation.addScene(face1, 300);
            animation.addScene(face2, 300);

            // animate the image position
            sprite = new Sprite(animation);

            // movieLoop
            long cumulativeTime = System.currentTimeMillis();
            while (running) {

                long timePassed = System.currentTimeMillis() - cumulativeTime;
                cumulativeTime += timePassed;

                // update X position
                if (sprite.getX() <= 343 && stepX < 0) {
                    x = 333;
                } else if (sprite.getX() >= 923 && stepX > 0) {
                    x = 933;
                } else {
                    x += stepX;
                }

                // update Y position
                if (sprite.getY() >= 623 && stepY > 0) {
                    y = 633;
                } else if (sprite.getY() <= 43 && stepY < 0) {
                    y = 33;
                } else {
                    y += stepY;
                }

                sprite.update(timePassed, x, y);

                // draw & update screen
                Graphics2D g = screen.getGraphics();

                // draw background
                g.drawImage(background, 0, 0, null);

                // draw sprite
                g.drawImage(sprite.getImage(), Math.round(sprite.getX()), Math.round(sprite.getY()), null);

                // draw key event messages
                g.drawString(helpMessage, 30, 30);
                g.drawString(keyEventMessage, 30, 50);
                if (!errMessage.equals("")) {
                    g.drawString(errMessage, 30, 700);
                }

                // reinit view...
                g.dispose();
                screen.update();

                // 'sleep'
                try {
                     Thread.sleep(50);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }

            }

        } finally {
            screen.restoreScreen();
        }

    }

    // KEY events
    public void keyPressed(KeyEvent e) {

        int keyCode = e.getKeyCode();

        if (keyCode == KeyEvent.VK_ESCAPE || keyCode == KeyEvent.VK_Q) {
            running = false;
        } else {

            if (keyCode == KeyEvent.VK_RIGHT) {
                stepX = 10;
            }

            if (keyCode == KeyEvent.VK_LEFT) {
                stepX = -10;
            }

            if (keyCode == KeyEvent.VK_UP) {
                stepY = -10;
            }

            if (keyCode == KeyEvent.VK_DOWN) {
                stepY = 10;
            }

            keyEventMessage = "Pressed: " + KeyEvent.getKeyText(keyCode);
            e.consume();

        }

    }
    public void keyReleased(KeyEvent e) {

        int keyCode = e.getKeyCode();

        // Y not moving
        if (keyCode == KeyEvent.VK_DOWN || (keyCode == KeyEvent.VK_UP)) {
            stepY = 0;
        }

        // X not moving
        if (keyCode == KeyEvent.VK_RIGHT || (keyCode == KeyEvent.VK_LEFT)) {
            stepX = 0;
        }

        // keyEventMessage = "Released: " + KeyEvent.getKeyText(keyCode);
        e.consume();

    }
    public void keyTyped(KeyEvent e) {
        e.consume();
    }

}
