package game;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Circle;

public class Ping {
	
	protected final static float VELOCITY = 0.1f;
	protected Circle ping;
	protected float x;
	protected float y;
	protected float radius;
	protected boolean expired;

	public Ping(float x, float y) {
		// TODO Auto-generated constructor stub
		this.radius = 0;
		this.x = x;
		this.y = y;
		ping = new Circle(x, y, radius);
		expired = false;
	}
	
	public void draw(Graphics g) {
		g.setColor(Color.white);
		g.draw(ping);
	}
	
	public void update(int delta) {
		radius += delta * VELOCITY;
		ping.setRadius(radius);
		ping.setCenterX(x);
		ping.setCenterY(y);
		if (radius > 5000) {
			expired = true;
		}
	}

	public boolean getExpired() {
		return expired;
	}
	
	public Circle getPing() {
		return ping;
	}
}
