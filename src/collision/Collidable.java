package collision;

import sprite.Ball;
import geometry.Point;
import geometry.Rectangle;

/**
 * A collidable interface.
 * @author Shai acoca
 */
public interface Collidable {
    /**
     * @return the "collision shape" of the object.
     */
    Rectangle getCollisionRectangle();
    /**
     * @param collisionPoint the collision point.
     * @param currentVelocity the velocity when we hit.
     * @param hitter the Sprites.Ball that hits the block.
     * @return the new velocity expected after the hit.
     */
    Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity);

    /**
     * If an object collide with the block changing the hit status to true or changing.
     * the it back to false.
     * @param bool true if there was a hit false if not.
     */
    void changeHitStatus(boolean bool);
}
