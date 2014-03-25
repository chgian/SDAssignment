package sd.com;

import java.awt.BorderLayout;
import java.awt.Graphics;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

import java.awt.Color;

import javax.swing.border.LineBorder;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import javax.swing.JSlider;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import java.awt.Dialog.ModalityType;

public class SDOptionsDlg extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField player1textField;
	private JTextField player2textField;
	public int proceedtogame = GV.GO_TO_EXIT;		// if user wants to play, this variable becomes GV.GO_TO_GAME,

	private BufferedImage faces=null; 	
	
	// the faces to show for every player. from 0 to 24
	private int player1Face = 0;
	private int player2Face = 0;
	
	private JPanel player1FacePanel;
	private JPanel player2FacePanel;
	
	private JSlider player1Slider;
	private JSlider player2Slider;	
	
	private ColorComboBox player1ColorCombo;
	private ColorComboBox player2ColorCombo;
	private JComboBox difficultyCombo;	
	
	
	private JComboBox player1Type;
	private JComboBox player2Type;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			SDOptionsDlg dialog = new SDOptionsDlg();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public SDOptionsDlg() {
		setModalityType(ModalityType.APPLICATION_MODAL);
		setBounds(100, 100, 719, 546);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(new Color(255, 228, 181));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Player 1");
		lblNewLabel.setForeground(new Color(153, 0, 0));
		lblNewLabel.setFont(new Font("Tempus Sans ITC", Font.BOLD, 23));
		lblNewLabel.setBounds(112, 11, 92, 25);
		contentPanel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Name");
		lblNewLabel_1.setForeground(new Color(153, 0, 0));
		lblNewLabel_1.setFont(new Font("Tempus Sans ITC", Font.BOLD, 16));
		lblNewLabel_1.setBounds(23, 248, 51, 25);
		contentPanel.add(lblNewLabel_1);
		
		player1textField = new JTextField();
		player1textField.setFont(new Font("Tempus Sans ITC", Font.PLAIN, 15));
		player1textField.setBounds(84, 248, 149, 25);
		contentPanel.add(player1textField);
		player1textField.setColumns(10);
		
		JLabel lblColor = new JLabel("Color");
		lblColor.setForeground(new Color(153, 0, 0));
		lblColor.setFont(new Font("Tempus Sans ITC", Font.BOLD, 16));
		lblColor.setBounds(23, 284, 51, 25);
		contentPanel.add(lblColor);
		
		player1ColorCombo = new ColorComboBox();
		player1ColorCombo.setFont(new Font("Tahoma", Font.PLAIN, 15));
		player1ColorCombo.setBounds(84, 284, 149, 25);
		contentPanel.add(player1ColorCombo);
		
		JLabel lblPlayer = new JLabel("Player 2");
		lblPlayer.setForeground(new Color(153, 0, 0));
		lblPlayer.setFont(new Font("Tempus Sans ITC", Font.BOLD, 23));
		lblPlayer.setBounds(489, 11, 117, 25);
		contentPanel.add(lblPlayer);
		
		JLabel label = new JLabel("Name");
		label.setForeground(new Color(153, 0, 0));
		label.setFont(new Font("Tempus Sans ITC", Font.BOLD, 16));
		label.setBounds(397, 248, 51, 25);
		contentPanel.add(label);
		
		player2textField = new JTextField();
		player2textField.setFont(new Font("Tempus Sans ITC", Font.PLAIN, 15));
		player2textField.setColumns(10);
		player2textField.setBounds(458, 248, 149, 25);
		contentPanel.add(player2textField);
		
		JLabel label_1 = new JLabel("Color");
		label_1.setForeground(new Color(153, 0, 0));
		label_1.setFont(new Font("Tempus Sans ITC", Font.BOLD, 16));
		label_1.setBounds(397, 284, 51, 25);
		contentPanel.add(label_1);
		
		player2ColorCombo = new ColorComboBox();
		player2ColorCombo.setBounds(458, 284, 149, 25);
		contentPanel.add(player2ColorCombo);
		
		JButton leaveButton = new JButton("I want to leave");
		leaveButton.setForeground(new Color(204, 0, 0));
		leaveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				proceedtogame = GV.GO_TO_EXIT;
				SDOptionsDlg.this.setVisible(false);				
			}
		});
		leaveButton.setFont(new Font("Tempus Sans ITC", Font.BOLD, 20));
		leaveButton.setBounds(23, 465, 308, 31);
		contentPanel.add(leaveButton);
		
		JButton playButton = new JButton("Enough talking, let's play!");
		playButton.setForeground(new Color(51, 153, 51));
		playButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				proceedtogame = GV.GO_TO_GAME;
				SDOptionsDlg.this.setVisible(false);
			}
		});
		playButton.setFont(new Font("Tempus Sans ITC", Font.BOLD, 20));
		playButton.setBounds(372, 465, 298, 31);
		contentPanel.add(playButton);
		
		player1FacePanel = new JPanel()
			{
				@Override
	            public void paintComponent(Graphics g)
				{
					super.paintComponent(g);
					
					int imagex = 190*(player1Face%5);
					int imagey = 190*((int)player1Face/5);
					g.drawImage(faces, 0, 0, 150, 150, imagex, imagey, imagex+190, imagey+190, Color.white, this);
				}
			};
		player1FacePanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		player1FacePanel.setBounds(83, 47, 150, 150);
		contentPanel.add(player1FacePanel);
		
		player2FacePanel = new JPanel()
			{
				@Override
	            public void paintComponent(Graphics g)
				{
					super.paintComponent(g);
					
					int imagex = 190*(player2Face%5);
					int imagey = 190*((int)player2Face/5);
					g.drawImage(faces, 0, 0, 150, 150, imagex, imagey, imagex+190, imagey+190, Color.white, this);
				}
			};
		player2FacePanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		player2FacePanel.setBounds(457, 47, 150, 150);
		contentPanel.add(player2FacePanel);
		
		player1Slider = new JSlider();
		player1Slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0)
			{
				player1Face = player1Slider.getValue();
				player1FacePanel.repaint();
			}
		});
		player1Slider.setValue(0);
		player1Slider.setMaximum(24);
		player1Slider.setBounds(84, 208, 149, 26);
		contentPanel.add(player1Slider);
		
		player2Slider = new JSlider();
		player2Slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0)
			{
				player2Face = player2Slider.getValue();
				player2FacePanel.repaint();
			}
		});
		player2Slider.setValue(0);
		player2Slider.setMaximum(24);
		player2Slider.setBounds(458, 208, 149, 26);
		contentPanel.add(player2Slider);
		
		JLabel lblFace = new JLabel("Face");
		lblFace.setForeground(new Color(153, 0, 0));
		lblFace.setFont(new Font("Tempus Sans ITC", Font.BOLD, 16));
		lblFace.setBounds(23, 212, 51, 25);
		contentPanel.add(lblFace);
		
		JLabel label_2 = new JLabel("Face");
		label_2.setForeground(new Color(153, 0, 0));
		label_2.setFont(new Font("Tempus Sans ITC", Font.BOLD, 16));
		label_2.setBounds(397, 208, 51, 25);
		contentPanel.add(label_2);
		
		JLabel lblSelectDifficulty = new JLabel("select difficulty");
		lblSelectDifficulty.setBounds(23, 399, 117, 22);
		contentPanel.add(lblSelectDifficulty);
		lblSelectDifficulty.setForeground(new Color(153, 0, 0));
		lblSelectDifficulty.setFont(new Font("Tempus Sans ITC", Font.BOLD, 16));
		
		difficultyCombo = new JComboBox();
		difficultyCombo.setBounds(164, 398, 248, 25);
		contentPanel.add(difficultyCombo);
		difficultyCombo.setFont(new Font("Tempus Sans ITC", Font.BOLD, 16));
		difficultyCombo.setModel(new DefaultComboBoxModel(new String[] {"piece of cake", "I am not a kid", "No, not this"}));
		
		JLabel lblType = new JLabel("Type");
		lblType.setForeground(new Color(153, 0, 0));
		lblType.setFont(new Font("Tempus Sans ITC", Font.BOLD, 16));
		lblType.setBounds(23, 320, 51, 25);
		contentPanel.add(lblType);
		
		player1Type = new JComboBox();
		player1Type.setModel(new DefaultComboBoxModel(new String[] {"Human bro...", "bip.. I, Robot"}));
		player1Type.setFont(new Font("Tempus Sans ITC", Font.BOLD, 16));
		player1Type.setBounds(83, 320, 150, 25);
		contentPanel.add(player1Type);
		
		JLabel label_3 = new JLabel("Type");
		label_3.setForeground(new Color(153, 0, 0));
		label_3.setFont(new Font("Tempus Sans ITC", Font.BOLD, 16));
		label_3.setBounds(397, 320, 51, 25);
		contentPanel.add(label_3);
		
		player2Type = new JComboBox();
		player2Type.setModel(new DefaultComboBoxModel(new String[] {"Human bro...", "bip.. I, Robot"}));
		player2Type.setFont(new Font("Tempus Sans ITC", Font.BOLD, 16));
		player2Type.setBounds(458, 320, 150, 25);
		contentPanel.add(player2Type);
		
		// load images
		faces = GV.loadImage("images\\faces.jpg");
				
		player1FacePanel.repaint();
		player2FacePanel.repaint();
			
	}		// end of public SDOptionsDlg() {
	
	/**
	 * gets all user input data from dialog and sets them to each player
	 * @param	player1 is the first player,
	 * @param 	player2 is the second player
	 */
	public void Dialog2Players(playerSearch player1, playerSearch player2) 
	{

		// save player's name
		player1.playerName = player1textField.getText();
		player2.playerName = player2textField.getText();

		// save player's color
		player1.playerColor = player1ColorCombo.getColor();
		player2.playerColor = player2ColorCombo.getColor();

		try
		{
			//save player's image face
			int imagex = 190*(player1Face%5);
			int imagey = 190*((int)player1Face/5);
			player1.playerFace = faces.getSubimage(imagex, imagey, 190, 190);
			
	
			imagex = 190*(player2Face%5);
			imagey = 190*((int)player2Face/5);
			player2.playerFace = faces.getSubimage(imagex, imagey, 190, 190);	
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(this, "Error when assign images to players. Message is "+e.getMessage());
		}
		// save player face number
		player1.playerFaceNum = player1Face;
		player2.playerFaceNum = player2Face;
		
		player1.playerType = player1Type.getSelectedIndex();
		player2.playerType = player2Type.getSelectedIndex();
	}
	
	/**
	 * sets all player data into dialog
	 */
	public void Players2Dialog(playerSearch player1, playerSearch player2) 
	{
		// set player's name
		player1textField.setText( player1.playerName);
		player2textField.setText( player2.playerName);

		// set player's color
		player1ColorCombo.setColor(player1.playerColor);
		player2ColorCombo.setColor(player2.playerColor);		

		// save player face number
		player1Face = player1.playerFaceNum;
		player2Face = player2.playerFaceNum;
		
		player1Type.setSelectedIndex(player1.playerType);
		player2Type.setSelectedIndex(player2.playerType);
	}	
	
	public int getDifficulty()
	{
		return difficultyCombo.getSelectedIndex();
	}
	
	
}
