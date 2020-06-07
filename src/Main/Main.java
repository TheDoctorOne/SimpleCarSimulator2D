package Main;

import java.awt.Color;

import javax.swing.JFrame;

import Input.TextHandler;
import Main.GameObjects.Border;

public class Main {

	public static final int WIDTH = Integer.parseInt(Configuration.getProperty("WINDOW_WIDTH"));
	public static final int HEIGHT = Integer.parseInt(Configuration.getProperty("WINDOW_HEIGHT"));
	
	static {
		Configuration.readConfig();
		TextHandler.getInstance();
	}

	public static void main(String[] args) {
		
		
		new Border(0, 0, 10, Main.HEIGHT, Color.BLACK);
		new Border(Main.WIDTH-15, 0,10 , Main.HEIGHT, Color.BLACK);
		new Border(0, 0,WIDTH , 10, Color.BLACK);
		new Border(0, HEIGHT-40, WIDTH , 10, Color.BLACK);
		JFrame frame = new JFrame();
		GamePanel gamePanel = GamePanel.GetInstance();
		
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(WIDTH, HEIGHT);
		frame.add(gamePanel);
		frame.setVisible(true);
		frame.addKeyListener(gamePanel);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		
		gamePanel.repaint();
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				while (true) {
					//gamePanel.i++;
					gamePanel.repaint();
					try {
						Thread.sleep(15);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					//System.out.println("here : " + gamePanel.i);
				}
			}
		}).start();
	}

}
