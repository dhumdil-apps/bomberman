package core.board;

// basic
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

// gui
import gui.image.ImageObject;
import gui.screen.Screen;

// core
import core.board.block.*;
import core.board.block.blocks.moving.*;
import core.board.block.blocks.standing.*;
import gui.sprite.Sprite;

public class Board extends JFrame {

    // GUI
    private Screen screen;

    // CORE
    private static Block[][] board;
    private static boolean gameOver;
    private ArrayList<Thread> characters;
    private static ArrayList<Sprite> sprites;
    private static ArrayList<ImageObject> images;

    // HELPERS
    private static Random randomGenerator;
    public static String keyEventMessage;
    public static String logMessage;

    public Board() {

        // base config
        // (some calculations to generate the game content)
        final int SIZE = 2; // sm
        int length = SIZE + SIZE + 3;
        int countEnemies = 1;
        int countWalls = length - 10;

        // initialize
        gameOver = false;
        board = new Block[length][length];
        ArrayList<Block> emptyBlocks = new ArrayList<>();
        randomGenerator = new Random();
        sprites = new ArrayList<>();
        images = new ArrayList<>();
        characters = new ArrayList<>();

        // load background image
        Image background = new ImageIcon(this.getClass().getResource("/gui/resources/bg.jpg")).getImage();
        images.add( new ImageObject(background, 0, 0) );

        // initialize the board content
        this.initBoard(emptyBlocks);
        this.createEnemies(countEnemies, emptyBlocks);
        this.createWalls(countWalls, emptyBlocks);

    }

    /**
     * Register Block in Board
     *
     * @param block - the block to register in the board
     */
    public synchronized static void createBlock(Block block) {
        board[block.x][block.y] = block;
    }

    /**
     * Board initialization:
     * - place borders
     * - register empty blocks
     * - reserve space for hero
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

                    // place empty blocks and store them
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
            characters.add(new Thread( new Enemy(block.x, block.y) ));

        }

    }

    /**
     * Initialize random Wall positions
     * @param countWalls - number of Walls to initialize
     */
    private void createWalls(int countWalls, ArrayList<Block> emptyBlocks) {

        Block block;

        for (int i = 0; i < countWalls; i++) {

            block = this.getRandomEmptyBlock(emptyBlocks);
            board[block.x][block.y] = new Wall(block.x, block.y);

        }

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
    private synchronized Block getRandomEmptyBlock(ArrayList<Block> emptyBlocks) {

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

    /**
     * Size getter
     * @return - board size
     */
    public static int getLength() {
        return board.length;
    }

    public synchronized static void addSprite(Sprite s) {
        sprites.add(s);
    }

    public synchronized static void addImage(ImageObject image) {
        images.add(image);
    }

    /**
     * Initialize game window
     */
    public void run() {

        try {

            // config
            setBackground(Color.BLACK);
            setForeground(Color.cyan);
            setFont( new Font("Arial", Font.PLAIN, 24) );

            // messages
            keyEventMessage = "Press Esc or Q to Exit. Navigate using arrow keys.";
            logMessage = "";

            screen = new Screen();

            // make full screen
            DisplayMode dm = screen.getDisplayMode();
            screen.setFullScreen(dm);

            // get window, set full screen
            Window window = screen.getFullScreen();
            window.setFocusTraversalKeysEnabled(false);

            // create Hero (add him the key listener)
            characters.add( new Thread( new Hero(1, 1, window) ) );

            // init view:
            // movieLoop();


        } catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

    /**
     * Start the game (threads)
     */
    public void start() {
        try {

            for (Thread character: characters) {
                character.start();
            }

            movieLoop();

        } finally {
            screen.restoreScreen();
        }
    }

    /**
     * Update the view, while not game over.
     */
    private void movieLoop() {

        Graphics2D graphics;
        long cumulativeTime = System.currentTimeMillis();
        long timePassed;

        while (!gameOver) {

            // update sprite animations
            timePassed = System.currentTimeMillis() - cumulativeTime;
            cumulativeTime += timePassed;
            for (Sprite sprite: sprites) {
                sprite.update(timePassed);
            }

            // graphics to draw on & update screen
            graphics = screen.getGraphics();

            // draw images
            for (ImageObject image: images) {
                graphics.drawImage(image.getImage(), image.getX(), image.getY(), null);
            }

            // draw sprites
            for (Sprite sprite: sprites) {
                graphics.drawImage(sprite.getImage(), Math.round(sprite.getX()), Math.round(sprite.getY()), null);
            }

            // draw messages
            graphics.drawString(keyEventMessage, 33, 33);
            graphics.drawString(logMessage, 33, 63);

            // update the view...
            graphics.dispose();
            screen.update();

            // 'sleep/animate'
            try {
                Thread.sleep(50);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

        }

    }

}
