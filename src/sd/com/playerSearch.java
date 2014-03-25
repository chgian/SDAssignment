package sd.com;

import java.awt.Color;



import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 * This class does all graphic job for each player. AI algorithm is run elsewhere
 * @author Christos Giannoukos
 *
 */
public class playerSearch 
{
	public String playerName;				// a string with player's name
	public int playerNumber;				// -1 or 1
	public int playerType = 0;				// 0 for human, 1 for computer
	public Color playerColor;
	public BufferedImage playerFace;		// the image to draw
	public int playerFaceNum;				// a number from 0 to 24
	public int gamesWon;					// how many games the playes has won
	private Font playerFont;				
	private SDMinimaxAlgorithm alg;			// the algorithm to play. We are using only minimax
	

	/**
	 * Constructor
	 * @param name			String with player's name
	 * @param color			Player's color
	 * @param facenum		integer  0-24 that indicates the face of the player
	 * @param playerNum		integer, -1 for player 2, 1 for player 1
	 * @param playerTyp		the type og the player 0 for humna, 1 for computer
	 */
	public playerSearch(String name, Color color, int facenum, int playerNum, int playerTyp)
	{
		playerName = name;
		playerColor = color;
		playerFaceNum = facenum;
		gamesWon = 0;
		playerNumber = playerNum;
		playerType = playerTyp;
		
		alg = new SDMinimaxAlgorithm();
		
		playerFont = new Font( "Tempus Sans ITC", Font.BOLD, 18 );
	}
	
	
	/**
	 * Draws the player
	 * @param g					Graphics
	 * @param xpos				where to begin
	 * @param histurntoplay		whether it is his turn to play
	 */
	public void draw(Graphics g, int xpos, boolean histurntoplay)
	{
		if( histurntoplay)	// this player must play, draw a playerColor rectangle
		{
			g.setColor(playerColor);
			g.fillRect(xpos-5,  25, 110,  110);
		}
		
		g.drawImage(playerFace, xpos, 30, 100, 100, null);
	
		g.setColor(playerColor);
			
		g.setFont(playerFont);		
		if( playerType == GV.PLAYERTYPE_HUMAN )
			g.drawString("Human: " + playerName, xpos, 152);
		else
			g.drawString("Machine: " + playerName, xpos, 152);
			
		g.drawString("Games won "+gamesWon, xpos, 170);		
	}
	

	/**
	 * This function returns player's next move
	 * @param grid				 the grid with the status of the tokens
	 * @param difficulty		 the level of difficulty, 0, 1 or 2
	 * @return
	 */
	public int getMove(int[][] grid, int difficulty)
	{		
		// according to difficult, define the search depth limit
		int searchDepthLimit = difficulty +1;
		
		// call AI algorithm to get the nxt move
		int move = alg.getMove(grid, playerNumber, searchDepthLimit);
		return move; //highest;
	}
	
	/**
	 * checks if we reached a final result
	 * @param grid  a 7x6 board with the tokens
	 * @return		-1 if player 2 won, 0 if draw, 1 if player 1 won, -2 if nothing yet
	 */
	public int getResult(int[][] grid)
	{
		if( alg.isDraw(grid))
			return 0;
		
		return alg.checkForWinner(grid);
	}
}
