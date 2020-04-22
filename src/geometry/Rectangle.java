package geometry;
import biuoop.DrawSurface;
import java.util.LinkedList;

/**
 * A geometry.Rectangle class.
 * @author Shai acoca
 */
public class Rectangle {
    private Point upperLeft;
    private double width;
    private double height;

    /**
     * Construct a rectangle given the upper left point, width, height.
     * @param upperLeft the upper left point
     * @param width the width
     * @param height the height
     */
    public Rectangle(Point upperLeft, double width, double height) {
        this.upperLeft = upperLeft;
        this.width = width;
        this.height = height;
    }
    /**
     * @return upperRight the upper right point of the rectangle.
     */
    public Point getUpperRight() {
        Point upperRight = new Point(this.upperLeft.getX() + this.width, this.upperLeft.getY());
        return upperRight;
    }

    /**
     * @return bottomLeft the left bottom point of the rectangle.
     */
    public Point getBottomLeft() {
        Point bottomLeft = new Point(this.upperLeft.getX(), this.upperLeft.getY() + this.height);
        return bottomLeft;
    }

    /**
     * @return bottomRight the right bottom point of the rectangle.
     */
    public Point getBottomRight() {
        Point bottomRight = new Point(this.upperLeft.getX() + this.width, this.upperLeft.getY() + this.height);
        return bottomRight;
    }

    /**
     * @return the upper side of the rectangle.
     */
    public Line upperLine() {
        Line upperLine = new Line(this.getUpperLeft(), this.getUpperRight());
        return upperLine;
    }

    /**
     * @return the bottom side of the rectangle.
     */
    public Line bottomLine() {
        Line bottomLine = new Line(this.getBottomLeft(), this.getBottomRight());
        return bottomLine;
    }

    /**
     * @return the left side of the rectangle.
     */
    public Line leftLine() {
        Line leftLine = new Line(this.upperLeft, this.getBottomLeft());
        return leftLine;
    }

    /**
     * @return the right side of the rectangle.
     */
    public Line rightLine() {
        Line rightLine = new Line(this.getUpperRight(), this.getBottomRight());
        return rightLine;
    }

    /**
     * Setting a new upper left point to the rectangle.
     * @param pt the new point.
     */
    public void setUpperLeftPt(Point pt) {
        this.upperLeft = pt;
    }

    /**
     * The method creating a list of intersection points of a given line.
     * with the rectangle.
     * @param line the line to check the intersection with.
     * @return list of intersection points.
     */
    public java.util.List<Point> intersectionPoints(Line line) {
        int index = 0;
        java.util.List<Point> interPoints = new LinkedList<>();
        //Creating all the rectangle sides(lines).
        Line upperLine = this.upperLine();
        Line bottomLine = this.bottomLine();
        Line leftLine = this.leftLine();
        Line rightLine = this.rightLine();
        //Checking for each side if intersecting with the line, adding the inter point if it is.
        if (upperLine.isIntersecting(line)) {
            interPoints.add(index, upperLine.intersectionWith(line));
            index++;
        }
        if (bottomLine.isIntersecting(line)) {
            interPoints.add(index, bottomLine.intersectionWith(line));
            index++;
        }
        if (leftLine.isIntersecting(line)) {
            interPoints.add(index, leftLine.intersectionWith(line));
            index++;
        }
        if (rightLine.isIntersecting(line)) {
            interPoints.add(index, rightLine.intersectionWith(line));
            index++;
        }
        return interPoints;
    }

    /**
     * @return width of the rectangle.
     */
    public double getWidth() {
        return this.width;
    }
    /**
     * @return the height of the rectangle.
     */
    public double getHeight() {
        return this.height;
    }

    /**
     * @return the upper left point of the rectangle.
     */
    public Point getUpperLeft() {
        return this.upperLeft;
    }

    /**
     * @param surface Drawing the rectangle.
     */
    public void drawRectangle(DrawSurface surface) {
        surface.fillRectangle((int) this.getUpperLeft().getX(), (int) this.getUpperLeft().getY(),
                (int) this.getWidth(), (int) this.getHeight());
    }
}
