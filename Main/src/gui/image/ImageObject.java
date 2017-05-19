package gui.image;

import java.awt.Image;

public class ImageObject {

    private Image image;
    private int x;
    private int y;

    public ImageObject(Image image, int x, int y) {
        this.image = image;
        this.x = x;
        this.y = y;
    }

    public Image getImage() {
        return this.image;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

}
