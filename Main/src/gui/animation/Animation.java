package gui.animation;

import java.awt.*;
import java.util.ArrayList;

public class Animation {

    private ArrayList scenes;
    private int screenIndex;
    private long movieTime;
    private long totalTime;

    private Animation() {

        scenes = new ArrayList();
        totalTime = 0;
        start();

    }

    public synchronized void start() {

        // reinit
        movieTime = 0;
        screenIndex = 0;

    }

    public synchronized void update(long timePassed) {

        if (scenes.size() > 1) {
            movieTime += timePassed;

            if (movieTime >= totalTime) {
                movieTime = 0;
                screenIndex = 0;
            }

            while (movieTime > getScene(screenIndex).endTime) {
                screenIndex++;
            }

        }

    }

    public synchronized void addScene(Image img, long time) {

        totalTime += time;

        scenes.add( new ImageScene(img, totalTime) );

    }

    public synchronized Image getImage() {

        if (scenes.size() > 0) {
            return getScene(screenIndex).pic;
        }

        return null;

    }

    private ImageScene getScene(int x) {

        // get scene
        return (ImageScene)scenes.get(x);

    }

    private class ImageScene {

        Image pic;
        long endTime;

        // inner class
        public ImageScene(Image pic, long endTime) {
            this.pic = pic;
            this.endTime = endTime;
        }

    }

}
