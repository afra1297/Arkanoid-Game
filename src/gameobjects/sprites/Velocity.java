// 323274480 Michael Ifraimov
package gameobjects.sprites;

import geometry.Point;

/**
 * Represents Velocity, defined by horizontal component dx and vertical
 * component dy.
 * @author Michael Ifraimov
 */
public class Velocity {
    private double dx; // change in position of x coordinate
    private double dy; // change in position of y coordinate

    /**
     * Constructor.
     * @param dx change in position of x coordinate
     * @param dy change in position of y coordinate
     */
    public Velocity(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }
    /**
     * Calculates horizontal(dx) and vertical(dy) components of velocity using
     * the formula: given an angle - U and speed - S:
     * horizontal: dx = S * COS(U)
     * vertical: dy = S * SIN(U).
     * @param angle direction in degrees(up is angle 0)
     * @param speed speed of the ball
     * @return Velocity type, the velocity of the ball
     */
    public static Velocity fromAngleAndSpeed(double angle, double speed) {
        // convert angle from degrees to radians
        double angleInRadians = Math.toRadians(angle);
        // calculate horizontal component
        double dx = speed * Math.sin(angleInRadians);
        // calculate vertical component
        double dy = speed * -Math.cos(angleInRadians);
        return new Velocity(dx, dy);
    }
    /**
     * Getter method to access horizontal component(dx) value.
     * @return horizontal component(dx)
     */
    public double getDx() {
        return this.dx;
    }

    /**
     * Getter method to access vertical component(dy) value.
     * @return vertical component(dy)
     */
    public double getDy() {
        return this.dy;
    }

    /**
     * Setter method to change the value of horizontal component(dx).
     * @param dx horizontal component(dx)
     */
    public void setDx(double dx) {
        this.dx = dx;
    }

    /**
     * Setter method to change the value of vertical component(dy).
     * @param dy vertical component(dy)
     */
    public  void setDy(double dy) {
        this.dy = dy;
    }

    /**
     * Takes a point with position (x,y) and returns a new point with position
     * (x+dx,y+dy).
     * @param p geometry.Point type, a given point
     * @return geometry.Point type, point after change(adding dx, dy)
     */
    public Point applyToPoint(Point p) {
        return new Point(p.getX() + this.dx, p.getY() + this.dy);
    }

    /**
     * Calculates the speed using a simple mathematical formula.
     * @param dx horizontal component
     * @param dy vertical component
     * @return double type, the speed
     */
    public double getSpeed(double dx, double dy) {
        return Math.sqrt((dx * dx) + (dy * dy));
    }

    /**
     * Setter method, set new angle for a given velocity.
     * @param angle double type, new angle
     * @return Velocity type, new angle velocity
     */
    public Velocity setAngle(double angle) {
        double speed = getSpeed(this.dx, this.dy);
        return fromAngleAndSpeed(angle, speed);
    }
}
