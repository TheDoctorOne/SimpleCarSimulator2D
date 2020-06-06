package Main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import Input.InputHandler;
import Input.TextHandler;
import Main.GameObjects.PlayerCar;
import Main.GameObjects.GameObject;

@SuppressWarnings("serial")
public class GamePanel extends JPanel implements KeyListener{
	
	private static GamePanel INSTANCE = null;
	private static boolean INPUT_OVERRIDE = false;
	private final Set<Integer> pressedList = new HashSet<Integer>(); 
	private PlayerCar CAR = null;
	private static Image bgImage = null;
	
	static {
	    try {
			bgImage = ImageIO.read(new File("bg.png"));
		} catch (IOException e) {
			System.out.println("Bg Image Read Fail");
		}
	}
	
	private GamePanel() {
		CAR = PlayerCar.getInstance();
	}
	
	public static GamePanel GetInstance() {
		if(INSTANCE == null)
			INSTANCE = new GamePanel();
		return INSTANCE;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		for(int i=0;i<Main.WIDTH;i+=bgImage.getWidth(null)) {
			for(int k=0;k<Main.HEIGHT;k+=bgImage.getHeight(null)) {
				g.drawImage(bgImage, i, k, null);
			}
		}
		Graphics2D g2d = (Graphics2D) g;
		KeyHandler();
		GameObject.drawAll(g);

	    g2d.setColor(Color.GRAY);
	    //int cur_x = (int)(k*Math.cos(-90)-k*Math.sin(-90));
	    //int cur_y = (int)(k*Math.sin(-90)+k*Math.cos(-90));
	    //Rectangle rect3 = new Rectangle(k, k, 25, 25);
	    //g2d.rotate(Math.toRadians(i), k+12,k);
	    
	    
	    
	    g2d.setColor(Color.BLACK);
	}
	
	private void KeyHandler() {
		if (pressedList.size() < 1)
			return;
		Set<Integer> holderSet = new HashSet<Integer>(pressedList);
		for(int k : holderSet) {
			switch(k){
				case 37: // Left
					CAR.setResetTireTurn(false);
					CAR.turnLeft();
					break;
				case 38: // Up - Forward
					CAR.moveForward();
					CAR.setCanMove(true);
					break;
				case 39: // Right
					CAR.setResetTireTurn(false);
					CAR.turnRight();
					break;
				case 40: // Down - Back
					CAR.moveBack();
					CAR.setCanMove(true);
					break;
				default:
					Set<Integer> r = new HashSet<>();
					r.add(k);
					pressedList.removeAll(r);
					if(pressedList.size() < 1)
						INPUT_OVERRIDE = false;
					break;
			}
				
		}
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		if(!(e.getSource() instanceof InputHandler)) {
			if(!INPUT_OVERRIDE) {
				pressedList.clear();
				TextHandler.getInstance().resetPreviousString();
				CAR.setResetTireTurn(true);
				CAR.setCanMove(false);
			}
			INPUT_OVERRIDE = true;
		} 
		if(pressedList.contains(e.getKeyCode()))
			return;
		Set<Integer> tempList = new HashSet<Integer>();
		tempList.add(e.getKeyCode());
		pressedList.addAll(tempList);
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		Set<Integer> r = new HashSet<>();
		r.add(e.getKeyCode());
		pressedList.removeAll(r);
		if(pressedList.size() < 1)
			INPUT_OVERRIDE = false;
		if(e.getKeyCode() == 37 || e.getKeyCode() == 39)
			CAR.setResetTireTurn(true);
		if(e.getKeyCode() == 38 || e.getKeyCode() == 40)
			CAR.setCanMove(false);
	}

	@Override
	public void keyTyped(KeyEvent e) {}

	public boolean getInputOverride() {
		return INPUT_OVERRIDE;
	}
}
