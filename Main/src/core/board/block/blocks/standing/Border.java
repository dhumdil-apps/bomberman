package core.board.block.blocks.standing;

import core.board.Board;
import core.board.block.Block;
import gui.animation.Animation;
import gui.image.ImageObject;
import gui.sprite.Sprite;

import javax.swing.*;
import java.awt.*;

public class Border extends Block {

    public Border(int x, int y) {

        super(x, y);

        Image img = new ImageIcon(this.getClass().getResource("/gui/resources/border.png")).getImage();
        int positionX = (imageSize * this.y) + X;
        int positionY = (imageSize * this.x) + Y;

        ImageObject image = new ImageObject(img, positionX, positionY);
        Board.addImage(image);

    }

    public void live() {}

    public void die() {}

}
