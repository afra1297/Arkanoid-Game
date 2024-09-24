// 323274480 Michael Ifraimov

package levels;

import biuoop.DrawSurface;
import gameobjects.collidables.Block;
import gameobjects.sprites.Velocity;
import java.util.List;


/**
 * The LevelInformation interface specifies the information required to fully describe a level in the game.
 * @author Michael Ifraimov
 */
public interface LevelInformation {
    /**
     * Returns the number of balls in the level.
     * @return int type, number of balls in the level
     */
    int numberOfBalls();

    /**
     * Returns a list of initial velocities of each ball in the level.
     * @return List of Velocity types, list of ball velocities in the level
     */
    List<Velocity> initialBallVelocities();

    /**
     * Returns the speed of the paddle in the level.
     * @return int type, paddle speed
     */
    int paddleSpeed();

    /**
     * Returns the width of the paddle in the level.
     * @return int type, paddle width
     */
    int paddleWidth();

    /**
     * Returns the name of the level.
     * @return String type, the name of the level
     */
    String levelName();

    /**
     * Draws the special background of the level.
     * @param drawSurface GUI drawing surface
     */
    void drawBackground(DrawSurface drawSurface);

    /**
     * Returns a list of blocks that make up the level, each block contains its size, color and location.
     * @return List of Block types, the blocks of the level
     */
    List<Block> blocks();

    /**
     * Returns the number of blocks that should be removed, before the level is considered to be "cleared".
     * This number should be <= blocks.size()
     * @return int type, number of blocks that should be removed
     */
    int numberOfBlocksToRemove();
}
