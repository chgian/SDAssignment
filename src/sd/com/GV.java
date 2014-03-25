package sd.com;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;




import java.io.*;
import java.util.Random;

import javax.sound.sampled.*;

/**
 * it is a static class with all important variables that must be accessible by many classes.
 * It also contains some functions of general purpose.
 * It does not require initialization as all variables and functions are called statically 
 * @author Christos Giannoukos
 *
 */
public class GV 
{
	public static final int WELCOME_SCREEN = 0;
	public static final int OPTIONS_SCREEN = 1;
	public static final int GAME_SCREEN = 2;
	public static final int ABOUT_DLG = 7;
	public static final int GO_TO_GAME = 3;
	public static final int GO_TO_OPTIONS = 4;
	public static final int GO_TO_EXIT = 5;
	public static final int RESTART = 6;
	
	// we set each element of board grid with 1 or 2 to denote to whom this token belongs. 0 if it is empty
	public final static int PLAYER_ONE = 1;			// user
	public final static int PLAYER_TWO = -1;		// computer, usually
	public final static int PLAYER_EMPTY = 0;
	
	public final static int PLAYERTYPE_HUMAN = 0;
	public final static int PLAYERTYPE_COMPUTER = 1;
	
	public final static int DIFFICULTY_CAKE = 0;
	public final static int DIFFICULTY_NOTAKID = 1;
	public final static int DIFFICULTY_NOTTHIS = 2;	
	
	public final static int BOARD_COLUMNS = 7;		// Do not changeboard columns and rows, unless you change AI algorithm and board Image
	public final static int BOARD_ROWS = 6;
	
	public final static String SOUND_RESTART = "sounds//restartgame.wav";
	public final static String SOUND_TOKENFALL = "sounds//tokenfall.wav";
	public final static String SOUND_TRACK = "sounds//track.wav";
    
			
	public GV() 
	{
		// TODO Auto-generated constructor stub
	}

	/**
	 * Loads the image that is in path. If error, closes application
	 * @param path  a string with the path of the image
	 * @return	a bufferedimage
	 */
	public static BufferedImage loadImage(String path)
	{
		BufferedImage image=null;
		// load image
		try 
		{
			image = ImageIO.read(new File(path));

		} 
		catch (IOException e) 
		{
			System.out.println("Screen: Error in reading image " + path + ". Exiting. error: " + e.getMessage());
			System.exit(0);
		}
		return image;
	}
	
	/**
	 * It is called to play a sound once. When sound finishes, function exits.
	 * @param sound
	 */
	public static void playSound(String sound)
	{
		try 
		{
		    File soundFile = new File(sound);
		    AudioInputStream stream;
		    AudioFormat format;
		    DataLine.Info info;
		    Clip clip;

		    stream = AudioSystem.getAudioInputStream(soundFile);
		    format = stream.getFormat();
		    info = new DataLine.Info(Clip.class, format);
		    clip = (Clip) AudioSystem.getLine(info);
		    clip.open(stream);
		    clip.start();
		}
		catch (Exception e) 
		{
			System.out.println("Error in playing "+sound);
		}    
	}	
	
	/**
	 * It is used to play a sound continuously, when the sound finished, the event listener starts the
	 * sound again. There isno control over the sound after it started.
	 * @param sound
	 */
	public static void playSoundTrack(String sound) 
	{
		try
		{ 	
	        final Clip clip = (Clip)AudioSystem.getLine(new Line.Info(Clip.class));

	        clip.addLineListener(new LineListener()
	        {
	            @Override
	            public void update(LineEvent event)
	            {
	                if (event.getType() == LineEvent.Type.STOP)		// if event is when the sound finished. Then play it again
	                {
	                	
	                    clip.close();
	                    try
	                    {
	                    	File soundFile;
	                    	soundFile = new File( SOUND_TRACK);	                    	
	                    	clip.open(AudioSystem.getAudioInputStream(soundFile));
	                    	clip.start();
	                    }
	                    catch (Exception exc)
	            	    {
	            	        exc.printStackTrace(System.out);
	            	    }
	                }
	            }
	        });

	    
	        File soundFile = new File(sound);
	        clip.open(AudioSystem.getAudioInputStream(soundFile));
	        clip.start();
	    }
	    catch (Exception exc)
	    {
	        exc.printStackTrace(System.out);
	    }
	}
}
