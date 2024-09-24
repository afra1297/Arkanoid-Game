// 323274480 Michael Ifraimov
package geometry;
/**
 * Represents a Point, defined by x coordinate and y coordinate.
 * @author Michael Ifraimov
 */

public class Point {
    public static final double EPSILON = Math.pow(10, -10); // threshold
    public static final double SQUARE_POWER = 2.0;
    private final double x; // x coordinate
    private final double y; // y coordinate
    /**
     * Constructor.
     * @param x the first (x) coordinate of a point
     * @param y the second (y) coordinate of a point
     */
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }
    /**
     * Calculates and returns the distance of this point to another point.
     * @param other the point we want to calculate the distance to
     * @return the distance from this point to the other given point
     */
    public double distance(Point other) {
        return Math.sqrt(Math.pow((this.x - other.x), SQUARE_POWER)
                + Math.pow((this.y - other.y), SQUARE_POWER));
    }
    /**
     * Checks if this point is equal to another given point.
     * @param other the point we want to check if equal or not
     * @return true if the points are equal, false otherwise
     */
    public boolean equals(Point other) {
        // compare double values of two points using threshold comparison method
            return (Math.abs(this.x - other.x) < EPSILON)
                    && (Math.abs(this.y - other.y) < EPSILON);
    }
    /**
     * Getter method to access the value of variable x.
     * @return value of the variable x
     */
    public double getX() {
        return this.x;
    }

    /** Getter method to access the value of variable y.
     * @return value of the variable y
     */
    public double getY() {
        return this.y;
    }
}