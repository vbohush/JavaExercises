package net.bohush.exercises.chapter18;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.LineBorder;

public class Exercise17 extends JApplet {

	private static final long serialVersionUID = 1L;
	private CarPanel carPanel1 = new CarPanel();
	private CarPanel carPanel2 = new CarPanel();
	private CarPanel carPanel3 = new CarPanel();
	private CarPanel carPanel4 = new CarPanel();
	private JTextField jTextField1 = new JTextField("1");
	private JTextField jTextField2 = new JTextField("1");
	private JTextField jTextField3 = new JTextField("1");
	private JTextField jTextField4 = new JTextField("1");
	
	public Exercise17() {
		JPanel panel2 = new JPanel(new GridLayout(1, 8));
		jTextField1.addKeyListener(new KeyAdapter() {			
			@Override
			public void keyReleased(KeyEvent e) {
				try {
					carPanel1.setSpeed(Integer.parseInt(jTextField1.getText()));					
				} catch (NumberFormatException e2) {
				}
			}
		});
		jTextField2.addKeyListener(new KeyAdapter() {			
			@Override
			public void keyReleased(KeyEvent e) {
				try {
					carPanel2.setSpeed(Integer.parseInt(jTextField2.getText()));					
				} catch (NumberFormatException e2) {
				}
			}
		});
		jTextField3.addKeyListener(new KeyAdapter() {			
			@Override
			public void keyReleased(KeyEvent e) {
				try {
					carPanel3.setSpeed(Integer.parseInt(jTextField3.getText()));					
				} catch (NumberFormatException e2) {
				}
			}
		});
		jTextField4.addKeyListener(new KeyAdapter() {			
			@Override
			public void keyReleased(KeyEvent e) {
				try {
					carPanel4.setSpeed(Integer.parseInt(jTextField4.getText()));					
				} catch (NumberFormatException e2) {
				}
			}
		});
		panel2.add(new JLabel("Car 1: ", JLabel.RIGHT));
		panel2.add(jTextField1);
		panel2.add(new JLabel("Car 2: ", JLabel.RIGHT));
		panel2.add(jTextField2);
		panel2.add(new JLabel("Car 3: ", JLabel.RIGHT));
		panel2.add(jTextField3);
		panel2.add(new JLabel("Car 4: ", JLabel.RIGHT));
		panel2.add(jTextField4);
		add(panel2, BorderLayout.NORTH);
		JPanel panel1 = new JPanel(new GridLayout(4, 1));
		panel1.add(carPanel1);
		panel1.add(carPanel2);
		panel1.add(carPanel3);
		panel1.add(carPanel4);
		add(panel1, BorderLayout.CENTER);
	}

	class CarPanel extends JPanel {
		private int yPos;
		private int xPos;
		private int size = 20;
		private int speed = 1;
		private boolean firstRun = true;
		private static final long serialVersionUID = 1L;

		public CarPanel() {
			setBorder(new LineBorder(Color.BLACK));
			timer.start();
		}
		
		public void setSpeed(int speed) {
			if (speed > 100) {
				speed = 100;
			} else if (speed < 0) {
				speed = 0;
			}
			this.speed = speed;
		}
		
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			if (firstRun) {
				xPos = - 5* size;
				firstRun = false;
			}
			yPos = getHeight();
			g.setColor(Color.BLACK);
			g.fillOval(xPos + size, yPos - size, size, size);
			g.fillOval(xPos + 3 * size, yPos - size, size, size);
			g.setColor(Color.RED);
			g.fillRect(xPos, yPos - 2 * size, 5 * size, size);
			g.setColor(Color.BLUE);
			Polygon p = new Polygon();
			p.addPoint(xPos + size, yPos - 2 * size);
			p.addPoint(xPos + 2 * size, yPos - 3 * size);
			p.addPoint(xPos + 3 * size, yPos - 3 * size);
			p.addPoint(xPos + 4 * size, yPos - 2 * size);
			g.fillPolygon(p);
		}
	
		Timer timer = new Timer(1, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				xPos+=speed;
				if (xPos >= getWidth()) {
					xPos = - 5* size;	
				}
				repaint();
			}
		});		

	}
}