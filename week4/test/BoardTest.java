import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;


public class BoardTest
{
    private static final int[][] tiles = {{8, 1, 3}, {4, 0, 2}, {7, 6, 5}};
    private Board board;

    @Before
    public void setUp() throws Exception
    {
        board = new Board(tiles);
    }

    @Test
    public void testHamming() throws Exception
    {
        Assert.assertEquals(5, board.hamming());
    }

    @Test
    public void testManhattan() throws Exception
    {
        Assert.assertEquals(10, board.manhattan());
    }

    @Test
    public void testIsGoal() throws Exception
    {
        int[][] goalTiles = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
        Board goalBoard = new Board(goalTiles);
        Assert.assertTrue(goalBoard.isGoal());
    }
}
