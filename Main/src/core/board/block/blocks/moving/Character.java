package core.board.block.blocks.moving;

import core.board.Board;
import core.board.block.Block;
import core.board.block.blocks.standing.EmptyBlock;

abstract public class Character extends Block {

    Character(int x, int y) {
        super(x, y);
        Board.createBlock(this);
    }

    public void run() {
        this.live();
    }

    abstract public void live();

    void move(String direction) {

        switch (direction) {
            case "down": {

                Block empty = new EmptyBlock(this.x, this.y);
                Board.createBlock(empty);
                this.x++;
                Board.createBlock(this);

                break;
            }
            case "up": {

                Block empty = new EmptyBlock(this.x, this.y);
                Board.createBlock(empty);
                this.x--;
                Board.createBlock(this);

                break;
            }
            case "right": {

                Block empty = new EmptyBlock(this.x, this.y);
                Board.createBlock(empty);
                this.y++;
                Board.createBlock(this);

                break;
            }
            case "left": {

                Block empty = new EmptyBlock(this.x, this.y);
                Board.createBlock(empty);
                this.y--;
                Board.createBlock(this);

                break;
            }
            default: break;
        }

        // simulate animation
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    abstract public void die();

}
