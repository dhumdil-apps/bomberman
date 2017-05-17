package core.board.block.blocks.moving;

import core.board.Board;
import core.board.block.Block;
import core.board.block.blocks.standing.EmptyBlock;

import java.util.ArrayList;

public class Enemy extends Character implements Runnable {

    public Enemy(int x, int y) {
        super(x, y);
    }

    public void live() {

        // initialize available directions
        ArrayList<String> directions = new ArrayList<>();
        String direction;
        int random;
        directions.add("up");
        directions.add("right");
        directions.add("down");
        directions.add("left");

        do {
            // if tried all available directions => do nothing
            if (directions.size() == 0) {
                move("sleep"); // Thread.sleep()
                break;
            }

            // get random direction and validate it
            random = Board.randomize(directions.size());
            direction = directions.get(random);
            directions.remove(random);

        } while (this.isInvalidMove(direction));

        // repeat if it's not game over
        if (!Board.isGameOver()) {
            this.live();
        }

    }

    /**
     * Check a random direction and if valid => move
     *
     * @param direction - random direction
     * @return valid/invalid direction,
     * false (on valid) => move
     * true (on invalid) => try another available direction
     */
    private boolean isInvalidMove(String direction) {

        switch (direction) {
            case "down": {

                if (Board.isEmpty((this.x+1), this.y)) {
                    move(direction);
                } else if (Board.isHero((this.x+1), this.y)) {
                    System.out.println("You Lost!");
                    Board.setGameOver();
                } else {
                    return true;
                }

                return false;
            }
            case "up": {

                if (Board.isEmpty((this.x-1), this.y)) {
                    move(direction);
                } else if (Board.isHero((this.x-1), this.y)) {
                    System.out.println("You Lost!");
                    Board.setGameOver();
                } else {
                    return true;
                }

                return false;
            }
            case "right": {

                if (Board.isEmpty(this.x, (this.y+1))) {
                    move(direction);
                } else if (Board.isHero(this.x, (this.y+1))) {
                    System.out.println("You Lost!");
                    Board.setGameOver();
                } else {
                    return true;
                }

                return false;
            }
            case "left": {

                if (Board.isEmpty(this.x, (this.y-1))) {
                    move(direction);
                } else if (Board.isHero(this.x, (this.y-1))) {
                    System.out.println("You Lost!");
                    Board.setGameOver();
                } else {
                    return true;
                }

                return false;
            }
            default: return true;
        }

    }


    public void die() {

        Block empty = new EmptyBlock(this.x, this.y);
        Board.createBlock(empty);

        System.out.println("Enemy died...");

    }

}