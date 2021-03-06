package net.bohush.exercises.chapter38;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.LineBorder;

public class Exercise09 extends JApplet {

	private static final long serialVersionUID = 1L;

	// Indicate which player has a turn, initially it is the X player
	private char whoseTurn = 'X';

	// Create and initialize cells
	private Cell[][] cells = new Cell[3][3];

	// Create and initialize a status label
	private JLabel jlblStatus = new JLabel("X's turn to play");
	private int playerToken = 'X';
	
	/** Initialize UI */
	public Exercise09() {
		
		
		JMenuBar jmb = new JMenuBar();
		this.setJMenuBar(jmb);
		JMenu fileMenu = new JMenu("File");
		fileMenu.setMnemonic('F');
		JMenuItem newGameItem = new JMenuItem("New Game"); 
		newGameItem.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				int answer = JOptionPane.showConfirmDialog(null, "Do you wish to go first", "2P", JOptionPane.YES_NO_OPTION);
				
				for (int i = 0; i < 3; i++)
					for (int j = 0; j < 3; j++)
						cells[i][j].setToken(' ');
				whoseTurn = 'X';
				jlblStatus.setText("X's turn to play");
				
				if(answer == JOptionPane.YES_OPTION) {
					playerToken = 'X';
				} else {
					playerToken = 'O';
					cells[1][1].doAction();
				}

			}
		});
		newGameItem.setMnemonic('N');
		fileMenu.add(newGameItem);
		fileMenu.addSeparator();
		JMenuItem exitItem = new JMenuItem("Exit");
		exitItem.setMnemonic('X');
		exitItem.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(2);
			}
		});
		fileMenu.add(exitItem);

		jmb.add(fileMenu);
		
		// Panel p to hold cells
		JPanel p = new JPanel(new GridLayout(3, 3, 0, 0));
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++)
				p.add(cells[i][j] = new Cell());

		// Set line borders on the cells panel and the status label
		p.setBorder(new LineBorder(Color.red, 1));
		jlblStatus.setBorder(new LineBorder(Color.yellow, 1));

		// Place the panel and the label to the applet
		add(p, BorderLayout.CENTER);
		add(jlblStatus, BorderLayout.SOUTH);
	}

	private Cell getRandomCell() {
		if(isFull()) {
			return null;
		} else {
			while(true) {
				int cellNumberX = (int)(Math.random() * 3);
				int cellNumberY = (int)(Math.random() * 3);
				if(cells[cellNumberX][cellNumberY].getToken() == ' '){
					return cells[cellNumberX][cellNumberY];
				}
			}
		}
	}
	
	/** Determine if the cells are all occupied */
	public boolean isFull() {
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++)
				if (cells[i][j].getToken() == ' ')
					return false;

		return true;
	}

	/** Determine if the player with the specified token wins */
	public boolean isWon(char token) {
		for (int i = 0; i < 3; i++)
			if ((cells[i][0].getToken() == token)
					&& (cells[i][1].getToken() == token)
					&& (cells[i][2].getToken() == token)) {
				return true;
			}

		for (int j = 0; j < 3; j++)
			if ((cells[0][j].getToken() == token)
					&& (cells[1][j].getToken() == token)
					&& (cells[2][j].getToken() == token)) {
				return true;
			}

		if ((cells[0][0].getToken() == token)
				&& (cells[1][1].getToken() == token)
				&& (cells[2][2].getToken() == token)) {
			return true;
		}

		if ((cells[0][2].getToken() == token)
				&& (cells[1][1].getToken() == token)
				&& (cells[2][0].getToken() == token)) {
			return true;
		}

		return false;
	}

	// An inner class for a cell
	public class Cell extends JPanel {
		private static final long serialVersionUID = 1L;
		// Token used for this cell
		private char token = ' ';
		private MyMouseListener myMouseListener = new MyMouseListener();
		
		public Cell() {
			setBorder(new LineBorder(Color.black, 1)); // Set cell's border
			addMouseListener(myMouseListener); // Register listener
		}

		/** Return token */
		public char getToken() {
			return token;
		}

		/** Set a new token */
		public void setToken(char c) {
			token = c;
			repaint();
		}

		public void doAction() {
			myMouseListener.mouseReleased(null);
		}
		
		@Override
		/** Paint the cell */
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);

			if (token == 'X') {
				g.drawLine(10, 10, getWidth() - 10, getHeight() - 10);
				g.drawLine(getWidth() - 10, 10, 10, getHeight() - 10);
			} else if (token == 'O') {
				g.drawOval(10, 10, getWidth() - 20, getHeight() - 20);
			}
		}

		private class MyMouseListener extends MouseAdapter {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// If cell is empty and game is not over
				if (token == ' ' && whoseTurn != ' ') {
					setToken(whoseTurn); // Set token in the cell

					// Check game status
					if (isWon(whoseTurn)) {
						jlblStatus.setText(whoseTurn + " won! The game is over");
						whoseTurn = ' '; // Game is over
					} else if (isFull()) {
						jlblStatus.setText("Draw! The game is over");
						whoseTurn = ' '; // Game is over
					} else {
						// Change the turn
						whoseTurn = (whoseTurn == 'X') ? 'O' : 'X';
						// Display whose turn
						jlblStatus.setText(whoseTurn + "'s turn");
						
						if(whoseTurn != playerToken) {
							getRandomCell().doAction();
						}
					}
				}
			}
		}
	}

	/** This main method enables the applet to run as an application */
	public static void main(String[] args) {
		// Create a frame
		JFrame frame = new JFrame("Exercise09");

		// Create an instance of the applet
		Exercise09 applet = new Exercise09();

		// Add the applet instance to the frame
		frame.add(applet, BorderLayout.CENTER);

		// Display the frame
		frame.setSize(300, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}