public class Board
{
    int N = 0;
    int tiles[][];

    public Board(int[][] tiles)            // construct a board from an N-by-N array of blocks
                                            // (where blocks[i][j] = block in row i, column j)
    {
        this.tiles = tiles;
        this.N = tiles.length;
    }

    public int dimension()                 // board dimension N
    {
        return N;
    }

    public int hamming()                   // number of blocks out of place
    {
        int hamming = 0;

        int tileValue;
        for (int i = 0 ; i < N; i++) {
            for (int j = 0; j < N; j++) {
                tileValue = tiles[i][j];
                if (tileValue == 0) {
                    continue;
                }

                if (tileValue != correctValueAt(i, j)) {
                    hamming++;
                }
            }
        }

        return hamming;
    }

    public int manhattan()                 // sum of Manhattan distances between blocks and goal
    {
        int distance = 0;

        int tileValue = 0;
        for (int i = 0 ; i < N; i++) {
            for (int j = 0; j < N; j++) {
                tileValue = tiles[i][j];
                if (tileValue == 0) {
                    continue;
                }
                distance += correctionDistance(tileValue);
            }
        }

        return distance;
    }

    public boolean isGoal()                // is this board the goal board?
    {
        return hamming() == 0;
    }

    public Board twin()                    // a board obtained by exchanging two adjacent blocks in the same row
    {
        int row = StdRandom.uniform(N);
        int column = StdRandom.uniform(N - 1);

        int twinTiles[][] = tiles.clone();


        // exchange
        int temp = twinTiles[row][column];
        twinTiles[row][column] = twinTiles[row][column + 1];
        twinTiles[row][column + 1] = temp;

        return new Board(twinTiles);
    }

    public boolean equals(Object y)        // does this board equal y?
    {
        if (y == this) {
            return true;
        }
        if (null == y) {
            return false;
        }

        if (y.getClass() != this.getClass()) {
            return false;
        }

        Board that = (Board)y;
        return (this.N == that.N) && (this.tiles == that.tiles);
    }

    public Iterable<Board> neighbors()     // all neighboring boards
    {
        Stack<Board> neighbors = new Stack<Board>();

        int i = 0;
        int j = 0;
        findEmptyTile:
        for (; i < N; i++){
            for (; j < N; j++) {
                if (isEmptyTile(i, j)) {
                    break findEmptyTile;
                }
            }
        }

        if (i > 0) {    // there is a tile above, move it down
            int nTiles[][] = tiles.clone();
            moveTileDown(nTiles, i, j);
            neighbors.push(new Board(nTiles));
        }

        if (j > 0) {    // there is a tile to the left, move it right
            int nTiles[][] = tiles.clone();
            moveTileRight(nTiles, i, j);
            neighbors.push(new Board(nTiles));
        }

        if (j < N - 1) {    // there is a tile to the right, move it left
            int nTiles[][] = tiles.clone();
            moveTileLeft(nTiles, i, j);
            neighbors.push(new Board(nTiles));
        }

        if (i < N - 1) {    // there is a tile below, move it up
            int nTiles[][] = tiles.clone();
            moveTileUp(nTiles, i, j);
            neighbors.push(new Board(nTiles));
        }

        return neighbors;
    }


    private boolean isEmptyTile(int i, int j)
    {
        return tiles[i][j] == 0;
    }

    private void moveTileDown(int t[][], int i, int j)
    {
        exchangeEmpty(t, i, j, i - 1, j);
    }

    private void moveTileUp(int t[][], int i, int j)
    {
        exchangeEmpty(t, i, j, i + 1, j);
    }

    private void moveTileLeft(int t[][], int i, int j)
    {
        exchangeEmpty(t, i, j, i, j - 1);
    }

    private void moveTileRight(int t[][], int i, int j)
    {
        exchangeEmpty(t, i, j, i, j + 1);
    }

    private void exchangeEmpty(int t[][], int fromRow, int fromColumn, int toRow, int toColumn)
    {
        t[fromRow][fromColumn] = t[toRow][toColumn];
        t[toColumn][toColumn] = 0;
    }

    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                sb.append(String.format("%2d ", tiles[i][j]));
            }
            sb.append("\n");
        }

        return sb.toString();
    }

    /**
     * Return the correct value for row i, column j
     */
    private int correctValueAt(int i, int j)
    {
        return (i * N) + j + 1;
    }

    private int correctionDistance(int value)
    {
        int distance = 0;

        while (value > N) {
            value -= N;
            distance++;

        }

        distance += value;

        return distance;
    }
}
