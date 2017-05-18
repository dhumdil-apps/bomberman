package gui.sprite;

import java.awt.Image;
import gui.animation.*;

public class Sprite {

    private Animation animation;
    private float x;
    private float y;

    // Sprite dimensions: 100x100
    public Sprite(Animation animation) {
        this.animation = animation;
    }

    public void update(long timePassed, float x, float y) {

        // update position
        this.x = x;
        this.y = y;

        // animate
        this.animation.update(timePassed);

    }

    // Sprite Image getter
    public Image getImage() {
        return this.animation.getImage();
    }

    // position getters/setters
    public float getX() {
        return this.x;
    }
    public float getY() {
        return this.y;
    }
    public void setX(long x) {
        this.x = x;
    }
    public void setY(long y) {
        this.y = y;
    }

}
