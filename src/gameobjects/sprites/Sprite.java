// 323274480 Michael Ifraimov
package gameobjects.sprites;

import biuoop.DrawSurface;

/**
 * Represents an interface of sprite object.
 * @author Michael Ifraimov
 */
public interface Sprite {
    /**
     * Draw the sprite to the screen.
     * @param d gui drawing surface
     */
    void drawOn(DrawSurface d);
    /**
     * Notify the sprite that time has passed.
     */
    void timePassed();
}
