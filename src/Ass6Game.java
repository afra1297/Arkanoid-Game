// 323274480 Michael Ifraimov

import biuoop.GUI;
import biuoop.Sleeper;
import gameanimation.AnimationRunner;
import levels.LevelInformation;
import levels.DirectHit;
import levels.WideEasy;
import levels.Green3;
import levels.GameFlow;
import java.util.ArrayList;
import java.util.List;

/**
 * This is the main class - starts the game.
 * @author Michael Ifraimov
 */
public class Ass6Game {
    private static final int DIRECT_HIT_LEVEL = 1;
    private static final int WIDE_EASY_LEVEL = 2;
    private static final int GREEN_3_LEVEL = 3;
    private static final int SCREEN_WIDTH = 800;
    private static final int SCREEN_HEIGHT = 600;
    private static final int FRAMES_PER_SECOND = 60;
    public static final int NUMBER_OF_LEVELS = 3;

    /**
     * Checks if a given String is a number.
     * @param str String type
     * @return true if str is an integer number, false otherwise
     */
    public static boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    /**
     * Driver function - starts the game.
     * @param args commandline arguments
     */
    public static void main(String[] args) {
        GUI gui = new GUI("Arkanoid", SCREEN_WIDTH, SCREEN_HEIGHT);
        AnimationRunner runner = new AnimationRunner(gui, FRAMES_PER_SECOND, new Sleeper());
        GameFlow gameFlow = new GameFlow(runner, gui.getKeyboardSensor(), SCREEN_WIDTH, SCREEN_HEIGHT);
        List<Integer> levelNumbers = new ArrayList<>();
        // check args and put in levelNumbers list only the integer numbers bigger than 0, if they exist
        for (String arg : args) {
            if (isInteger(arg) && Integer.parseInt(arg) > 0 && Integer.parseInt(arg) <= NUMBER_OF_LEVELS) {
                levelNumbers.add(Integer.parseInt(arg));
            }
        }
        List<LevelInformation> gameLevels = new ArrayList<>(); // list of the levels in the game
        // there are no integer number arguments - run levels in this order
        if (levelNumbers.size() == 0) {
            gameLevels.add(new DirectHit());
            gameLevels.add(new WideEasy());
            gameLevels.add(new Green3());
        } else {
            for (int levelNum : levelNumbers) {
                switch (levelNum) {
                    case DIRECT_HIT_LEVEL -> gameLevels.add(new DirectHit());
                    case WIDE_EASY_LEVEL -> gameLevels.add(new WideEasy());
                    case GREEN_3_LEVEL -> gameLevels.add(new Green3());
                    default -> {
                    }
                }
            }
        }
        gameFlow.runLevels(gameLevels);
        gui.close();
    }
}
