import java.util.Arrays;

public class Board
{
    private int N = 0;
    private int[][] tiles;

    public Board(int[][] tiles)            // construct a board from an N-by-N array of blocks
                                            // (where blocks[i][j] = block in row i, column j)
    {
        this.tiles = deepArrayClone(tiles);
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
        for (int row = 0; row < N; row++) {
            for (int col = 0; col < N; col++) {
                tileValue = tiles[row][col];
                if (tileValue == 0) {
                    continue;
                }

                if (tileValue != correctValueAt(row, col)) {
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
        for (int row = 0; row < N; row++) {
            for (int col = 0; col < N; col++) {
                tileValue = tiles[row][col];
                if (tileValue == 0) {
                    continue;
                }

                distance += correctionDistance(row, col);
            }
        }

        return distance;
    }

    public boolean isGoal()                // is this board the goal board?
    {
        return (hamming() == 0) && (manhattan() == 0);
    }

    public Board twin()                    // a board obtained by exchanging two adjacent blocks in the same row
    {
        int[][] twinTiles = deepArrayClone(tiles);

        // find the first row with no empty tile
        int row = 0;
        int col = 0;
        findEmptyTile:
        for (row = 0; row < N; row++) {
            for (col = 0; col < N; col++) {
                if (col < N - 1 && !isEmptyTile(row, col) && !isEmptyTile(row, col + 1)) {
                    break findEmptyTile;
                }
            }
        }

        // exchange
        int temp = twinTiles[row][col];
        twinTiles[row][col] = twinTiles[row][col + 1];
        twinTiles[row][col + 1] = temp;

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

        Board that = (Board) y;
        if (this.N != that.N) {
            return false;
        }

        for (int row = 0; row < N; row++) {
            if (!Arrays.equals(this.tiles[row], that.tiles[row])) {
                return false;
            }
        }

        return true;
    }

    public Iterable<Board> neighbors()     // all neighboring boards
    {
        Stack<Board> neighbors = new Stack<Board>();

        findEmptyTile:
        for (int row = 0; row < N; row++) {
            for (int col = 0; col < N; col++) {
                if (isEmptyTile(row, col)) {
                    if (row > 0) {    // there is a tile above, move it down
                        int[][] nTiles = exchangeEmptyTile(row - 1, col, row, col);
                        neighbors.push(new Board(nTiles));
                    }

                    if (col > 0) {    // there is a tile to the left, move it right
                        int[][] nTiles = exchangeEmptyTile(row, col - 1, row, col);
                        neighbors.push(new Board(nTiles));
                    }

                    if (col < N - 1) {    // there is a tile to the right, move it left
                        int[][] nTiles = exchangeEmptyTile(row, col + 1, row, col);
                        neighbors.push(new Board(nTiles));
                    }

                    if (row < N - 1) {    // there is a tile below, move it up
                        int[][] nTiles = exchangeEmptyTile(row + 1, col, row, col);
                        neighbors.push(new Board(nTiles));
                    }
                }
            }
        }

        return neighbors;
    }

    private boolean isEmptyTile(int row, int col)
    {
        return tiles[row][col] == 0;
    }

    private int[][] exchangeEmptyTile(int fromRow, int fromColumn, int toRow, int toColumn)
    {
        int[][] copy = deepArrayClone(tiles);
        copy[toRow][toColumn] = copy[fromRow][fromColumn];
        copy[fromRow][fromColumn] = 0;

        return copy;
    }

    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append(N + "\n");
        for (int row = 0; row < N; row++) {
            for (int col = 0; col < N; col++) {
                sb.append(String.format("%2d ", tiles[row][col]));
            }
            sb.append("\n");
        }

        return sb.toString();
    }

    /**
     * Return a deep clone of the 2D array
     */
    private static int[][] deepArrayClone(int[][] original)
    {
        if (null == original) {
            return null;
        }

        int[][] copy = new int[original.length][];
        for (int i = 0; i < original.length; i++) {
            copy[i] = original[i].clone();
        }

        return copy;
    }

    /**
     * Return the correct value for row, col
     */
    private int correctValueAt(int row, int col)
    {
        return (row * N) + col + 1;
    }

    private int correctionDistance(int row, int column)
    {
        int value = tiles[row][column];
        int goalRow = (value - 1) / N;
        int goalColumn = (value - 1) % N;

        return Math.abs(row - goalRow) + Math.abs(column - goalColumn);
    }
}
