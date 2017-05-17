package board;

import java.util.ArrayList;
import java.util.Random;

import board.block.*;
import board.block.blocks.standing.*;
import board.block.blocks.moving.*;

public class Board {

    private static Block[][] board;
    private static boolean gameOver;
    private static Random randomGenerator = new Random();

    public Board(int size, int lvl) {

        // some calculations to generate the game content
        int countEnemies = lvl * 2;
        int countBlocks = (size * (size + 1)) + ((size + 1) * (((size * 2) + 3) - 2));
        int countWalls = ((countBlocks - 9) > 0) ? (countBlocks - countEnemies - 9)/2 : 0;
        size = (size * 2) + 3;

        gameOver = false;

        board = new Block[size][size];
        ArrayList<Block> emptyBlocks = new ArrayList<>(countBlocks);

        // init the board
        this.initBoard(emptyBlocks);
        this.createEnemies(countEnemies, emptyBlocks);
        this.createWalls(countWalls, emptyBlocks);

        // create Hero Thread
        Thread hero = new Thread( new Hero(1, 1) );
        hero.start();

    }

    public static void createBlock(Block block) {
        board[block.x][block.y] = block;
    }

    /**
     * Board initialization
     * - place Borders
     * - store Empty-blocks
     * - reserve space for Hero
     *
     * @param emptyBlocks - store empty-blocks for later
     */
    private void initBoard(ArrayList<Block> emptyBlocks) {

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {

                if (isFieldBorder(i, j)) {

                    // place borders
                    board[i][j] = new Border(i, j);

                } else if (i > 2 || j > 2) {

                    // place empty blocks and store them,
                    // used for initialization of walls and enemies
                    board[i][j] = new EmptyBlock(i, j);
                    emptyBlocks.add(board[i][j]);

                } else {

                    // reserve space for hero
                    board[i][j] = new EmptyBlock(i, j);

                }

            }
        }

    }

    /**
     * Initialize Enemies at random positions
     *
     * @param countEnemies - the number of enemies to create
     */
    private void createEnemies(int countEnemies, ArrayList<Block> emptyBlocks) {

        Block block;

        for (int i = 0; i < countEnemies; i++) {

            block = this.getRandomEmptyBlock(emptyBlocks);
            Thread enemy = new Thread( new Enemy(block.x, block.y) );
            enemy.start();

        }

//        Thread[] hero = new Thread( );
//        hero.start();
/*
        // TODO - check if there are available empty blocks
        for (int i = 0; i < countEnemies; i++) {
            block = this.getRandomEmptyBlock(emptyBlocks);
            board[block.x][block.y] = new Enemy(block.x, block.y);
        }
*/
    }

    /**
     * Initialize random Wall positions
     * @param countWalls - number of Walls to initialize
     */
    private void createWalls(int countWalls, ArrayList<Block> emptyBlocks) {

        Block block;

        // TODO - check if there are available empty blocks
        for (int i = 0; i < countWalls; i++) {
            block = this.getRandomEmptyBlock(emptyBlocks);
            board[block.x][block.y] = new Wall(block.x, block.y);
        }

    }

    /**
     * Visualize the board on a CLI
     */
    public static void viewBoard() {

        for (int i = 0; i < board.length; i++) {

            for (int j = 0; j < board.length; j++) {

                if (board[i][j] instanceof Border) {
                    System.out.print( " #" );
                } else if (board[i][j] instanceof Wall) {
                    System.out.print( " ." );
                } else if (board[i][j] instanceof Hero) {
                    System.out.print( " H" );
                } else if (board[i][j] instanceof Enemy) {
                    System.out.print( " E" );
                } else if (board[i][j] instanceof Bomb) {
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

    /**
     * Return a random integer in range [0, <max>)
     *
     * @param max - must be positive
     * @return - random integer
     */
    public static int randomize(int max) {

        if (max > 0)
            return randomGenerator.nextInt(max);

        return 0;

    }

    /**
     * Get a random empty block
     * - used to initialize enemies and walls on available empty block
     *
     * @param emptyBlocks - list of the available empty blocks
     * @return - an empty block
     */
    private Block getRandomEmptyBlock(ArrayList<Block> emptyBlocks) {

        int randomNumber = randomize(emptyBlocks.size());

        Block block = emptyBlocks.get(randomNumber);
        emptyBlocks.remove(randomNumber);

        return block;

    }

    /**
     * Game status
     * - it's up and running / it's over
     *
     * @return - the status of the game
     */
    public static boolean isGameOver() {
        return gameOver;
    }

    /**
     * End the game
     */
    public static void setGameOver() {
        gameOver = true;
    }

    /**
     * Bomber-man game borders
     *
     * @param i - position
     * @param j - position
     * @return - true if it's a valid position for border
     */
    private static boolean isFieldBorder(int i, int j) {

        boolean isOuterBorder = (i == 0 || j == 0 || i == board.length - 1 || j == board.length - 1);
        boolean isBorder = (i%2 == 0) && (j%2 == 0);

        return (isBorder || isOuterBorder);

    }

    /**
     * Check for empty-block with position (x,y)
     *
     * @param i - position
     * @param j - position
     * @return - true/false
     */
    public static boolean isEmpty(int i, int j) {
        return (board[i][j] instanceof EmptyBlock);
    }

    /**
     * Check for enemy with position (x,y)
     *
     * @param i - position
     * @param j - position
     * @return - true/false
     */
    public static boolean isEnemy(int i, int j) {
        return  (board[i][j] instanceof Enemy);
    }

    /**
     * Check for hero with position (x,y)
     *
     * @param i - position
     * @param j - position
     * @return - true/false
     */
    public static boolean isHero(int i, int j) {
        return  (board[i][j] instanceof Hero);
    }

}
