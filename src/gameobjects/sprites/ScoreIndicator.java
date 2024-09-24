// 323274480 Michael Ifraimov
package gameobjects.sprites;

import levels.GameLevel;
import biuoop.DrawSurface;
import listeners.listenerclass.Counter;
import geometry.Rectangle;
import java.awt.Color;

/**
 * Class ScoreIndicator, in charge of displaying the current score.
 * The ScoreIndicator will hold a reference to the scores counter, and will be added to the game as a sprite positioned
 * at the top of the screen.
 * @author Michael Ifraimov
 */
public class ScoreIndicator implements Sprite {
    private final Counter score;
    private final String levelName;
    private final Rectangle indicatorBorders; // a rectangle to represent the borders of the score indicator

    /**
     * Constructor.
     * @param currentScore Counter type, current score of the player
     * @param levelName String type, the name of the level
     * @param borderRectangle Rectangle type, the borders of the score indicator
     */
    public ScoreIndicator(Counter currentScore, String levelName, Rectangle borderRectangle) {
        this.score = currentScore;
        this.levelName = levelName;
        this.indicatorBorders = borderRectangle;
    }

    /**
     * Getter method to access the values of borders rectangle.
     * @return Rectangle type, the borders rectangle of the score indicator
     */
    public Rectangle getBorders() {
        return this.indicatorBorders;
    }

    /**
     * Getter method to access the value of the score counter.
     * @return Counter type, the score
     */
    public Counter getScore() {
        return this.score;
    }

    /**
     * Getter method to access the value of the level name.
     * @return String type, level name
     */
    public String getLevelName() {
        return this.levelName;
    }
    /**
     * Draw the sprite to the screen.
     *
     * @param d gui drawing surface
     */
    @Override
    public void drawOn(DrawSurface d) {
        String scoreStr = "Score: " + this.getScore().getValue(); // the string that will be printed on screen
        d.setColor(this.getBorders().getColor());
        // draw score indicator rectangle on the drawing surface
        d.fillRectangle((int) this.getBorders().getUpperLeft().getX(), (int) this.getBorders().getUpperLeft().getY(),
                (int) this.getBorders().getWidth(), (int) this.getBorders().getHeight());
        d.setColor(Color.BLACK);
        // draw text on the score indicator rectangle - put text in the middle of the rectangle
        d.drawText((int) ((this.getBorders().getWidth() / 2) - (2 * this.getBorders().getHeight())),
                (int) (this.getBorders().getUpperLeft().getY() + this.getBorders().getHeight() - 2),
                scoreStr, (int) this.getBorders().getHeight());
        // draw level name on the score indicator rectangle - put text in the middle of the rectangle
        d.drawText((int) ((this.getBorders().getWidth() * 2 / 3) - this.getBorders().getHeight()),
                (int) (this.getBorders().getUpperLeft().getY() + this.getBorders().getHeight() - 4),
                "Level Name: " + this.getLevelName(), (int) this.getBorders().getHeight());
    }

    /**
     * Notify the sprite that time has passed.
     */
    @Override
    public void timePassed() {
    }

    /**
     * Adds a ScoreIndicator object to the game.
     * @param g Game type, the game
     */
    public void addToGame(GameLevel g) {
        g.addSprite(this);
    }
}
