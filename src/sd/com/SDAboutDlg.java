package sd.com;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * It is an about dialog with information about the author and some copyrights
 * @author Christos Giannoukos
 *
 */
public class SDAboutDlg extends JDialog {

	private final JPanel contentPanel = new JPanel();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			SDAboutDlg dialog = new SDAboutDlg();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public SDAboutDlg() {
		setBounds(100, 100, 372, 236);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblProgrammerChristosGiannoukos = new JLabel("Programmer: Christos Giannoukos");
		lblProgrammerChristosGiannoukos.setBounds(10, 11, 236, 14);
		contentPanel.add(lblProgrammerChristosGiannoukos);
		
		JLabel lblEmailChgianyahoocom = new JLabel("email: ");
		lblEmailChgianyahoocom.setBounds(10, 54, 49, 14);
		contentPanel.add(lblEmailChgianyahoocom);
		
		JLabel lblNewLabel = new JLabel("chgian@yahoo.com");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel.setForeground(Color.BLUE);
		lblNewLabel.setBounds(50, 54, 143, 14);
		contentPanel.add(lblNewLabel);
		
		JLabel lblSoundsWwwfreefxcouk = new JLabel("Sounds: www.freefx.co.uk");
		lblSoundsWwwfreefxcouk.setBounds(10, 76, 236, 14);
		contentPanel.add(lblSoundsWwwfreefxcouk);
		
		JLabel lblAiAlgorithm = new JLabel("AI Algorithm: 2006 Matthias Braunhofer");
		lblAiAlgorithm.setBounds(10, 120, 236, 14);
		contentPanel.add(lblAiAlgorithm);
		
		JLabel lblYear = new JLabel("Year: 2014");
		lblYear.setBounds(10, 32, 236, 14);
		contentPanel.add(lblYear);
		
		JLabel lblTrack = new JLabel("Track: http://www.maxgames.com/play/flip-and-go.html");
		lblTrack.setBounds(10, 98, 324, 14);
		contentPanel.add(lblTrack);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton closeButton = new JButton("Close");
				closeButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) 
					{
						SDAboutDlg.this.setVisible(false);
					}
				});
				closeButton.setActionCommand("OK");
				buttonPane.add(closeButton);
				getRootPane().setDefaultButton(closeButton);
			}
		}
	}
}
