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

    public Block[][] board;
    private int size;
    private int countBlocks;
    private int countEnemies;
    private int countWalls;
    public Block hero;
    public List<Block> enemies;
    public List<Block> wallBlocks;
    public List<Block> emptyBlocks;
    private Random randomGenerator;

    public Field(int size, int lvl) {

        this.size = (size * 2) + 3;
        this.countBlocks = (size * (size + 1)) + ((size + 1) * (this.size - 2));
        this.countEnemies = lvl;
        this.countWalls = ((this.countBlocks - 9) > 0) ? (this.countBlocks - this.countEnemies - 9)/2 : 0;

        this.randomGenerator = new Random();
        this.board = new Block[this.size][this.size];
        this.emptyBlocks = new ArrayList<Block>(this.countBlocks);
        this.enemies = new ArrayList<Block>(this.countEnemies);
        this.wallBlocks = new ArrayList<Block>(this.countWalls);

        this.init();

    }

    private void init() {

        Block block;

        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {

                if (this.isBorder(i, j)) {

                    // borders:
                    this.board[i][j] = new BorderBlock(i, j);

                } else if (i > 2 || j > 2) {

                    // empty-blocks:
                    this.board[i][j] = new EmptyBlock(i, j);
                    this.emptyBlocks.add(this.board[i][j]);

                }

            }
        }

        // hero:
        this.board[1][1] = new Hero(1,1);
        this.board[1][2] = new Block();
        this.board[2][1] = new Block();
        this.hero = this.board[1][1];

        // enemies:
        for (int i = 0; i < this.countEnemies; i++) {

            block = this.getRandomEmptyBlock();
            this.board[block.x][block.y] = new Enemy(block.x, block.y);
            this.enemies.add(this.board[block.x][block.y]);

        }

        // wall-blocks:
        for (int i = 0; i < this.countWalls; i++) {

            block = this.getRandomEmptyBlock();
            this.board[block.x][block.y] = new WallBlock(block.x, block.y);
            this.wallBlocks.add(this.board[block.x][block.y]);

        }

        // keep empty around hero position:
        this.board[1][2] = new EmptyBlock(1, 2);
        this.emptyBlocks.add(this.board[1][2]);
        this.board[2][1] = new EmptyBlock(2, 1);
        this.emptyBlocks.add(this.board[2][1]);

    }

    // visualization

    public void printField() {

        for (int i = 0; i < this.size; i++) {

            for (int j = 0; j < this.size; j++) {

                if (this.board[i][j] instanceof BorderBlock) {
                    System.out.print( " #" );
                } else if (this.board[i][j] instanceof WallBlock) {
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

    public void move(String direction, int i, int j) {

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

    // helpers

    private boolean isEmpty(int i, int j) {
        return (this.board[i][j] instanceof EmptyBlock);
    }

    private boolean isBorder(int i, int j) {
        return ( (i%2 == 0) && (j%2 == 0) || (i == 0 || j == 0 || i == this.size - 1 || j == this.size - 1) );
    }

    private int randomize(int max) {
        return this.randomGenerator.nextInt(max);
    }

    private Block getRandomEmptyBlock() {
        int randomNumber = this.randomize(this.emptyBlocks.size());
        Block block = this.emptyBlocks.get(randomNumber);
        this.emptyBlocks.remove(randomNumber);
        return block;
    }

}
