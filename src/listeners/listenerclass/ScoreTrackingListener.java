// 323274480 Michael Ifraimov
package listeners.listenerclass;

import gameobjects.sprites.Ball;
import gameobjects.collidables.Block;
import listeners.listenerinterface.HitListener;

/**
 * Class ScoreTrackingListener updates the score counter when blocks are being hit and removed.
 * @author Michael Ifraimov
 */
public class ScoreTrackingListener implements HitListener {
    private final Counter currentScore;
    private static final int SINGLE_BLOCK_POINTS = 15;

    /**
     * Constructor.
     * @param scoreCounter Counter type, initial score counter
     */
    public ScoreTrackingListener(Counter scoreCounter) {
        this.currentScore = scoreCounter;
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
        this.currentScore.increase(SINGLE_BLOCK_POINTS);

    }
}
