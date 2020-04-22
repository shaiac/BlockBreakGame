package sprite;

import java.util.ArrayList;
import java.util.List;

import biuoop.DrawSurface;

/**
 * A sprite.Sprite collection.
 * @author Shai acoca
 */
public class SpriteCollection {
    private List<Sprite> spriteList;

    /**
     * Constructor to the sprite collection given one sprite.
     * @param sprite one sprite.
     */
    public SpriteCollection(Sprite sprite) {
        this.spriteList = new ArrayList<>();
        spriteList.add(sprite);
    }

    /**
     * Constructor to the game environment making a new empty sprite list.
     */
    public SpriteCollection() {
        this.spriteList = new ArrayList<>();
    }

    /**
     * Constructor to the sprite collection a sprite List.
     * @param spriteList list of sprites.
     */
    public SpriteCollection(List<Sprite> spriteList) {
        this.spriteList = spriteList;
    }

    /**
     * Adding one sprite to the list.
     * @param s one sprite.
     */
    public void addSprite(Sprite s) {
        this.spriteList.add(s);
    }

    /**
     * call timePassed() on all sprites.
     */
    public void notifyAllTimePassed() {
        List<Sprite> sprites = new ArrayList<>(this.spriteList);
        for (Sprite s : sprites) {
            s.timePassed();
        }
    }

    /**
     * call drawOn(d) on all sprites.
     * @param d the draw surface.
     */
    public void drawAllOn(DrawSurface d) {
        for (Sprite s : this.spriteList) {
            s.drawOn(d);
        }
    }

    /**
     * @return the sprite list
     */
    public List<Sprite> getSpriteCollection() {
        return this.spriteList;
    }
}
