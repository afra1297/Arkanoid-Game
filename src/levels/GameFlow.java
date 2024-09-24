// 323274480 Michael Ifraimov
package levels;

import biuoop.KeyboardSensor;
import gameanimation.AnimationRunner;
import gameanimation.EndScreen;
import gameanimation.KeyPressStoppableAnimation;
import listeners.listenerclass.Counter;

import java.util.List;

/**
 * Class GameFlow is in charge of creating the different levels, and moving from one level to the next.
 * @author Michael Ifraimov
 */
public class GameFlow {
    private static final int STARTING_COUNT = 0;
    private final int screenWidth;
    private final int screenHeight;
    private final KeyboardSensor keyboardSensor;
    private final AnimationRunner animationRunner; // runs the animation in the game
    private final Counter scoreCounter; // score counter of the player in the game
    private boolean gameContinue; // indicates if the game can continue or the player has lost

    /**
     * Constructor.
     * @param runner AnimationRunner type, runs the animation in the game
     * @param keyboard KeyboardSensor type
     * @param screenWidth int type, width of the screen
     * @param screenHeight int type, height of the screen
     */
    public GameFlow(AnimationRunner runner, KeyboardSensor keyboard, int screenWidth, int screenHeight) {
        this.animationRunner = runner;
        this.keyboardSensor = keyboard;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.scoreCounter = new Counter(STARTING_COUNT);
        this.gameContinue = true;
    }

    /**
     * Runs all the levels in the game one after the other.
     * @param gameLevels List of LevelInformation objects, list of the levels in the game
     */
    public void runLevels(List<LevelInformation> gameLevels) {
        // run each level in order of there appearance in gameLevels list
        for (LevelInformation levelInfo : gameLevels) {
            GameLevel level = new GameLevel(this.keyboardSensor, this.animationRunner, levelInfo,
                    this.scoreCounter, this.screenWidth, this.screenHeight);
            level.initialize();
            // run level as long as there are balls and blocks remaining in the game
            while (level.getRemainingBalls() > 0 && level.getRemainingBlocks() > 0) {
                level.run();
            }
            // there are no balls in the game, but some block are left - player lost, stop the game
            if (level.getRemainingBlocks() > 0) {
                this.gameContinue = false;
                break;
            }

        }
        this.animationRunner.run(new KeyPressStoppableAnimation(this.keyboardSensor, KeyboardSensor.SPACE_KEY,
                new EndScreen(this.scoreCounter, this.gameContinue)));
    }
}
