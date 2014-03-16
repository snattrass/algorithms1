import java.util.ArrayList;
import java.util.List;

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
            root = new Node2D(p);
            size++;
            return;
        }

        Node2D node = findInsertionPoint(p);
        if (null != node) {
           return;
        }

        if (node.isEvenNode()) {
            if (p.x() < node.parent.point.x()) {
                node.parent.left = new Node2D(p, node.parent.parent);
            }
            else {
                node.parent.right = new Node2D(p, node.parent.parent);
            }

        }
        else {
            if (p.y() < node.parent.point.y()) {
                node.parent.left = new Node2D(p, node.parent.parent);
            }
            else {
                node.parent.right = new Node2D(p, node.parent.parent);
            }
        }
    }

    private Node2D findInsertionPoint(Point2D point)
    {
        Node2D node = root;
        while (null != node) {
            if (node.point.equals(point)) {     // it already exists
                return node;
            }

            if (node.isEvenNode()) {
                node = (point.x() < node.point.x()) ? node.left : node.right;
            }
            else {
                node = (point.y() < node.point.y()) ? node.left : node.right;
            }
        }

        return node;
    }

    public boolean contains(Point2D p)              // does the set contain the point p?
    {
        return null != findInsertionPoint(p);   // did we find a null node to insert in?  If so the tree doesn't contain p
    }

    public void draw()                              // draw all of the points to standard draw
    {
    }

    public Iterable<Point2D> range(RectHV rect)     // all points in the set that are inside the rectangle
    {
        List<Point2D> inRange = new ArrayList<Point2D>();

        return inRange;
    }

    public Point2D nearest(Point2D p)               // a nearest neighbor in the set to p; null if set is empty
    {
        Point2D nearest = null;

        return nearest;
    }

    private class Node2D
    {
        public Node2D(Point2D point)
        {
            this.point = point;
        }

        public Node2D(Point2D point, Node2D parent)
        {
            this.point = point;
            this.parent = parent;
            level = parent.level + 1;
        }

        public boolean isEvenNode()
        {
            return level % 2 == 0;
        }

        private Point2D point;
        private Node2D left;
        private Node2D right;
        private Node2D parent;
        private int level;
    }
}
