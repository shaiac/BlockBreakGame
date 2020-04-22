package collision;
import geometry.Point;

/**
 * The collision info class, saving the collision info of two objects.
 * @author Shai acoca
 */
public class CollisionInfo {
    private Point collisionPoint;
    private Collidable collidObject;

    /**
     * Constructor for the collisionInfo class.
     * @param collisionPoint The point at which the collision occurs.
     * @param collidObject The object that we collide with.
     */
    public CollisionInfo(Point collisionPoint, Collidable collidObject) {
        this.collisionPoint = collisionPoint;
        this.collidObject = collidObject;
    }

    /**
     @return the point at which the collision occurs.
     */
    public Point getcollisionPoint() {
        return this.collisionPoint;
    }

    /**
     @return the collidable object involved in the collision.
     */
    public Collidable collisionObject() {
        return this.collidObject;
    }
}
