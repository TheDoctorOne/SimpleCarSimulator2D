package Main.GameObjects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import Main.Configuration;
import Main.Main;

public class PlayerCar extends GameObject{
	
	private final static double MAX_SPEED = Configuration.getDoubleProperty("MAX_SPEED");
	private final static double VELOCITY = Configuration.getDoubleProperty("VELOCITY");
	
	private double turnValue = 0.0d;
	private double tireTurnValue = 0.0d;
	private boolean canMove = false;
	private boolean isMovingBack = false;
	private Tire tire1, tire2, tire3, tire4;
	private double speed = 0.0f;
	private static PlayerCar INSTANCE = null;
	
	
	private boolean resetTireTurn = false;
	
	public static PlayerCar getInstance() {
		return INSTANCE == null ? (INSTANCE = new PlayerCar()) : INSTANCE;
	}
	
	private PlayerCar() {
		super();
		this.width = Configuration.getDoubleProperty("CAR_WIDTH");
		this.height = Configuration.getDoubleProperty("CAR_HEIGHT");
		this.x = Configuration.getDoubleProperty("CAR_X");
		this.y = Configuration.getDoubleProperty("CAR_Y");

		tire1 = new Tire(this.x-width/9, this.y+height/8, width/6, height/5, true);
		tire2 = new Tire(this.x-width/18+width, this.y+height/8, width/6, height/5, true);
		tire3 = new Tire(this.x-width/9, this.y+height/8+height/1.6, width/6, height/5, false);
		tire4 = new Tire(this.x-width/18+width, this.y+height/8+height/1.6, width/6, height/5, false);
	}

	@Override
	public void draw(Graphics g) {
		if(turnValue > 360 || turnValue < -360)
			turnValue = 0;
		//System.out.println(canMove + ":" + speed);
		if(!canMove) {
			if(speed>=VELOCITY/3)
				speed -= VELOCITY/6; 
			else if(speed<=-VELOCITY/3)
				speed += VELOCITY/6;
			if(speed<VELOCITY/3 && speed>-VELOCITY/3)
				speed = 0; 
		}
		if(speed>0.5 || speed<-0.5) {
			if (speed>0)
				moveForward(true);
			else if(speed<0)
				moveBack(true);
		}
			
		
		Graphics2D g2d = (Graphics2D) g;
		g2d.rotate(Math.toRadians(turnValue), x+(width/2),y+(height/3));
		 drawTires(g.create());
		
	    g2d.setColor(Color.BLACK);
	    //System.out.println("Body X : " + x + " Y : " + y);
		Rectangle2D body = new Rectangle2D.Double(x, y, width, height);
	    g2d.draw(body);
	    g2d.fill(body);
	    g2d.setColor(Color.GRAY);
		g2d.draw(new Rectangle2D.Double(x+(width/2),y+(height/3), 2, 2));
		
		g2d.rotate(Math.toRadians(360-turnValue));
		
	}
	
	private void drawTires(Graphics g) {
		tire1.draw(g);
		tire2.draw(g);
		tire3.draw(g);
		tire4.draw(g);
	}
	
	private void updateTires() {
		tire1.update(this.x-width/9, this.y+height/8, false);
		tire2.update(this.x-width/18+width, this.y+height/8, false);
		tire3.update(this.x-width/9, this.y+height/8+height/1.6, false);
		tire4.update(this.x-width/18+width, this.y+height/8+height/1.6, false);
	}
	
	public void turnLeft() {
		if(canMove && speed != 0)
			if(isMovingBack)
				turnValue += speed/2*2;
			else
				turnValue -= speed/2*2;
		tireTurnValue -= 1;
	}
	public void turnRight() {
		if(canMove && speed != 0)
			if(!isMovingBack)
				turnValue += speed/2*2;
			else
				turnValue -= speed/2*2;
		tireTurnValue += 1;
	}
	public void moveBack() { moveBack(false); }
	private void moveBack(boolean forceMove) {
		if(canMove || forceMove) {
			isMovingBack = true;
			if(speed>-MAX_SPEED && canMove && !forceMove)
				speed -= VELOCITY;
			/*if(x<0)
				x=1;
			if(x>Main.WIDTH-width*2)
				x=Main.WIDTH-width*2-1;
			if(y<0)
				y=1;
			if(y>Main.HEIGHT-height*2)
				y=Main.HEIGHT-height*2-1;*/
			
			double temp_x = x;
			double temp_y = y;
			y += Math.cos(-Math.toRadians(turnValue))/8 * 5 * speed * (speed < 0 ? -1 : 1);
			x -= Math.sin(Math.toRadians(turnValue))/8 * 5 * speed * (speed < 0 ? -1 : 1);
			if(doIcollide()) {
				x = temp_x;
				y = temp_y;
				speed = 0;
			}
			updateTires();
		}
	}
	public void moveForward() { moveForward(false); }
	private void moveForward(boolean forceMove) {
		if(canMove || forceMove) {
			isMovingBack = false;
			if(speed<MAX_SPEED && canMove && !forceMove)
				speed += VELOCITY;
			/*if(x<0)
				x=1;
			if(x>Main.WIDTH-width*2)
				x=Main.WIDTH-width*2-1;
			if(y<0)
				y=1;
			if(y>Main.HEIGHT-height*2)
				y=Main.HEIGHT-height*2-1;*/
			double temp_x = x;
			double temp_y = y;
			y -= Math.cos(Math.toRadians(turnValue))/8 * 5 * speed;
			x += Math.sin(Math.toRadians(turnValue))/8 * 5 * speed;
			if(doIcollide()) {
				x = temp_x;
				y = temp_y;
				speed = 0;
			}
			updateTires();
		}
	}
	
	public double getTurnValue() {
		return turnValue;
	}
	
	public void setResetTireTurn(boolean resetTireTurn) {
		this.resetTireTurn = resetTireTurn;
	}
	
	public void setCanMove(boolean canMove) {
		this.canMove = canMove;
	}
	
	public boolean doIcollide() {
		for(GameObject gObject: getGameObjects()) {
			if(gObject instanceof PlayerCar || gObject instanceof Tire)
				continue;
			if(this.isCollidingWith(gObject)) {
				return true;
			}
		}
		return false;
	}
	/*@Override
	public boolean isCollidingWith(GameObject object) {
		
		double x2 = object.getX();
		double y2 = object.getY();
		double h2 = object.getHeight();
		double w2 = object.getWidth();

		
			
		return false;
	}*/
	
	protected class Tire extends GameObject {
		private boolean isFront;
		public Tire(double nx,double ny,double w,double h, boolean isFront) {
			this.x = nx;
			this.y = ny;
			this.width = w;
			this.height = h;
			this.isFront = isFront;
		}
		
		protected void update(double nx,double ny, boolean debug) {
			if(debug)
				System.out.println("Player : " + PlayerCar.INSTANCE.getX() + " : " + PlayerCar.INSTANCE.getY());
			
			this.x = nx;
			this.y = ny;
			
			if(debug)
				System.out.println("UPDATE : " + this.x + " : " + this.y);
		}

		@Override
		public void draw(Graphics g) {
			Graphics2D g2d = (Graphics2D) g.create();
			//System.out.println(tireTurnValue);
			if(tireTurnValue > 30)
				tireTurnValue = 30;
			else if(tireTurnValue < -30)
				tireTurnValue = -30;
			if(resetTireTurn)
				if (tireTurnValue < 1 && tireTurnValue > 1)
					tireTurnValue = 0;
				else if(tireTurnValue>0)
					tireTurnValue -= 0.5;
				else if(tireTurnValue<0)
					tireTurnValue += 0.5;
			
			if(this.isFront)
				g2d.rotate(Math.toRadians(tireTurnValue), this.x+width/2, this.y+height/2);
			
			g2d.setColor(Color.GRAY);
		    //System.out.println("Tire X : " + x + " Y : " + y);
			Rectangle2D tire = new Rectangle2D.Double(this.x, this.y, width, height);
			
			g2d.draw(tire);
			g2d.fill(tire);
			
			g2d.rotate(Math.toRadians(360-tireTurnValue));
		}
	}
}
