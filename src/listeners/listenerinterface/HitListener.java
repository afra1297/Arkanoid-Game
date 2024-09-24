// 323274480 Michael Ifraimov
package listeners.listenerinterface;

import gameobjects.sprites.Ball;
import gameobjects.collidables.Block;

/**
 * Represents objects that want to be notified of hit events.
 * @author Michael Ifarimov
 */
public interface HitListener {
    /**
     * This method is called whenever the beingHit object is hit.
     * The hitter parameter is the Ball that's doing the hitting.
     * @param beingHit Block type, the block that being hit
     * @param hitter Ball type, the ball that hits the block
     */
    void hitEvent(Block beingHit, Ball hitter);
}
