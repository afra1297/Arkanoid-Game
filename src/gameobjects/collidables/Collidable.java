// 323274480 Michael Ifraimov
package gameobjects.collidables;

import biuoop.DrawSurface;
import gameobjects.sprites.Ball;
import gameobjects.sprites.Velocity;
import geometry.Point;
import geometry.Rectangle;

/**
 * Represents an interface of collide able objects.
 * @author Michael Ifraimov
 */
public interface Collidable {
    /**
     * Return the "collision shape" of the object.
     * @return geometry.Rectangle type
     */
    Rectangle getCollisionRectangle();

    /**
     * Notify the object that we collided with it at collisionPoint with
     * a given velocity. The return is the new velocity expected after the hit
     * (based on the force the object inflicted on us).
     * @param hitter Ball type, a ball
     * @param collisionPoint Point type, point of collision with the object
     * @param currentVelocity Velocity type, previous velocity
     * @return Velocity type, new velocity after the hit
     */
    Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity);
    /**
     * Draws the block on the drawing surface, using drawOn method of class
     * Rectangle.
     * @param surface gui drawing surface
     */
    void drawOn(DrawSurface surface);
}
