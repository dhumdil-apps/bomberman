
public class Field {

    // field
    private Block[][] board;
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

                if ((i%2 != 0) && (j%2 != 0)) {
                    this.board[i][j].type = 1;
                } else {
                    this.board[i][j].type = 0;
                }

            }
        }

    }

    //
    private void initHero() {

        // initiates hero on top-left corner
        int x = 0; // top
        int y = 0; // left

        this.board[x][y].type = 3; // hero
        this.hero = new Hero(x, y);

    }

    // TODO: based on lvl & randomize
    private void initEnemies(int lvl) {

        // initiates 2 enemies on bottom-left corner
        int numOfEnemies = 2;
        this.enemies = new Enemy[numOfEnemies];
        for (int i = 0; i < numOfEnemies; i++) {
            int randomX = this.size; // bottom
            int randomY = i; // left

            this.board[randomX][randomY].type = 4; // enemy
            this.enemies[i] = new Enemy(randomX, randomY);
        }

    }

    // TODO: randomize
    private void initBricks(int size) {

        // creates a horizontal wall of bricks in the middle
        int middle = 3;
        for (int i = 0; i < size; i++) {
            this.board[middle][i].type = 2; // brick
        }

    }

    // TODO: create new Bomb()...
    public void placeBomb(int x, int y) {
        this.board[x][y].type = 5;
    }

    private void printField() {

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
            case 0: return " ";
            case 1: return "W";
            case 2: return "B";
            case 3: return "H";
            case 4: return "E";
            case 5: return "B";
            default: return "";
        }

    }

}
