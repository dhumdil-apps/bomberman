
public class Main {

    public static void main(String[] args) {

         System.out.println("i'm fun at parties :D");

        // Init
        Field f = new Field(5);

    }

}

/**
 * Block Legend:
 * 0 - empty space
 * 1 - wall
 * 2 - brick
 * 3 - hero
 * 4 - enemy
 * 5 - bomb
 */
public class Field {

    // field
    private int[][] board;
    private int size;
    // characters
    private Hero hero;
    private Enemy[] enemies;

    Field(int size) {

        this.size = size;
        this.initBoard(0);
    }

    /**
     * Create Board
     * @param lvl
     */
    private void initBoard(int lvl) {
        // TODO: set dificulty based on lvl

        this.board = new int[size][size];

        initWalls(this.size);
        initHero();
        initEnemies(lvl);
        initBricks(this.size);

    }

    private void initWalls(int size) {

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {

                if (i % 2 && j % 2) {
                    this.board[i][j] = 1; // wall
                } else {
                    this.board[i][j] = 0; // empty space
                }

            }
        }

    }

    private void initHero() {

        // init hero on top-left corner
        int x = 0; // top
        int y = 0; // left

        this.board[x][y] = 3; // hero
        this.hero = new Hero(x, y);

    }

    private void initEnemies(int lvl) {

        // TODO: based on lvl
        // TODO: randomize
        // init 2 enemies on bottom-left corner
        int numOfEnemies = 2;
        this.enemies = new Enemy[numOfEnemies];
        for (int i = 0; i < numOfEnemies; i++) {
            int randomX = this.size; // bottom
            int randomY = i; // left

            this.board[randomX][randomY] = 4; // enemy
            this.enemies[i] = Enemy(randomX, randomY);
        }

    }

    private initBricks(int size) {

        // init bricks

    }

}

public class Block {
    // TODO: make every field (Bomb, Character, Brick, Wall, ...) a block
    // TODO: store here the positions...
}

public class Brick {
    // TODO
}

public class Character {

    // position
    private int x;
    private int y;

    Character(int x, int y) {
        this.x = x;
        this.y = y;
    }

}

public class Hero extends Character {

    Hero(int x, int y) {
        super(x, y);

        this.init();
    }

    private void init() {
        System.out.println("i'm a Hero!");
    }

}
public class Enemy extends Character {

    Enemy(int x, int y) {
        super(x, y);

        this.init();
    }

    private void init() {
        System.out.println("i'm an Enemy!");
    }

}

public class Bomb {
    // TODO
}
