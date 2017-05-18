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
    private String message;
    private boolean running;

    // CORE
    private void run() {

        // init config
        setBackground(Color.BLACK);
        setForeground(Color.cyan);
        setFont( new Font("Arial", Font.PLAIN, 24) );

        message = "Press Esc or Q to Exit.";
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

            // init speed
            sprite.setVelocityX(0.3f);
            sprite.setVelocityY(0.3f);

            // movieLoop
            long startingTime = System.currentTimeMillis();
            long cumulativeTime = startingTime;

            // simulate for 6 sec
            while (running) {

                long timePassed = System.currentTimeMillis() - cumulativeTime;
                cumulativeTime += timePassed;

                // update velocity
                if (sprite.getX() < 0) {
                    sprite.setVelocityX(Math.abs(sprite.getVelocityX()));
                } else if (sprite.getX() + sprite.getWidth() >= screen.getWidth()) {
                    sprite.setVelocityX(-Math.abs(sprite.getVelocityX()));
                }
                if (sprite.getY() < 0) {
                    sprite.setVelocityY(Math.abs(sprite.getVelocityY()));
                } else if (sprite.getY() + sprite.getHeight() >= screen.getHeight()) {
                    sprite.setVelocityY(-Math.abs(sprite.getVelocityY()));
                }

                // update position
                sprite.update(timePassed);


                // draw & update screen
                Graphics2D g = screen.getGraphics();

                // draw background
                g.drawImage(background, 0, 0, null);

                // draw sprite
                g.drawImage(sprite.getImage(), Math.round(sprite.getX()), Math.round(sprite.getY()), null);

                // reinit...
                g.dispose();
                screen.update();

                // 'sleep'
                try {
                    Thread.sleep(20);
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

            message = "You Pressed: " + KeyEvent.getKeyText(keyCode);
            e.consume();

        }

    }
    public void keyReleased(KeyEvent e) {

        int keyCode = e.getKeyCode();

        message = "You Released: " + KeyEvent.getKeyText(keyCode);
        e.consume();

    }
    public void keyTyped(KeyEvent e) {
        e.consume();
    }

// draw
//    public synchronized void draw(Graphics2D g) {
//
//        Window w = screen.getFullScreen();
//        g.setBackground(w.getBackground());
//        g.fillRect(0, 0, screen.getWidth(), screen.getHeight());
//        g.setColor(w.getForeground());
//        g.drawString(message, 30, 30);
//
//    }

}
