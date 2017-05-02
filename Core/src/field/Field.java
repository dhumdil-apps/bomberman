package field;

import java.util.ArrayList;
import java.util.Random;
import java.util.List;

// local
import field.block.Block;
import field.block.bomb.Bomb;
import field.block.border.BorderBlock;
import field.block.empty.EmptyBlock;
import field.block.wall.WallBlock;
import field.block.character.hero.Hero;
import field.block.character.enemy.Enemy;

public class Field {

    private Block[][] board;
    private int size;

    private int countBlocks;
    private int countEnemies;
    private int countWalls;

    private Block hero;
    private List<Block> enemies;
    private List<Block> wallBlocks;
    private List<Block> emptyBlocks;

    public Field(int size) {

        this.size = (size * 2) + 3;
        this.countBlocks = (size * (size + 1)) + ((size + 1) * (this.size - 2));
        this.countEnemies = 2;
        this.countWalls = 3;

        this.board = new Block[this.size][this.size];
        this.emptyBlocks = new ArrayList<Block>(this.countBlocks);
        this.enemies = new ArrayList<Block>(this.countEnemies);
        this.wallBlocks = new ArrayList<Block>(this.countWalls);

        this.init();

    }

    // TODO: optimize!
    private void init() {

        Random randomGenerator = new Random();
        int randomNumber;
        Block block;

        // place borders
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                this.board[i][j] = this.isBorder(i, j) ? new BorderBlock(i, j) : new EmptyBlock(i, j);
            }
        }

        // store empty blocks
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                if (this.board[i][j] instanceof EmptyBlock) {
                    this.emptyBlocks.add(this.board[i][j]);
                }
            }
        }

        // hero:
        randomNumber = randomGenerator.nextInt(this.emptyBlocks.size());
        block = this.emptyBlocks.get(randomNumber);
        this.emptyBlocks.remove(randomNumber);
        this.hero = new Hero(block.x, block.y);
        this.board[block.x][block.y] = this.hero;

        // enemies:
        for (int i = 0; i < this.countEnemies; i++) {

            randomNumber = randomGenerator.nextInt(this.emptyBlocks.size());
            block = this.emptyBlocks.get(randomNumber);
            this.emptyBlocks.remove(randomNumber);
            this.enemies.add(new Enemy(block.x, block.y));
            this.board[block.x][block.y] = this.enemies.get(i);

        }

        // walls:
        for (int i = 0; i < this.countWalls; i++) {

            randomNumber = randomGenerator.nextInt(this.emptyBlocks.size());
            block = this.emptyBlocks.get(randomNumber);
            this.emptyBlocks.remove(randomNumber);
            this.wallBlocks.add(new WallBlock(block.x, block.y));
            this.board[block.x][block.y] = this.wallBlocks.get(i);

        }

        this.printField();
        // this.start();

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

                if (this.isEmpty(character.x+1, character.y)) {
                    character.x = character.x + 1;
                }

                return character;
            }
            case "up": {

                if (this.isEmpty(character.x-1, character.y)) {
                    character.x = character.x - 1;
                }

                return character;
            }
            case "right": {

                if (this.isEmpty(character.x, character.y+1)) {
                    character.y = character.y + 1;
                }

                return character;
            }
            case "left": {

                if (this.isEmpty(character.x, character.y-1)) {
                    character.y = character.y - 1;
                }

                return character;
            }
            default: return character;
        }

    }

    // HELPERS
    private boolean isEmpty(int i, int j) {
        return (this.board[i][j] instanceof EmptyBlock);
    }
    private boolean isBorder(int i, int j) {
        return ( (i%2 == 0) && (j%2 == 0) || (i == 0 || j == 0 || i == this.size - 1 || j == this.size - 1) );
    }

}
