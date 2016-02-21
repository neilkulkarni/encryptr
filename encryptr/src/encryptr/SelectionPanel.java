package encryptr;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.swing.*;
import javax.swing.filechooser.*;
import javax.swing.SwingUtilities;
import javax.swing.JTextArea;
import javax.jnlp.*;

public class SelectionPanel extends JPanel implements ActionListener {

	public static final int BUTTON_NOT_SET = 0;
	public static final int ENCRYPT = 1;
	public static final int DECRYPT = 2;
	
	static private final String newline = "\n";
	
	private Font f;
	private int pos;
	private double ratioX, ratioY;
	
	private int cryptSelection = BUTTON_NOT_SET;

	private JTextField textField;
	private Point position;
	
	private JButton openButton, saveButton;
	private JRadioButton encrypt, decrypt;
	private ButtonGroup b;
	private JTextArea log;
	JFileChooser fc;

	public SelectionPanel() {
		super();

		f = new Font("Arial Unicode MS", Font.BOLD, 16);
		pos = 20;

		position = new Point();
		
		encrypt = new JRadioButton("encrypt");
		decrypt = new JRadioButton("decrypt");
		
		encrypt.addActionListener(this);
		decrypt.addActionListener(this);
		
		b = new ButtonGroup();
		b.add(encrypt);
		b.add(decrypt);
		
		add(encrypt);
		add(decrypt);
		
		
		//Create the log first, because the action listeners
		//need to refer to it.
		log = new JTextArea(5,20);
		log.setMargin(new Insets(5,5,5,5));
		log.setEditable(false);
		JScrollPane logScrollPane = new JScrollPane(log);

		//Create a file chooser
		fc = new JFileChooser();

		//Uncomment one of the following lines to try a different
		//file selection mode.  The first allows just directories
		//to be selected (and, at least in the Java look and feel,
		//shown).  The second allows both files and directories
		//to be selected.  If you leave these lines commented out,
		//then the default mode (FILES_ONLY) will be used.
		//
		//fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		//fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

		//Create the open button.  We use the image from the JLF
		//Graphics Repository (but we extracted it from the jar).
		openButton = new JButton("Open a File...",
				createImageIcon("images/Open16.gif"));
		openButton.addActionListener(this);

		//Create the save button.  We use the image from the JLF
		/*Graphics Repository (but we extracted it from the jar).
		saveButton = new JButton("Save a File...",
				createImageIcon("images/Save16.gif"));
		saveButton.addActionListener(this);*/

		//For layout purposes, put the buttons in a separate panel
		JPanel buttonPanel = new JPanel(); //use FlowLayout
		buttonPanel.add(openButton);
		//buttonPanel.add(saveButton);
		
		// Set size of the textfield
		textField = new JTextField(20);
		textField.setText("Replace this text with a word...");

		//Add the buttons and the log to this panel.
		add(textField);
		add(buttonPanel);
		add(logScrollPane);
	}

	/**
	 * Paints the window with changes to the window.
	 * 
	 * @param g A graphics object.
	 */
	public void paintComponent(Graphics g) 
	{
		super.paintComponent(g); // Call JPanel's paintComponent method

		int width = getWidth();
		int height = getHeight();

		ratioX = (double)width/350;
		ratioY = (double)height/250;

		Graphics2D g2 = (Graphics2D)g; // Import Graphics2D.
		AffineTransform at = g2.getTransform(); // Matrix stuff.
		g2.scale(ratioX, ratioY); // Scale.

		g.setFont(f);

		g2.setTransform(at); // Return matrix.
	}

		public void actionPerformed(ActionEvent e) {

			File output = null;
			
			if (e.getSource() == encrypt) {
				cryptSelection = ENCRYPT;
				System.out.println("encrytttt");
			}
			else if (e.getSource() == decrypt) {
				cryptSelection = DECRYPT;
				System.out.println("Dncrytttt");
			} 
			//Handle open button action.
			else if (e.getSource() == openButton) {
				int returnVal = fc.showOpenDialog(SelectionPanel.this);

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					
					//This is where a real application would open the file.
					
					File file = fc.getSelectedFile();
					String word = textField.getText().trim().toLowerCase();
				
					if (cryptSelection == ENCRYPT) {
						Crypt c = new Crypt(file, formatOutputName(file.getName()), word);
						output = c.encrypt();
						log.append("File saved to location of program." + newline);
					}
					else if (cryptSelection == DECRYPT) {
						Crypt c = new Crypt(file, formatOutputName(file.getName()), word);
						output = c.decrypt();
						log.append("File saved to location of program." + newline);
					}
					else if (cryptSelection == BUTTON_NOT_SET) {
						log.append("Select encrypt or decrypt." + newline);
					}
				} else {
					log.append("Open command cancelled by user." + newline);
				}
				log.setCaretPosition(log.getDocument().getLength());

				
			} 
			//Handle save button action.
			else if (e.getSource() == saveButton) {
				int returnVal = fc.showSaveDialog(SelectionPanel.this);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					//File file = fc.getSelectedFile();
					
					File file = new File ("EncryptedRomeo.txt");
					fc.setSelectedFile(output);
					//This is where a real application would save the file.
					log.append("Saving: " + file.getName() + "." + newline);
				} else {
					log.append("Save command cancelled by user." + newline);
				}
				log.setCaretPosition(log.getDocument().getLength());
			}
		}

		/** Returns an ImageIcon, or null if the path was invalid. */
		protected static ImageIcon createImageIcon(String path) {
			java.net.URL imgURL = SelectionPanel.class.getResource(path);
			if (imgURL != null) {
				return new ImageIcon(imgURL);
			} else {
				System.err.println("Couldn't find file: " + path);
				return null;
			}
		}
		
		private String formatOutputName (String s) {
			if (s.contains("Encrypted") || s.contains("Decrypted")) {
				return s.substring(9, s.length());
			}
			else if (cryptSelection == ENCRYPT) {
				return "Encrypted" + s;
			}
			else if (cryptSelection == DECRYPT) {
				return "Decrypted" + s;
			}
			else {
				return s;
			}
		}
	}
