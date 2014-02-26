public class Solver
{
    private MinPQ<SearchNode> minPQ = new MinPQ<SearchNode>();
    private MinPQ<SearchNode> twinPQ = new MinPQ<SearchNode>();
    private SearchNode goalNode = null;

    public Solver(Board initial)            // find a solution to the initial board (using the A* algorithm)
    {
        minPQ.insert(new SearchNode(initial, null));
        twinPQ.insert(new SearchNode(initial.twin(), null));
        goalNode = solve();
    }

    public boolean isSolvable()             // is the initial board solvable?
    {
        return (null != goalNode);
    }

    public int moves()                      // min number of moves to solve initial board; -1 if no solution
    {
        if (!isSolvable()) {
            return -1;
        }

        return goalNode.moves;
    }

    public Iterable<Board> solution()       // sequence of boards in a shortest solution; null if no solution
    {
        if (!isSolvable()) {
            return null;
        }

        Stack<Board> solutionSequence = new Stack<Board>();
        for (SearchNode node = goalNode; node != null; node = node.previous) {
            solutionSequence.push(node.board);
        }

        return solutionSequence;
    }

    public static void main(String[] args)
    {
        // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }

    private SearchNode solve()
    {
        SearchNode node;
        while (queuedNodesExist()) {

            node = removeMinAndAddNeigbors(minPQ);
            if (node.board.isGoal()) {
                return node;
            }

            node = removeMinAndAddNeigbors(twinPQ);
            if (node.board.isGoal()) {
                return null;
            }
        }

        return null;
    }

    private SearchNode removeMinAndAddNeigbors(MinPQ<SearchNode> pq)
    {
        SearchNode node = pq.delMin();

        for (Board neighbor : node.board.neighbors()) {
            if (isNeighborAllowed(node, neighbor)) {
                pq.insert(new SearchNode(neighbor, node));
            }
        }

        return node;
    }

    private boolean queuedNodesExist()
    {
        return !(minPQ.isEmpty()) && !(twinPQ.isEmpty());
    }

    private boolean isNeighborAllowed(SearchNode node, Board neighborBoard)
    {
        if (null != node.previous) {
            return !(neighborBoard.equals(node.previous.board));
        }

        return true;
    }

    private class SearchNode implements Comparable<SearchNode>
    {
        private Board board = null;
        private int moves = 0;
        private SearchNode previous = null;
        private int priority = 0;

        public SearchNode(Board board, SearchNode previous)
        {
            this.board = board;
            this.previous = previous;

            if (null != previous) {
                moves = previous.moves + 1;
            }
            priority = board.manhattan() + moves;
        }

        @Override
        public int compareTo(SearchNode that)
        {
            return this.priority - that.priority;
        }
    }
}
