import field.block.Block;
import field.Field;

import java.util.Scanner;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        int size = selectSize("xlg");
        int lvl = selectLevel("insane");
        int countEnemies = lvl * 2;
        int countBlocks = (size * (size + 1)) + ((size + 1) * (((size * 2) + 3) - 2));
        int countWalls = ((countBlocks - 9) > 0) ? (countBlocks - countEnemies - 9)/2 : 0;
        size = (size * 2) + 3;

        // init board
        Field field = new Field(size, countBlocks);

        // init enemies
        ArrayList<Block> enemies = field.initEnemies(countEnemies);

        // init walls
        field.initWalls(countWalls);

        // init hero
        Block hero = field.initHero();

        field.printField();

        // cli demo:
        Scanner cin = new Scanner(System.in);
        String direction;
        while (true) {

            // print instructions
            System.out.println("q: quit");
            System.out.println("w: up");
            System.out.println("a: left");
            System.out.println("s: down");
            System.out.println("d: right");

            // get input
            direction = selectDirection(cin.next());

            // validate input
            if (direction.equals("")) {
                System.out.println("Input error, try again:");
                continue;
            }

            // check if the game is over
            if (direction.equals("q")) {
                System.out.println("Quitting...");
                break;
            }

            // execute hero movement
            hero = field.moveHero(direction, hero);
            if (hero.x == 0 && hero.y == 0) {
                System.out.println("Game Over!");
                break;
            }

            // print updated board
            field.printField();

        }

    }


    private static int selectSize(String size) {

        switch (size) {
            case "xsm": return 1;
            case "sm": return 2;
            case "md": return 3;
            case "lg": return 4;
            case "xlg": return 5;
            default: return 2;
        }

    }

    private static int selectLevel(String level) {

        switch (level) {
            case "basic": return 0;
            case "easy": return 1;
            case "medium": return 2;
            case "hard": return 3;
            case "insane": return 4;
            default: return 2;
        }

    }

    private static String selectDirection(String input) {

        switch (input) {
            case "w": return "up";
            case "a": return "left";
            case "s": return "down";
            case "d": return "right";
            case "q": return "q";
            default: return "";
        }

    }

}
