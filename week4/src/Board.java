public class Board
{
    int N = 0;
    int tiles[][];

    public Board(int[][] tiles)            // construct a board from an N-by-N array of blocks
                                            // (where blocks[i][j] = block in row i, column j)
    {
        this.tiles = tiles;
    }

    public int dimension()                 // board dimension N
    {
        return N;
    }

    public int hamming()                   // number of blocks out of place
    {
        int hamming = 0;

        for (int i = 0 ; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (tiles[i][j] != correctValue(i, j)) {
                    hamming++;
                }
            }
        }

        return hamming;
    }

    public int manhattan()                 // sum of Manhattan distances between blocks and goal
    {

    }

    public boolean isGoal()                // is this board the goal board?
    {
        int goal[][] = new int[N][N];

        int tileValue = 1;
        for (int i = 0 ; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (i == (N - 1) && j == (N - 1)) {
                    goal[i][j] = 0;
                }
                else {
                    goal[i][j] = tileValue++;
                }
            }
        }

        return this.equals(new Board(goal));
    }

    public Board twin()                    // a board obtained by exchanging two adjacent blocks in the same row
    {

    }

    public boolean equals(Object y)        // does this board equal y?
    {

    }

    public Iterable<Board> neighbors()     // all neighboring boards
    {

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
    private int correctValue(int i, int j)
    {
        return (i * N) + j + 1;
    }
}
