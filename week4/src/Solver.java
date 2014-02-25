public class Solver
{
    private MinPQ<SearchNode> minPQ = new MinPQ<SearchNode>();
    private MinPQ<SearchNode> twinPQ = new MinPQ<SearchNode>();
    private boolean isSolvable;

    public Solver(Board initial)            // find a solution to the initial board (using the A* algorithm)
    {
        minPQ.insert(new SearchNode(initial, 0, null));
        twinPQ.insert(new SearchNode(initial.twin(), 0, null));
        solve();
    }

    public boolean isSolvable()             // is the initial board solvable?
    {
        return isSolvable;
    }

    public int moves()                      // min number of moves to solve initial board; -1 if no solution
    {
        if (!isSolvable()) {
            return -1;
        }

        while (true) {
            SearchNode node = (SearchNode)minPQ.delMin();
            if (node.board.isGoal()) {
                return node.moves;
            }

            for (Board board : node.board.neighbors()) {
                minPQ.insert(new SearchNode(board, ++node.moves, node));
            }
        }
    }

    public Iterable<Board> solution()       // sequence of boards in a shortest solution; null if no solution
    {
        Stack<Board> solutionSequence = new Stack<Board>();
        solutionSequence.push(goalNode.board);
        SearchNode node = goalNode;
        while (node.previous != null)
        {
            solutionSequence.push(node.previous.board);
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

    private boolean solve()
    {
        SearchNode node;
        while (queuedNodesExist()) {
            node = minPQ.delMin();

            for (Board board : node.board.neighbors()) {
                if (isNeighborAllowed(node, board)) {
                    minPQ.insert(new SearchNode(board, ++node.moves, node));
                }
            }

            if (node.board.isGoal()) {
                isSolvable = true;
                break;
            }

            node = twinPQ.delMin();
            for (Board board : node.board.neighbors()) {
                if (isNeighborAllowed(node, board)) {
                    twinPQ.insert(new SearchNode(board, ++node.moves, node));
                }
            }

            if (node.board.isGoal()) {
                isSolvable = false;
                break;
            }
        }

        return isSolvable;
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

        public SearchNode(Board board, int moves, SearchNode previous)
        {
            this.board = board;
            this.moves = moves;
            this.previous = previous;
            priority = board.manhattan() + moves;
        }

        @Override
        public int compareTo(SearchNode that)
        {
            if (this.priority > that.priority) {
                return 1;
            }
            if (this.priority < that.priority) {
                return -1;
            }
            return 0;
        }
    }
}
