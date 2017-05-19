package core.board.block;

abstract public class Block {

    public int x;
    public int y;
    protected final static int imageSize = 100;
    protected final static int X = 333;
    protected final static int Y = 33;

    public Block(int i, int j) {
        this.x = i;
        this.y = j;
    }

    // on initializing the com.block
    abstract public void live();

    // on destroying the com.block
    abstract public void die();

}
