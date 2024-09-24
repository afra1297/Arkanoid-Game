// 323274480 Michael Ifraimov
package geometry;
import biuoop.DrawSurface;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Rectangle, defined by upper left point (x,y), height and width.
 * @author Michael Ifraimov
 */
public class Rectangle {
    private Point upperLeft; // the top left point of the rectangle
    private final double height;
    private final double width;
    private Color color;

    /**
     * Constructor - Basic constructor.
     * @param upperLeft the top left point of the rectangle
     * @param width width of the rectangle
     * @param height height of the rectangle
     */
    public Rectangle(Point upperLeft, double width, double height) {
        this.upperLeft = upperLeft;
        this.width = width;
        this.height = height;
    }
    /**
     * Constructor - upper left point given as two coordinates x and y.
     * @param x x coordinate of start point
     * @param y y coordinate of start point
     * @param height height of the rectangle
     * @param width width of the rectangle
     */
    public Rectangle(double x, double y, double height, double width) {
        this.upperLeft = new Point(x, y);
        this.height = height;
        this.width = width;
    }

    /**
     * Constructor - also with color component.
     * @param x x coordinate of start point
     * @param y y coordinate of start point
     * @param height height of the rectangle
     * @param width width of the rectangle
     * @param color color of the rectangle
     */
    public Rectangle(double x, double y, double height, double width,
                     Color color) {
        this.upperLeft = new Point(x, y);
        this.height = height;
        this.width = width;
        this.color = color;
    }

    /**
     * Getter method to access the values of the upper left point.
     * @return geometry.Point type, upper left point of the rectangle
     */
    public Point getUpperLeft() {
        return this.upperLeft;
    }

    /**
     * Setter method to change the upper left point of the rectangle.
     * @param newUpperLeft Point type, new upper left point
     */
    public void setUpperLeft(Point newUpperLeft) {
        this.upperLeft = newUpperLeft;
    }

    /**
     * Get access to the upper right point of the rectangle.
     * @return geometry.Point type, the upper right point of the rectangle
     */
    public Point getUpperRight() {
        // calculate the x and y coordinates of the upper right point
        double xCor = this.getUpperLeft().getX() + this.getWidth();
        double yCor = this.getUpperLeft().getY();
        return (new Point(xCor, yCor));
    }

    /**
     * Get access to the bottom left point of the rectangle.
     * @return geometry.Point type, the bottom left point of the rectangle
     */
    public Point getBottomLeft() {
        // calculate the x and y coordinates of the bottom left point
        double xCor = this.getUpperLeft().getX();
        double yCor = this.getUpperLeft().getY() + this.getHeight();
        return (new Point(xCor, yCor));
    }

    /**
     * Get access to the bottom right point of the rectangle.
     * @return geometry.Point type, the bottom right point of the rectangle
     */
    public Point getBottomRight() {
        // calculate the x and y coordinates of the bottom right point
        double xCor = this.getUpperLeft().getX() + this.getWidth();
        double yCor = this.getUpperLeft().getY() + this.getHeight();
        return (new Point(xCor, yCor));
    }

    /**
     * Finds the upper vertex of the rectangle and returns it as Line object.
     * @return Line type, upper vertex of the rectangle
     */
    public Line getUpVertex() {
        Point start = this.getUpperLeft();
        Point end = this.getUpperRight();
        return (new Line(start, end));
    }

    /**
     * Finds the bottom vertex of the rectangle and returns it as Line object.
     * @return Line type, bottom vertex of the rectangle
     */
    public Line getDownVertex() {
        Point start = this.getBottomLeft();
        Point end = this.getBottomRight();
        return (new Line(start, end));
    }

    /**
     * Finds the left vertex of the rectangle and returns it as Line object.
     * @return Line type, left vertex of the rectangle
     */
    public Line getLeftVertex() {
        Point start = this.getUpperLeft();
        Point end = this.getBottomLeft();
        return (new Line(start, end));
    }

    /**
     * Finds the right vertex of the rectangle and returns it as Line object.
     * @return Line type, right vertex of the rectangle
     */
    public Line getRightVertex() {
        Point start = this.getUpperRight();
        Point end = this.getBottomRight();
        return (new Line(start, end));
    }


    /**
     * Checks if a given point is located within the borders of the rectangle.
     * @param p Point type, the point we check
     * @return true if the point is in the rectangle, false otherwise
     */
    public boolean isPointInRectangle(Point p) {
        return ((p.getX() < this.getUpperRight().getX()
                || Math.abs(p.getX() - this.getUpperRight().getX()) < Point.EPSILON)
                && (p.getX() > this.getUpperLeft().getX()
                || Math.abs(p.getX() - this.getUpperLeft().getX()) < Point.EPSILON)
                && (p.getY() < this.getBottomLeft().getY()
                || Math.abs(p.getY() - this.getBottomLeft().getY()) < Point.EPSILON)
                && (p.getY() > this.getUpperLeft().getY()
                || Math.abs(p.getY() - this.getUpperLeft().getY()) < Point.EPSILON));
    }

    /**
     * Checks if a given point is located on the up vertex of the rectangle.
     * @param p Point type, the point we check
     * @return true if the point is on the vertex, false otherwise
     */
    public boolean isOnUpVertex(Point p) {
        return (this.getUpVertex().isIntersecting(new Line(p, p)));
    }

    /**
     * Checks if a given point is located on the down vertex of the rectangle.
     * @param p Point type, the point we check
     * @return true if the point is on the vertex, false otherwise
     */
    public boolean isOnDownVertex(Point p) {
        return (this.getDownVertex().isIntersecting(new Line(p, p)));
    }

    /**
     * Checks if a given point is located on the left vertex of the rectangle.
     * @param p Point type, the point we check
     * @return true if the point is on the vertex, false otherwise
     */
    public boolean isOnLeftVertex(Point p) {
        return (this.getLeftVertex().isIntersecting(new Line(p, p)));
    }

    /**
     * Checks if a given point is located on the right vertex of the rectangle.
     * @param p Point type, the point we check
     * @return true if the point is on the vertex, false otherwise
     */
    public boolean isOnRightVertex(Point p) {
        return (this.getRightVertex().isIntersecting(new Line(p, p)));
    }
    /**
     * Getter method to access the height of the rectangle.
     * @return height of the rectangle
     */
    public double getHeight() {
        return this.height;
    }

    /**
     * Getter method to access the width of the rectangle.
     * @return width of the rectangle
     */
    public double getWidth() {
        return this.width;
    }
    /**
     * Getter method to access the color of the rectangle.
     * @return color of the rectangle
     */
    public Color getColor() {
        return this.color;
    }

    /**
     * Sets the color of the rectangle.
     * @param color Color type, new color
     */
    public void setColor(Color color) {
        this.color = color;
    }
    /**
     * Draws the rectangle on the drawing surface.
     * @param surface gui drawing surface
     */
    public void drawOn(DrawSurface surface) {
        // draws the rectangle itself on the drawing surface
        surface.setColor(this.getColor());
        int x = (int) Math.round(this.upperLeft.getX());
        int y = (int) Math.round(this.upperLeft.getY());
        surface.fillRectangle(x, y, (int) this.getWidth(),
                (int) this.getHeight());
    }

    /**
     * Returns a (possibly empty) List of intersection points with the
     * specified line.
     * @param line geometry.Line type, the line we check intersections with
     * @return List of intersection points (List of geometry.Point types)
     */
    public java.util.List<Point> intersectionPoints(Line line) {
        List<Point> intersectionList = new ArrayList<>();
        // given line has null value
        if (line == null) {
            return intersectionList;
        }
        // get all rectangle vertices (up, down, left, right)
        Line upVertex = this.getUpVertex();
        Line downVertex = this.getDownVertex();
        Line leftVertex = this.getLeftVertex();
        Line rightVertex = this.getRightVertex();
        // get all intersection points
        Point upInter = line.intersectionWith(upVertex);
        Point downInter = line.intersectionWith(downVertex);
        Point leftInter = line.intersectionWith(leftVertex);
        Point rightInter = line.intersectionWith(rightVertex);
        // check if intersection points are not null and add to the list
        if (upInter != null) {
            intersectionList.add(upInter);
        }
        if (downInter != null) {
            intersectionList.add(downInter);
        }
        if (leftInter != null) {
            intersectionList.add(leftInter);
        }
        if (rightInter != null) {
            intersectionList.add(rightInter);
        }
        return intersectionList;
    }
}

