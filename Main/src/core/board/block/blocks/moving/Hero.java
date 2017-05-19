package core.board.block.blocks.moving;

// core
import core.board.Board;

// gui
import gui.animation.Animation;
import gui.sprite.Sprite;

// system
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Hero extends Character implements KeyListener {

    private static boolean listenKeys;

    public Hero(int x, int y, Window window) {

        super(x, y);

        // load images
        Image image1 = new ImageIcon(this.getClass().getResource("/gui/resources/hero1.png")).getImage();
        Image image2 = new ImageIcon(this.getClass().getResource("/gui/resources/hero2.png")).getImage();

        // animate image
        this.animation = new Animation();
        this.animation.addScene(image1, 300);
        this.animation.addScene(image2, 300);

        // animate position
        this.sprite = new Sprite(animation, (imageSize * this.y) + X, (imageSize * this.x) + Y);
        Board.addSprite(this.sprite);

        // add key listener
        window.addKeyListener(this);

    }

    public void live() {

        // listen user input
        Board.logMessage = "Game Started, listening for key events";
        listenKeys = true;

    }

    public void die() {

        Board.logMessage = "You died. Try again?";
        Board.setGameOver();

    }

    // Detect Key Pressed
    public void keyPressed(KeyEvent e) {

        int keyCode = e.getKeyCode();

        if (keyCode == KeyEvent.VK_ESCAPE || keyCode == KeyEvent.VK_Q) {

            // Board.logMessage = "Are you sure you want to exit?";
            Board.setGameOver();

        } else if (!Board.isGameOver() && listenKeys) {

            if (keyCode == KeyEvent.VK_RIGHT) {

                stepX = 1;

                if (Board.isEmpty(this.x, (this.y+1))) {
                    move("right");
                } else if (Board.isEnemy(this.x, (this.y+1))) {
                    this.die();
                }

            }

            if (keyCode == KeyEvent.VK_LEFT) {

                stepX = -1;

                if (Board.isEmpty(this.x, (this.y-1))) {
                    move("left");
                } else if (Board.isEnemy(this.x, (this.y-1))) {
                    this.die();
                }

            }

            if (keyCode == KeyEvent.VK_UP) {

                stepY = -1;

                if (Board.isEmpty((this.x-1), this.y)) {
                    move("up");
                } else if (Board.isEnemy((this.x-1), this.y)) {
                    this.die();
                }

            }

            if (keyCode == KeyEvent.VK_DOWN) {

                stepY = 1;

                if (Board.isEmpty((this.x+1), this.y)) {
                    move("down");
                } else if (Board.isEnemy((this.x+1), this.y)) {
                    this.die();
                }

            }

        }

        Board.keyEventMessage = "Pressed: " + KeyEvent.getKeyText(keyCode);
        e.consume();

    }

    // Detect key release
    public void keyReleased(KeyEvent e) {

        int keyCode = e.getKeyCode();

        if (!Board.isGameOver() && listenKeys) {

            if (keyCode == KeyEvent.VK_DOWN || (keyCode == KeyEvent.VK_UP)) {
                stepY = 0;
            }
            if (keyCode == KeyEvent.VK_RIGHT || (keyCode == KeyEvent.VK_LEFT)) {
                stepX = 0;
            }

        }

        Board.keyEventMessage = "Released: " + KeyEvent.getKeyText(keyCode);
        e.consume();

    }

    // required by KeyEvent
    public void keyTyped(KeyEvent e) {
        e.consume();
    }

}
