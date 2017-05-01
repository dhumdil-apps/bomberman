package field.block;

public class Block {

    // position
    private int x;
    private int y;

    protected void setX(int x) {
        this.x = x;
    }

    protected void setY(int y) {
        this.y = y;
    }

    protected int getX() {
        return this.x;
    }

    protected int getY() {
        return this.y;
    }

}
