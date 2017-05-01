package field;

import field.block.Block;
import field.block.character.hero.Hero;
import field.block.character.enemy.Enemy;

/**
 * Field
 */
public class Field {

    // field
    private Block[][] board;
    private int size;

    // characters
    private Hero hero;
    private Enemy[] enemies;

    public Field(int size) {

        this.size = size;
        this.initBoard();

    }

    /**
     * Create Board
     */
    // TODO: set dificulty based on lvl
    private void initBoard() {

        this.board = new Block[size][size];

        initWalls();
        initHero();
        initEnemies();
        initBricks();

    }

    // create borders and walls
    private void initWalls() {

        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {

                if ((i%2 == 0) && (j%2 == 0)) {

                    // walls inside board
                    this.board[i][j] = new Block(1);

                } else if (i == 0 || j == 0 || i == this.size - 1 || j == this.size - 1) {

                    // border walls
                    this.board[i][j] = new Block(1);

                } else {

                    // no walls
                    this.board[i][j] = new Block(0);

                }

            }
        }

    }

    // place the hero on top-left corner
    private void initHero() {

        int x = 1; // top
        int y = 1; // left

        this.board[x][y] = new Block(3); // hero
        this.hero = new Hero(x, y);

    }

    // place one enemy on bottom-right corner
    // TODO: (change numberOf & typeOf) based on lvl & randomize position
    private void initEnemies() {

        int n = 1; // one enemy
        this.enemies = new Enemy[n];

        int x = this.size - 2; // top
        int y = this.size - 2; // left

        this.board[x][y] = new Block(4); // hero
        this.enemies[n - 1] = new Enemy(x, y);

    }

    // creates a horizontal wall of bricks
    // in the middle of the board
    // TODO: randomize positions
    private void initBricks() {

        int middle = 3;
        for (int i = 1; i < this.size - 1; i++) {
            this.board[middle][i] = new Block(2); // brick
        }

    }

    // TODO: use Bomb()
    public void placeBomb(int x, int y) {
        this.board[x][y] = new Block(5);
    }

    // method for visualization
    public void printField() {

        for (int i = 0; i < this.size; i++) {

            for (int j = 0; j < this.size; j++) {
                System.out.print("\t");
                System.out.print( this.shiftTypeToChar(this.board[i][j].type) );
            }

            System.out.println();
        }

    }
    private String shiftTypeToChar(int type) {

        switch (type) {
            case 0: return ".";
            case 1: return "#";
            case 2: return "+";
            case 3: return "H";
            case 4: return "E";
            case 5: return "B";
            default: return "";
        }

    }

}
