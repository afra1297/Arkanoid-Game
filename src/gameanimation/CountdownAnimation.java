// 323274480 Michael Ifraimov

package gameanimation;

import biuoop.DrawSurface;
import biuoop.Sleeper;
import gameobjects.sprites.SpriteCollection;
import levels.LevelInformation;


/**
 * The CountdownAnimation will display the given gameScreen, for numOfSeconds seconds, and on top of them it will show
 * a countdown from countFrom back to 1, where each number will appear on the screen for (numOfSeconds / countFrom)
 * seconds, before it is replaced with the next one.
 * @author Michael Ifraimov
 */
public class CountdownAnimation implements Animation {
    private static final int MILLI_SECONDS = 1000;
    private static final int FONT_SIZE = 48;
    private static final int END_COUNT = -1;
    private int countFrom; // the number from which the countdown begins and decreases
    private final int startFrom; // the number from which the countdown begins
    private final SpriteCollection gameScreen; // the given game screen with all the sprites
    private final Sleeper sleeper; // game sleeper
    private final int millisecondsPerFrame; // the time in milliseconds each number will be shown on the screen
    private final LevelInformation levelInfo;

    /**
     * Constructor.
     * @param numOfSeconds double type, number of seconds the countdown takes
     * @param countFrom int type, the number from which the countdown begins
     * @param gameScreen SpriteCollection type, a collection of sprite objects
     * @param levelInfo LevelInformation type
     */
    public CountdownAnimation(double numOfSeconds, int countFrom, SpriteCollection gameScreen,
                              LevelInformation levelInfo) {
        this.countFrom = countFrom;
        this.gameScreen = gameScreen;
        this.startFrom = countFrom;
        this.levelInfo = levelInfo;
        this.sleeper = new Sleeper();
        // the time in milliseconds each number will be shown on the screen
        this.millisecondsPerFrame = (int) (MILLI_SECONDS * (numOfSeconds / this.countFrom));
    }
    /**
     * A method that is in charge of the animation logic.
     * @param d DrawSurface type, the drawing surface of the game
     */
    @Override
    public void doOneFrame(DrawSurface d) {
        this.levelInfo.drawBackground(d);
        this.gameScreen.drawAllOn(d); // draw all sprites
        d.drawText((d.getWidth() / 2), (d.getHeight() / 2) + 100, "" + this.countFrom, FONT_SIZE); // draw count
        // check if the count already started
        if (this.countFrom != this.startFrom) {
            long startTime = System.currentTimeMillis();
            long usedTime = System.currentTimeMillis() - startTime;
            long milliSecondLeftToSleep = millisecondsPerFrame - usedTime;

            // sleeps for millisecondsPerFrame milliseconds for each number
            while (milliSecondLeftToSleep > 0) {
                this.sleeper.sleepFor(milliSecondLeftToSleep);
                usedTime = System.currentTimeMillis() - startTime;
                milliSecondLeftToSleep = millisecondsPerFrame - usedTime;
            }
        }
        this.countFrom--;
    }

    /**
     * A method that is in charge of stopping the game from running.
     * @return boolean type, true if condition is met and false otherwise
     */
    @Override
    public boolean shouldStop() {
        return this.countFrom == END_COUNT;
    }
}
