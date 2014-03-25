
package sd.com;

import javax.swing.JFrame;
class runGame 
{
	public static void main(String args[]) throws Exception
	{
		//initializing frame, game panel and GUI
		JFrame frame = new JFrame();
	    frame.getContentPane().add(new SDGamePanel());

	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.setSize(800,600);
	    frame.setResizable(false);
	    frame.setLocationRelativeTo(null);
	    frame.setVisible(true);	   
	}
}




