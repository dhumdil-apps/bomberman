
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

    Field(int size) {

        this.size = size;
        this.initBoard();
    }

    private void initBoard() {

        this.board = new int[size][size];

        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {

                if (i % 2 && j % 2) {
                    this.board[i][j] = 1; // wall
                } else {
                    this.board[i][j] = 0; // empty space
                }

            }
        }

        // init hero on top-left corner
        int x = 0;
        int y = 0;
        this.board[x][y] = 3; // hero
        this.hero = new Hero(x, y);

        // init enemies

        // init bricks

    }

}

public class Brick {

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

}
