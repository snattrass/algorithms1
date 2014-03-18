public class KdTree
{
    private Node2D root = null;
    private int size = 0;

    public KdTree()                               // construct an empty set of points
    {
    }

    public boolean isEmpty()                        // is the set empty?
    {
        return size == 0;
    }

    public int size()                               // number of points in the set
    {
        return size;
    }

    public void insert(Point2D p)                   // add the point p to the set (if it is not already in the set)
    {
        if (isEmpty()) {
            RectHV rect = new RectHV(0, 0, 1, 1);
            root = new Node2D(p, rect);
            size++;
            return;
        }

        Node2D node = root;
        Node2D parent = root;
        int level = 0;
        while (null != node) {
            if (node.point.equals(p)) {     // it already exists
                return;
            }

            parent = node;
            if ((level++ % 2) == 0) {
                node = (p.x() < node.point.x()) ? node.left : node.right;
            }
            else {
                node = (p.y() < node.point.y()) ? node.left : node.right;
            }
        }

        // so we're now at the point where we can insert
        if (((level - 1) % 2) == 0) {
            if (p.x() < parent.point.x()) {
                RectHV rect = new RectHV(parent.rect.xmin(), parent.rect.ymin(), parent.point.x(), parent.rect.ymax());
                parent.left = new Node2D(p, rect);
            }
            else {
                RectHV rect = new RectHV(parent.point.x(), parent.rect.ymin(), parent.rect.xmax(), parent.rect.ymax());
                parent.right = new Node2D(p, rect);
            }
        }
        else {
            if (p.y() < parent.point.y()) {
                RectHV rect = new RectHV(parent.rect.xmin(), parent.rect.ymin(), parent.rect.xmax(), parent.point.y());
                parent.left = new Node2D(p, rect);
            }
            else {
                RectHV rect = new RectHV(parent.rect.xmin(), parent.point.y(), parent.rect.xmax(), parent.rect.ymax());
                parent.right = new Node2D(p, rect);
            }
        }

        size++;
    }

    public boolean contains(Point2D p)              // does the set contain the point p?
    {
        Node2D node = root;
        int level = 0;
        while (null != node) {
            if (node.point.equals(p)) {     // it already exists
                return true;
            }

            if ((level++ % 2) == 0) {
                node = (p.x() < node.point.x()) ? node.left : node.right;
            }
            else {
                node = (p.y() < node.point.y()) ? node.left : node.right;
            }
        }

        return false;
    }

    public void draw()                              // draw all of the points to standard draw
    {
        draw(root, 0);
    }

    private void draw(Node2D node, int level)
    {
       if (null == node) {
           return;
       }

        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(.005);
        node.point.draw();

        StdDraw.setPenRadius(.003);

        if ((level++) % 2 == 0) {
            StdDraw.setPenColor(StdDraw.RED);
            node.point.drawTo(new Point2D(node.point.x(), node.rect.ymax()));
            node.point.drawTo(new Point2D(node.point.x(), node.rect.ymin()));

            draw(node.left, level);
            draw(node.right, level);
        }
        else {
            StdDraw.setPenColor(StdDraw.BLUE);
            node.point.drawTo(new Point2D(node.rect.xmax(), node.point.y()));
            node.point.drawTo(new Point2D(node.rect.xmin(), node.point.y()));

            draw(node.left, level);
            draw(node.right, level);
        }
    }

    public Iterable<Point2D> range(RectHV rect)     // all points in the set that are inside the rectangle
    {
        Stack<Point2D> points = new Stack<Point2D>();
        range(root, rect, points);
        return points;
    }

    private void range(Node2D node, RectHV rect, Stack<Point2D> points)
    {
        if (null == node) {
           return;
        }

        if (!node.rect.intersects(rect)) {
            return;
        }

        if (rect.contains(node.point)) {
            points.push(node.point);
        }

        range(node.left, rect, points);
        range(node.right, rect, points);
    }

    public Point2D nearest(Point2D p)               // a nearest neighbor in the set to p; null if set is empty
    {
        Stack<Point2D> stack = new Stack<Point2D>();
        nearest(root, p, stack, 2.0, 0);
        return stack.pop();
    }

    private void nearest(Node2D node, Point2D point, Stack<Point2D> points, double distance, int level)
    {
        if (null == node) {
            return;
        }

        if (distance < node.rect.distanceSquaredTo(point))
            return;

        double d = point.distanceSquaredTo(node.point);
        if (d < distance) {
            points.push(node.point);
        }
        distance = d;

        if ((level++ % 2) == 0) {
            if (point.x() < node.point.x()) {
                nearest(node.left, point, points, distance, level);
                nearest(node.right, point, points, distance, level);
            }
            else {
                nearest(node.right, point, points, distance, level);
                nearest(node.left, point, points, distance, level);
            }
        } else {
            if (point.y() < node.point.y()) {
                nearest(node.left, point, points, distance, level);
                nearest(node.right, point, points, distance, level);
            }
            else {
                nearest(node.right, point, points, distance, level);
                nearest(node.left, point, points, distance, level);
            }
        }
    }

    private static class Node2D
    {
        public Node2D(Point2D point, RectHV rect)
        {
            this.point = point;
            this.rect = rect;
        }

        private Point2D point;
        private Node2D left;
        private Node2D right;
        private RectHV rect;
    }
}
