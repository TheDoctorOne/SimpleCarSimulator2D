package Main.GameObjects;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;

import Main.GameObjects.PlayerCar.Tire;

public abstract class GameObject {
	
	private static ArrayList<GameObject> gameObjects = new ArrayList<>();
	protected double x = 0.0f;
	protected double y = 0.0f;
	protected double height = 0.0f;
	protected double width = 0.0f;
	protected double visibleHeight = 0.0f;
	protected double visibleWidth = 0.0f;
	protected boolean isVisible = true;
	protected boolean isCollidable = true;
	
	protected GameObject() {
		gameObjects.add(this);
	}
	
	
	public static void drawAll(Graphics g) {
		for(GameObject obj: gameObjects) {
			if(obj.isVisible && !(obj instanceof Tire))
				obj.draw(g.create());
		}
	}
	
	public static void drawOnly(Class<?> c, Graphics g) {
		for(GameObject obj: gameObjects) {
			if(obj.isVisible)
				if(c.getClass().isInstance(obj))
					obj.draw(g.create());
		}
	}

	public abstract void draw(Graphics g);
	
	
	public static boolean collisionCheck(GameObject go1, GameObject go2) {
		double x1 = go1.getX();
		double y1 = go1.getY();
		double h1 = go1.getHeight();
		double w1 = go1.getWidth();
		
		double x2 = go2.getX();
		double y2 = go2.getY();
		double h2 = go2.getHeight();
		double w2 = go2.getWidth();
		// Check if in between
		boolean between_x = false;
		boolean between_y = false;
		
		if (x2 >= x1 && x2 <= x1+w1) // x in between
			between_x = true;
		
		if(y2 >= y1 && y2 <= y1+h1)
			between_y = true;
		
		if(between_x && between_y)
			return true;
		
		if(between_x)
			if(y2+h2 >= y1 && y2+h2 <= y1+h1)
				return true;
		
		if(between_y)
			if(x2+w2 >= x1 && x2+w2 <= x1+w1)
				return true;
		
		//******************************
		between_x = false;
		between_y = false;
		if (x1 >= x2 && x1 <= x2+w2) // x in between
			between_x = true;
		
		if(y1 >= y2 && y1 <= y2+h2)
			between_y = true;
		
		if(between_x && between_y)
			return true;
		
		if(between_x)
			if(y1+h1 >= y2 && y1+h1 <= y2+h2)
				return true;
		
		if(between_y)
			if(x1+w1 >= x2 && x1+w1 <= x2+w2)
				return true;
			
		return false;
	}
	
	public boolean isCollidingWith(GameObject object) {
		return collisionCheck(this, object);
	}
	
	public void setX(double x) {
		this.x = x;
	}
	
	public void setY(double y) {
		this.y = y;
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public double getHeight() {
		return height;
	}
	
	public double getWidth() {
		return width;
	}
	
	public boolean isCollidable() {
		return isCollidable;
	}
	
	public static ArrayList<GameObject> getGameObjects() {
		return gameObjects;
	}
	
	public void setCollidable(boolean isCollidable) {
		this.isCollidable = isCollidable;
	}
	
	/*public static void runCollider() { //Not Neccesseary, but here and works.
		if(!isRunning)
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					//collideChecker();
					
				}
			}).start();
	}
	
	private static boolean isRunning= false;
	private static void collideChecker() {
		isRunning = true;
		while(true) {
			try {
				for(GameObject obj1: gameObjects) {
					for(GameObject obj2: gameObjects) {
						if(obj1.equals(obj2))
							continue;
						if((obj1 instanceof PlayerCar && obj2 instanceof Tire) || (obj2 instanceof PlayerCar && obj1 instanceof Tire))
							continue;
						if(obj1.isCollidable && obj2.isCollidable) {
							if(obj1.isCollidingWith(obj2)) {
								System.out.println(obj1.getClass().getSimpleName() + " colliding with " + obj2.getClass().getSimpleName());
							}
						}
					}
				}
			} catch (ConcurrentModificationException e) {
				e.printStackTrace();
			}
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				isRunning = false;
				e.printStackTrace();
			}
		}
	}*/
}
