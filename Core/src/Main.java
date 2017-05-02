import field.Field;

public class Main {

    public static void main(String[] args) {

        final int size = selectSize("sm");
        Field field = new Field(size);

    }


    private static int selectSize(String size) {

        switch (size) {
            case "xsm": return 1;
            case "sm": return 2;
            case "md": return 3;
            case "lg": return 4;
            case "xlg": return 5;
            default: return 0;
        }

    }

}
