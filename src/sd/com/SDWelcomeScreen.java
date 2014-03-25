package sd.com;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

/**
 * It is the screen that is first shown when application starts
 * @author Christos Giannoukos
 *
 */
public class SDWelcomeScreen extends SDScreen
{
	// define 3 string buttons
	SDStringButton playButton;
	SDStringButton quitButton;
	SDStringButton aboutButton;
	
	// the log of connect 4
	BufferedImage connect4Image;

	/**
	 * constructor
	 * @param scrRect	a rectangle with the dimensions of the panel
	 */
	public SDWelcomeScreen(Rectangle scrRect) 
	{		
		//load connect 4 logo
		connect4Image = GV.loadImage("images\\connect4logo.png");	
				
		// TODO Auto-generated constructor stub
		screenRect = scrRect;
		
		// create button I want to play
		playButton = new SDStringButton("I want to play NOW !!!", new Color(0, 140, 0), 40, 300, 20);
		// create button I want to leave
		quitButton = new SDStringButton("I am too scared to play, I want to leave", new Color(180, 0, 0), 40, 350, 20);	
		aboutButton = new SDStringButton("About...", new Color(50, 50, 50), 40, 400, 20);	
	}
	
	/**
	 * draw the screen
	 * @param g  		Graphics
	 * @param mousePos 	a Point with the mouse position
	 */
	public void draw(Graphics g, Point mousePos)
	{
		// draw gradient  background
		g.setColor(backgroundColor);
		GradientPaint gp = new GradientPaint( screenRect.width/2,  0, new Color(144,115,200), screenRect.width/2, screenRect.height ,new Color(210,170,224));
		Graphics2D g2d = (Graphics2D)g;
		g2d.setPaint(gp);		
		g2d.fillRect(screenRect.x,  screenRect.y,  screenRect.width, screenRect.height);
		
		// draw line at the bottom to make it pretty
		g.setColor(new Color(179, 69, 171)); 
		g.fillOval(71, 475,  60,  60);
		g.fillRect(130, 500, screenRect.width-100, 10);
			
		g.drawImage(connect4Image, (screenRect.width - connect4Image.getWidth())/2, 20, null );
		
		
		// Draw buttons play and quit
		// draw button I want to play
		playButton.draw(g, mousePos, screenRect.width);  		
		// draw button I want to leave	
		quitButton.draw(g, mousePos, screenRect.width); 	
		aboutButton.draw(g, mousePos, screenRect.width); 		
	}
	
	/**
	 * is called by SDGamePanel when user clicks
	 * @param mousePos
	 * @return	0 if nothing,  2 if user clicked on play, 3 if user clicked on leave, 7 if clicked on About Dialog
	 */
	public int onMouseClick(Point mousePos)
	{
		if (playButton.isPointOnButton(mousePos))	// user clicked on options button
		{
			return GV.GO_TO_GAME ;
		}
		else if (quitButton.isPointOnButton(mousePos))	// user clicked on leave button
		{
			return GV.GO_TO_EXIT;				
		}
		else if (aboutButton.isPointOnButton(mousePos))	// user clicked on leave button
		{
			return GV.ABOUT_DLG;				
		}
		return 0;		
	}
}
