package board.block.blocks.moving;

import java.util.Scanner;
import board.Board;
import board.block.Block;
import board.block.blocks.standing.EmptyBlock;

public class Hero extends Character implements Runnable {

    // user input
    private static Scanner cin = new Scanner(System.in);

    public Hero(int x, int y) {
        super(x, y);
    }

    public void live() {

        // show instructions
        System.out.print("q: quit \t");
        System.out.print("w: up \t");
        System.out.print("a: left \t");
        System.out.print("s: down \t");
        System.out.print("d: right \n");

        Board.viewBoard();

        // get the user input
        String direction = selectDirection(cin.next());

        // check if the user input is valid
        if (direction.equals("")) {
            System.out.println("Input error, check the instructions:");
            this.live();
        }

        // check if the user requested to quit
        if (direction.equals("q")) {
            System.out.println("Quitting...");
            this.die();
        }

        // movement
        this.validateMove(direction);

        // repeat if it's not game over
        if (!Board.isGameOver())
            this.live();

    }

    /**
     * CLI movement
     *
     * - invalid moves => ignore
     * - valid move with direction on empty-block => move
     * - valid move with direction on enemy => game over
     *
     * @param direction - the movement direction
     */
    private void validateMove(String direction) {

        switch (direction) {
            case "down": {

                if (Board.isEmpty((this.x+1), this.y)) {
                    move(direction);
                } else if (Board.isEnemy((this.x+1), this.y)) {
                    this.die();
                }

                break;
            }
            case "up": {

                if (Board.isEmpty((this.x-1), this.y)) {
                    move(direction);
                } else if (Board.isEnemy((this.x-1), this.y)) {
                    this.die();
                }

                break;
            }
            case "right": {

                if (Board.isEmpty(this.x, (this.y+1))) {
                    move(direction);
                } else if (Board.isEnemy(this.x, (this.y+1))) {
                    this.die();
                }

                break;
            }
            case "left": {

                if (Board.isEmpty(this.x, (this.y-1))) {
                    move(direction);
                } else if (Board.isEnemy(this.x, (this.y-1))) {
                    this.die();
                }

                break;
            }
            default: break;
        }

    }

    public void die() {

        System.out.println("Game Over!");
        Board.setGameOver();

    }

    /**
     * User Input
     * - directions: 'w', 'a', 's', 'd' (up, left, down, right).
     * - closing the game: 'q'.
     * - any other string is evaluated as empty string.
     *
     * @param input - user input
     * @return - 'the direction', 'quit' or 'invalid'
     */
    private static String selectDirection(String input) {

        switch (input) {
            case "w": return "up";
            case "a": return "left";
            case "s": return "down";
            case "d": return "right";
            case "q": return "q";
            default:  return "";
        }

    }

}
