// 323274480 Michael Ifraimov
package levels;

import biuoop.DrawSurface;
import gameobjects.collidables.Block;
import gameobjects.sprites.Velocity;
import geometry.Point;
import geometry.Rectangle;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * Class DirectHit, describes "Direct Hit" game level.
 * @author Michael Ifraimov
 */
public class DirectHit implements LevelInformation {
    // decoration circles constants
    private static final int CENTER_Y = 170;
    private static final int SMALL_RADIUS = 60;
    private static final int MEDIUM_RADIUS = 90;
    private static final int BIG_RADIUS = 120;
    private static final int SCREEN_WIDTH = 800;
    private static final int SCREEN_HEIGHT = 600;
    private static final int NUMBER_OF_BALLS = 1;
    private static final int PADDLE_SPEED = 10;
    private static final int PADDLE_WIDTH = 85;
    // blocks constants
    private static final int NUMBER_OF_BLOCKS = 1;
    private static final double FIRST_BLOCK_X = 385; // block X coordinate
    private static final double FIRST_BLOCK_Y = 155; // block Y coordinate
    private static final int BLOCK_HEIGHT = 30; // height of each block
    private static final int BLOCK_WIDTH = 30; // width of each block
    // velocity constants
    private static final double STRAIGHT_UP_DEGREE = 0; // the degree in which the ball goes straight up
    private static final double DEFAULT_SPEED = 5;
    // field members
    private final List<Block> levelBlocks = new ArrayList<>();

    /**
     * Constructor.
     */
    public DirectHit() {
        this.initializeBlocks();
    }

    /**
     * Initializes the block layout at the beginning of the level.
     */
    public void initializeBlocks() {
        Point upperLeft = new Point(FIRST_BLOCK_X, FIRST_BLOCK_Y);
        Block block = new Block(new Rectangle(upperLeft, BLOCK_WIDTH, BLOCK_HEIGHT));
        block.setColor(Color.RED);
        this.levelBlocks.add(block);
    }
    /**
     * Returns the number of balls in the level.
     *
     * @return int type, number of balls in the level
     */
    @Override
    public int numberOfBalls() {
        return NUMBER_OF_BALLS;
    }

    /**
     * Returns a list of initial velocities of each ball in the level.
     *
     * @return List of Velocity types, list of ball velocities in the level
     */
    @Override
    public List<Velocity> initialBallVelocities() {
        List<Velocity> velocities = new ArrayList<>();
        velocities.add(Velocity.fromAngleAndSpeed(STRAIGHT_UP_DEGREE, DEFAULT_SPEED));
        return velocities;
    }

    /**
     * Returns the speed of the paddle in the level.
     *
     * @return int type, paddle speed
     */
    @Override
    public int paddleSpeed() {
        return PADDLE_SPEED;
    }

    /**
     * Returns the width of the paddle in the level.
     *
     * @return int type, paddle width
     */
    @Override
    public int paddleWidth() {
        return PADDLE_WIDTH;
    }

    /**
     * Returns the name of the level.
     *
     * @return String type, the name of the level
     */
    @Override
    public String levelName() {
        return "Direct Hit";
    }

    /**
     * Draws the special background of the level.
     *
     * @param drawSurface GUI drawing surface
     */
    @Override
    public void drawBackground(DrawSurface drawSurface) {
        drawSurface.setColor(Color.GRAY);
        drawSurface.fillRectangle(0, 50, drawSurface.getWidth(), drawSurface.getHeight());
        drawSurface.setColor(Color.BLUE);
        // draw circles
        drawSurface.drawCircle(SCREEN_WIDTH / 2, CENTER_Y, SMALL_RADIUS);
        drawSurface.drawCircle(SCREEN_WIDTH / 2, CENTER_Y, MEDIUM_RADIUS);
        drawSurface.drawCircle(SCREEN_WIDTH / 2, CENTER_Y, BIG_RADIUS);
        // draw bottom line
        drawSurface.drawLine(SCREEN_WIDTH / 2, CENTER_Y, 400, 290);
        // draw right line
        drawSurface.drawLine(SCREEN_WIDTH / 2, CENTER_Y, 520, 170);
        // draw left line
        drawSurface.drawLine(SCREEN_WIDTH / 2, CENTER_Y, 280, 170);
        // draw up line
        drawSurface.drawLine(SCREEN_WIDTH / 2, CENTER_Y, 400, 22);

    }

    /**
     * Returns a list of blocks that make up the level, each block contains its size, color and location.
     *
     * @return List of Block types, the blocks of the level
     */
    @Override
    public List<Block> blocks() {
        return this.levelBlocks;
    }

    /**
     * Returns the number of blocks that should be removed, before the level is considered to be "cleared".
     * This number should be <= blocks.size()
     *
     * @return int type, number of blocks that should be removed
     */
    @Override
    public int numberOfBlocksToRemove() {
        return NUMBER_OF_BLOCKS;
    }
}
