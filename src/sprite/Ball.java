package sprite;
import biuoop.DrawSurface;
import biuoop.GUI;
import collision.CollisionInfo;
import collision.HitListener;
import collision.HitNotifier;
import collision.Velocity;
import game.GameLevel;
import game.GameEnvironment;
import geometry.Line;
import geometry.Point;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * Sprites.Ball class.
 * @author Shai acoca
 */
public class Ball implements Sprite, HitNotifier {
    private Point center;
    private int radius;
    private java.awt.Color color;
    private Velocity velocity;
    private GameEnvironment gameEn;
    private GUI gui;
    private List<HitListener> hitListeners;

    /**
     * Construct a ball.
     * @param centerX the x coordinates of the center
     * @param centerY the y coordinates of the center
     * @param radius the radius
     * @param color the color
     * @param gameEn the game environment.
     */
    public Ball(double centerX, double centerY, int radius, java.awt.Color color, GameEnvironment gameEn) {
        this.center = new Point(centerX, centerY);
        this.radius = radius;
        this.color = color;
        this.velocity = new Velocity(0, 0);
        this.gameEn = gameEn;
        this.hitListeners = new ArrayList<>();
    }

    @Override
    public void addHitListener(HitListener hl) {
        this.hitListeners.add(hl);
    }

    /**
     * @return the hitListeners list.
     */
    public List<HitListener> getHitListeners() {
        return this.hitListeners;
    }

    /**
     * Setting a the velocity of the ball.
     * @param dx the change in the x
     * @param dy the change in the y
     */
    public void setVelocity(double dx, double dy) {
        this.velocity = new Velocity(dx, dy);
    }

    /**
     * setting the velocity of a ball.
     * @param v a velocity.
     */
    public void setVelocity(Velocity v) {
        this.velocity = v;
    }

    /**
     * Setting the ball that he will know the gui surface.
     * @param g gui.
     */
    public void setGui(GUI g) {
        this.gui = g;
    }
    /**
     * @return the velocity of the ball.
     */
    public Velocity getVelocity() {
        return this.velocity;
    }

    /**
     * The method is changing the location of the ball according to the velocity.
     * each time checking if the ball is in the borders of the game surface,
     * if he reached the border changing the direction to the other side.
     * @param topBorder the top boarder of the game surface.
     * @param bottomBorder the bottom boarder of the game surface.
     * @param rightBorder the right boarder of the game surface.
     * @param leftBorder the top boarder of the game surface.
     */
    public void moveOneStepBorders(int topBorder, int bottomBorder, int rightBorder, int leftBorder) {
        if (this.getVelocity().getDX() > 0) {
            if (this.center.getX() + this.getSize() + this.getVelocity().getDX() >= rightBorder) {
                this.setVelocity(-this.getVelocity().getDX(), this.getVelocity().getDY());
            }
        } else {
            if (this.center.getX() - this.getSize() - this.getVelocity().getDX() <= leftBorder) {
                this.setVelocity(-this.getVelocity().getDX(), this.getVelocity().getDY());
            }
        }
        if (this.getVelocity().getDY() > 0) {
            if (this.center.getY() + this.getSize() + this.getVelocity().getDY() >= topBorder) {
                this.setVelocity(this.getVelocity().getDX(), -this.getVelocity().getDY());
            }
        } else {
            if (this.center.getY() - this.getSize() - this.getVelocity().getDY() <= bottomBorder) {
                this.setVelocity(this.getVelocity().getDX(), -this.getVelocity().getDY());
            }
        }
        this.center = this.getVelocity().applyToPoint(this.center);
    }

    /**
     * The method creating the ball trajectory and checking if there is a collision.
     * between the ball and one of the collidable objects. if there isn't, move the ball in
     * the regular way, if there is changing the velocity of the ball according to the hit
     * with the object.
     */
    public void moveOneStep() {
        int bottomBorder = 0, leftBorder = 0;
        int topBorder = this.gui.getDrawSurface().getHeight();
        int rightBorder = this.gui.getDrawSurface().getWidth();
        Point centerWithRadius = addRadiusToCenter(this.center);
        Line trajectory = new Line(this.center, this.getVelocity().applyToPoint(centerWithRadius));
        CollisionInfo closestCollisionPt = this.gameEn.getClosestCollision(trajectory);
        if (closestCollisionPt == null) {
            //No collision move the ball in a regular course (checking the borders of the surface).
            moveOneStepBorders(topBorder, bottomBorder, rightBorder, leftBorder);
            return;
        } else {
            //If the gap between the ball and the collision point is bigger then that we want to move
            //him closer to the collision point
            if (closestCollisionPt.getcollisionPoint().getX() - centerWithRadius.getX() > 0.001
                    || closestCollisionPt.getcollisionPoint().getY() - centerWithRadius.getY() > 0.001) {
                applyStepBeforeHit(centerWithRadius, closestCollisionPt.getcollisionPoint());
            }
            closestCollisionPt.collisionObject().changeHitStatus(true);
            Velocity v = new Velocity(closestCollisionPt.collisionObject().hit(this, closestCollisionPt.
                            getcollisionPoint(), this.getVelocity()));
            this.setVelocity(v.getDX(), v.getDY());
            this.center = this.getVelocity().applyToPoint(this.center);
        }
    }

    /**
     * This method changing the ball location before he hits an object, we are using.
     * different method to avoid a situation that the ball is going inside one of the object,
     * we want him only to get really close to the object.
     * @param centerWithR A point on the ball frame.
     * @param collisionPt the collision point.
     */
    public void applyStepBeforeHit(Point centerWithR, Point collisionPt) {
        double dx = collisionPt.getX() - centerWithR.getX();
        double dy = collisionPt.getY() - centerWithR.getY();
        Velocity newV = new Velocity(dx, dy);
        this.center = newV.applyToPoint(this.center);
        biuoop.Sleeper sleeper = new biuoop.Sleeper();
        this.drawOn(this.gui.getDrawSurface());
    }

    /**
     * Getting a geometry.Point on the circle frame (adding the radius to the center x, y).
     * if the velocity is minus so - the radius, otherwise + the radius.
     * @param centerPt the center of the ball.
     * @return the geometry.Point on the circle frame.
     */
    public Point addRadiusToCenter(Point centerPt) {
        double r = this.getSize();
        double x = 0, y = 0;
        if (this.getVelocity().getDX() > 0) {
            x = centerPt.getX() + r;
        }
        if (this.getVelocity().getDX() <= 0) {
            x = centerPt.getX() - r;
        }
        if (this.getVelocity().getDY() > 0) {
            y = centerPt.getY() + r;
        }
        if (this.getVelocity().getDY() <= 0) {
            y = centerPt.getY() - r;
        }
        Point newCenter = new Point(x, y);
        return newCenter;
    }

    /**
     * @return the x coordinate of the center
     */
    public int getX() {
        return (int) this.center.getX();
    }

    /**
     * @return the y coordinate of the point
     */
    public int getY() {
        return (int) this.center.getY();
    }

    /**
     * @return the size of the circle (radius)
     */
    public int getSize() {
        return this.radius;
    }

    /**
     * @return the color of the circle.
     */
    public java.awt.Color getColor() {
        return this.color;
    }

    /**
     * @return The collision.HitListener list.
     */
    public List<HitListener> getListener() {
        return this.hitListeners;
    }


    /**
     * @param surface Drawsurface
     */
    @Override
    public void drawOn(DrawSurface surface) {
        surface.setColor(this.color);
        surface.drawCircle(this.getX(), this.getY(), this.getSize());
        surface.setColor(Color.white);
        surface.fillCircle(this.getX(), this.getY(), this.getSize());
    }

    /**
     * Implement the interface method timePassed with moveOneStep.
     */
    @Override
    public void timePassed() {
        this.moveOneStep();
    }

    /**
     * Adding to the game the a ball (to the sprite collection).
     * @param game the game.
     */
    public void addToGame(GameLevel game) {
        game.addSprite(this);
    }

    /**
     * Removing the ball from the game (the sprite list).
     * @param g The game.
     */
    public void removeFromGame(GameLevel g) {
        g.removeSprite(this);
    }

    @Override
    public void removeHitListener(HitListener hl) {
        this.hitListeners.remove(hl);
    }
}
