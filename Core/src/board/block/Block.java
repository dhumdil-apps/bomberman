package board.block;

abstract public class Block {

    // position on the board
    public int x;
    public int y;

    public Block(int i, int j) {
        this.x = i;
        this.y = j;
    }

    // on initializing the com.block
    abstract public void live();

    // on destroying the com.block
    abstract public void die();

}
