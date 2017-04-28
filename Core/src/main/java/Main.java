
public class Main {

    public static void main(String[] args) {

         System.out.println("i'm fun at parties :D");

        // Init
        Field f = new Field(5);

    }

}

public class Field {

    // field
    private int[][] board;
    private int size;

    Field(int size) {

        this.size = size;
        this.initBoard();
    }

    private void initBoard() {

        this.board = new int[size][size];

        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {

                if (i%2 && j%2) {
                    this.board[i][j] = 1; // wall
                } else {
                    this.board[i][j] = 0; // empty space
                }

            }
        }

    }

}

public class Brick {

}

public class Character {

}

public class Bomb {

}
