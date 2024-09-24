// 323274480 Michael Ifraimov
package gameanimation;
import biuoop.DrawSurface;
import java.awt.Color;

/**
 * Class PauseScreen, displays a screen with the message paused -- press space to continue.
 * @author Michael Ifraimov
 */
public class PauseScreen implements Animation {
    private static final int TOP_LEFT_SCREEN = 0;
    private static final int FONT_SIZE = 36;
    private static final int MESSAGE_X = 150;

    /**
     * A method that is in charge of the animation logic.
     *
     * @param d DrawSurface type, the drawing surface of the game
     */
    @Override
    public void doOneFrame(DrawSurface d) {
        // Background
        d.setColor(Color.WHITE);
        d.fillRectangle(TOP_LEFT_SCREEN, TOP_LEFT_SCREEN, d.getWidth(), d.getHeight());

        // Message
        String message = "Paused - Press Space to Continue";
        d.setColor(Color.BLACK);
        d.drawText(MESSAGE_X, d.getHeight() / 2, message, FONT_SIZE);
    }

    /**
     * A method that is in charge of stopping the game from running.
     *
     * @return boolean type, true if the condition is met and false otherwise
     */
    @Override
    public boolean shouldStop() {
        return false;
    }
}
