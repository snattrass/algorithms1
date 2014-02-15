/*************************************************************************
 * Name:
 * Email:
 *
 * Compilation:  javac Point.java
 * Execution:
 * Dependencies: StdDraw.java
 *
 * Description: An immutable data type for points in the plane.
 *
 *************************************************************************/

import java.util.Comparator;

public class Point implements Comparable<Point> {

    // compare points by slope
    public final Comparator<Point> SLOPE_ORDER = new BySlope();

    private final int x;                              // x coordinate
    private final int y;                              // y coordinate

    // create the point (x, y)
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    // plot this point to standard drawing
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    // draw line between this point and that point to standard drawing
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    // slope between this point and that point
    public double slopeTo(Point that)
    {
        if ((this.y == that.y) && (this.x - that.x != 0)) { // vertical
            return 0;
        }
        if ((this.x == that.x) && (this.y - that.y != 0)) { // horizontal
            return Double.POSITIVE_INFINITY;
        }

        if ((this.x == that.x) && (this.y == that.y)) {     // degenerate
            return Double.NEGATIVE_INFINITY;
        }

        return (double) (that.y - this.y) / (double) (that.x - this.x);
    }

    // is this point lexicographically smaller than that point?
    // comparing y-coordinates and breaking ties by x-coordinates
    public int compareTo(Point that)
    {
        if (this.y < that.y) { return -1; }
        if (this.y > that.y) { return +1; }
        if (this.x < that.x) { return -1; }
        if (this.x > that.x) { return +1; }
        return 0;
    }

    // return string representation of this point
    public String toString()
    {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    // unit test
    public static void main(String[] args)
    {
    }

    private class BySlope implements Comparator<Point>
    {
        // The SLOPE_ORDER comparator should compare points by the slopes they
        // make with the invoking point (x0, y0). Formally, the point (x1, y1)
        // is less than the point (x2, y2) if and only if the slope
        // (y1 − y0) / (x1 − x0) is less than the slope (y2 − y0) / (x2 − x0).
        // Treat horizontal, vertical, and degenerate line segments as in the slopeTo() method.

        public int compare(Point q, Point r)
        {
            if (Point.this.slopeTo(q) < Point.this.slopeTo(r)) { return -1; }
            if (Point.this.slopeTo(q) > Point.this.slopeTo(r)) { return +1; }
            return 0;
        }
    }
}
