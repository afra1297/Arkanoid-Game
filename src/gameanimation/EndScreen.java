// 323274480 Michael Ifraimov
package gameanimation;

import biuoop.DrawSurface;
import listeners.listenerclass.Counter;
import java.awt.Color;

/**
 * Class EndScreen, represent the end of the game screen.
 * displays if the player has won (cleared all levels) or lost (no balls left in game)
 * @author Michael Ifraimov
 */
public class EndScreen implements Animation {
    // constants
    private static final int TOP_LEFT_SCREEN = 0;
    private static final int RESULT_MESSAGE_X = 50;
    private static final int RESULT_MESSAGE_Y = 150;
    private static final int RESULT_FONT_SIZE = 50;
    private static final int EXIT_MESSAGE_X = 220;
    private static final int EXIT_MESSAGE_Y = 500;
    private static final int EXIT_FONT_SIZE = 32;
    // field members
    private final Counter scoreCount; // current score of the player
    private final boolean playerWon; // flag - indicates if player won or lost
    /**
     * Constructor.
     * @param scoreCount Count type, current score of the player
     * @param playerWon boolean type, indicates if player won or lost
     */
    public EndScreen(Counter scoreCount, boolean playerWon) {
        this.scoreCount = scoreCount;
        this.playerWon = playerWon;
    }

    /**
     * A method that is in charge of the animation logic.
     *
     * @param drawSurface DrawSurface type, the drawing surface of the game
     */
    @Override
    public void doOneFrame(DrawSurface drawSurface) {
        drawSurface.setColor(Color.YELLOW);
        drawSurface.fillRectangle(TOP_LEFT_SCREEN, TOP_LEFT_SCREEN, drawSurface.getWidth(), drawSurface.getHeight());
        String resultMessage = "You Win! Your score is ";
        if (!this.playerWon) {
            resultMessage = "Game Over. Your score is ";
        }
        drawSurface.setColor(Color.BLACK);
        drawSurface.drawText(RESULT_MESSAGE_X, RESULT_MESSAGE_Y, resultMessage + this.scoreCount.getValue(),
                RESULT_FONT_SIZE);
        String exitMessage = "Press space to exit";
        drawSurface.setColor(Color.BLACK);
        drawSurface.drawText(EXIT_MESSAGE_X, EXIT_MESSAGE_Y, exitMessage, EXIT_FONT_SIZE);
    }

    /**
     * A method that is in charge of stopping the game from running.
     * @return boolean type, true if condition is met and false otherwise
     */
    public boolean shouldStop() {
        return false;
    }
}
