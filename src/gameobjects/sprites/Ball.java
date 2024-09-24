// 323274480 Michael Ifraimov
package gameobjects.sprites;
import levels.GameLevel;
import biuoop.DrawSurface;
import gameobjects.collidables.Collidable;
import gameobjects.collidables.CollisionInfo;
import gameobjects.collidables.GameEnvironment;
import geometry.Line;
import geometry.Point;
import java.awt.Color;

/**
 * Represents a Ball, defined by center(geometry.Point), size(radius), color and
 * velocity.
 * @author Michael Ifraimov
 */
public class Ball implements Sprite {
    public static final int DEFAULT_VALUE = 0;
    public static final int BAD_HIT_NUMBER = 4;
    private Point center; // center point
    private final int size; // radius
    private final Color color;
    private Velocity velocity;
    private final GameEnvironment gameEnvironment; // all collidable objects
    /**
     * Constructor.
     *
     * @param center center point of the ball
     * @param r      size(radius) of the ball
     * @param color  color of the ball
     * @param gameEnvironment all collidable objects the ball can collide with
     */
    public Ball(Point center, int r, java.awt.Color color,
                GameEnvironment gameEnvironment) {
        this.center = center;
        this.size = r;
        this.color = color;
        this.gameEnvironment = gameEnvironment;
        // default velocity (0,0)
        this.velocity = new Velocity(DEFAULT_VALUE, DEFAULT_VALUE);
    }


    /**
     * Getter method for ball's environment.
     * @return GameEnvironment type object
     */
    public GameEnvironment getGameEnvironment() {
        return this.gameEnvironment;
    }

    /**
     * Getter function to get x coordinate of the center.
     * @return x coordinate of the center point
     */
    public int getX() {
        return (int) (this.center.getX());
    }

    /**
     * Getter function to get y coordinate of the center.
     * @return y coordinate of the center point
     */
    public int getY() {
        return (int) (this.center.getY());
    }

    /**
     * Getter function to get the size(radius) of the ball.
     * @return value of the radius
     */
    public int getSize() {
        return this.size;
    }

    /**
     * Getter function to get the color of the ball.
     * @return color of the ball
     */
    public java.awt.Color getColor() {
        return this.color;
    }

    /**
     * Getter function to get the center point of the ball.
     * @return Point type, center point of the ball
     */
    public Point getCenter() {
        return this.center;
    }
    /**
     * Draws the ball on the drawing surface.
     *
     * @param surface gui drawing surface
     */
    public void drawOn(DrawSurface surface) {
        surface.setColor(this.getColor());
        surface.fillCircle(this.getX(), this.getY(), this.size);
    }

    @Override
    public void timePassed() {
        this.moveOneStep();
    }

    /**
     * Getter method, allows access to velocity field of ball.
     *
     * @return Velocity type, the velocity of the ball
     */
    public Velocity getVelocity() {
        return this.velocity;
    }

    /**
     * Setter method, allows to access and change velocity field of ball,
     * when given new Velocity type velocity.
     *
     * @param v Velocity type, the new velocity
     */
    public void setVelocity(Velocity v) {
        this.velocity = v;
    }

    /**
     * Setter method, allows to access and change velocity field of ball,
     * when given new dx and dy values.
     *
     * @param dx change in position of x coordinate
     * @param dy change in position of y coordinate
     */
    public void setVelocity(double dx, double dy) {
        this.velocity = new Velocity(dx, dy);
    }

    /**
     * Returns the line that represent ball trajectory line after a single move.
     * @return Line type, ball trajectory line
     */
    public Line getTrajectory() {
        return new Line(this.getCenter(), this.getVelocity().
                applyToPoint(this.getCenter()));
    }
    /**
     * Moves the ball one step on its trajectory line, according to ball's
     * velocity. Check if the ball hit any collidables and changes its direction
     * accordingly.
     */

    public void moveOneStep() {
        Line ballTrajectory = this.getTrajectory(); // ball trajectory line
        // closest collidable object information
        CollisionInfo closeCol = this.getGameEnvironment().
                getClosestCollision(ballTrajectory);
        int count = 0;
        // the ball will collide with object
        while (closeCol != null) {
            /*
             * Special case - ball stuck on the collidable:
             * in this case the ball hit the collidable 4 times, and it didn't
             * move.
             */
            if (count >= BAD_HIT_NUMBER) {
                // reverse ball direction
                this.setVelocity(-this.getVelocity().getDx(),
                        -this.getVelocity().getDy());
                break;
            }
            // the ball hits the collidable object
            Collidable nextCollidable = closeCol.collisionObject();
            // set ball velocity according to hitting the collidable
            this.setVelocity(nextCollidable.hit(this, closeCol.collisionPoint(),
                    this.getVelocity()));
            // check again if there is an immediate collision
            ballTrajectory = this.getTrajectory();
            closeCol = this.getGameEnvironment().
                    getClosestCollision(ballTrajectory);
            count++;
        }
        // if the paddle overrides the ball - move the ball out of the paddle
        this.moveBallOutOfPaddle();
        this.setCenter(this.getVelocity().applyToPoint(this.getCenter()));
    }

    /**
     * A method that moves a stuck ball outside the paddle, if the paddle overrides it.
     */
    public void moveBallOutOfPaddle() {
        Line trajectory = this.getTrajectory();
        CollisionInfo closestCollision = this.getGameEnvironment().getClosestCollision(trajectory);
        // if there is a collision point
        if (closestCollision != null) {
            Collidable collisionBlock = closestCollision.collisionObject();

            // check if the ball is inside the collision block
            while (collisionBlock.getCollisionRectangle().isPointInRectangle(this.getCenter())) {
                // change the ball's vertical velocity
                this.getVelocity().setDy(-this.getVelocity().getDy());
                // calculate the distances from the ball's center to the left and right edges of the collision block
                double distanceFromLeft = Math.abs(collisionBlock.getCollisionRectangle().getUpperLeft().getX()
                        - this.getCenter().getX());
                double distanceFromRight = Math.abs(collisionBlock.getCollisionRectangle().getUpperRight().getX()
                        - this.getCenter().getX());
                // change the ball's horizontal velocity based on the closer edge
                if (distanceFromLeft >= distanceFromRight) {
                    this.getVelocity().setDx(Math.abs(this.getVelocity().getDx()));
                } else {
                    this.getVelocity().setDx(-Math.abs(this.getVelocity().getDx()));
                }
                // move the ball to the next position based on its velocity
                this.setCenter(this.getVelocity().applyToPoint(this.getCenter()));
            }
            // move the ball to the next position if it was inside the collision block
            this.setCenter(this.getVelocity().applyToPoint(this.getCenter()));
        }
    }


    /**
     * Adds a ball to the sprite collection of the game.
     * @param g Game type, the game
     */
    public void addToGame(GameLevel g) {
        g.addSprite(this);
    }

    /**
     * Removes a ball from the sprite collection of the game.
     * @param g Game type, the game
     */
    public void removeFromGame(GameLevel g) {
        g.removeSprite(this);
    }

    /**
     * Sets a new center for the ball.
     * @param center Point type, new center
     */
    public void setCenter(Point center) {
        this.center = center;
    }
}