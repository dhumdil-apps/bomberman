
public class Enemy extends Character {

    Enemy(int x, int y) {
        super(x, y);

        this.init();
    }

    private void init() {
        System.out.println("i'm an Enemy!");
    }

}
