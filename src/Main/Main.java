package Main;

import java.awt.Color;

import javax.swing.JFrame;

import Input.TextHandler;
import Main.GameObjects.Border;
import Main.GameObjects.GameObject;
import Main.GameObjects.Obsticle;
import Main.GameObjects.PlayerCar;


public class Main {

	public static final int WIDTH = Integer.parseInt(Configuration.getProperty("WINDOW_WIDTH"));
	public static final int HEIGHT = Integer.parseInt(Configuration.getProperty("WINDOW_HEIGHT"));
	
	static {
		Configuration.readConfig();
		
	}

	public static void main(String[] args) {
		
		
		new Border(0, 0, 10, Main.HEIGHT, Color.BLACK);
		new Border(Main.WIDTH-PlayerCar.getInstance().getWidth()/4-5, 0,10 , Main.HEIGHT, Color.BLACK);
		new Border(0, 0,WIDTH , 10, Color.BLACK);
		new Border(0, HEIGHT-PlayerCar.getInstance().getHeight()/2-5, WIDTH , PlayerCar.getInstance().getWidth()/4, Color.BLACK);
		// TODO Auto-generated method stub
		JFrame frame = new JFrame();
		GamePanel gamePanel = GamePanel.GetInstance();

		TextHandler.getInstance();
		
		
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
