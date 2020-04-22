package collision;

/**
 * A collision.HitNotifier interface.
 * @author Shai acoca
 */
public interface HitNotifier {
    /**
     * Adding collision.HitListener to the list of the hits.
     * @param hl the collision.HitListener that we add.
     */
    void addHitListener(HitListener hl);

    /**
     * Remove hl from the list of listeners to hit events.
     * @param hl the collision.HitListener that we remove.
     */
    void removeHitListener(HitListener hl);
}
