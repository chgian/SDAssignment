package sd.com;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

/**
 * it is a general object for screen. It is extended to more specific screens
 * @author Christos Giannoukos
 *
 */

public class SDScreen 
{
	public Color backgroundColor = new Color(154,125,224);
	public Rectangle screenRect= new Rectangle();		// it is the rectangle of the screen

	public SDScreen()
	{}
	
	public void makePhysics(long time_elapsed, Point mousePos)
	{}
	
	public void draw(Graphics g, Point mousePos)
	{}
	
	public int onMouseClick(Point mousePos)
	{
		return 0;		
	}
	
	public int onMouseMove(Point mousePos)
	{
		return 0;		
	}
	

}
