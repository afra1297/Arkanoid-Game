// 323274480 Michael Ifraimov
package levels;

import biuoop.DrawSurface;
import gameobjects.collidables.Block;
import gameobjects.sprites.Velocity;
import geometry.Rectangle;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * Class WideEasy, describes "Wide Easy" game level.
 * @author Michael Ifraimov
 */
public class WideEasy implements LevelInformation {
    private static final int SCREEN_WIDTH = 800;
    private static final int FRAME_WIDTH = 25;
    // ball and paddle constants
    private static final int NUMBER_OF_BALLS = 10;
    private static final int PADDLE_SPEED = 2;
    private static final int PADDLE_WIDTH = 600;
    // blocks constants
    private static final int NUMBER_OF_BLOCKS = 15;
    private static final double FIRST_BLOCK_X = 25; // 1st block X coordinate
    private static final double FIRST_BLOCK_Y = 250; // 1st block Y coordinate
    private static final int BLOCK_HEIGHT = 25; // height of each block
    private static final int BLOCK_WIDTH = 50; // width of each block
    // velocity constants
    private static final int FIRST_ANGLE = -50; // first ball initial moving angle
    private static final int STRAIGHT_UP_ANGLE = 0; // straight up moving angle
    private static final int LAST_ANGLE = 50; // last ball initial moving angle
    private static final int ANGLE_STEP = 10; // step from each ball initial moving angle
    private static final double DEFAULT_SPEED = 5;
    // color constants
    private static final Color PALE_YELLOW = new Color(239, 231, 176);
    private static final Color LIGHT_BLUE = new Color(135, 206, 235);
    private static final Color GOLDEN_YELLOW = new Color(236, 215, 73);
    private static final Color STRONG_YELLOW = new Color(255, 225, 24);
    // circle constants
    private static final int SMALL_RADIUS = 40;
    private static final int MID_RADIUS = 50;
    private static final int BIG_RADIUS = 60;
    private static final int CIRCLE_CENTER = 150;
    private static final int NUMBER_OF_RAYS = 100;
    // field members
    private final List<Block> levelBlocks = new ArrayList<>();

    /**
     * Constructor.
     */
    public WideEasy() {
        this.initializeBlocks();
    }

    /**
     * Initializes the block layout at the beginning of the level.
     */
    public void initializeBlocks() {
        boolean switchColor = false;
        for (int i = 0; i < NUMBER_OF_BLOCKS; i++) {
            Color blockColor;
            if (!switchColor) {
                blockColor = Color.YELLOW;
                switchColor = true;
            } else {
                blockColor = Color.BLUE;
                switchColor = false;
            }
            // calculate coordinates for each block
            double corX = FIRST_BLOCK_X + (i * BLOCK_WIDTH);
            geometry.Point firstPoint = new geometry.Point(corX, FIRST_BLOCK_Y);
            Block block = new Block(new Rectangle(firstPoint, BLOCK_WIDTH, BLOCK_HEIGHT)); // block creation
            block.setColor(blockColor); // color block
            this.levelBlocks.add(block); // add block to game
        }
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
        // give each ball a different velocity, each ball will move in a different direction
        for (int angle = FIRST_ANGLE; angle <= LAST_ANGLE; angle += ANGLE_STEP) {
            // if the angle is straight up (0), skip this angle
            if (angle == STRAIGHT_UP_ANGLE) {
                continue;
            }
            velocities.add(Velocity.fromAngleAndSpeed(angle, DEFAULT_SPEED));
        }
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
        return "Wide Easy";
    }

    /**
     * Draws the special background of the level.
     *
     * @param drawSurface GUI drawing surface
     */
    @Override
    public void drawBackground(DrawSurface drawSurface) {
        drawSurface.setColor(LIGHT_BLUE); // screen color
        drawSurface.fillRectangle(0, 50, drawSurface.getWidth(), drawSurface.getHeight()); // draw screen
        // draw big sun circle
        drawSurface.setColor(PALE_YELLOW);
        drawSurface.fillCircle(CIRCLE_CENTER, CIRCLE_CENTER, BIG_RADIUS);
        // draw sun rays
        for (int i = 1; i <= NUMBER_OF_RAYS; ++i) {
            drawSurface.drawLine(CIRCLE_CENTER, CIRCLE_CENTER,
                    (SCREEN_WIDTH - FRAME_WIDTH) / NUMBER_OF_RAYS * i, (int) FIRST_BLOCK_Y);
        }
        // draw medium sun circle
        drawSurface.setColor(GOLDEN_YELLOW);
        drawSurface.fillCircle(CIRCLE_CENTER, CIRCLE_CENTER, MID_RADIUS);
        // draw middle sun circle - small circle
        drawSurface.setColor(STRONG_YELLOW);
        drawSurface.fillCircle(CIRCLE_CENTER, CIRCLE_CENTER, SMALL_RADIUS);
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
        return this.levelBlocks.size();
    }
}
