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

    public Field(int size, int countBlocks) {
        this.size = size;

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

    public void initWalls(int countWalls) {

        Block block;

        for (int i = 0; i < countWalls; i++) {
            block = this.getRandomEmptyBlock();
            this.board[block.x][block.y] = new Wall(block.x, block.y);
        }

    }

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
    public Block moveHero(String direction, Block hero) {

        Block character = new Hero(hero.x, hero.y);
        final boolean isHero = true;
        boolean gameOver = validateMove(direction, character, isHero);

        if (!gameOver) {

            // previous
            final int i = hero.x;
            final int j = hero.y;

            // new
            final int x = character.x;
            final int y = character.y;

            // if the position was updated -> swap content
            if (x != i || y != j) {

                final Block tmp = this.board[i][j];
                this.board[i][j] = this.board[x][y];
                this.board[x][y] = tmp;

            }

            return character;

        }

        // TODO: use a method instead...
        return new Hero(0,0);

    }

/*
    public ArrayList<Block> moveEnemies(ArrayList<Block> enemies) {

        // TODO:
        // iterate over enemies execute the movement if possible,
        // in foreach example:
        // Block character = validateMove(<direction>, enemy);
        // <direction> -> random from this array: ["down", "up", "right", "left"]
        // in case there is no valid direction, don't move.
        // else: this.move(enemy, character);

        return enemies;

    }
*/

    // validate movement
    private boolean validateMove(String direction, Block character, boolean isHero) {

        switch (direction) {
            case "down": {

                // valid move
                if (this.isEmpty(character.x+1, character.y)) {
                    character.x = character.x + 1;
                    return false;
                }

                return (this.isEnemy(character.x+1, character.y) && isHero);

            }
            case "up": {

                // valid move
                if (this.isEmpty(character.x-1, character.y)) {
                    character.x = character.x - 1;
                    return false;
                }

                return (this.isEnemy(character.x-1, character.y) && isHero);

            }
            case "right": {

                // valid move
                if (this.isEmpty(character.x, character.y+1)) {
                    character.y = character.y + 1;
                    return false;
                }

                return (this.isEnemy(character.x, character.y+1) && isHero);

            }
            case "left": {

                // valid move
                if (this.isEmpty(character.x, character.y-1)) {
                    character.y = character.y - 1;
                    return false;
                }

                // game over
                return (this.isEnemy(character.x, character.y-1) && isHero);

            }
            default: break;

        }
        
        return false;

    }

    // helpers

    private boolean isEmpty(int i, int j) {

        return (this.board[i][j] instanceof EmptyBlock);

    }

    private boolean isEnemy(int i, int j) {

        return  (this.board[i][j] instanceof Enemy);

    }

    private boolean isBorder(int i, int j) {

        boolean isOuterBorder = (i == 0 || j == 0 || i == this.size - 1 || j == this.size - 1);
        boolean isBorder = (i%2 == 0) && (j%2 == 0);

        return (isBorder || isOuterBorder);

    }

    private int randomize(int max) {

        // return random number
        return this.randomGenerator.nextInt(max);

    }

    private Block getRandomEmptyBlock() {

        // random pick of empty block
        int randomNumber = this.randomize(this.emptyBlocks.size());
        Block block = this.emptyBlocks.get(randomNumber);
        this.emptyBlocks.remove(randomNumber);

        return block;

    }

}
