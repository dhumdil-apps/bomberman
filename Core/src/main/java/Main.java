
public class Main {

    public static void main(String[] args) {

         System.out.println("i'm fun at parties :D");

        // Init
        public Field f = new Field(5);

    }

}

public class Field {

    // field
    private Block[][] board;
    private int size;
    // characters
    private Hero hero;
    private Enemy[] enemies;
    private Brick[] bricks;

    Field(int size) {

        this.size = size;
        this.initBoard(0);
    }

    /**
     * Create Board
     * @param lvl
     */
    // TODO: set dificulty based on lvl
    private void initBoard(int lvl) {

        this.board = new Block[size][size];

        initWalls(this.size);
        initHero();
        initEnemies(lvl);
        initBricks(this.size);

    }

    private void initWalls(int size) {

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {

                if (i % 2 && j % 2) {
                    this.board[i][j].type = 1;
                } else {
                    this.board[i][j].type = 0;
                }

            }
        }

    }

    private void initHero() {

        // init hero on top-left corner
        int x = 0; // top
        int y = 0; // left

        this.board[x][y].type = 3; // hero
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

            this.board[randomX][randomY].type = 4; // enemy
            this.enemies[i] = Enemy(randomX, randomY);
        }

    }

    private void initBricks(int size) {

        // TODO: randomize

        // creates a horizontal wall of bricks in the middle
        int middle = 3;
        this.bricks = new Brick[size];
        for (int i = 0; i < size; i++) {
            this.board[middle][i].type = 2; // brick
            this.bricks[i] = new Brick(middle, i);
        }

    }

    public void placeBomb(int x, int y) {
        this.board[x][y].type = 5;
    }

}

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

    Block(int type) {
        this.type = type;
    }

}

public class Brick {

    Brick() {

    }

}

public class Character {

    // position
    private int x;
    private int y;

    Character(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void move() {}

}

public class Hero extends Character {

    Hero(int x, int y) {
        super(x, y);

        this.init();
    }

    private void init() {
        System.out.println("i'm a Hero!");
    }

    public void placeBomb() { }

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

    private int range;
    private int delay;

}
