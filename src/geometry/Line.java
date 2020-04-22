package geometry;

/**
 * A geometry.Line class.
 * @author Shai acoca
 */
public class Line {
    private Point start;
    private Point end;

    /**
     * Construct a line given the starting point and the ending point.
     * @param start the starting point of the line
     * @param end   the ending point of the line
     */
    public Line(Point start, Point end) {
        this.start = new Point(start.getX(), start.getY());
        this.end = new Point(end.getX(), end.getY());
    }

    /**
     * Construct a line given the coordinates of the starting point.
     * and the coordinates of the ending point.
     * @param x1 the x coordinates of the starting point
     * @param y1 the y coordinates of the starting point
     * @param x2 the x coordinates of the ending point
     * @param y2 the y coordinates of the ending point
     */
    public Line(double x1, double y1, double x2, double y2) {
        this.start = new Point(x1, y1);
        this.end = new Point(x2, y2);
    }

    /**
     * length method returns the length of this line.
     * @return the length of the line
     */
    public double length() {
        return this.start.distance(this.end);
    }

    /**
     * middle method returns the middle point of the line.
     * @return middlePt the middle point of the line.
     */
    public Point middle() {
        Point middlePt;
        double middleX = (this.start.getX() + this.end.getX()) / 2;
        double middleY = (this.start.getY() + this.end.getY()) / 2;
        middlePt = new Point(middleX, middleY);
        return middlePt;
    }

    /**
     * start method returns the starting point of the line.
     * @return the starting point.
     */
    public Point start() {
        return this.start;
    }

    /**
     * end method returns the ending point of the line.
     * @return the ending point.
     */
    public Point end() {
        return this.end;
    }

    /**
     * isIntersecting method returns true if the lines intersect, false otherwise.
     * @param otherLine the line that we want to check intersection with
     * @return true or false
     */
    public boolean isIntersecting(Line otherLine) {
        if (intersectionWith(otherLine) == null) {
            return false;
        }
        return true;
    }

    /**
     * intersectionWith method calculating the two align equations and finding the point of intersection.
     * sending to another method that checks if this point is on the lines.
     * @param otherLine the line that we want to find the point of intersection with.
     * @return the point of intersection of the to line if exist and null if not.
     */
    public Point intersectionWith(Line otherLine) {
        double x, y;
        Point pt = null;
        Point intersectionPt = null;
        if (this.start().getX() == this.end().getX()) {
            pt = ifParallelToY(this, otherLine);
            x = pt.getX();
            y = pt.getY();
        } else if (otherLine.start().getX() == otherLine.end().getX()) {
            pt = ifParallelToY(otherLine, this);
            x = pt.getX();
            y = pt.getY();
        } else {
            //The factors of the two line
            double factor = (this.end().getY() - this.start().getY()) / (this.end().getX() - this.start().getX());
            double factorOther = (otherLine.end().getY() - otherLine.start().getY())
                    / (otherLine.end().getX() - otherLine.start().getX());
            //If the factors are equal it means the lines will never intersect
            if (factor == factorOther) {
                return null;
            } else {
                //Finding the (x,y) of the point of intersection.
                x = (factor * this.start().getX() - factorOther * otherLine.start().getX() + otherLine.start().getY()
                        - this.start().getY()) / (factor - factorOther);
                y = factor * (x - this.start().getX()) + this.start().getY();
            }
        }
            //if the point of intersection is on the line create a new point - point of intersection.
            if (this.isOnTheLine(x, y) && (otherLine.isOnTheLine(x, y))) {
                intersectionPt = new Point(x, y);
            }
        return intersectionPt;
    }

    /**
     * Method that find that intersection of two lines if one of the lines.
     * is parallel to the y axis.
     * @param parallelLine the line that parallel to the Y axis
     * @param line the other line
     * @return the intersection geometry.Point
     */
    public Point ifParallelToY(Line parallelLine, Line line) {
        Point intersectionPt = null;
        double factor = (line.end().getY() - line.start().getY())
                / (line.end().getX() - line.start().getX());
        double x = parallelLine.start().getX();
        double y = factor * (parallelLine.start().getX() - line.start().getX()) + line.start().getY();
        intersectionPt = new Point(x, y);
        return intersectionPt;
    }

    /**
     * isOnTheLine method check if a geometry.Point is on the line.
     * @param x the x coordinates of a point
     * @param y the y coordinates of a point
     * @return true if the coordinates are on the line, false otherwise
     */
    public boolean isOnTheLine(double x, double y) {
        if ((this.start().getX() <= x && this.end().getX() >= x)
                || (this.start().getX() >= x && this.end().getX() <= x)) {
            if ((this.start().getY() <= y && this.end().getY() >= y)
                    || (this.start().getY() >= y && this.end().getY() <= y)) {
                    return true;
            }
        }
        return false;
    }

    /**
     * hashCode for the equals method.
     * @return 1
     */
    public int hashCode() {
        return 1;
    }

    /**
     * equals checking if the two line have the same starting and ending points.
     * @param otherLine line
     * @return true is the lines are equal, false otherwise
     */
    public boolean equals(Line otherLine) {
        if (this.start() == otherLine.start() && this.end() == otherLine.end()
                || this.start() == otherLine.end() && this.end() == otherLine.start()) {
            return true;
        }
        return false;
    }

    /**
     * The method making a list of all the intersection points of the line with a rectangle.
     * checking with point is the closest to the start point of the line and return it.
     * @param rect the rectangle
     * @return null if there are no intersection points, the closest point if there are.
     */
    public Point closestIntersectionToStartOfLine(Rectangle rect) {
        java.util.List<Point> interPoints = rect.intersectionPoints(this);
        if (interPoints.isEmpty()) {
            return null;
        }
        Point closestPt =  interPoints.get(0);
        for (int i = 1; i < interPoints.size(); i++) {
            if (this.start.distance(interPoints.get(i)) < this.start.distance(closestPt)) {
                closestPt = interPoints.get(i);
            }
        }
        return closestPt;
    }
}

