package field.block;

/**
 * Block
 */
public class Block {

    /**
     * Types:
     * 0 - empty space
     * 1 - wall
     * 2 - brick
     * 3 - hero
     * 4 - enemy
     * 5 - bomb
     */
    public int type;

    public Block(int type) {
        this.type = type;
    }

}

