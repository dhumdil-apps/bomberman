package field;

import field.block.Block;
import field.block.bomb.Bomb;
import field.block.character.Character;
import field.block.empty.Empty;
import field.block.wall.Wall;
import field.block.brick.Brick;
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

        // TODO:
        // this.go();

    }

    // create borders and walls
    private void initWalls() {

        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {

                if ((i%2 == 0) && (j%2 == 0)) {

                    // walls inside board
                    this.board[i][j] = new Wall(i, j);

                } else if (i == 0 || j == 0 || i == this.size - 1 || j == this.size - 1) {

                    // border walls
                    this.board[i][j] = new Wall(i, j);

                } else {

                    // empty block
                    this.board[i][j] = new Empty(i, j);

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

    // creates a horizontal wall of bricks
    // in the middle of the board
    // TODO: randomize positions
    private void initBricks() {

        final int middle = 3;
        for (int i = 1; i < this.size - 1; i++) {
            this.board[middle][i] = new Brick(middle, i);
        }

    }

    public void placeBomb(int x, int y) {
        this.board[x][y] = new Bomb(x, y);
    }

    // method for visualization
    public void printField() {

        for (int i = 0; i < this.size; i++) {

            for (int j = 0; j < this.size; j++) {

                if (this.board[i][j] instanceof Wall) {
                    System.out.print( "\t#" );
                } else if (this.board[i][j] instanceof Brick) {
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

    }

    private void go() {

        // simulate keyboard movement
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {

                if (this.board[i][j] instanceof Hero) {
                    move("right", i, j);
                } else if (this.board[i][j] instanceof Enemy) {
                    move("left", i, j);
                }

            }
        }

    }

    // character movement
    private void move(String direction, int i, int j) {

        Block tmp1 = checkMove(direction, this.board[i][j]);
        Block tmp2 = this.board[i][j];
        this.board[i][j] = tmp1;
        this.board[tmp1.x][tmp1.y] = tmp2;

    }

    private Block checkMove(String direction, Block character) {

        switch (direction) {
            case "up": {

                // check if empty
                if (this.board[character.x][character.y-1] instanceof Empty) {
                    character.y--;
                }

                break;
            }
            case "down": {

                // check if empty
                if (this.board[character.x][character.y+1] instanceof Empty) {
                    character.y++;
                }

                break;
            }
            case "right": {

                // check if empty
                if (this.board[character.x+1][character.y] instanceof Empty) {
                    character.x++;
                }

                break;
            }
            case "left": {


                // check if empty
                if (this.board[character.x-1][character.y] instanceof Empty) {
                    character.x--;
                }

                break;
            }
            default: break;
        }

        return character;

    }

}
