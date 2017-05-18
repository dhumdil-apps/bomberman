import java.awt.*;
import java.awt.Image;
import javax.swing.*;

// gui
import gui.screen.*;
import gui.animation.*;

// core
import core.board.*;

public class Main extends JFrame {

    public static void main(String[] args) {

        Main game = new Main();
        game.run();

        // new Board();

    }

    // GUI

    private Animation animation;
    private Screen screen;

    private void run() {

        setBackground(Color.BLACK);
        setForeground(Color.cyan);
        setFont( new Font("Arial", Font.PLAIN, 24) );

        screen = new Screen();

        try {

            DisplayMode dm = screen.getDisplayMode();
            screen.setFullScreen(dm);


            // load images
            Image background = new ImageIcon(this.getClass().getResource("/resources/bg.jpg")).getImage();
            Image face1 = new ImageIcon(this.getClass().getResource("/resources/face1.png")).getImage();
            Image face2 = new ImageIcon(this.getClass().getResource("/resources/face2.png")).getImage();

            // add animation
            animation = new Animation();
            animation.addScene(face1, 250);
            animation.addScene(face2, 250);

            // movieLoop
            long startingTime = System.currentTimeMillis();
            long cumulativeTime = startingTime;

            while (cumulativeTime - startingTime < 6000) {

                long timePassed = System.currentTimeMillis() - cumulativeTime;
                cumulativeTime += timePassed;
                animation.update(timePassed);

                // draw & update screen
                Graphics2D g = screen.getGraphics();

                // draw graphics: bg & hero:
                g.drawImage(background, 0, 0, null);
                g.drawImage(animation.getImage(), 333, 33, null);

                // reinit...
                g.dispose();
                screen.update();

                // 'animate'
                try {
                    Thread.sleep(50);
                } catch (Exception e) { }

            }


        } finally {
            screen.restoreScreen();
        }

    }

}
