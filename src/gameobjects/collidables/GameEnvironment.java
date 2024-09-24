// 323274480 Michael Ifraimov
package gameobjects.collidables;

import geometry.Line;
import geometry.Point;
import java.util.ArrayList;
import java.util.List;

/**
 * Game environment class, a collection of all collide able objects in the game.
 * @author Michael Ifraimov
 */
public class GameEnvironment {
    private static final int FIRST_INDEX = 0;
    private final List<Collidable> collidableList; // list of collidable objects

    /**
     * Constructor, initialises the collidables list.
     */
    public GameEnvironment() {
        this.collidableList = new ArrayList<>();
    }

    /**
     * Getter method to access the values a collidable list.
     * @return ArrayList collection, collidables list
     */
    public List<Collidable> getCollidableList() {
        // return collidable list as ArrayList collection
        return new ArrayList<>(this.collidableList);
    }

    /**
     * Add collidable object to the game environment list.
     * @param c Collidable object we want to add to the list
     */
    public void addCollidable(Collidable c) {
        if (c != null) {
            this.collidableList.add(c);
        }

    }

    /**
     * Remove collidable object from the game environment list.
     * @param c Collidable object we want to remove from the list
     */
    public void removeCollidable(Collidable c) {
        if (c != null) {
            this.collidableList.remove(c);
        }
    }

    /**
     * Creates an ArrayList collection of CollisionInfo type objects. Gets
     * the closest point of intersection between each collidable object and
     * a given line. Creates CollisionInfo object according to the intersection
     * point and returns the list.
     * @param line Line type, a given line - ball trajectory line
     * @return ArrayList of CollisionInfo types
     */

    public List<CollisionInfo> getCollisionInfoList(Line line) {
        List<CollisionInfo> collisionInfos = new ArrayList<>();
        /*
        * Iterate over all Collidable objects list, check closest intersection
        * point, if there is such a point (not null) - add to CollisionInfo
        * list.
        */
        for (Collidable col : this.getCollidableList()) {
            Point intersection = line.closestIntersectionToStartOfLine(col.
                    getCollisionRectangle());
            if (intersection != null) {
                collisionInfos.add(new CollisionInfo(intersection, col));
            }
        }
        return collisionInfos;
    }
    /**
     * Assume an object moving from line.start() to line.end().
     * If this object will not collide with any of the collidables
     * in this collection, return null. Else, return the information
     * about the closest collision that is going to occur.
     * @param trajectory Line type, ball trajectory line
     * @return CollisionInfo type, information about collision
     */
    public CollisionInfo getClosestCollision(Line trajectory) {
        // create collision information list (ArrayList of CollisionInfo objects)
        List<CollisionInfo> colInfos = this.getCollisionInfoList(trajectory);
        Point lineStart = trajectory.start();
        // no collision with any of the collidable objects in the list
        if (colInfos.size() == 0) {
            return null;
        }
        /*
        * There is at least one collision - list not empty.
        * Take CollisionInfo from first-index collision in the list, and set
        * it as the closest, in order to compare with the rest.
         */
        CollisionInfo closestCol = colInfos.get(FIRST_INDEX);
        double minDistance = lineStart.distance(closestCol.collisionPoint());
        colInfos.remove(FIRST_INDEX); // remove calculated collision from list
        // loop all remaining collisions and compare distance
        for (CollisionInfo colInfo: colInfos) {
            double distance = lineStart.distance(colInfo.collisionPoint());
            if (distance < minDistance) {
                // there is a closer collidable object in the list - switch
                closestCol = colInfo;
                minDistance = distance;
            }
        }
        return closestCol;
    }
}
