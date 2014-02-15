import java.util.Arrays;

public class Fast
{
    public Fast()
    {
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.show(0);
    }

    public static void main(String[] args)
    {
        Fast fast = new Fast();

        String fileName = args[0];
        Point[] points = fast.readFile(new In(fileName));
        fast.processPoints(points);
    }

    private void processPoints(Point[] points)
    {
        int N = points.length;

        Point[] pointsSortedInSlopOrder = points.clone();

        for (int i = 0; i < N; i++) {
            Point p = points[i];     // origin

            Arrays.sort(pointsSortedInSlopOrder, p.SLOPE_ORDER);

            System.out.println("Point sorted in slope order with respect to origin [" + i + "] " + p);

            for (int j = 0; j < N - 2; j++)
            {
                Point q = pointsSortedInSlopOrder[j];
                if (p.compareTo(q) == 0) {
                    continue;
                }

                Point r = pointsSortedInSlopOrder[j + 1];
                Point s = pointsSortedInSlopOrder[j + 2];

                FourTuple tuple = new FourTuple(p, q, r, s);
                if (tuple.isCollinear()) {
                    StdOut.println(tuple);
                    tuple.drawSegment();
                }
            }
        }
        StdDraw.show(0);

    }

    private Point[] readFile(In inputFile)
    {
        int numberOfPoints = inputFile.readInt();

        int i = 0;
        Point[] points = new Point[numberOfPoints];
        while (!inputFile.isEmpty())
        {
            int x = inputFile.readInt();
            int y = inputFile.readInt();

            points[i++] = new Point(x, y);
        }

        return points;
    }

    private class FourTuple
    {
        private Point p;
        private Point q;
        private Point r;
        private Point s;

        public FourTuple(Point p, Point q, Point r, Point s)
        {
            this.p = p;
            this.q = q;
            this.r = r;
            this.s = s;
        }

        /**
         * Tuples are collinear if the slopes between p and q, between p and r,
         * and between p and s are all equal
         * @return
         */
        public boolean isCollinear()
        {
            return (p.slopeTo(q) == p.slopeTo(r) && p.slopeTo(r) == p.slopeTo(s));
        }

        public void drawSegment()
        {
            p.drawTo(s);
        }

        public String toString()
        {
            return p + " -> " + q + " -> " + r + " -> " + s;
        }
    }
}
