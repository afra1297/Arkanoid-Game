// 323274480 Michael Ifraimov
package gameobjects.sprites;

import biuoop.DrawSurface;
import java.util.ArrayList;
import java.util.List;

/**
 * Sprite collection class, a collection of all sprites in the game.
 * @author Michael Ifraimov
 */
public class SpriteCollection {
    private final List<Sprite> spritesList; // list of all sprites in game

    /**
     * Constructor- initializes an empty sprites list.
     */
    public SpriteCollection() {
        this.spritesList = new ArrayList<>();
    }

    /**
     * Getter method to access the values a sprites list.
     * @return ArrayList collection, sprites list
     */
    public List<Sprite> getSpritesList() {
        return new ArrayList<>(this.spritesList);
    }

    /**
     * Add Sprite object to the game sprites list.
     * @param s Sprite object we want to add to the list
     */
    public void addSprite(Sprite s) {
        if (s != null) {
            this.spritesList.add(s);
        }
    }

    /**
     * Remove Sprite object from the game sprites list.
     * @param s Sprite object we want to remove from the list
     */
    public void removeSprite(Sprite s) {
        if (s != null) {
            this.spritesList.remove(s);
        }
    }

    /**
     * Notify all sprites, in sprites list, that time passed.
     */
    public void notifyAllTimePassed() {
        for (Sprite sprite: this.getSpritesList()) {
            sprite.timePassed();
        }
    }

    /**
     * Draw all sprites in sprites list, on the drawing surface.
     * @param d gui drawing surface
     */
    public void drawAllOn(DrawSurface d) {
        for (Sprite sprite : this.getSpritesList()) {
            sprite.drawOn(d);
        }

    }
}
