import field.Field;

public class Main {

    public static void main(String[] args) {

        final int size = selectSize("xlg");
        final int lvl = selectLevel("insane");

        Field field = new Field(size, lvl);

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
