// 323274480 Michael Ifraimov
package listeners.listenerinterface;

/**
 * The HitNotifier interface indicate that objects that implement it send
 * notifications when they are being hit.
 * @author Michael Ifraimov
 */
public interface HitNotifier {
    /**
     * Add hl as a listener to hit events.
     * @param hl HitListener object
     */
    void addHitListener(HitListener hl);

    /**
     * Remove hl from the list of listeners to hit events.
     * @param hl HitListener object
     */
    void removeHitListener(HitListener hl);
}
