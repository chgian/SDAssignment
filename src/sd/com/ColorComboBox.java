package sd.com;

// A class that transforms a combo box to a colour selection box.


import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

/**
 *	ColorComboBox is a GUI widget for selecting colors. It is a non-editable
 *	drop-down combo box, and each entry prints the color name along with
 *	a rectangle displaying the color.
 *
 *	<P>The default constructor populates the combo box with the "basic"
 *	colors defined as constants of class <code>java.awt.Color</code>.
 *	Using <code>ColorComboBox(boolean)</code>, an "extended" color list may
 *	be selected.
 *
 *	<P>A future enhancement will allow a "custom" color entry to be added,
 *	which will bring up the full-blown Swing color chooser dialog box to
 *	allow the user to specify any color via HSB or RGB.
 *
 *	<P><small>Software developed for the BaBar Detector at the SLAC B-Factory.
 *	<br>Copyright &copy; 1998 California Institute of Technology.</small>
 *
 *	@see		PlotFormatTabPanel
 *
 *	@version	$Id: ColorComboBox.java,v 1.3 2000/05/19 17:43:41 serbo Exp $
 *
 *	@author		Alex Samuel			(Apr 98; originator)
 */

public class ColorComboBox extends JComboBox
{
// initializers & constructors

	/**
	 *	Creates a combo box populated with the basic Java colors
	 *	in <code>java.awt.Color</code>.
	 *
	 *	@return						a new color combo box
	 */
	public ColorComboBox()
	{
		this(false);
	}

	/**
	 *	Creates a color combo box. If extended colors is selected,
	 *	the combo box is populated with a longer list of colors. 
	 *	Otherwise, the basic Java colors in <code>java.awt.Color</code>
	 *	are used.
	 *
	 *	@param		extendedColors	true if extended colors are to be used
	 *	@return						a new color combo box
	 */
	public ColorComboBox(boolean extendedColors)
	{
		super(extendedColors ? EXTENDED_COLORS : BASIC_COLORS);

		if(extendedColors)
		{
			_colors = EXTENDED_COLORS;
			_colorNames = EXTENDED_COLOR_NAMES;
		}
		else
		{
			_colors = BASIC_COLORS;
			_colorNames = BASIC_COLOR_NAMES;
		}

		setEditable(false);
		setRenderer(new Renderer());
		_font = new Font("Dialog", Font.BOLD, 10);

		setColor(_colors[0]);

		addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				_currentColor = (Color) getSelectedItem();
			}
		});
	}

// accessors

	/**
	 *	Returns the currently selected color.
	 *
	 *	@return					the currently selected color
	 */
	public Color getColor()				{ return (Color) getSelectedItem(); }
	// public Color getColor()			{ return _currentColor; }

	/**
	 *	Sets the color selection to the specified color. If the color
	 *	specified is not a color obtained from a color combo box,
	 *	the color is set instead to the first color in the box.
	 *
	 *	@param		color		the color to be selected
	 */	
	public void setColor(Color color)	
	{ 
		_currentColor = (colorToIndex(color) == -1) ? _colors[0] : color;
		setSelectedItem(_currentColor);
	}

// helpers

	protected int colorToIndex(Color color)
	{
		for(int i=0; i<_colors.length; i++)
		{
			if(color.equals(_colors[i]))
				return i;
		}

		return -1;
	}

// data members

	protected Color _currentColor;
	protected Font _font;

	protected Color[] _colors;
	protected String[] _colorNames;

// static stuff

	/**
	 *	An array containing the basic colors defined as constants in
	 *	<code>java.awt.Color</code>.
	 */
	public final static Color[] BASIC_COLORS = 
	{ 
		Color.black,		Color.darkGray,			Color.gray, 
		Color.lightGray,	Color.blue, 			Color.cyan, 
		Color.green,		Color.magenta,			Color.red, 
		Color.pink,			Color.orange,			Color.yellow, 
		Color.white 
	};

	/**
	 *	An array containing the names of colors defined as constants in
	 *	<code>java.awt.Color</code>.
	 *
	 *	@see		ColorComboBox#BASIC_COLORS
	 */
	public final static String[] BASIC_COLOR_NAMES = 
	{ 
		"black",			"dark gray",			"gray", 
		"light gray",		"blue",					"cyan", 
		"green",			"magenta",				"red", 
		"pink",				"orange",				"yellow", 
		"white" 
	};

	// all hail to Crayola

	/**
	 *	An array containing an expanded selection of colors.
	 */
	public final static Color[] EXTENDED_COLORS = 
	{
		Color.black,					new Color(0.1f, 0.1f, 0.1f),
		new Color(0.2f, 0.2f, 0.2f),	new Color(0.3f, 0.3f, 0.3f),
		new Color(0.4f, 0.4f, 0.4f),	new Color(0.5f, 0.5f, 0.5f),
		new Color(0.6f, 0.6f, 0.6f),	new Color(0.7f, 0.7f, 0.7f),
		new Color(0.8f, 0.8f, 0.8f),	new Color(0.9f, 0.9f, 0.9f),
		Color.white,					Color.red,
		new Color(255, 136, 28),		new Color(120, 62, 27),
		new Color(0, 125, 32),			new Color(11, 157, 150),
		Color.blue,						new Color(109, 0, 168),
		new Color(168, 0, 126),			Color.pink,
		Color.orange,					Color.yellow,
		Color.green,					Color.cyan,
		new Color(164, 207, 255),		new Color(225, 170, 255),
		new Color(255, 170, 210)
	};

	/**
	 *	An array containing names of the expanded selection of colors.
	 *
	 *	@see	ColorComboBox#EXTENDED_COLORS
	 */
	public final static String[] EXTENDED_COLOR_NAMES =
	{
		"black",						"grey 10%",
		"grey 20%",						"grey 30%",
		"grey 40%",						"grey 50%",
		"grey 60%",						"grey 70%",
		"grey 80%",						"grey 90%",
		"white",						"red",
		"orange",						"brown",
		"green",						"turquoise",
		"blue",							"purple",
		"magenta",						"pink",
		"light orange",					"yellow",
		"light green",					"cyan",
		"sky blue",						"violet",
		"light magenta"
	};

// inner classes

	protected class Renderer implements ListCellRenderer
	{
		public Component getListCellRendererComponent(JList list,
			Object value, int index, boolean isSelected, boolean cellHasFocus)      
		{
			return new ColorLabel((Color) value, isSelected, !isEnabled());
		}
	}

	protected class ColorLabel extends JPanel
	{
		protected Color _color;
		protected boolean _isSelected;
		protected boolean _isDisabled;
		
		public ColorLabel(Color color, boolean isSelected, boolean isDisabled)
		{
			_color = color;
			_isSelected = isSelected;
			_isDisabled = isDisabled;

			this.setOpaque(true);
			this.setBackground(_isSelected ? Color.yellow : 
				ColorComboBox.this.getBackground()); 
		}

		public void paint(Graphics g)
		{
			g.setColor(_isSelected ? 
				UIManager.getColor("ComboBox.selectedBackground") :
				UIManager.getColor("ComboBox.background"));
			Rectangle r = this.getBounds();
			g.fillRect(0, 0, r.width, r.height);

			g.setFont(_font);
			FontMetrics metrics = g.getFontMetrics();

			if(_isDisabled)
				g.setColor(UIManager.getColor("Label.disabled"));
			else
				g.setColor(ColorComboBox.this.getForeground());

			int index = colorToIndex(_color);
			String colorName = (index == -1) ? "(unknown)" : 
				_colorNames[index];
			g.drawString(colorName, 2, metrics.getHeight());
	
			g.setColor(_color);
			g.fillRect(60, 2, 36, 11);
			g.setColor(ColorComboBox.this.getForeground());
			g.drawRect(60, 2, 36, 11);
		}

		public Dimension getPreferredSize()
		{
			return new Dimension(102, 16);
		}
	}

}