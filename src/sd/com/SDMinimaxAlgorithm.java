package sd.com;

/***
 * Found in the internet. It supports only 6x7 grid.
 * Do not try other dimensions
 * @author Christos Giannoukos
 *
 */
public class SDMinimaxAlgorithm extends SDAlgorithm
{
	private int searchDepthLimit;
	
	// it is only for 6x7 boards
	private static int[][] evaluationTable = {	{3, 4,  5,  7,  5, 4, 3}, 
												{4, 6,  8, 10,  8, 6, 4},
												{5, 8, 11, 13, 11, 8, 5}, 
												{5, 8, 11, 13, 11, 8, 5},
												{4, 6,  8, 10,  8, 6, 4},
												{3, 4,  5,  7,  5, 4, 3} };
	
	public SDMinimaxAlgorithm() 
	{
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * this function is called from outside to provide the next move
	 * @param grid		an array int[6][7] with the current state
	 * @param player	for which player 1 or -1 we want the next move	
	 * @return
	 */
	public int getMove(int[][] grid, int player, int searchDepthLimit)
	{		
		int[][] grid2 = new int[GV.BOARD_ROWS ][GV.BOARD_COLUMNS];
		
		// copy the grid because algorithm makes changes to it.
		for( int i=0; i<GV.BOARD_ROWS; i++)
			for( int j=0; j<GV.BOARD_COLUMNS; j++)
				grid2[i][j] = grid[i][j];
		
		this.player = player;
		this.opponent = -1*player;
		this.searchDepthLimit = searchDepthLimit;
		
		miniMaxValue( grid, 0, this.player);		
		return proposedColumn;
	}
	
		
	/**
	 * This method implements the minimax algorithm. It returns either the 
	 * highest minimax value that is possible for the max player 
	 * starting from the passed board state or the lowest minimax value 
	 * that is possible for the min player starting from the passed board 
	 * state. It also modifies the global variable maxColumn that is 
	 * associated to the column with the highest minimax value.
	 */
	private int miniMaxValue(int[][] grid, int depth, int playerToMove) 
	{		
		//check if the board is in a terminal (winning) state and
		//return the maximum or minimum utility value (255 - depth or 
		//0 + depth) if the max player or min player is winning.
		int winner = checkForWinner(grid);
		if (  winner != -2)
		{
			if (winner == player)
				return 255 - depth;
			return depth;				// winner is opponent 
		}
		
		//check if the depth has reached its limit or if the 
		//board is in a terminal (tie) state and return its utility value.
		if ( (depth == searchDepthLimit) || isDraw(grid) )
		{
			return evaluateContent(grid);
		}
		depth++;
		
		if (playerToMove == player) 
		{
			int max = Integer.MIN_VALUE;
			int column = 0;
			for (int i = 0; i < GV.BOARD_COLUMNS; i++) 
			{
				int lowestemptyrow = getLowestEmptyRow(grid, i);
				if (lowestemptyrow>=0) 
				{
					grid[lowestemptyrow][i] = player;
				
					int value = miniMaxValue(grid, depth, opponent);
					if (max < value) 
					{
						max = value;
						column = i;
					}
					remove(grid, i);
				}
			}
			this.proposedColumn = column;
			return max;
		} 
		else
		{
			int min = Integer.MAX_VALUE;
			for (int i = 0; i < GV.BOARD_COLUMNS; i++)
			{
				int lowestemptyrow = getLowestEmptyRow(grid, i);
				if (lowestemptyrow>=0) 
				{
					grid[lowestemptyrow][i] = opponent;
					int value = miniMaxValue(grid, depth, player);
					if (min > value)
						min = value;
					remove(grid, i);
				}
			}
			return min;
		}
	}
	
	/**
	 * The methods alphaBetaSearch, maxValue and minValue implement
	 * the alpha-beta pruning algorithm. This method simply calls
	 * the maxValue method and it returns the column that corresponds to 
	 * that column with the highest maxValue value. 
	 */
	private int alphaBetaSearch(int[][] grid) 
	{
		maxValue(grid, 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
		return this.proposedColumn;
	}
	
	/**
	 * This method returns the highest utility value that is possible
	 * for the max player by starting from the passed board state. It
	 * also modifies the global variable maxColumn that is associated
	 * to the column with the highest maxValue value.
	 */
	private int maxValue(int[][] grid, int depth, int alpha, int beta)
	{
		//check if the board is in a terminal (winning) state and
		//return the maximum or minimum utility value (255 - depth or 
		//0 + depth) if the max player or min player is winning.
		int winner = checkForWinner(grid);
		if ( winner != GV.PLAYER_EMPTY)
		{
			if ( winner == player)
				return 255 - depth;
			else
				return depth; 
		}
		//check if the depth has reached its limit or if the 
		//board is in a terminal (tie) state and return its utility value.
		if ( (depth == searchDepthLimit) || isDraw(grid))
			return evaluateContent(grid);
		
		depth++;
		
		int column = 0;
		for (int i = 0; i < GV.BOARD_COLUMNS; i++)
		{ 
			int lowestemptyrow = getLowestEmptyRow(grid, i);
			if (lowestemptyrow>=0) 
			{
				grid[lowestemptyrow][i] = player;
				
				int value = minValue(grid, depth, alpha, beta);
				if (value > alpha) 
				{
					alpha = value;
					column = i;
				}
				remove(grid, i);
				if (alpha >= beta) 
					return alpha;
			}
		}
		this.proposedColumn = column;
		return alpha;
	}

	/**
	 * This method returns the lowest utility value that is possible
	 * for the min player by starting from the passed board state.
	 */
	private int minValue(int[][] grid, int depth, int alpha, int beta) 
	{
		//check if the board is in a terminal (winning) state and
		//return the maximum or minimum utility value (255 - depth or 
		//0 + depth) if the max player or min player is winning.
		int winner = checkForWinner(grid);
		if ( winner != GV.PLAYER_EMPTY)
		{
			if ( winner == player)
				return 255 - depth;
			else 
				return depth;
		}
		
		//check if the depth has reached its limit or if the 
		//board is in a terminal (tie) state and return its utility value.
		if ( (depth == searchDepthLimit) || isDraw(grid) )
			return evaluateContent(grid);
		
		depth++;
		for (int i = 0; i < GV.BOARD_COLUMNS; i++) 
		{
			int lowestemptyrow = getLowestEmptyRow(grid, i);
			if (lowestemptyrow>=0) 
			{
				grid[lowestemptyrow][i] = opponent;
				
				int value = maxValue(grid, depth, alpha, beta);
				if (value < beta)
					beta = value;
				remove(grid, i);
				if (beta <= alpha) 
					return beta;
			}
		}
		return beta;
	}
	
	
	/**
	 * This method implements the evaluation function by taking into
	 * account the two-dimensional array evaluationTable.
	 */
	private int evaluateContent(int [][] grid) 
	{
		int utility = 128;
		int sum = 0;
		for (int i = 0; i < GV.BOARD_ROWS; i++)
		{
			for (int j = 0; j <GV.BOARD_COLUMNS; j++)
			{
				if (grid[i][j] == player)
					sum += evaluationTable[i][j];
				else if (grid[i][j] == -1*player )
					sum -= evaluationTable[i][j];
			}
		}
		return utility + sum;
	}

}
