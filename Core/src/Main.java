import field.Field;

import java.util.Scanner;


public class Main {

    public static void main(String[] args) {

        final int size = selectSize("xlg");
        final int lvl = selectLevel("insane");

        Field field = new Field(size, lvl);
        Scanner cin = new Scanner(System.in);

        field.printField();

        // cli demo:
        while (true) {

            System.out.println("q: exit");
            System.out.println("h: left");
            System.out.println("j: down");
            System.out.println("k: up");
            System.out.println("l: right");

            String input = cin.next();

            if (input.equals("q")) {
                break;
            } else {

                switch (input) {
                    case "h":
                        field.move("left", field.hero.x, field.hero.y);
                        break;
                    case "j":
                        field.move("down", field.hero.x, field.hero.y);
                        break;
                    case "k":
                        field.move("up", field.hero.x, field.hero.y);
                        break;
                    case "l":
                        field.move("right", field.hero.x, field.hero.y);
                        break;
                    default:
                        System.out.println("Try again!");
                }

                field.printField();

            }


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

}
