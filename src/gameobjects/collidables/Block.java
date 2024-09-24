// 323274480 Michael Ifraimov
package gameobjects.collidables;

import biuoop.DrawSurface;
import levels.GameLevel;
import listeners.listenerinterface.HitListener;
import listeners.listenerinterface.HitNotifier;
import gameobjects.sprites.Ball;
import gameobjects.sprites.Sprite;
import gameobjects.sprites.Velocity;
import geometry.Point;
import geometry.Rectangle;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Block, a collide able object.
 * @author Michael Ifraimov
 */
public class Block implements Collidable, Sprite, HitNotifier {
    private final Rectangle blockRectangle; // specifies the size and the location of the block
    private final List<HitListener> hitListeners; // list of objects to notify if there is a hit
    private boolean isFrame = false;

    /**
     * Constructor.
     * @param rectangle Rectangle type, the block rectangle
     */
    public Block(Rectangle rectangle) {
        this.blockRectangle = rectangle;
        this.hitListeners = new ArrayList<>();
    }

    /**
     * Constructor.
     * Added flag (boolean type) 'isFrame' to indicate if the block is a frame block or not
     * @param rectangle Rectangle type, block rectangle
     * @param isFrame boolean type, indicates if the block is a frame block (true) or not (false)
     */
    public Block(Rectangle rectangle, boolean isFrame) {
        this.blockRectangle = rectangle;
        this.hitListeners = new ArrayList<>();
        this.isFrame = isFrame;
    }

    /**
     * Sets a color for the block.
     * @param color Color type, a given color
     */
    public void setColor(Color color) {
        this.blockRectangle.setColor(color);
    }

    @Override
    public Rectangle getCollisionRectangle() {
        return this.blockRectangle;
    }

    /**
     * Calculates the new velocity of a ball after it collides with a block.
     * @param hitter Ball type, ball
     * @param collisionPoint Point type, point of collision with the object
     * @param currentVelocity Velocity type, previous velocity
     * @return Velocity type, ball new velocity
     */
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        // initialize the new velocity as the current velocity
        Velocity newVelocity = new Velocity(currentVelocity.getDx(),
                currentVelocity.getDy());
        /*
         * Checks which of the block vertices the ball hit and changes its
         * movement accordingly.
         */
        if (currentVelocity.getDy() > 0 && this.blockRectangle.isOnUpVertex(collisionPoint)) {
            // the ball collides with block's up vertex
            newVelocity.setDy(-currentVelocity.getDy()); // change  vertical component
        } else if (currentVelocity.getDy() < 0 && this.blockRectangle.isOnDownVertex(collisionPoint)) {
            // the ball collides with block's down vertex
            newVelocity.setDy(-currentVelocity.getDy()); // change vertical component
        } else if (currentVelocity.getDx() > 0 && this.blockRectangle.isOnLeftVertex(collisionPoint)) {
            // the ball collides with block's left vertex
            newVelocity.setDx(-currentVelocity.getDx()); // change horizontal component
        } else if (currentVelocity.getDx() < 0 && this.blockRectangle.isOnRightVertex(collisionPoint)) {
            // the ball collides with block's right vertex
            newVelocity.setDx(-currentVelocity.getDx());  // change horizontal component
        }
        // Notify all listeners about a hit
        this.notifyHit(hitter);
        return newVelocity;
    }
    @Override
    public void drawOn(DrawSurface surface) {
        this.getCollisionRectangle().drawOn(surface);
        surface.setColor(Color.BLACK);
        if (!this.isFrame) {
            //draws rectangle frame around it - rectangle vertices (borders)
            int blockCorX = (int) Math.round(this.getCollisionRectangle().getUpperLeft().getX());
            int blockCorY = (int) Math.round(this.getCollisionRectangle().getUpperLeft().getY());
            int blockWidth = (int) Math.round(this.getCollisionRectangle().getWidth());
            int blockHeight = (int) Math.round(this.getCollisionRectangle().getHeight());
            surface.drawRectangle(blockCorX, blockCorY, blockWidth, blockHeight);
        }
    }

    @Override
    public void timePassed() {
    }

    /**
     * Adds a Block object to the game.
     * @param g Game type, the game
     */
    public void addToGame(GameLevel g) {
        g.addCollidable(this);
        g.addSprite(this);
    }

    /**
     * Removes a Block object from the game.
     * @param gameLevel Game type, the game
     */
    public void removeFromGame(GameLevel gameLevel) {
        gameLevel.removeCollidable(this); // remove block from collidables list
        gameLevel.removeSprite(this); // remove block from sprites list
    }

    /**
     * Add hl as a listener to hit events.
     *
     * @param hl HitListener object
     */
    @Override
    public void addHitListener(HitListener hl) {
        this.hitListeners.add(hl);
    }

    /**
     * Remove hl from the list of listeners to hit events.
     *
     * @param hl HitListener object
     */
    @Override
    public void removeHitListener(HitListener hl) {
        this.hitListeners.remove(hl);
    }

    /**
     * Notifies all HitListener objects by calling their hitEvent method.
     * @param hitter Ball type, the ball that makes the hit
     */
    public void notifyHit(Ball hitter) {
        // Make a copy of the hitListeners before iterating over them
        List<HitListener> listeners = new ArrayList<>(this.hitListeners);
        // Notify all listeners about a hit event:
        for (HitListener hl : listeners) {
            hl.hitEvent(this, hitter);
        }

    }
}