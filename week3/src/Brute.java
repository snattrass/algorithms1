import java.util.Arrays;

public class Brute
{
    public Brute()
    {
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.show(0);
    }

    public static void main(String[] args)
    {
        Brute brute = new Brute();

        String fileName = args[0];
        Point[] points = brute.readFile(new In(fileName));
        brute.processPoints(points);
    }

    private void processPoints(Point[] points)
    {
        Arrays.sort(points);

        int N = points.length;
        for (int p = 0; p < N; p++) {
            points[p].draw();   // draw point
            for (int q = p + 1; q < N; q++) {
                for (int r = q + 1; r < N; r++) {
                    for (int s = r + 1; s < N; s++) {
                        FourTuple tuple = new FourTuple(points[p], points[q],
                                                        points[r], points[s]);

                        if (tuple.isCollinear()) {
                            StdOut.println(tuple);
                            tuple.drawSegment();
                        }
                    }
                }
            }

            StdDraw.show(0);
        }

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
