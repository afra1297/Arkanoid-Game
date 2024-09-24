// 323274480 Michael Ifraimov
package gameanimation;

import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.Sleeper;

/**
 * Class AnimationRunner, runs the game animation on the GUI drawing surface.
 * @author Michael Ifraimov
 */
public class AnimationRunner {
    private static final int MILLI_SECONDS = 1000; // default milliseconds
    private final GUI gui;
    private final int framesPerSecond;
    private final Sleeper sleeper;

    /**
     * Constructor.
     * @param gui GUI type, game screen
     * @param framesPerSecond int type, frames shown per second
     * @param sleeper Sleeper type
     */
    public AnimationRunner(GUI gui, int framesPerSecond, Sleeper sleeper) {
        this.gui = gui;
        this.framesPerSecond = framesPerSecond;
        this.sleeper = sleeper;
    }

    /**
     * Getter method to access the value of framesPerSecond.
     * @return int type, framesPerSecond value
     */
    public int getFramesPerSecond() {
        return this.framesPerSecond;
    }

    /**
     * Runs the animation loop on the screen.
     * @param animation Animation type, the animation that will run
     */
    public void run(Animation animation) {
        int millisecondsPerFrame = MILLI_SECONDS / this.getFramesPerSecond(); // calculate milliseconds per frame
        /*
         * the animation loop, shows multiple frames per millisecond on the screen.
         * the loop will stop when the shouldStop method condition is met.
         */
        while (!animation.shouldStop()) {
            long startTime = System.currentTimeMillis(); // timing
            DrawSurface d = this.gui.getDrawSurface();
            animation.doOneFrame(d);
            this.gui.show(d);
            long usedTime = System.currentTimeMillis() - startTime;
            long milliSecondLeftToSleep = millisecondsPerFrame - usedTime;
            if (milliSecondLeftToSleep > 0) {
                this.sleeper.sleepFor(milliSecondLeftToSleep);
            }
        }
    }

}
