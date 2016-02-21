package encryptr;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;


public class encryptr {
	/**
	 * Main method of the program. Begins execution and creation of encryption and decryption program.
	 * 
	 * @param args
	 * 
	 * 2/20/16
	 */

	public static void main(String[] agrs) {
		try 
		{
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} 
		catch (Exception e) 
		{
			System.out.println("Uh oh!");
		}

		JFrame w = new JFrame("Encrypt or Decrypt?");
		w.setBounds(0, 0, 350, 250);
		w.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panelHeading = new JPanel();
		JPanel panel = new JPanel();

		// Create Border Layout
		BorderLayout layout = new BorderLayout();
		panel.setLayout(layout);
		panel.setBackground(Color.WHITE);

		SelectionPanel sp = new SelectionPanel();

		panel.add(sp, layout.CENTER);

		w.add(panel);
		w.setResizable(true);
		w.setVisible(true);
	}
}
