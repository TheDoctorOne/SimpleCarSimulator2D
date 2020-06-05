package Main;

import javax.swing.JFrame;

import Input.TextHandler;


public class Main {

	public static final int WIDTH = Integer.parseInt(Configuration.getProperty("WINDOW_WIDTH"));
	public static final int HEIGHT = Integer.parseInt(Configuration.getProperty("WINDOW_HEIGHT"));
	public static final String INPUT_FILE_NAME_STRING = "input.txt";
	
	static {
		Configuration.readConfig();
		TextHandler.getInstance();
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
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
