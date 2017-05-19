package gui.sprite;

import java.awt.Image;
import gui.animation.*;

public class Sprite {

    private Animation animation;
    private float x;
    private float y;

    public Sprite(Animation animation, float x, float y) {
        this.animation = animation;
        this.x = x;
        this.y = y;
    }

    // animate
    public void update(long timePassed) {
        this.animation.update(timePassed);
    }

    // position setter
    public void updateXY(float x, float y) {
        this.x = x;
        this.y = y;
    }

    // Sprite Image getter
    public Image getImage() {
        return this.animation.getImage();
    }

    // Position x getter
    public float getX() {
        return this.x;
    }

    // Position y getter
    public float getY() {
        return this.y;
    }

}
