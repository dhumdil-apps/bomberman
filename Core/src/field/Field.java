package field;

import java.util.ArrayList;
import java.util.Random;

// local
import field.block.Block;
import field.block.blocks.Bomb;
import field.block.blocks.Border;
import field.block.blocks.EmptyBlock;
import field.block.blocks.Wall;
import field.block.character.hero.Hero;
import field.block.character.enemy.Enemy;

public class Field {

    private int size;
    private Block[][] board;
    private Random randomGenerator;
    private ArrayList<Block> emptyBlocks;
    private boolean gameOver;
    private boolean isValid;

    public Field(int size, int countBlocks) {

        this.size = size;
        this.gameOver = false;
        this.isValid = false;

        this.randomGenerator = new Random();
        this.board = new Block[this.size][this.size];
        this.emptyBlocks = new ArrayList<Block>(countBlocks);

        this.initBoard();

    }

    /**
     * Initialize Borders, Empty Blocks and reserve space for Hero position
     */
    private void initBoard() {

        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {

                if (this.isBorder(i, j)) {

                    // borders
                    this.board[i][j] = new Border(i, j);

                } else if (i > 2 || j > 2) {

                    // empty blocks
                    this.board[i][j] = new EmptyBlock(i, j);
                    this.emptyBlocks.add(this.board[i][j]);

                } else {

                    // reserve space around hero position (1,1):
                    this.board[i][j] = new Block();

                }

            }
        }

    }

    // initialize
    public ArrayList<Block> initEnemies(int countEnemies) {

        Block block;
        ArrayList<Block> enemies = new ArrayList<Block>(countEnemies);

        for (int i = 0; i < countEnemies; i++) {
            block = this.getRandomEmptyBlock();
            this.board[block.x][block.y] = new Enemy(block.x, block.y);
            enemies.add(this.board[block.x][block.y]);
        }

        return enemies;

    }

    // initialize
    public void initWalls(int countWalls) {

        Block block;

        for (int i = 0; i < countWalls; i++) {
            block = this.getRandomEmptyBlock();
            this.board[block.x][block.y] = new Wall(block.x, block.y);
        }

    }

    // initialize
    public Block initHero() {

        Block hero = new Hero(1, 1);

        this.board[1][1] = hero;
        this.board[1][2] = new EmptyBlock(1, 2);
        this.board[2][1] = new EmptyBlock(2, 1);

        return hero;

    }

    // visualize
    public void printField() {

        for (int i = 0; i < this.size; i++) {

            for (int j = 0; j < this.size; j++) {

                if (this.board[i][j] instanceof Border) {
                    System.out.print( " #" );
                } else if (this.board[i][j] instanceof Wall) {
                    System.out.print( " ." );
                } else if (this.board[i][j] instanceof Hero) {
                    System.out.print( " H" );
                } else if (this.board[i][j] instanceof Enemy) {
                    System.out.print( " E" );
                } else if (this.board[i][j] instanceof Bomb) {
                    System.out.print( " B" );
                } else {
                    System.out.print( "  " );
                }

            }

            System.out.println();
        }
        System.out.println();
        System.out.println();

    }

    // movement
    public void moveHero(String direction, Block hero) {

        Block character = new Hero(hero.x, hero.y);
        validateMove(direction, character, true);

        if (this.isValid) {

            // swap content
            final Block tmp = this.board[hero.x][hero.y];
            this.board[hero.x][hero.y] = this.board[character.x][character.y];
            this.board[character.x][character.y] = tmp;

            // update hero
            hero.x = character.x;
            hero.y = character.y;

        }

    }

    // movement
    public void moveEnemies(ArrayList<Block> enemies) {

        String direction;
        Block character;
        int random;

        for (Block enemy: enemies) {

            // reinitialize
            character = new Enemy(enemy.x, enemy.y);
            ArrayList<String> directions = new ArrayList<>();

            // list of directions
            directions.add("up");
            directions.add("right");
            directions.add("down");
            directions.add("left");

            // randomize direction
            random = this.randomize(directions.size());

            // validate move
            this.validateMove(directions.get(random), character, false);

            // game over
            if (this.gameOver) {
                break;
            }

            // if the random directions isn't valid,
            // then try to use the rest of possible directions
            while (!this.isValid) {

                // no valid directions available
                if (directions.size() == 0) {
                    break;
                }

                // get new random direction
                random = (directions.size() == 0) ? 0 : this.randomize(directions.size());
                direction = directions.get(random);
                directions.remove(random);

                // reinitialize character
                character.x = enemy.x;
                character.y = enemy.y;

                // validate move
                this.validateMove(direction, character, false);

                // game over
                if (this.gameOver) {
                    break;
                }
                
            }

            if (this.isValid) {

                // swap content
                final Block tmp = this.board[enemy.x][enemy.y];
                this.board[enemy.x][enemy.y] = this.board[character.x][character.y];
                this.board[character.x][character.y] = tmp;

                // update enemy
                enemy.x = character.x;
                enemy.y = character.y;

            }

        }

    }

    // validate movement
    private void validateMove(String direction, Block character, boolean isHero) {

        switch (direction) {
            case "down": {

                if (this.isEmpty(character.x+1, character.y)) {

                    // valid move
                    this.isValid = true;
                    character.x = character.x+1;

                } else if (isHero) {

                    // game over
                    this.gameOver = this.isEnemy(character.x+1, character.y);

                    // invalid hero move
                    this.isValid = false;

                } else {

                    // game over
                    this.gameOver = this.isHero(character.x+1, character.y);

                    // invalid enemy move
                    this.isValid = false;

                }

                break;
            }
            case "up": {

                if (this.isEmpty(character.x-1, character.y)) {

                    // valid move
                    this.isValid = true;
                    character.x = character.x-1;

                } else if (isHero) {

                    // game over
                    this.gameOver = this.isEnemy(character.x-1, character.y);

                    // invalid hero move
                    this.isValid = false;

                } else {

                    // game over
                    this.gameOver = this.isHero(character.x-1, character.y);

                    // invalid enemy move
                    this.isValid = false;

                }

                break;
            }
            case "right": {

                if (this.isEmpty(character.x, character.y+1)) {

                    // valid move
                    this.isValid = true;
                    character.y = character.y+1;

                } else if (isHero) {

                    // game over
                    this.gameOver = this.isEnemy(character.x, character.y+1);

                    // invalid hero move
                    this.isValid = false;

                } else {

                    // game over
                    this.gameOver = this.isHero(character.x, character.y+1);

                    // invalid enemy move
                    this.isValid = false;

                }

                break;
            }
            case "left": {

                if (this.isEmpty(character.x, character.y-1)) {

                    // valid move
                    this.isValid = true;
                    character.y = character.y-1;

                } else if (isHero) {

                    // game over
                    this.gameOver = this.isEnemy(character.x, character.y-1);

                    // invalid hero move
                    this.isValid = false;

                } else {

                    // game over
                    this.gameOver = this.isHero(character.x, character.y-1);

                    // invalid enemy move
                    this.isValid = false;

                }

                break;
            }
            default: this.isValid = false;

        }

    }

    // helper
    private boolean isEmpty(int i, int j) {

        return (this.board[i][j] instanceof EmptyBlock);

    }

    // helper
    private boolean isEnemy(int i, int j) {

        return  (this.board[i][j] instanceof Enemy);

    }

    // helper
    private boolean isHero(int i, int j) {

        return  (this.board[i][j] instanceof Hero);

    }

    // helper
    private boolean isBorder(int i, int j) {

        boolean isOuterBorder = (i == 0 || j == 0 || i == this.size - 1 || j == this.size - 1);
        boolean isBorder = (i%2 == 0) && (j%2 == 0);

        return (isBorder || isOuterBorder);

    }

    // helper
    public boolean isGameOver() {

        return this.gameOver;

    }

    // helper
    private int randomize(int max) {

        // return random number
        return this.randomGenerator.nextInt(max);

    }

    // helper
    private Block getRandomEmptyBlock() {

        // random pick of empty block
        int randomNumber = this.randomize(this.emptyBlocks.size());
        Block block = this.emptyBlocks.get(randomNumber);
        this.emptyBlocks.remove(randomNumber);

        return block;

    }

}
