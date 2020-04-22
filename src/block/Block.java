package block;
import backgrounds.ColorsParser;
import biuoop.DrawSurface;
import collision.Collidable;
import collision.HitListener;
import collision.HitNotifier;
import collision.Velocity;
import counter.ScoreTrackingListener;
import game.GameLevel;
import geometry.Point;
import geometry.Rectangle;
import sprite.Ball;
import sprite.Sprite;
import javax.imageio.ImageIO;
import java.awt.Image;
import java.awt.Color;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * A block.Block class.
 * @author Shai acoca
 */
public class Block implements Collidable, Sprite, HitNotifier {
    private Rectangle block;
    private String text;
    private Color color;
    private boolean hitStatus;
    private List<HitListener> hitListeners;
    private ScoreTrackingListener tracking;
    private int hitPoints;
    private List<String> fill;
    private String stroke;
    private Map<String, Image> images;

    /**
     * Construct a block.
     * @param upperLeft the upper left point
     * @param width the width
     * @param height the height
     * @param color the color
     */
    public Block(Point upperLeft, double width, double height, java.awt.Color color) {
        this.block = new Rectangle(upperLeft, width, height);
        this.color = color;
        this.hitStatus = false;
        this.hitListeners = new ArrayList<>();
        this.stroke = null;
        this.fill = new ArrayList<>();
    }


    /**
     * Another constructor to the class.
     * @param rectangle the block.
     * @param hitPoints number of hits that need to crush the block.
     * @param fill the fills of the block.
     * @param stroke the frame of the block.
     */
    public Block(Rectangle rectangle, int hitPoints, List<String> fill, String stroke) {
        this.block = rectangle;
        this.hitPoints = hitPoints;
        this.fill = fill;
        this.images = new TreeMap<>();
        this.hitStatus = false;
        this.hitListeners = new ArrayList<>();
        this.stroke = stroke;
        this.text = "";
    }

    /**
     * Setting the score tracking in block, to know how many scores to add each hit.
     * @param scoreTracking the current score.
     */
    public void setScoreTracking(ScoreTrackingListener scoreTracking) {
        this.tracking = scoreTracking;
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
     * If an object collide with the block changing the hit status to true or changing.
     * the it back to false.
     * @param bool true if there was a hit false if not.
     */
    public void changeHitStatus(boolean bool) {
        this.hitStatus = bool;
    }

    /**
     * @param str the text that we want to write in the block.
     */
    public void setBlockText(String str) {
        this.text = str;
    }

    /**
     * @return the block.Block text.
     */
    public String getBlockText() {
        return this.text;
    }

    /**
     * @return the hitPoints.
     */
    public int getHitPoints() {
        return this.hitPoints;
    }

    /**
     * Decreasing one from the hitPoints.
     */
    public void decreaseHitPoint() {
        this.hitPoints--;
    }

    /**
     * @return the block width.
     */
    public double getWidth() {
        return this.block.getWidth();
    }

    /**
     * Construct a block.
     * @param block a rectangle that will be the block.
     */
    public Block(Rectangle block) {
        this.block = new Rectangle(block.getUpperLeft(), block.getWidth(), block.getHeight());
    }

    /**
     * Setting the images map, for each fill that is image the key is a string (the string from the file)
     * and the image is the image.
     */
    public void setImages() {
        for (String oneFill : fill) {
            if (oneFill.startsWith("image")) {
                Image image;
                try {
                    InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(getImageLocation(oneFill));
                    image = ImageIO.read(is);
                    this.images.put(oneFill, image);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * The location and size of the collision rectangle.
     * @return the block, with his location and size.
     */
    @Override
    public Rectangle getCollisionRectangle() {
        return this.block;
    }

    /**
     * @param collisionPoint the collision point.
     * @param currentVelocity the velocity when we hit.
     * @param hitter the Sprites.Ball that hits the block.
     * @return the new velocity expected after the hit.
     */
    @Override
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        Velocity newVelocity = new Velocity(0, 0);
        newVelocity.setVelocity(currentVelocity.getDX(), -currentVelocity.getDY());
        if (collisionBorder(collisionPoint).equals("RightLine") || collisionBorder(collisionPoint).equals("LeftLine")) {
            newVelocity.setVelocity(-currentVelocity.getDX(), currentVelocity.getDY());
        }
        this.notifyHit(hitter);
        return newVelocity;
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
     * @param surface Drawsurface.
     */
    @Override
    public void drawOn(DrawSurface surface) {
        if (this.fill.size() == 0) {
            surface.setColor(this.color);
            this.block.drawRectangle(surface);
        } else {
            String blockFill;
            //Drawing the frame of the block.
            if (this.stroke != null) {
                surface.setColor(new ColorsParser().colorFromString(stroke));
                surface.drawRectangle((int) this.block.getUpperLeft().getX(), (int) this.block.getUpperLeft().getY(),
                        (int) this.block.getWidth(), (int) this.block.getHeight());
            }
            blockFill = chooseFill();
            if (blockFill.startsWith("color")) {
                surface.setColor(new ColorsParser().colorFromString(blockFill));
                surface.fillRectangle((int) this.block.getUpperLeft().getX(), (int) this.block.getUpperLeft().getY(),
                        (int) this.block.getWidth(), (int) this.block.getHeight());
            } else {
                surface.drawImage((int) this.block.getUpperLeft().getX(),
                        (int) this.block.getUpperLeft().getY(), this.images.get(blockFill));
            }
        }
    }

    /**
     * @return The image location extracted from the string.
     * @param blockFill A String that has the image location.
     */
    private String getImageLocation(String blockFill) {
        return blockFill.substring(6, blockFill.length() - 1);
    }

    /**
     * @return A string the indicate how we need to fill that block (from the fill list).
     */
    private String chooseFill() {
        int difference = this.fill.size() - getHitPoints();
        if (difference <= 0) {
            return fill.get(0);
        }
        //if (difference > 0)
        return fill.get(difference);
    }

    @Override
    public void timePassed() {
        if (this.hitStatus) {
            if (!this.getBlockText().equals("X") && !this.getBlockText().equals("")
                    && !this.getBlockText().equals("DeathBlock")) {
                int num = Integer.parseInt(this.getBlockText());
                num -= 1;
                this.setBlockText(String.valueOf(num));
            }
        }
        this.hitStatus = false;
    }

    /**
     * Adding the a block to the sprite collection and the collidable collection.
     * @param game the game.
     */
    public void addToGame(GameLevel game) {
        game.addSprite(this);
        game.addCollidable(this);
    }

    @Override
    public void removeHitListener(HitListener hl) {
        this.hitListeners.remove(hl);
    }

    /**
     * @param hitter the ball that hits the block.
     */
    private void notifyHit(Ball hitter) {
        // Make a copy of the hitListeners before iterating over them.
        List<HitListener> listeners = new ArrayList<>(this.hitListeners);
        // Notify all listeners about a hit event:
        for (HitListener hl : listeners) {
            hl.hitEvent(this, hitter);
        }
        if (this.getBlockText().equals("DeathBlock")) {
            List<HitListener> ballListeners = new ArrayList<>(hitter.getHitListeners());
            for (HitListener h2 : ballListeners) {
                h2.hitEvent(this, hitter);
            }
        }
        if (this.tracking != null) {
            this.tracking.hitEvent(this, hitter);
        }
    }
}
