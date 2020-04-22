package sprite;

import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.KeyboardSensor;
import collision.Collidable;
import collision.Velocity;
import game.GameLevel;
import geometry.Line;
import geometry.Point;
import geometry.Rectangle;

import java.awt.Color;
/**
 * sprite.Paddle class.
 * @author Shai acoca
 */
public class Paddle implements Sprite, Collidable {
    private biuoop.KeyboardSensor keyBoard;
    private Rectangle paddle;
    private boolean hitStatus;
    private GUI gui;
    private int paddleSpeed;

    /**
     * Construct a sprite.Paddle.
     * @param upperLeft the upper left point
     * @param width the width
     * @param height the height
     * @param gui the GUI of the game.
     * @param paddleSpeed the paddle speed.
     */
    public Paddle(Point upperLeft, double width, double height, GUI gui, int paddleSpeed) {
        this.keyBoard = gui.getKeyboardSensor();
        this.paddle = new Rectangle(upperLeft, width, height);
        this.gui = gui;
        this.hitStatus = false;
        this.paddleSpeed = paddleSpeed;
    }

    /**
     * Moving the paddle to the left with the keyboard.
     */
    public void moveLeft() {
        this.applyToPoint(this.paddle.getUpperLeft(), -this.paddleSpeed);
    }

    /**
     * Moving the paddle to the right with the keyboard.
     */
    public void moveRight() {
        this.applyToPoint(this.paddle.getUpperLeft(), this.paddleSpeed);
    }

    @Override
    public void timePassed() {
        if (this.keyBoard.isPressed(KeyboardSensor.LEFT_KEY)) {
        this.moveLeft();
        }
        if (this.keyBoard.isPressed(KeyboardSensor.RIGHT_KEY)) {
            this.moveRight();
        }
    }

    /**
     * @param surface Drawsurface.
     */
    @Override
    public void drawOn(DrawSurface surface) {
        surface.setColor(Color.black);
        this.paddle.drawRectangle(surface);
    }

    /**
     * The location and size of the collision rectangle in this case the paddle.
     * @return the block, with his location and size.
     */
    @Override
    public Rectangle getCollisionRectangle() {
        return this.paddle;
    }

    /**
     * @param collisionPoint the collision point.
     * @param currentVelocity the velocity when we hit.
     * @param hitter the Sprites.Ball that hits the block.
     * @return the new velocity expected after the hit.
     */
    @Override
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        Point pt = new Point(0, 0);
        double speed = pt.distance(currentVelocity.applyToPoint(pt));
        Velocity newVelocity = new Velocity(0, 0);
        int regionOfTheHit = regionOfTheHit(this.getCollisionRectangle().upperLine(), collisionPoint);
        if (regionOfTheHit == 1) {
            newVelocity = Velocity.fromAngleAndSpeed(60, speed);
        } else if (regionOfTheHit == 2) {
            newVelocity = Velocity.fromAngleAndSpeed(30, speed);
        } else if (regionOfTheHit == 4) {
            newVelocity = Velocity.fromAngleAndSpeed(150, speed);
        } else if (regionOfTheHit == 5) {
            newVelocity = Velocity.fromAngleAndSpeed(120, speed);
        } else {
            newVelocity.setVelocity(currentVelocity.getDX(), -currentVelocity.getDY());
            if (collisionBorder(collisionPoint).equals("RightLine")
                    || collisionBorder(collisionPoint).equals("LeftLine")) {
                newVelocity.setVelocity(-currentVelocity.getDX(), currentVelocity.getDY());
            }
        }
        //The ball velocity dy should always be - after hitting the paddle.
        if (newVelocity.getDY() > 0)  {
            newVelocity.setVelocity(newVelocity.getDX(), -newVelocity.getDY());
        }
        return newVelocity;
    }


    /**
     * Dividing the line to 5 regions and checking on with region the hit happened.
     * @param line the line that the ball collide with.
     * @param collisionPt the collision point.
     * @return the number of the region that the collision occur in, -1 if not on the top line of the paddle.
     */
    public int regionOfTheHit(Line line, Point collisionPt) {
        double regionDistance = line.start().distance(line.end()) / 5;
        double xStart = line.start().getX();
        double y = line.start().getY();
        //Creating 4 lines each one is a region in the big geometry.Line.
        Line region1 = new Line(xStart, y, xStart + regionDistance, y);
        Line region2 = new Line(xStart + regionDistance, y, xStart + 2 * regionDistance, y);
        Line region3 = new Line(xStart + 2 * regionDistance, y, xStart + 3 * regionDistance, y);
        Line region4 = new Line(xStart + 3 * regionDistance, y, xStart + 4 * regionDistance, y);
        Line region5 = new Line(xStart + 4 * regionDistance, y, line.end().getX(), y);
        if (region1.isOnTheLine(collisionPt.getX(), collisionPt.getY())) {
            return 1;
        }
        if (region2.isOnTheLine(collisionPt.getX(), collisionPt.getY())) {
            return 2;
        }
        if (region3.isOnTheLine(collisionPt.getX(), collisionPt.getY())) {
            return 3;
        }
        if (region4.isOnTheLine(collisionPt.getX(), collisionPt.getY())) {
            return 4;
        }
        if (region5.isOnTheLine(collisionPt.getX(), collisionPt.getY())) {
            return 5;
        }
        return -1;
    }
    /**
     * @param pt the point that we want to check.
     * @return a string that indicates on witch side of the rectangle the point is.
     */
    public String collisionBorder(Point pt) {
        if (this.getCollisionRectangle().upperLine().isOnTheLine(pt.getX(), pt.getY())) {
            return "UpperLine";
        }
        if (this.getCollisionRectangle().bottomLine().isOnTheLine(pt.getX(), pt.getY())) {
            return "BottomLine";
        }
        if (this.getCollisionRectangle().leftLine().isOnTheLine(pt.getX(), pt.getY())) {
            return "LeftLine";
        }
        if (this.getCollisionRectangle().rightLine().isOnTheLine(pt.getX(), pt.getY())) {
            return "RightLine";
        }
        return "";
    }

    /**
     * Adding the a paddle to the sprite collection and the collidable collection.
     * @param game the game.
     */
    public void addToGame(GameLevel game) {
        game.addSprite(this);
        game.addCollidable(this);
    }

    /**
     * changing the location of a point by the dx.
     * @param point the point that we want to move.
     * @param dx the movement of the point in the x axis.
     */
    public void applyToPoint(Point point, double dx) {
        Point newPt = new Point(point.getX() + dx, point.getY());
        if (isInTheBorders(newPt)) {
            this.paddle.setUpperLeftPt(newPt);
        } else {
            newPt = new Point(point.getX(), point.getY());
            this.paddle.setUpperLeftPt(newPt);
        }

    }

    /**
     * @param pt the upper left point of the paddle.
     * @return true if in the borders of the surface and false if not.
     */
    public boolean isInTheBorders(Point pt) {
        if (pt.getX() <= 21 || pt.getX() + this.getCollisionRectangle().getWidth()
                >= this.gui.getDrawSurface().getWidth() - 18) {
            return false;
        }
        return true;
    }

    /**
     * If an object collide with the block changing the hit status to true or changing.
     * the it back to false.
     * @param bool true if there was a hit false if not.
     */
    public void changeHitStatus(boolean bool) {
        this.hitStatus = bool;
    }

    /**
     * Removing the paddle from the game (the sprite, the collidable lists).
     * @param game the game.
     */
    public void removeFromGame(GameLevel game) {
        game.removeSprite(this);
        game.removeCollidable(this);
    }
}
