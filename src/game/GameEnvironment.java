package game;

import collision.Collidable;
import collision.CollisionInfo;
import geometry.Line;
import geometry.Point;
import geometry.Rectangle;
import java.util.ArrayList;
import java.util.List;


/**
 * The game environment class.
 * @author Shai acoca
 */
public class GameEnvironment {

    private List<Collidable> collidableList;

    /**
     * Constructor to the game environment.
     * @param c one collidable
     */
    public GameEnvironment(Collidable c) {
        this.collidableList = new ArrayList<>();
        collidableList.add(c);
    }
    /**
     * Constructor to the game environment making a new collidable list.
     */
    public GameEnvironment() {
        this.collidableList = new ArrayList<>();
    }

    /**
     * Constructor to the sprite collection a sprite List.
     * @param collidableList list of collidables.
     */
    public GameEnvironment(List<Collidable> collidableList) {
        this.collidableList = collidableList;
    }

    /**
     * Adding one collidable to the list.
     * @param c one collidable.
     */
    public void addCollidable(Collidable c) {
        collidableList.add(c);
    }

    /**
     * @return the collidable List
     */
    public List<Collidable> getGameEnv() {
        return this.collidableList;
    }

    /**
     * If this line will not collide with any of the collidables
     * in this collection, return null. Else, return the information
     * about the closest collision that is going to occur.
     * @param trajectory the line that we check if collide with.
     * @return the closest collision that is going to occur.
     */
    public CollisionInfo getClosestCollision(Line trajectory) {
        CollisionInfo closestPointInfo = null;
        int index = -1;
        Point closestInterPt = null;
        Point tempPoint;
        for (int i = 0; i < this.collidableList.size(); i++) {
            Rectangle rect = this.collidableList.get(i).getCollisionRectangle();
            tempPoint = trajectory.closestIntersectionToStartOfLine(rect);
            if (tempPoint == null) {
                continue;
            }
            if (closestInterPt == null) {
                index = i;
                closestInterPt = tempPoint;
            }
            if (trajectory.start().distance(tempPoint) < trajectory.start().distance(closestInterPt)) {
                index = i;
                closestInterPt = tempPoint;
            }
        }
        if (index == -1) {
            return null;
        }
        closestPointInfo = new CollisionInfo(closestInterPt, this.collidableList.get(index));
        return closestPointInfo;
    }
}
