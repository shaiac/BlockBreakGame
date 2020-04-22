package collision;

import geometry.Point;

/**
 * collision.Velocity class.
 * @author Shai acoca
 */
public class Velocity {
    private double dx;
    private double dy;
    /**
     * Construct collision.Velocity.
     * @param dx the change in the x
     * @param dy the change in the y
     */
    public Velocity(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }

    /**
     * Construct collision.Velocity.
     * @param velocity the velocity
     */
    public Velocity(Velocity velocity) {
        this.dx = velocity.getDX();
        this.dy = velocity.getDY();
    }

    /**
     * Setting a the velocity.
     * @param dX the change in the x
     * @param dY the change in the y
     */
    public void setVelocity(double dX, double dY) {
        this.dx = dX;
        this.dy = dY;
    }


    /**
     * another construct to collision.Velocity getting the angle and speed.
     * @param angle the angle
     * @param speed the speed
     * @return the velocity set by the angle and speed
     */
    public static Velocity fromAngleAndSpeed(double angle, double speed) {
        double angleRadians = Math.toRadians(angle);
        double dx = Math.cos(angleRadians) * speed;
        double dy = Math.sin(angleRadians) * speed;
        return new Velocity(dx, dy);
    }

    /**
     * @return dx
     */
    public double getDX() {
        return this.dx;
    }
    /**
     * @return dy
     */
    public double getDY() {
        return this.dy;
    }

    /**
     * changing the location of a point by the dx , dy.
     * @param point the point that we want to move
     * @return new point in a new location according to dx, dy
     */
    public Point applyToPoint(Point point) {
            Point newPt = new Point(point.getX() + this.dx, point.getY() + this.dy);
            return newPt;
    }
}
