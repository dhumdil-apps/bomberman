package core.board.block.blocks.moving;

import core.board.Board;
import core.board.block.Block;
import core.board.block.blocks.standing.EmptyBlock;
import gui.animation.Animation;
import gui.sprite.Sprite;

abstract public class Character extends Block implements Runnable {

    Sprite sprite;
    Animation animation;
    int stepX;
    int stepY;

    Character(int x, int y) {
        super(x, y);
        Board.createBlock(this);
    }

    /**
     * Start live() method on Thread start
     */
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
            default: {
                // Board.logMessage = "Ups, something went wrong...";
                break;
            }
        }

        update();

    }

    private void update() {

        // update x position
        if ( (this.sprite.getX() > X) && (this.sprite.getX() < (X + (Board.getLength()*imageSize))) ) {
            stepX = 0;
        }
        float positionX = ((imageSize * this.y) + X) + (stepX * imageSize);

        // update y position
        if ((this.sprite.getY() > Y) && (this.sprite.getY() < (Y + (Board.getLength()*imageSize))) ) {
            stepY = 0;
        }
        float positionY = ((imageSize * this.x) + Y) + (stepY * imageSize);

        this.sprite.updateXY(positionX, positionY);

    }

    abstract public void die();

}
