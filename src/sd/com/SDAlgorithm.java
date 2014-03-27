package sd.com;

/**
 * It is a class that describes the general form of the AI algorithm
 * It is inherited to the real algorithm
 * @author Christos Giannoukos
 *
 */
public class SDAlgorithm
{
	protected int player;
	protected int opponent;
	protected int proposedColumn;
	
	public SDAlgorithm() 
	{
		// TODO Auto-generated constructor stub
	}
		
	/**
	 * this function is called from outside to provide the next move
	 * Code is written in the implementation of the algorithm class
	 * @param grid		an array int[6][7] with the current state
	 * @return
	 */
	public int getMove(int[][] grid)
	{
		return 0;
	}	
	
	/**
	 * searches the grid to find 4 tokens in row to declare the winner
	 * @param grid	an array int[6][7] with the current state
	 * @return  -1 if player 2 won, 1 if player 1 won, -2 if nothing
	 */
	public int checkForWinner(int[][] grid)
	{
		//check for win horizontally
		for (int row=0; row < GV.BOARD_ROWS; row++)
		{
			for (int col=0; col < GV.BOARD_COLUMNS-3; col++)
			{
				if (  	(grid[row][col] != GV.PLAYER_EMPTY ) &&
						(grid[row][col] == grid[row][col+1]) &&
						(grid[row][col] == grid[row][col+2]) && 
						(grid[row][col] == grid[row][col+3]))
				return grid[row][col];
			}
		}
		//check for win vertically
		for (int row = 0; row < GV.BOARD_ROWS-3; row++)
		{
			for (int col = 0; col < GV.BOARD_COLUMNS; col++)
			{
				if (	(grid[row][col] != GV.PLAYER_EMPTY ) &&
						(grid[row][col] == grid[row+1][col]) &&
						(grid[row][col] == grid[row+2][col]) &&
						(grid[row][col] == grid[row+3][col]) )
					return grid[row][col];
			}
		}
		//check for win diagonally (upper left to lower right)
		for (int row = 0; row < GV.BOARD_ROWS-3; row++) 
		{
			for (int col = 0; col < GV.BOARD_COLUMNS-3; col++) 
			{
				if (	(grid[row][col] != GV.PLAYER_EMPTY) &&
						(grid[row][col] == grid[row+1][col+1]) &&
						(grid[row][col] == grid[row+2][col+2]) &&
						(grid[row][col] == grid[row+3][col+3]) ) 
					return grid[row][col];
			}
		}
		//check for win diagonally (lower left to upper right)
		for (int row = 3; row < GV.BOARD_ROWS; row++)
		{
			for (int col = 0; col < GV.BOARD_COLUMNS-3; col++) 
			{
				if (	(grid[row][col] != GV.PLAYER_EMPTY   ) &&
						(grid[row][col] == grid[row-1][col+1]) &&
						(grid[row][col] == grid[row-2][col+2]) &&
						(grid[row][col] == grid[row-3][col+3]))
					return grid[row][col];
			}
		}
		return -2;
	}
	
	/**
	 * checks is it is draw. checks if there is any empty position at the top fo the rows.
	 * If there is no empty position returns draw (0). it must be called after checkForWinner
	 * as it does not check for 4 token in line.
	 * @param grid	an array int[6][7] with the current state
	 * @return
	 */
	public boolean isDraw(int[][] grid) 
	{
		for (int j = 0; j < GV.BOARD_COLUMNS; j++)
		{
			if (grid[0][j] == GV.PLAYER_EMPTY)
				return false;
		}
		return true;
	}
	
	/**
	 * checks if the move can be played
	 * @param grid	an array int[6][7] with the current state
	 * @param column
	 * @return		true if move is feasible
	 */
	public boolean isLegalMove(int[][] grid, int column) 
	{
		// check if the move is inside the bounds of the grid
		if(column >= GV.BOARD_COLUMNS)
			return false;
		if( column < 0)
			return false;
		
		// checks if there are empty cells on the column that we want to play
		if( grid[0][column] != GV.PLAYER_EMPTY)
			return false;
		return true;
	}
	
	/**
	 * It is the same as in Board
	 * returns the first free row from the bottom ,for a given column
	 * row 0 if the upper, boardRows-1 is the lowest 
	 * @param grid	an array int[6][7] with the current state 
	 * @param column
	 * @return the empty row. if there is no empty row returns -1
	 */
	public int getLowestEmptyRow(int[][] grid, int column)
	{
		if( column <0 )
			return -1;
		if( column >= GV.BOARD_COLUMNS)
			return -2;
				
		for( int row = GV.BOARD_ROWS-1; row>=0; row--)
		{
			if( grid[row][column]==0)
				return row;
		}
		return -3;	
	}
	
	/**
	 * makes the top most token of column equal to 0.
	 * It is used to reverse the run of some algorithms (minimax for example)
	 * @param grid	an array int[6][7] with the current state
	 * @param column
	 */
	public void remove(int[][] grid, int column) 
	{
		for (int i = 0; i < grid.length; i++) 
		{
			if (grid[i][column] != GV.PLAYER_EMPTY) 
			{
				grid[i][column] = GV.PLAYER_EMPTY;
				return;
			}
		}
	}
	
}
