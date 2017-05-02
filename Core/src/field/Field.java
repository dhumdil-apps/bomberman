package field;

import field.block.Block;
import field.block.bomb.Bomb;
import field.block.border.BorderBlock;
import field.block.empty.EmptyBlock;
import field.block.wall.WallBlock;
import field.block.character.hero.Hero;
import field.block.character.enemy.Enemy;

/**
 * Field
 */
public class Field {

    private Block[][] board;
    private int size;

    public Field(int size) {
        this.size = size;
        this.initBoard();
    }

    // TODO: set difficulty based on lvl
    private void initBoard() {

        this.board = new Block[size][size];

        this.initWalls();
        this.initHero();
        this.initEnemies();
        this.initBricks();

        // visualize...
        this.printField();
        this.start();
        this.printField();

    }

    // create borders and walls
    private void initWalls() {

        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {

                if ((i%2 == 0) && (j%2 == 0)) {

                    // walls inside board
                    this.board[i][j] = new BorderBlock(i, j);

                } else if (i == 0 || j == 0 || i == this.size - 1 || j == this.size - 1) {

                    // border walls
                    this.board[i][j] = new BorderBlock(i, j);

                } else {

                    // EmptyBlock block
                    this.board[i][j] = new EmptyBlock(i, j);

                }

            }
        }

    }

    // place the hero on top-left corner
    private void initHero() {

        final int x = 1; // top
        final int y = 1; // left

        this.board[x][y] = new Hero(x, y);

    }

    // place one enemy on bottom-right corner
    // TODO: (change numberOf & typeOf) based on lvl & randomize position
    private void initEnemies() {

        final int x = this.size - 2; // bottom
        final int y = this.size - 2; // right

        this.board[x][y] = new Enemy(x, y);

    }

    // creates a horizontal border of bricks in the middle of the board
    // TODO: randomize positions
    private void initBricks() {

        // static border of bricks...
        this.board[5][1] = new WallBlock(5, 1);
        this.board[3][3] = new WallBlock(3, 3);
        this.board[1][5] = new WallBlock(1, 5);

    }

    public void placeBomb(int x, int y) {
        this.board[x][y] = new Bomb(x, y);
    }

    // method for visualization
    private void printField() {

        for (int i = 0; i < this.size; i++) {

            for (int j = 0; j < this.size; j++) {

                if (this.board[i][j] instanceof BorderBlock) {
                    System.out.print( "\t#" );
                } else if (this.board[i][j] instanceof WallBlock) {
                    System.out.print( "\t+" );
                } else if (this.board[i][j] instanceof Hero) {
                    System.out.print( "\tH" );
                } else if (this.board[i][j] instanceof Enemy) {
                    System.out.print( "\tE" );
                } else if (this.board[i][j] instanceof Bomb) {
                    System.out.print( "\tB" );
                } else {
                    System.out.print( "\t." );
                }

            }

            System.out.println();
        }
        System.out.println();
        System.out.println();

    }

    // listen for keyboard events
    private void start() {

        // simulate movement of enemy (he's position being (size-2, size-2))
        this.move("up", this.size - 2, this.size - 2);

        // simulate movement of hero (he's position being (1,1))
        this.move("right", 1, 1);

    }

    // character movement
    private void move(String direction, int i, int j) {

        // validate move and store the new position
        Block character = validateMove(direction, this.board[i][j]);
        final int x = character.x;
        final int y = character.y;

        // if the position was updated
        if (x != i || y != j) {

            // swap content using a temporary variable -> (i,j) <=> (x,y)
            final Block tmp = this.board[i][j];
            this.board[i][j] = this.board[x][y];
            this.board[x][y] = tmp;

        }

    }

    // check if valid
    private Block validateMove(String direction, Block character) {

        switch (direction) {
            case "down": {

                if (this.board[character.x + 1][character.y] instanceof EmptyBlock) {
                    character.x = character.x + 1;
                }

                break;
            }
            case "up": {

                if (this.board[character.x - 1][character.y] instanceof EmptyBlock) {
                    character.x = character.x - 1;
                }

                break;
            }
            case "right": {

                if (this.board[character.x][character.y + 1] instanceof EmptyBlock) {
                    character.y = character.y + 1;
                }

                break;
            }
            case "left": {

                if (this.board[character.x][character.y - 1] instanceof EmptyBlock) {
                    character.y = character.y - 1;
                }

                break;
            }
            default: break;
        }

        return character;

    }

}
