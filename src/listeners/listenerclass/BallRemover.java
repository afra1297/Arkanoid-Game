// 323274480 Michael Ifraimov
package listeners.listenerclass;

import levels.GameLevel;
import gameobjects.sprites.Ball;
import gameobjects.collidables.Block;
import listeners.listenerinterface.HitListener;

/**
 * BallRemover Class is in charge of removing balls from the game,
 * as well as keeping count of the number of balls that remain.
 * @author Michael Ifraimov
 */
public class BallRemover implements HitListener {
    private static final int SINGLE_COUNT = 1;
    private final GameLevel gameLevel;
    private final Counter remainingBalls;

    /**
     * Constructor.
     * @param gameLevel Game type, the game
     * @param removedBalls Counter type, initial counter of the number of balls
     */
    public BallRemover(GameLevel gameLevel, Counter removedBalls) {
        this.gameLevel = gameLevel;
        this.remainingBalls = removedBalls;
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
        this.remainingBalls.decrease(SINGLE_COUNT); // decrease number of balls by one
        hitter.removeFromGame(this.gameLevel); // remove the hitter ball from the game

    }
}
