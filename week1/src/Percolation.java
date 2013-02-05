/**
 *  Percolation object to model a N-by-N grid of sites, each of which may be
 *  open or blocked.  A full site is an open site that can be connected to an
 *  open site in the top row via a chain of neighboring (left, right, up, down)
 *  open sites. We say the system percolates if there is a full site in the
 *  bottom row.
 *
 *  @author Simon Nattrass
 */
public class Percolation
{
    private int size;   // grid size (N)

    private WeightedQuickUnionUF weightedQuickUnionUF;
    private int vTopIndex;      // index of the virtual top site
    private int vBottomIndex;   // index of the virtual bottom site

    private enum SiteState { OPEN, BLOCKED }
    private SiteState[] site;

    /**
     * Create a percolation for a N-by-N site, with all sites blocked
     */
    public Percolation(int N)
    {
        size = N;

        initializeSites();
        initializeUnionFind();
    }

    /**
     * Open site at row i, column j if it is not already and connect
     */
    public void open(int i, int j)
    {
        if (i <= 0 || i > size || j <= 0 || j > size) {
            throw new IndexOutOfBoundsException("row or column out of bounds");
        }

        if (!isOpen(i, j)) {
            site[toIndex(i, j) - 1] = SiteState.OPEN;
            connect(i, j);
        }
    }

    /**
     * Is the site at row i, column j open?
     */
    public boolean isOpen(int i, int j)
    {
        if (i <= 0 || i > size || j <= 0 || j > size) {
            throw new IndexOutOfBoundsException("row or column out of bounds");
        }

        return site[toIndex(i, j) - 1] == SiteState.OPEN;
    }

    /**
     * Is site (row i, column j) full?
     */
    public boolean isFull(int i, int j)
    {
        return isOpen(i, j)
            && weightedQuickUnionUF.connected(vTopIndex, toIndex(i, j));
    }

    /**
     * Does the system percolate?
     */
    public boolean percolates()
    {
        return weightedQuickUnionUF.connected(vTopIndex, vBottomIndex);
    }

    /**
     * Initialize N-by-N sites to closed
     */
    private void initializeSites()
    {
        int nSqr = size * size;
        site = new SiteState[nSqr];
        for (int i = 0; i < nSqr; i++) {
            site[i] = SiteState.BLOCKED;
        }
    }

    /**
     * Initialize Union Find and connect top and bottom virtual sites
     */
    private void initializeUnionFind()
    {
        int nSqr = size * size;
        vTopIndex = 0;              // index of virtual top site
        vBottomIndex = nSqr + 1;    // index of virtual bottom site

        // N-by-N plus the 2 virtual sites
        weightedQuickUnionUF = new WeightedQuickUnionUF(1 + nSqr + 1);


        int bottomOffset =  size * (size - 1);    // offset index for bottom row
        for (int i = 1; i <= size; i++) {
            // connect virtual top site to top row
            weightedQuickUnionUF.union(vTopIndex, i);
            // connect virtual bottom site to bottom row
            weightedQuickUnionUF.union(vBottomIndex, i + bottomOffset);
        }
    }

    /**
     * Connect the site at row, col to neighbouring sites if open
     */
    private void connect(int row, int col)
    {
        if (row > 1 && isOpen(row - 1, col)) {          // above open?
            weightedQuickUnionUF.union(toIndex(row, col),
                                       toIndex(row - 1, col));
        }

        if (row <= size - 1 && isOpen(row + 1, col)) {  // below open?
            weightedQuickUnionUF.union(toIndex(row, col),
                                       toIndex(row + 1, col));
        }

        if (col > 1 && isOpen(row, col - 1)) {          // left open?
            weightedQuickUnionUF.union(toIndex(row, col),
                                       toIndex(row, col - 1));
        }

        if (col <= size - 1 && isOpen(row, col + 1)) {  // right open?
            weightedQuickUnionUF.union(toIndex(row, col),
                                       toIndex(row, col + 1));
        }
    }

    /**
     * Convert row and col to array index
     */
    private int toIndex(int row, int col)
    {
        return ((row - 1) * size) + col;
    }
}