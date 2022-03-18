package lab12;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;

/**
 * This class implements a Memory Game GUI.
 * This is also a program for creating and launching the GUI
 * (see the main method).
 * 
 * This is the "finished" version of the GUI built in class.
 * 
 * @author Erin Parker, David Johnson last updated 11/14/16
 */
public class Memory extends JFrame implements ActionListener {
		
	// buttons on game board currently selected. Buttons get
	// added when clicked, and cleared when already two stored.
	private ArrayList<MemoryButton> buttonsSelected; 
	private JButton quitButton;

	/**
	 * Creates a Memory Game GUI.
	 */
	public Memory() {
		
		// Exit on close
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		// Use this to make sure button colors work on all platforms
		try {
		    UIManager.setLookAndFeel( UIManager.getCrossPlatformLookAndFeelClassName() );
		 } catch (Exception e) {
		            e.printStackTrace();
		 }
		
		// The JFrame holds a JPanel, which holds a GridLayout, which
		// holds a bunch of buttons.
		
		// Create a container to hold and organize the 16 buttons
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(4,4  ));
		
		// Create 16 buttons and for each button: 
		//    --construct the button and hide a secret value
		//    --add the button to the container (so it can be seen)
		//    --add a listener for the button (what to do when user selects)
		
		ArrayList<Integer> secretValues = generateSecretValues(8);
	
		for(int i = 0; i < 16; i++) {
			MemoryButton button = new MemoryButton(secretValues.remove(0));
			// Items are added like an ArrayList and the layout
			// files top to bottom and left to right.
			panel.add(button);
			button.addActionListener(this);
		}
		
		// Keep track of buttons selected by the user, none yet
		buttonsSelected = new ArrayList<MemoryButton>();  
		
		quitButton = new JButton("quit");
		quitButton.addActionListener(this);
		JPanel panel2 = new JPanel();
		panel2.setLayout(new BorderLayout());
		panel2.add(quitButton, BorderLayout.NORTH);
		
		panel2.add(panel);
		
		// Set up the JFrame
		setTitle("Memory");
		setContentPane(panel2 );
		setPreferredSize(new Dimension(300, 300));
		pack();
	}
	
	/**
	 * This is the method that is invoked when a button is selected.
	 * 
	 * @param -- an object the represents the event of the button 
	 *           being selected
	 */
	public void actionPerformed(ActionEvent e) {	
		
		if(e.getSource() == quitButton) {
			dispose();
			return;
		}
		
		// If two buttons were saved from last round, their values need to be hidden
		if(buttonsSelected.size() == 2) {
			buttonsSelected.get(0).hideValue();
			buttonsSelected.get(1).hideValue();
			buttonsSelected.get(0).setEnabled(true);
			buttonsSelected.get(1).setEnabled(true);
			buttonsSelected.clear();
			
		}
		
		// Get the button just selected by user
		MemoryButton button = (MemoryButton)e.getSource();
		
		button.setEnabled(false);
		
		// Show the value for the button
		button.showValue();
		
		// Keep track of this button for future rounds
		buttonsSelected.add(button);
 
		// If this is the second button of a pair, check for a match
		if(buttonsSelected.size() == 2 && button.getValue() == buttonsSelected.get(0).getValue()) {
			System.out.println("MATCH!");
//			//disabling button
//			buttonsSelected.get(0).setEnabled(false);
			button.setBackground(Color.green);
			buttonsSelected.get(0).setBackground(Color.green);
			// We are finished with these buttons, do not save for future round
			buttonsSelected.clear();
		}
	}
	
	/** 
	 * Generates a list of integers 1 to limit, two of each, 
	 * randomly shuffled.
	 * 
	 * (This is a helper method for the constructor -- make private.)
	 * 
	 * @param limit -- the largest integer generated
	 * @returns an ArrayList of integers
	 */
	private ArrayList<Integer> generateSecretValues(int limit) {
		ArrayList<Integer> list = new ArrayList<Integer>();
		// Add pairs of numbers to the list
		for(int i = 1; i <= limit; i++) {
			list.add(i);
			list.add(i);
		}	
		// Shuffle them randomly
		Collections.shuffle(list);
		return list;
	}
	
	// Required by a serializable class (ignore for now)
	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		// Create and launch the GUI
		Memory m = new Memory();
		m.setVisible(true);
	}
}