package sd.com;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Point;

/**
 * It is a button that writes one string. When user clicks on it, it returns this information so
 * container can act accordingly
 * @author Christos Giannoukos
 *
 */
public class SDStringButton {

	// it is one button that is a string. user can click on it and give a command
	String buttonLabel;
	Font buttonFont = new Font( "Ravie", Font.PLAIN, 20 );
	Color buttonColor = new Color (210, 100, 100);
	Rectangle buttonRect = new Rectangle();

	/**
	 * Create an SDStringButton
	 * @param label		the text of the button
	 * @param color		the color
	 * @param xpos		button's position on x axis
	 * @param ypos		button's position on y axis
	 * @param g			graphics, it is not stored, just to find some parameters
	 */
	public SDStringButton(String label, Color color, int xpos, int ypos, int fontsize)
	{
		buttonLabel = label;		// we set the label of the button
		buttonColor = color;		// set button color
		buttonFont = new Font( "Ravie", Font.PLAIN, fontsize );
		
		buttonRect.x = xpos;		// set button x position on screen, width stays the same.
		buttonRect.y = ypos;		// set button y position on screen
	}

	/**
	 * Sets button in the middle of the screen
	 * @param screenwidth	integer, the width of the screen
	 */
	public void setButtonPositionInCenter(int screenwidth)
	{
		buttonRect.x = (screenwidth-buttonRect.width)/2;	// we put the button in the middle			
	}

	/**
	 * checks if point is on the button
	 * @param mousePos
	 * @return
	 */
	public boolean isPointOnButton(Point p)
	{
		if( p.x < buttonRect.x)
			return false;
		if( p.x > buttonRect.x+buttonRect.width)
			return false;
		if( p.y<buttonRect.y)
			return false;
		if( p.y > buttonRect.y+buttonRect.height)
			return false;
		return true;		
	}

	/**
	 * Draws the button
	 * @param g				Graphics
	 * @param mousePos		Point with the position of the mouse
	 * @param screenwidth	integer with the screen width
	 */
	public void draw(Graphics g, Point mousePos, int screenwidth)
	{
		g.setFont(buttonFont);			
		buttonRect.width = g.getFontMetrics(buttonFont).getStringBounds(buttonLabel, g).getBounds().width;	// set button bounds
		buttonRect.height = g.getFontMetrics(buttonFont).getStringBounds(buttonLabel, g).getBounds().height;	// set button bounds
		if( screenwidth>0)		// center the button
			setButtonPositionInCenter(screenwidth);

		// check if the mouse is over the string
		if( isPointOnButton(mousePos) )
			g.setColor(Color.yellow);
		else
			g.setColor(buttonColor);

		g.drawString(buttonLabel, buttonRect.x, buttonRect.y+buttonRect.height);			
	}


}
