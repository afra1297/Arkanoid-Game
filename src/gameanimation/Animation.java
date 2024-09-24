// 323274480 Michael Ifraimov
package gameanimation;

import biuoop.DrawSurface;

/**
 * Represents an interface for different animation classes.
 * @author Michael Ifarimov
 */
public interface Animation {
    /**
     * A method that is in charge of the animation logic.
     * @param d DrawSurface type, the drawing surface of the game
     */
    void doOneFrame(DrawSurface d);

    /**
     * A method that is in charge of stopping the game from running.
     * @return boolean type, true if condition is met and false otherwise
     */
    boolean shouldStop();
}
