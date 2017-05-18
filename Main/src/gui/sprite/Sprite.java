package gui.sprite;

import java.awt.Image;
import gui.animation.*;

public class Sprite {

    private Animation animation;
    private float x;
    private float y;
    private float velocityX;
    private float velocityY;

    public Sprite(Animation animation) {
        this.animation = animation;
    }

    public void update(long timePassed) {

        // update position
        this.x += this.velocityX * timePassed;
        this.y += this.velocityY * timePassed;

        this.animation.update(timePassed);

    }

    // Sprite dimension getter
    public int getWidth() {
        return this.animation.getImage().getWidth(null);
    }
    public int getHeight() {
        return this.animation.getImage().getHeight(null);
    }

    // Sprite getter
    public Image getImage() {
        return this.animation.getImage();
    }

    // position getters
    public float getX() {
        return this.x;
    }
    public float getY() {
        return this.y;
    }

    // position setters
    public void setX(long x) {
        this.x = x;
    }
    public void setY(long y) {
        this.y = y;
    }

    // velocity getter
    public float getVelocityX() {
        return this.velocityX;
    }
    public float getVelocityY() {
        return this.velocityY;
    }

    // velocity setter
    public void setVelocityX(float velocityX) {
        this.velocityX = velocityX;
    }
    public void setVelocityY(float velocityY) {
        this.velocityY = velocityY;
    }

}
