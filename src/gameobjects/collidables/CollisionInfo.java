// 323274480 Michael Ifraimov
package gameobjects.collidables;
import geometry.Point;

/**
 * CollisionInfo class include information about a collision (intersection
 * between ball's trajectory line and a collidable object): collision point
 * and collision object.
 * @author Michael Ifraimov
 */

public class CollisionInfo {
    private final Point collisionPoint;
    private final Collidable collisionObject;

    /**
     * Constructor.
     * @param point Point type, collision point
     * @param collObject Collidable object
     */
    public CollisionInfo(Point point, Collidable collObject) {
        this.collisionPoint = point;
        this.collisionObject = collObject;
    }

    /**
     * Returns the collision point.
     * @return Point type, collision point
     */
    public Point collisionPoint() {
        return this.collisionPoint;
    }

    /**
     * Returns the collision object.
     * @return Collidable type, collision object
     */
    public Collidable collisionObject() {
        return this.collisionObject;
    }
}
