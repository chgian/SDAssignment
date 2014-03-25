package sd.com;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseEvent;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import javax.swing.Timer;

/**
 * It is the main panel that contains all the graphics of the game
 * @author Christos Giannoukos
 *
 */
public class SDGamePanel extends JPanel 
{
	
	private static int screen;							// at which screen we are, 0 for welcome screen, 1 for options screen, 2 for game screen,
	private Rectangle screenRect;						// the rectangle of the panel. 0,0 is the top left corner
	private static Point mousePos = new Point();		//the position of the mouse
	private boolean leftMouseButtonDown = false;		// if left mousebutton is down
		
	private SDOptionsDlg optionsDlg=null;	
	private SDAboutDlg   aboutDlg = null;
	private SDGameScreen gameScreen = null;
	private SDWelcomeScreen welcomeScreen = null;
	
	private Timer timer;								// at every tick we calculate physics and draw graphics
	private long previous_frame_time = 0;				// it is the time when the previous frame was painted. it is used for animating
		
	private playerSearch player1;
	private playerSearch player2;
		
	/**
	 * Create the panel.
	 */
	public SDGamePanel() 
	{		
		screen = GV.WELCOME_SCREEN;
		previous_frame_time = System.currentTimeMillis();	
		
		optionsDlg = new SDOptionsDlg();
		aboutDlg = new SDAboutDlg();		
		
		player1 = new playerSearch("player1", Color.red, 0, GV.PLAYER_ONE, GV.PLAYERTYPE_HUMAN);
		player2 = new playerSearch("player2", Color.green, 1, GV.PLAYER_TWO, GV.PLAYERTYPE_COMPUTER);			
		screenRect= new Rectangle (0,0, 794, 571); //this.getBounds();	// find panels bounds.		
		
		// initialize the screens
		gameScreen = new SDGameScreen(screenRect);
		gameScreen.setPlayers(player1,  player2);
		welcomeScreen = new SDWelcomeScreen(screenRect);		
		
		GV.playSoundTrack(GV.SOUND_TRACK);			// start playing music
		
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) 
			{
				leftMouseButtonDown = SwingUtilities.isLeftMouseButton(e);
				int res;
				switch(screen)
				{
				case GV.WELCOME_SCREEN:		// welcome
					res = welcomeScreen.onMouseClick(mousePos);
					if( res == GV.GO_TO_EXIT)	// user wants to leave
						System.exit(0);
					if( res == GV.GO_TO_GAME)	// user wants to proceed to the game
					{
						screen = GV.OPTIONS_SCREEN;		// next step is options
						ShowOptionsDlg();
					}
					else if( res == GV.ABOUT_DLG)	// user wants to see the about dlg. make it modal
					{
						aboutDlg.setModal(true);
						aboutDlg.setLocationRelativeTo(null);
						aboutDlg.setVisible(true);
					}
					break;
				case GV.OPTIONS_SCREEN:		// options						
					break;
				case GV.GAME_SCREEN:		// game
					res = gameScreen.onMouseClick(mousePos);
					if( res == GV.GO_TO_EXIT)	// user wants to leave
					{
						screen  = GV.WELCOME_SCREEN;	// go to welcome screen 
					}
					else if( res == GV.GO_TO_OPTIONS)	// user wants to go to options
					{						
						screen = GV.OPTIONS_SCREEN;
						repaint();
						ShowOptionsDlg();
					}
					else if( res == GV.RESTART)		// user wants to restart
					{						
					}
					break;
				default:
				}	
				//repaint();
			}
			@Override
			public void mouseReleased(MouseEvent e) 
			{
				leftMouseButtonDown = false;
				//repaint();
			}
		});
		addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent arg0) 
			{
				mousePos = arg0.getPoint();
				switch(screen)
				{
				case GV.WELCOME_SCREEN:		// welcome
					welcomeScreen.onMouseMove(mousePos);
					break;
				case GV.OPTIONS_SCREEN:		// options		
					break;
				case GV.GAME_SCREEN:		// game
					gameScreen.onMouseMove(mousePos);
					break;
				default:
				}	
				
				//repaint();
			}
		});		
		
		// every 50 milliseconds, run the taskPerformer
		timer = new Timer(50, taskPerformer);
		timer.start();		
	}		// end of 	public SDGamePanel() 	
	
	/**
	 * It is called by timer at every tick
	 */
	ActionListener taskPerformer = new ActionListener() 
	{
		public void actionPerformed(ActionEvent evt) 
		{
			long time_elapsed = System.currentTimeMillis() - previous_frame_time;
			previous_frame_time = System.currentTimeMillis();
			// first deal with the physics
			switch(screen)
			{
			case GV.WELCOME_SCREEN:		// welcome screen
				welcomeScreen.makePhysics(time_elapsed, mousePos);
				break;
			case GV.OPTIONS_SCREEN:		// options	
				break;
			case GV.GAME_SCREEN:		// game
				gameScreen.makePhysics(time_elapsed, mousePos); 
				break;
			default:
			}		
			
			// now paint
			repaint();
		}
	};

	/**
	 * It is called at every time we make repaint in timer task.
	 */
	public void paint(Graphics g) 
	{
		super.paintComponent(g);
				
		switch(screen)
		{
		case GV.WELCOME_SCREEN:		// welcome screen
			welcomeScreen.draw(g, mousePos);
			break;
		case GV.OPTIONS_SCREEN:		// options	
			welcomeScreen.draw(g, mousePos);
			// draw background
			g.setColor(new Color(50, 50, 50, 170));
			g.fillRect(screenRect.x,  screenRect.y,  screenRect.width, screenRect.height);	
			break;
		case GV.GAME_SCREEN:		// game
			gameScreen.draw(g, mousePos); 
			break;
		default:
		}					
					
		// draw mouse position
		//g.setFont(new Font( "Arial", Font.PLAIN, 15));
		//g.setColor(Color.white);
		//g.drawString("Game " + mousePos.x + " " + mousePos.y + " " + leftMouseButtonDown + " ", 10, 15);			
	}
	
	/**
	 * Shows option dialog
	 */
	public void ShowOptionsDlg()
	{
		if( !optionsDlg.isVisible() )
		{			
			// first pass both players' data into the dialog 
			optionsDlg.Players2Dialog(player1, player2);
			optionsDlg.setModal(true);
			optionsDlg.setLocationRelativeTo(null);		// show dialog in the center of the screen
			optionsDlg.setVisible(true);
		
			
			if( optionsDlg.proceedtogame == GV.GO_TO_GAME)	// user want to proceed to game, go to game
			{
				screen = GV.GAME_SCREEN;		
				optionsDlg.Dialog2Players(player1, player2);		// take players' data from dialog and pass them to players' objects
				gameScreen.setGameData( optionsDlg.getDifficulty());	// get difficult, the only setting not in player data
			}
			else if( optionsDlg.proceedtogame == GV.GO_TO_EXIT)	// user want to proceed to leave, go to main screen
			{
				screen = GV.WELCOME_SCREEN;	
				optionsDlg.Dialog2Players(player1, player2);
			}
		}
		
	}
	
}
