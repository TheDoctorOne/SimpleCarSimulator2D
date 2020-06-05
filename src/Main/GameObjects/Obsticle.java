package Main.GameObjects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import Main.Main;

public class Obsticle extends GameObject {
	
	private final Color color;
	private final Rectangle2D rectangle;

	static {
			
			
			/*y=1;
			y=Main.HEIGHT-height*2-1;*/
	}
	
	public Obsticle(double x,double y,double w,double h, Color c) {
		super();
		this.x = x;
		this.y = y;
		this.width = w;
		this.height = h;
		this.color = c;
		rectangle = new Rectangle2D.Double(this.x, this.y, this.width, this.height);
	}

	@Override
	public void draw(Graphics g) {
		Graphics2D g2d = (Graphics2D) g.create();
		g2d.setColor(this.color);
		g2d.draw(rectangle);
		g2d.fill(rectangle);
		
	}

}
