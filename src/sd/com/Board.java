package sd.com;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Timer;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

public class Board
{	
	private int[][] grid;								// this is the 6x7 board, -1 if player 2, 0 if empty, 1 if player 1 
	
	private BufferedImage boardImage;					// it contains the images of the board	
	private Point boardPosition;						// the x,y of up left point of board position

	private double angle = 0;							// it is used to draw the sinusoidal movement of green arrow above the columns
	
	private static int tokenDiameterInPixels = 50;		// 50 pixel is token diameter. so it is the width of each column
	
	// the next variables are to control the token that is falling
	private int movingToken_YPos = -1;					// if it is negative, no token is moving. we will use it also to check whether it exists a moving token
	private int movingToken_FinalYPos = -1;				// gives the final vertical position of the falling token
	private double movingToken_Velocity = 0;			// how fast does the token fall
	private int movingToken_Column = -1;				// at which column is the falling token
	private int movingToken_Player = 0;					// to which player does the fallen token belong
	private int movingToken_Row = -1;					// the destination row of the token
	private boolean movingToken_JustStopped = false;	// it become true when the moving token is stopped and animation stops. It is used by Gamescreen to know when to look for game result
	
	private Color[] playerColors = new Color[3];		// it contains the colours of the players, 0 for player 2, 1 is null and 2 position for player 1
	
	// the points to draw the vibrating arrow above columns
	private int[] vibratingArrow_xPoints = new int[3];
	private int[] vibratingArrow_yPoints = new int[3];	
	
	/**
	 * Board constructor
	 * @param boardpos
	 */
	public Board(Point boardpos)
	{
		boardPosition = new Point( boardpos.x, boardpos.y);
		
		initialize();	
		
		for(int i=0; i<3; i++)
			playerColors[i] = new Color(50,50,50);
		
		boardImage = GV.loadImage("images//board.png");				
	}
	
	/**
	 * Does all initialization of the board. 
	 */
	public void initialize()
	{
		grid=new int[GV.BOARD_ROWS][GV.BOARD_COLUMNS];		// initialize board, make all cells 0
		movingToken_YPos = -1;					// if it is negative, no token is moving. we will use it also to check whether it exists a moving token
		
	}
	
	/**
	 * draws the board, tokens, moving token and arrow on top
	 * @param g				Graphics,
	 * @param mousePos		Point with mouse position
	 * @param playerToPlay	integer which player has his turn to play, -1 or 1
	 */
	public void draw(Graphics g, Point mousePos, int playerToPlay)
	{		
		// first check if the mouse is on a board's column 
		int mouseOnColumn = mouseIsOnColumn(mousePos);	
		
		// draw an arrow if mouse is on a column and there is no moving token,
		if( (mouseOnColumn>=0) && (movingToken_YPos<0))		
		{	
			g.setColor(playerColors[playerToPlay+1]);		// playerToPlay is -1 or 1, that's why we add 1.
			g.fillPolygon(vibratingArrow_xPoints, vibratingArrow_yPoints, 3);
		}		
		
		// if moving token exists, draw it.
		if(movingToken_YPos>0	)
		{
			drawToken(g, getXfromColumn( movingToken_Column), movingToken_YPos, playerColors[movingToken_Player+1]);
		}		
		
		// draw the tokens of the grid
		for( int row = 0; row<GV.BOARD_ROWS; row++)
		{
			for( int col=0; col<GV.BOARD_COLUMNS; col++)
			{
				if( grid[row][col]!=0)		// it is -1 (player two)  or 1 (player one). if 0 there is no token
					drawToken(g, getXfromColumn( col), getYfromRow(row), playerColors[ grid[row][col]+1]);
			}			
		}
		
		// draw the image of the board
		g.drawImage(boardImage, boardPosition.x, boardPosition.y, null );
	}
	
	/**
	 * Calculate all movements and physics prior any draw
	 * @param time_elapsed	how much time in milliseconds has passed since the last frame
	 * @param mousePos  	A Point that shows mouse's position on panel.
	 */
	public void makePhysics(long time_elapsed, Point mousePos)
	{
		// check if the mouse is on a board's column
		int mouseOnColumn = mouseIsOnColumn(mousePos);		
		
		// calculate vibrating arrow's corner so to be able to draw it
		// arrow is going up down following a sinusoidal movement.
		if( (mouseOnColumn>=0) && (movingToken_YPos<0))		
		{	
			angle += ((double)(time_elapsed))/200;	// this is to give a sinusoidal movement to the arrow			
			
			// calculate vibrating arrow's corners
			vibratingArrow_xPoints[0] = boardPosition.x + 17 + mouseOnColumn*tokenDiameterInPixels;
			vibratingArrow_xPoints[1] = vibratingArrow_xPoints[0]+30;
			vibratingArrow_xPoints[2] = vibratingArrow_xPoints[0]+15;
			vibratingArrow_yPoints[0] = boardPosition.y - 50 + Math.abs((int)(20*Math.sin(angle)));
			vibratingArrow_yPoints[1] = vibratingArrow_yPoints[0];
			vibratingArrow_yPoints[2] = vibratingArrow_yPoints[1]+20;
		}
		
		// if moving token exists, calculate its velocity and position
		if(movingToken_YPos>0	)
		{
			movingToken_Velocity += (double)time_elapsed;							// u = u0 + gdt
			movingToken_YPos += 0.003*movingToken_Velocity*time_elapsed;			// S = S0 + udt
			// moving token reached its final position. stop it
			if(movingToken_YPos > movingToken_FinalYPos )					
			{
				grid[ movingToken_Row ][movingToken_Column] = movingToken_Player;	// assign player's number to the specific cell of the grid
				movingToken_YPos = -1;
				movingToken_JustStopped = true;					// this assignment will inform GamePanel when to check for winner and calculate next move

				// print the status of the grid
				for( int row = 0; row<GV.BOARD_ROWS; row++)
				{
					for( int col=0; col<GV.BOARD_COLUMNS; col++)
					{
						System.out.print( grid[row][col]);

					}
					System.out.println();
				}
				System.out.println("\n");
			}
		}
		
	}
	
	
	/**
	 * Board has boardColumns= 7 columns. depending on which column user is moving the mouse,
	 * it is returned the corresponding column number
	 * @param mousePos  a point with mouse position
	 * @return			integer from 0 to boardColumn-1  (0-6), negative otherwise
	 */
	public int mouseIsOnColumn(Point mousePos)
	{
		if( mousePos.y < boardPosition.y)
			return -1;
		if( mousePos.y > boardPosition.y + boardImage.getHeight())
			return -2;
		if( mousePos.x<boardPosition.x)
			return -3;
		if( mousePos.x> boardPosition.x+boardImage.getWidth()-14)
			return -4;
		
		return (int)(mousePos.x-boardPosition.x-9)/tokenDiameterInPixels;
	}
	
	/**
	 * Draws the token at the given position. 
	 * token diameter is tokenDiameterInPixelsxtokenDiameterInPixels (50x50) 
	 * @param g				Graphics
	 * @param xpos			x position of token
	 * @param ypos			y position of token
	 * @param tokenColor	the color of the token
	 */
	public void drawToken( Graphics g, int xpos, int ypos, Color tokenColor)
	{
		// we get the color to make some effects to be more realistic
		int red = tokenColor.getRed();
		int green = tokenColor.getGreen();
		int blue = tokenColor.getBlue();
		
		GradientPaint gp = new GradientPaint( xpos,  ypos, new Color(Math.min(255, red+80), Math.min(255, green+80), Math.min(255, blue+80)), xpos+tokenDiameterInPixels, ypos+tokenDiameterInPixels,new Color( Math.max(0, red-70), Math.max(0,  green-70), Math.max(0, blue-70)));
		Graphics2D g2d = (Graphics2D)g;
		g2d.setPaint(gp);		
		g2d.fillOval(   xpos,  ypos, tokenDiameterInPixels ,  tokenDiameterInPixels);			
	}
	
	
	/**
	 * Initializes a moving token that will fall from the top of the board to its final position
	 * on top of the other tokens at the same column
	 * @param player		the player, -1 or 1
	 * @param column		which column, from 0 to boardColumn-1
	 * @return				negative if error, 0 if OK
	 */
	public int addToken(int player, int column)
	{
		// check if column is out of bounds
		if( column <0)
			return -1;
		if( column >=GV.BOARD_COLUMNS)
			return -2;
		// check whether there is already another moving token
		if( movingToken_YPos >0)		
			return -3;	
				
		movingToken_Row = getLowestEmptyRow(column);	// find the row where the new token will stop
		if(movingToken_Row <0 )							// there is no free row in that column
			return -4;
		
		// set initial position of the new token and other characteristics
		movingToken_YPos = boardPosition.y -50;
		movingToken_Velocity = 0;
		movingToken_Column = column;
		movingToken_Player = player;
		movingToken_FinalYPos = getYfromRow( getLowestEmptyRow(column) );
		return 0;		
	}
	
	/**
	 * Given the column, it returns the x position of the column (at which x it starts) 
	 * it is used for draw purposes
	 * @param column
	 * @return		an integer that indicates the x position of the column
	 */
	public int getXfromColumn(int column)
	{
		return boardPosition.x +9+column*tokenDiameterInPixels;
		
	}
	
	/**
	 * Given a row, it returns the y position of the row (at which y it starts)
	 * it is used for draw purposed
	 * @param row
	 * @return		an integer that indicates the y position of the row
	 */
	public int getYfromRow(int row)
	{
		return boardPosition.y + row*tokenDiameterInPixels;
	}
	
	
	/**
	 * returns the first free row from the bottom ,for a given column
	 * row 0 if the upper, boardRows-1 is the lowest 
	 * @param column
	 * @return the empty row. if there is no empty row returns -1
	 */
	public int getLowestEmptyRow(int column)
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
	 * sets the colours for each player
	 * @param pl1color
	 * @param pl2color
	 */
	public void setPlayerColors(Color pl1color, Color pl2color)
	{
		playerColors[GV.PLAYER_ONE + 1] = pl1color;			// position 2 of playerColors
		playerColors[GV.PLAYER_TWO + 1] = pl2color;			// position 0 of playerColors. PLAYER_TWO is -1
	}
	
	/**
	 * checks if  there is no moving token, all animation finished and we can proceed with the next player
	 * @return true if movingToken_YPos <0
	 */
	public boolean freeForNextPlayer()
	{
		return (movingToken_YPos<0);
	}
	
	/**
	 * a getter that returns the array of the grid
	 * @return
	 */
	public int[][] getGrid()
	{
		return grid;
	}
	
	/**
	 * a getter to check if animation of moving token has stopped
	 * @return
	 */
	public boolean get_movingToken_JustStopped()
	{
		return movingToken_JustStopped;
	}
	
	/**
	 * A setter that makes false the moving Token just stopped
	 */
	public void set_movingToken_JustStopped()
	{
		movingToken_JustStopped = false;
	}
	

}