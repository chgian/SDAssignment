package sd.com;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Point;
import java.util.Random;

import javax.swing.JOptionPane;

/**
 *  it is the screen where user plays the gam
 * @author Christos Giannoukos
 *
 */
public class SDGameScreen extends SDScreen 
{	
	// players objects
	private playerSearch player1;
	private playerSearch player2;
	
	// we define 3 string buttons
	private SDStringButton restartButton;
	private SDStringButton optionsButton;
	private SDStringButton leaveButton ;
	
	private Board board;
	
	private int playerToPlay = GV.PLAYER_ONE;			// the player that it is his turn to play	
	private int difficulty = GV.DIFFICULTY_CAKE;		
	private int gameresult = -2;						// it is -1 if player 2 won, 0 if draw, 1 if player 1 won. -2 for nothing
		
	public SDGameScreen(Rectangle scrRect) 
	{
		// TODO Auto-generated constructor stub
		screenRect = scrRect;
		
		// create and draw button restart game
		restartButton = new SDStringButton("Restart", new Color(0, 50, 160), screenRect.width - 220, 420, 20);
		// create and draw button options
		optionsButton = new SDStringButton("Options", new Color(0, 50, 160), screenRect.width - 220, 450, 20);
		leaveButton = new SDStringButton("Please, let me go", new Color(180, 0, 0), screenRect.width - 260, 480, 20);
		
		board = new Board(new Point(30, 230));
	}
	
	/**
	 * It is called when we are at game screen
	 * @param g  the graphics of the panel
	 * @param mousePos mouse position on the game panel
	 */
	public void draw(Graphics g, Point mousePos)
	{
		// draw background
		g.setColor(backgroundColor);
		g.fillRect(screenRect.x,  screenRect.y,  screenRect.width, screenRect.height);				
				
		// draw players
		player1.draw(g, 100, playerToPlay == GV.PLAYER_ONE);
		player2.draw(g, screenRect.width-350, playerToPlay == GV.PLAYER_TWO);	
		
		// draw VS between players
		g.setFont(new Font( "Tempus Sans ITC", Font.BOLD, 35));
		g.setColor(Color.yellow);
		g.drawString("VS", 300, 100);	
		
		board.draw(g, mousePos, playerToPlay);
		
		// draw buttons restart, options, leave down right
		//draw button restart game, don't center the button
		restartButton.draw(g, mousePos,0);  				
		// draw button options	
		optionsButton.draw(g, mousePos,0); 		
		// and draw button Leave	
		leaveButton.draw(g, mousePos,0); 	
		
		// if we reached final result show the result to player
		if( gameresult !=-2)
		{
			Font fnt = new Font( "Tempus Sans ITC", Font.BOLD, 70);
			g.setFont(fnt);
			g.setColor(Color.yellow);
			int resultWidth;
			String resultString="";
			if( gameresult ==  GV.PLAYER_TWO)		// player two won	
				resultString = player2.playerName + " won";
			else if (gameresult == GV.PLAYER_ONE)		// player one won
				resultString = player1.playerName + " won";
			else if (gameresult == GV.PLAYER_EMPTY)	// draw
				resultString = "Draw";
			
			// set swting in the middle of the screen
			resultWidth = g.getFontMetrics(fnt).getStringBounds("Player 2 won", g).getBounds().width;	
			g.drawString( resultString, (screenRect.width-resultWidth)/2, 250);
						
		}
		
		// draw what a player must do.
		g.setFont(new Font( "Tempus Sans ITC", Font.BOLD, 30));
		g.setColor(Color.yellow);
		g.drawString("Purpose", 530, 250);	
		g.setFont(new Font( "Tempus Sans ITC", Font.BOLD, 20));
		g.setColor(Color.ORANGE);
		g.drawString("Create a line with 4 tokens to win", 450, 280);			
	}	

	/**
	 * Calculate physics, it is called at every tiemr tick
	 */
	public void makePhysics(long time_elapsed, Point mousePos)
	{
		// first check the game result
		switch(gameresult)
		{
		case GV.PLAYER_TWO:		// player two won			
		case GV.PLAYER_ONE:		// player one won			
		case GV.PLAYER_EMPTY:	// draw
			return;		
		}
		
		// first check if we reached a final result
		if( board.get_movingToken_JustStopped())
		{
			// check if draw
			gameresult = player1.getResult(board.getGrid());
			board.set_movingToken_JustStopped();	
			GV.playSound(GV.SOUND_TOKENFALL);
			if( gameresult!= -2)			// if game has finished, hinder the rest of the physics
				return;
		}		
		
		// play computer's move whether it is one player or both
		// check if board is free to play and computer must play
		if( playerToPlay == GV.PLAYER_ONE) 							// it is the turn of player 1 to play
		{
			if( player1.playerType == GV.PLAYERTYPE_COMPUTER)		// player 1 is computer, play
			{
				if( board.freeForNextPlayer() )						// we are free to take the next move
				{
					int newmove = player1.getMove(board.getGrid(), difficulty);
					System.out.println("Player 1 plays " + newmove);
					
					board.addToken(playerToPlay, newmove);		// we played nextmove, add the token to the board
					playerToPlay = GV.PLAYER_TWO;
				}
			}
		}
		else if( playerToPlay == GV.PLAYER_TWO) 					// it is the turn of player 2 to play
		{
			if( player2.playerType == GV.PLAYERTYPE_COMPUTER)		// player 2 is computer, play
			{
				if( board.freeForNextPlayer() )						// we are free to take the next move
				{
					int newmove = player2.getMove(board.getGrid(), difficulty);
					System.out.println("Player 2 plays " + newmove);
					board.addToken(playerToPlay, newmove);
					playerToPlay = GV.PLAYER_ONE;
				}
			}
		}		
		
		board.makePhysics(time_elapsed, mousePos);		
	}
	
	/**
	 * It inform game screen about the players.
	 * @param player1
	 * @param player2
	 */
	public void setPlayers(playerSearch playr1, playerSearch playr2)
	{
		player1 = playr1;
		player2 = playr2;		
		board.setPlayerColors(player1.playerColor,  player2.playerColor);
	}
	
	/**
	 * sets some data of the game
	 * @param difficulty
	 */
	public void setGameData( int difficulty)
	{
		this.difficulty = difficulty;
		board.setPlayerColors(player1.playerColor,  player2.playerColor);
	}
	
	/**
	 * is called by SDGamePanel when user clicks
	 * @param mousePos
	 * @return	0 if nothing, 1 if user clicked on restart, 2 if user clicked on options, 3 if user clicked on leave
	 */
	public int onMouseClick(Point mousePos)
	{
		if( gameresult != -2)			// it is shown the game result, when users clicks, make new game
		{
			if( gameresult == GV.PLAYER_ONE)
			{
				player1.gamesWon++;
			}
			else if( gameresult == GV.PLAYER_TWO)
			{
				player2.gamesWon++;
			}
			
			board.initialize();
			gameresult = -2;	
			
			return 0;			
		}
		
		// check if user has clicked on any of the buttons
		if( restartButton.isPointOnButton(mousePos))	// user clicked on restart button
		{
			GV.playSound(GV.SOUND_RESTART);
			board.initialize();
			playerToPlay = GV.PLAYER_ONE;
			player1.gamesWon = 0;
			player2.gamesWon = 0;
			return GV.RESTART;	
		}
		else if (optionsButton.isPointOnButton(mousePos))	// user clicked on options button
		{
			board.initialize();
			return GV.GO_TO_OPTIONS;
		}
		else if (leaveButton.isPointOnButton(mousePos))	// user clicked on leave button
		{
			return GV.GO_TO_EXIT;				
		}		
		
		// check if board has any moving tokens
		if( !board.freeForNextPlayer())
			return -1;
		
		//used clicked on board, add token on that column
		int columnclicked = board.mouseIsOnColumn(mousePos);
		if( columnclicked>=0  )
		{
			int res = board.addToken(playerToPlay, columnclicked);
			if( res==0)	// token added
			{
				playerToPlay *= -1;
			}			
		}
		return 0;		
	}	
	
	/**
	 *	It is called by GamePanel every time the mouse moves
	 *@param mousePos the mouse position
	 *@return 0 if everything OK 
	 */
	public int onMouseMove(Point mousePos)
	{		
		return 0;
	}

}
