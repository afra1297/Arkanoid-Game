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
 * Class Green3 represents the game level - "Green 3".
 * @author Michael Ifraimov
 */
public class Green3 implements LevelInformation {
    // ball and paddle constants
    private static final int NUMBER_OF_BALLS = 2;
    private static final int PADDLE_SPEED = 10;
    private static final int PADDLE_WIDTH = 85;
    // color constants
    private static final Color SKY_BLUE = new Color(135, 206, 235);
    private static final Color PALE_BLUE = new Color(175, 238, 238);
    private static final Color LIGHT_GREEN = new Color(144, 238, 144);
    private static final Color MINT_GREEN = new Color(152, 255, 152);
    private static final Color SEA_FOAM_GREEN = new Color(32, 178, 170);
    private static final Color PALE_YELLOW = new Color(239, 231, 176);
    // blocks constants
    private static final int FIRST_ROW_BLOCKS = 10; // number of blocks in 1st row
    private static final double FIRST_BLOCK_X = 225; // 1st block X coordinate
    private static final double FIRST_BLOCK_Y = 150; // 1st block Y coordinate
    private static final int BLOCK_HEIGHT = 25; // height of each block
    private static final int BLOCK_WIDTH = 50; // width of each block
    // velocity constants
    private static final double BALL_ANGLE = 40; // initial ball angle movement
    private static final double OPPOSITE_BALL_ANGLE = (-1) * BALL_ANGLE; // opposite ball angle movement
    private static final double DEFAULT_SPEED = 5;
    // field members
    private final List<Block> levelBlocks = new ArrayList<>();
    /**
     * Constructor.
     */
    public Green3() {
        this.initializeBlocks();
    }
    /**
     * Initializes the block layout at the beginning of the level.
     */
    public void initializeBlocks() {
        Color[] blockColors = new Color[]{SKY_BLUE, PALE_BLUE, LIGHT_GREEN, MINT_GREEN, SEA_FOAM_GREEN};
        int blocksInRow = FIRST_ROW_BLOCKS; // number of blocks in 1st row
        for (int i = 0; i < blockColors.length; i++) {
            // choose different color for each row
            Color rowColor = blockColors[i];
            for (int j = 0; j < blocksInRow; j++) {
                // calculate coordinates for each block
                double corX = FIRST_BLOCK_X + ((i) * BLOCK_WIDTH)
                        + ((j + 1) * BLOCK_WIDTH);
                double corY = FIRST_BLOCK_Y + ((i) * BLOCK_HEIGHT);
                Point firstPoint = new Point(corX, corY);
                Block block = new Block(new Rectangle(firstPoint, BLOCK_WIDTH, BLOCK_HEIGHT)); // block creation
                block.setColor(rowColor); // color block
                this.levelBlocks.add(block); // add block to game
            }
            blocksInRow--;
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
        // set balls velocities to move in different directions
        velocities.add(Velocity.fromAngleAndSpeed(BALL_ANGLE, DEFAULT_SPEED));
        velocities.add(Velocity.fromAngleAndSpeed(OPPOSITE_BALL_ANGLE, DEFAULT_SPEED));
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
        return "Green 3";
    }

    /**
     * Draws the special background of the level.
     * @param drawSurface GUI drawing surface
     */
    @Override
    public void drawBackground(DrawSurface drawSurface) {
        drawSurface.setColor(PALE_YELLOW); // screen color
        drawSurface.fillRectangle(0, 50, drawSurface.getWidth(), drawSurface.getHeight()); // draw screen
    }

    /**
     * Returns a list of blocks that make up the level, each block contains its size, color and location.
     * @return List of Block types, the blocks of the level
     */
    public List<Block> blocks() {
        return this.levelBlocks;
    }

    /**
     * Returns the number of blocks that should be removed, before the level is considered to be "cleared".
     * This number should be <= blocks.size()
     * @return int type, number of blocks that should be removed
     */
    public int numberOfBlocksToRemove() {
        return this.levelBlocks.size();
    }
}
