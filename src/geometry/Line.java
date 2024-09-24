// 323274480 Michael Ifraimov
package geometry;
import java.util.List;

/**
 * Represents a Line, defined by start point and end point.
 * @author Michael Ifraimov
 */
public class Line {
    private final Point start; // starting point of the line
    private final Point end; // ending point of the line
    private static final int FIRST_INDEX = 0;

    /**
     * Constructor 1 - receives 2 points.
     *
     * @param start geometry.Point type start of a line
     * @param end   geometry.Point type end of a line
     */
    public Line(Point start, Point end) {
        this.start = start;
        this.end = end;
    }

    /**
     * Constructor 2 - receives coordinates of start point and coordinates of
     * end point.
     *
     * @param x1 x coordinate of start point
     * @param y1 y coordinate of start point
     * @param x2 x coordinate of end point
     * @param y2 y coordinate of end point
     */
    public Line(double x1, double y1, double x2, double y2) {
        Point start = new Point(x1, y1);
        Point end = new Point(x2, y2);
        this.start = start;
        this.end = end;
    }
    /**
     * Check if the line is parallel to Y-Axis - check if the x coordinates of start and end are equal.
     * @return true - if parallel, false otherwise
     */
    public boolean parallelToYAxis() {
        return (this.end().getX() - this.start().getX() == 0);
    }

    /**
     * Calculates the incline of the line. Checks two special cases:
     * 1. The line is parallel to the x-axis
     * 2. The line is in fact a dot
     *
     * @return the incline of the line if exists, null otherwise
     */
    public Double findIncline() {
        double x1 = this.start.getX();
        double y1 = this.start.getY();
        double x2 = this.end.getX();
        double y2 = this.end.getY();
        // the line is parallel to the y-axis
        if (this.parallelToYAxis()) {
            return null;
        } else if (this.start.equals(this.end)) {
            // special case - the line is a dot
            return null;
        } else {
            // incline calculation
            return (y2 - y1) / (x2 - x1);
        }
    }

    /**
     * Calculates the length of the line.
     *
     * @return the length of the line
     */
    public double length() {
        return this.start.distance(this.end);
    }

    /**
     * Calculates and returns the middle of the line(point).
     *
     * @return geometry.Point type, middle coordinates of the line
     */
    public Point middle() {
        /* middle of the line calculation */
        double midX = (this.start.getX() + this.end.getX()) / 2.0;
        double midY = (this.start.getY() + this.end.getY()) / 2.0;
        return new Point(midX, midY);
    }

    /**
     * Returns the given start point of the line.
     * @return geometry.Point type variable
     */
    public Point start() {
        return this.start;
    }

    /**
     * Returns the given end point of the line.
     * @return geometry.Point type variable
     */
    public Point end() {
        return this.end;
    }

    /**
     * Returns the point that starts the line, if the line is parallel to Y-axis
     * it will return the lower point, in any other case returns the left-most
     * point.
     *
     * @return geometry.Point type, coordinates of the start of the line
     */
    public Point firstPoint() {
        // line parallel to Y-axis or the line is a single dot
        if (this.findIncline() == null) {
            return start.getY() <= end.getY() ? start : end;
        } else {
            // returns the left-most point
            return start.getX() <= end.getX() ? start : end;
        }
    }

    /**
     * Given a line (start point and end point) and a collinear point p,
     * the method checks if point p lies on the given line.
     *
     * @param start start point of line
     * @param end   end point of line
     * @param p     a point that is collinear to the line points
     * @return true if the point lies on the line, false otherwise
     */
    public static boolean onLine(Point start, Point end, Point p) {
        if (p == null) {
            return false;
        }
        // check if the point coordinates are between line coordinates
        double pX = p.getX();
        double pY = p.getY();
        double startX = start.getX();
        double startY = start.getY();
        double endX = end.getX();
        double endY = end.getY();
        // check if x and y coordinates of a given point are between the line's x and y coordinates
        return ((pX <= Math.max(startX, endX)) || (Math.abs(Math.max(startX, endX) - pX) < Point.EPSILON))
                && ((pX >= Math.min(startX, endX)) || (Math.abs(pX - Math.min(startX, endX)) < Point.EPSILON))
                && ((pY <= Math.max(startY, endY)) || (Math.abs(Math.max(startY, endY) - pY) < Point.EPSILON))
                && ((pY >= Math.min(startY, endY)) || (Math.abs(pY - Math.min(startY, endY)) < Point.EPSILON));
    }

    /**
     * Checks if a line is a single point.
     * @return true if the line is single point, false otherwise
     */
    public boolean isPoint() {
        return (Math.abs(this.length() - 0) < Point.EPSILON);
    }

    /**
     * Checks if a given point is on one of the edges of the line (start or end).
     * @param p Point type, a given point.
     * @return Point type, the edge of the line which is equal to the given point
     */
    public Point pointOnEdge(Point p) {
        if (p.equals(this.start())) {
            return (p);
        }
        if (p.equals(this.end())) {
            return (p);
        }
        return null;
    }
    /**
     * Finds the intersection point between two parallel lines (returns null if no intersection point is found).
     * @param other Line type,line to find the intersection with
     * @return Point type, the intersection point if found, null otherwise.
     */
    public Point parallelLinesIntersection(Line other) {
        Point intersection = null;
        // check if the start point of this line is on the other line
        Point edge = other.pointOnEdge(this.start());
        if (edge != null && !onLine(other.start(), other.end(), this.end())) {
            // get the other point of the other line (not the intersection point)
            Point otherPoint = (edge.equals(other.start())) ? other.end() : other.start();
            if (!onLine(this.start(), this.end(), otherPoint)) {
                intersection = edge; // intersection point found
            }
        } else if (other.pointOnEdge(this.end()) != null) {
            // check if the end point of this line is on the other line
            edge = other.pointOnEdge(this.end());
            assert edge != null;
            // get the other point of the other line (not the intersection point)
            Point otherPoint = (other.start().equals(edge)) ? other.end() : other.start();
            if (!onLine(other.start(), other.end(), this.start()) && !onLine(this.start(), this.end(), otherPoint)) {
                intersection = edge; // intersection point found
            }
        }
        return intersection;
    }

    /**
     * Checks the intersection between two lines, when one of the lines is parallel to the Y-Axis.
     * @param other Line type, line to find the intersection with
     * @return Point type, the intersection point if found, null otherwise.
     */
    public Point parallelToYIntersection(Line other) {
        Point finalIntersection = null;
        // one of the lines is not  parallel to the Y-Axis
        if (!this.parallelToYAxis() || !other.parallelToYAxis()) {
            double corY; // y coordinate of intersection point
            Point intersectionPoint;
            // this line is not parallel to Y-Axis
            if (!this.parallelToYAxis()) {
                // calculate y coordinate of intersection
                corY = this.findIncline() * other.start().getX()
                        + (this.start().getY() - (this.findIncline() * this.start().getX()));
                intersectionPoint = new Point(other.start().getX(), corY);
            } else { // other line is not parallel to Y-Axis
                // calculate y coordinate of intersection
                corY = other.findIncline() * this.start().getX()
                        + (other.start().getY() - (other.findIncline() * other.start().getX()));
                intersectionPoint = new Point(this.start().getX(), corY);
            }
            // check if intersection point is on both of the lines
            if (onLine(this.start(), this.end(), intersectionPoint)
                    && onLine(other.start(), other.end(), intersectionPoint)) {
                finalIntersection = intersectionPoint;
            }
        } else { // both lines are parallel to Y-Axis or special case: the lines are single points
            // check if other line is a single point and if it is on this line
            if (other.isPoint() && onLine(this.start(), this.end(), other.start())) {
                finalIntersection = other.start();
            } else if (this.isPoint() && onLine(other.start(), other.end(), this.start())) { // this line is a point
                finalIntersection = this.start();
            } else { // both lines are parallel to Y-Axis
                finalIntersection = this.parallelLinesIntersection(other);
            }
        }
        return finalIntersection;
    }

    /**
     * Finds the intersection point between two lines that are not parallel to the Y-Axis.
     * Returns null if no intersection point is found or if the lines are parallel to each other.
     * @param other Line type, line to find the intersection with
     * @return Point type, the intersection point if found, null otherwise.
     */
    public Point intersectionPoint(Line other) {
        Point intersection = null;
        double thisIncline = this.findIncline(); // this line incline
        double otherIncline = other.findIncline(); // other line incline
        double thisIntersectionY = this.start().getY() - (this.findIncline() * this.start().getX());
        double otherIntersectionY = other.start().getY() - (other.findIncline() * other.start().getX());
        // check if the lines are parallel
        if (Math.abs(thisIncline - otherIncline) < Point.EPSILON) {
            intersection = this.parallelLinesIntersection(other); // find intersection if parallel
        } else {
            // find the x-coordinate of the intersection point between the two lines
            double corX = (otherIntersectionY - thisIntersectionY) / (thisIncline - otherIncline);
            // find the y-coordinate of the intersection point between the two lines
            double corY = (((otherIncline * thisIntersectionY) - (thisIncline * otherIntersectionY))
                    / (otherIncline - thisIncline));
            Point intersectionPoint = new Point(corX, corY);
            // check if the intersection point lies on both line segments
            if (onLine(this.start(), this.end(), intersectionPoint)
                    && onLine(other.start(), other.end(), intersectionPoint)) {
                intersection = intersectionPoint;
            }
        }
        return intersection;
    }

    /**
     * Finds and returns the intersection point if the lines intersect, returns
     * null otherwise.
     * @param other geometry.Line type variable, the line we want to check if there is
     *              intersection with.
     * @return geometry.Point type intersection point or null if there is no intersection
     * or more than one intersection point(overlap)
     */
    public Point intersectionWith(Line other) {
        if (other == null) {
            return null;
        }
        // check if the lines are equal
        if (this.equals(other)) {
            // check if both lines are single points - there is one intersection point
            if (this.isPoint() && other.isPoint()) {
                return this.start();
            }
            // more than one intersection point
            return null;
        }
        // check if one of the lines (or both) is parallel to the Y-Axis and find the intersection point
        if (this.parallelToYAxis() || other.parallelToYAxis()) {
            return this.parallelToYIntersection(other);
        }
        return this.intersectionPoint(other);

    }

    /**
     * Checks if two lines are intersecting with each other, using the intersectionWith method.
     * @param other Line type object
     * @return true if the lines intersect, false otherwise
     */
    public boolean isIntersecting(Line other) {
        return (this.intersectionWith(other)  != null);
    }


    /**
     * Checks if the lines are equal, by comparing the first and last point
     * of each line.
     * @param other geometry.Line type variable, the line we compare
     * @return true if the lines are equal, false otherwise
     */
    public boolean equals(Line other) {
        return (this.start().equals(other.start()) && this.end().equals(other.end()))
                || (this.start().equals(other.end()) && this.end().equals(other.start()));
    }
    /**
     * Finds and returns the closest intersection point to the start of the
     * line, if the line does not intersect with the rectangle, returns null.
     * @param rect geometry.Rectangle type, a given rectangle
     * @return geometry.Point type, the closest intersection point to the start of the
     * line, or null if there is no intersection
     */
    public Point closestIntersectionToStartOfLine(Rectangle rect) {
        Point closePoint;
        // get all intersection points with the rectangle
        List<Point> intersectionList = rect.intersectionPoints(this);
        if (intersectionList.size() == 0) {
            // there are no intersection points
            return null;
        }
        // choose first point in the list as the closest, only to compare
        closePoint = intersectionList.get(FIRST_INDEX);
        // find distance from the start of the line to the chosen point
        double minDistance = this.firstPoint().distance(closePoint);
        // remove first point from the list to avoid double-checking
        intersectionList.remove(FIRST_INDEX);
        // check distance between start of the line to other intersections
        for (Point intersection : intersectionList) {
            double distance = this.firstPoint().distance(intersection);
            if (distance < minDistance) {
                minDistance = distance;
                closePoint = intersection;
            }
        }
        return closePoint;
    }
}
