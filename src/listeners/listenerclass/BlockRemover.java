// 323274480 Michael Ifraimov
package listeners.listenerclass;

import levels.GameLevel;
import gameobjects.sprites.Ball;
import gameobjects.collidables.Block;
import listeners.listenerinterface.HitListener;

/**
 * BlockRemover Class is in charge of removing blocks from the game,
 * as well as keeping count of the number of blocks that remain.
 * @author Michael Ifraimov
 */
public class BlockRemover implements HitListener {
    private static final int SINGLE_COUNT = 1;
    private final GameLevel gameLevel;
    private final Counter remainingBlocks;

    /**
     * Constructor.
     * @param gameLevel Game type, the game
     * @param removedBlocks Counter type, initial counter of the number of blocks
     */
    public BlockRemover(GameLevel gameLevel, Counter removedBlocks) {
        this.gameLevel = gameLevel;
        this.remainingBlocks = removedBlocks;
    }

    /**
     * This method is called whenever the beingHit object is hit.
     * The hitter parameter is the Ball that's doing the hitting.
     *
     * @param beingHit Block type, the block that being hit
     * @param hitter   Ball type, the ball that hits the block
     */
    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        this.remainingBlocks.decrease(SINGLE_COUNT); // decrease number of blocks by one
        beingHit.removeFromGame(this.gameLevel); // remove block from the game
        beingHit.removeHitListener(this); // remove the hit-listener of the block that is removed
    }
}
