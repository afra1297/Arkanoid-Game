// 323274480 Michael Ifraimov
package gameobjects.collidables;

import levels.GameLevel;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import gameobjects.sprites.Ball;
import gameobjects.sprites.Sprite;
import gameobjects.sprites.Velocity;
import geometry.Point;
import geometry.Rectangle;

import java.awt.Color;

/**
 * Represents a Paddle, the paddle is the player in the game.
 * It is a rectangle that is controlled by the arrow keys,
 * and moves according to the player key presses.
 * @author Michael Ifraimov
 */
public class Paddle implements Collidable, Sprite {
    private static final int FRAME_SIZE = 25;
    private static final int MOST_LEFT_REGION = 0; // most left region of the paddle
    private static final int MID_LEFT_REGION = 1; // middle left region of the paddle
    private static final int MID_REGION = 2; // middle region of the paddle
    private static final int MID_RIGHT_REGION = 3; // middle right region of the paddle
    private static final int HIT_REGIONS = 5; // number of paddle regions
    // ball movement angle after hitting the left-most region of the paddle
    // Class fields
    private final biuoop.KeyboardSensor keyboard;
    private final Rectangle paddleShape; // the paddle itself (rectangle)
    private final Rectangle frame; // frame of the game (rectangle)
    private final double oneStep; // single step of the paddle

    /**
     * Constructor.
     * @param keyboard KeyboardSensor type, keyboard click
     * @param paddleShape the paddle itself (rectangle)
     * @param frame the frame of the game
     * @param step single step of the paddle
     */
    public Paddle(KeyboardSensor keyboard, Rectangle paddleShape,
                  Rectangle frame, double step) {
        this.keyboard = keyboard;
        this.paddleShape = paddleShape;
        this.frame = frame;
        this.oneStep = step;
    }

    /**
     * Sets a new color for the paddle.
     * @param color Color type, new color
     */
    public void setColor(Color color) {
        this.paddleShape.setColor(color);
    }
    /**
     * Moves the paddle one step left, if the movement is possible. If paddle
     * touches border - it won't move.
     */
    public void moveLeft() {
        // frame left border x coordinate
        double frameLeftBorder = frame.getUpperLeft().getX() + FRAME_SIZE;
        // the location of the paddle after a single step left
        double moveLeft = this.getCollisionRectangle().getUpperLeft().getX()
                - this.oneStep;
        Point newLoc; // new upper left point location of the paddle
        // the paddle touches left frame border - it will not move
        if (moveLeft <= frameLeftBorder) {
            // paddle remain "stuck" to  the border
            newLoc = new Point(frameLeftBorder,
                    this.getCollisionRectangle().getUpperLeft().getY());
        } else {
            // move paddle one step left
            newLoc = new Point(moveLeft,
                    this.getCollisionRectangle().getUpperLeft().getY());
        }
        this.paddleShape.setUpperLeft(newLoc);
    }

    /**
     * Moves the paddle one step right, if the movement is possible. If paddle
     * touches border - it won't move.
     */
    public void moveRight() {
        // frame right border x coordinate
        double frameBorder = frame.getUpperRight().getX() - FRAME_SIZE;
        // the location of the paddle after a single step right
        double moveRight = this.getCollisionRectangle().getUpperLeft().getX()
                + this.oneStep;
        Point newLoc; // new upper left point location of the paddle
        // the paddle touches right frame border - it will not move
        if (this.getCollisionRectangle().getUpperRight().getX() >= frameBorder) {
            // paddle remain "stuck" to  the border
            newLoc = new Point(frameBorder - this.paddleShape.getWidth(),
                    this.getCollisionRectangle().getUpperLeft().getY());
        } else {
            // move paddle one step right
            newLoc = new Point(moveRight,
                    this.getCollisionRectangle().getUpperLeft().getY());
        }
        this.paddleShape.setUpperLeft(newLoc);
    }


    @Override
    public Rectangle getCollisionRectangle() {
        double xCor = this.paddleShape.getUpperLeft().getX();
        double yCor = this.paddleShape.getUpperLeft().getY();
        return new Rectangle(xCor, yCor, this.paddleShape.getHeight(),
                this.paddleShape.getWidth(), this.paddleShape.getColor());
    }

    /**
     * Notify the object that we collided with it at collisionPoint with
     * a given velocity. The return is the new velocity expected after the hit
     * (based on the force the object inflicted on us).
     *
     * @param hitter          Ball type, a ball
     * @param collisionPoint  Point type, point of collision with the object
     * @param currentVelocity Velocity type, previous velocity
     * @return Velocity type, new velocity after the hit
     */
    @Override
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        // set new velocity as the current velocity to initialize it
        Velocity newVelocity = new Velocity(currentVelocity.getDx(),
                currentVelocity.getDy());
        // collision point is on left vertex of the paddle
        if (this.getCollisionRectangle().isOnLeftVertex(collisionPoint)) {
            // reverse horizontal component of the velocity
            newVelocity.setDx(-currentVelocity.getDx());
        }
        // collision point is on right vertex of the paddle
        if (this.getCollisionRectangle().isOnRightVertex(collisionPoint)) {
            // reverse horizontal component of the velocity
            newVelocity.setDx(-currentVelocity.getDx());
        }
        // collision point is on top of the paddle - check region
        if (this.getCollisionRectangle().isOnUpVertex(collisionPoint)) {
            double distanceFromLeft = collisionPoint.distance(this.getCollisionRectangle().getUpperLeft());
            double regionSize = this.getCollisionRectangle().getUpVertex().length() / HIT_REGIONS;
            int region = (int) (distanceFromLeft / regionSize);
            return switch (region) {
                case MOST_LEFT_REGION -> newVelocity.setAngle(-50.0);
                case MID_LEFT_REGION -> newVelocity.setAngle(-25.0);
                case MID_REGION -> newVelocity.setAngle(0.0);
                case MID_RIGHT_REGION -> newVelocity.setAngle(25.0);
                default -> newVelocity.setAngle(50.0);
            };
        }
        // collision point is on the bottom of the paddle (happens when paddle goes over the ball)
        if (this.getCollisionRectangle().isOnDownVertex(collisionPoint)) {
                newVelocity.setDy(-currentVelocity.getDy());
        }
        return newVelocity;
    }

    @Override
    public void drawOn(DrawSurface surface) {
        Block block = new Block(this.getCollisionRectangle());
        block.drawOn(surface);
    }

    @Override
    public void timePassed() {
        if (this.keyboard.isPressed(this.keyboard.LEFT_KEY)) {
            this.moveLeft();
        }
        if (this.keyboard.isPressed(this.keyboard.RIGHT_KEY)) {
            this.moveRight();
        }
    }
    /**
     * Add paddle to the game - adds it to Sprites and Collidable collections.
     * @param g Game type
     */
    public void addToGame(GameLevel g) {
        g.addSprite(this);
        g.addCollidable(this);
    }
}