package Main.GameObjects;

import java.awt.Graphics;
import java.util.ArrayList;

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
	
	protected GameObject() {
		if(!(this instanceof Tire))
			gameObjects.add(this);
	}
	
	public static void drawAll(Graphics g) {
		for(GameObject obj: gameObjects) {
			if(obj.isVisible)
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
			if(y2+h2 >= y1 && y2+h2 <= y1+h2)
				return true;
		
		if(between_y)
			if(x2+w2 >= x1 && x2+w2 <= x1+w1)
				return true;
			
		return false;
	}
	
	public boolean isCollidingWith(GameObject object) {
		return collisionCheck(this, object);
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
	
}
