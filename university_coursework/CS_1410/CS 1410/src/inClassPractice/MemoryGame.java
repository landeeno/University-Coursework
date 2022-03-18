package inClassPractice;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JPanel;

public class MemoryGame {

	public MemoryGame() {

		setDefaultCloseOperation(EXIT_ON_CLOSE);

		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(4,4));

		for (int i =0; i < 16; i++){
			MemoryBUtton button = new MemorryButton(2);
			panel.add(button);
			button.addActionListener(this);
		}
	}

	private ArrayList<Integer> generateValues(int numberOfPairs) {
		ArrayList<Integer> vals = new ArrayList<Integer>();
		for (int i=1; i <= numberOfPairs; i++) {
			vals.add(i);
			vals.add(i);
		}
		Collections.shuffle(vals);

	}


	setTitle("Memory");
	setContentPane(panel);
	setPreferredSize(new Dimension(300, 300));
	pack();


	public void showValue() {
		setBackground(Color.yellow);
		setText(""+secretValue);
	}

	public void actionPerformed(ActionEvent e) {
		MemoryButton button = (MemoryButton)e.getSource();
		button.showValue();
	}

	publics static void main(String[] args) {

	}


}


