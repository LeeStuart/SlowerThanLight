package game;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Transform;

public class Ship {
	
	protected static final float[] FORWARD_VELOCITY = {0.01f, 0.02f, 0.04f, 0.08f, 0.1f, 0.12f, 0.16f};
	protected static final float[] ROTATION_VELOCITY = {0.005f, 0.01f, 0.02f, 0.04f, 0.05f, 0.06f, 0.08f};
	protected static int setting;
	protected Rectangle box;
	protected Color color;
	protected float oldx;
	protected float oldy;
	protected float x;
	protected float y;
	protected ArrayList<float[]> lines;
	protected double angle;

	public Ship(float x, float y, float angle) {
		// TODO Auto-generated constructor stub
		box = new Rectangle(x, y, 10, 25);
		color = Color.white;
		this.x = x;
		this.y = y;
		oldx = x;
		oldy = y;
		lines = new ArrayList<float[]>();
		this.angle = angle;
		setting = 0;
	}
	
	public void draw(Graphics g) {
		g.setColor(color);
		Shape temp = box.transform(Transform.createRotateTransform((float)Math.toRadians(angle), box.getCenterX(), box.getCenterY()));
		g.draw(temp);
		for (float[] f : lines) {
			g.drawLine(f[0], f[1], f[2], f[3]);
		}
	}
	
	public void update(int delta, float rotvel, float fwdvel) {
		oldx = x;
		oldy = y;
		angle += delta * ROTATION_VELOCITY[setting] * rotvel;
		float vel = FORWARD_VELOCITY[setting] * delta * fwdvel;
		float xvel = (float) (vel * Math.cos(Math.toRadians(angle - 90)));
		float yvel = (float) (vel * Math.sin(Math.toRadians(angle - 90)));
		x += xvel;
		y += yvel;
		float[] temp = {oldx, oldy, x, y};
		lines.add(temp);
		box.setLocation(x - box.getWidth() / 2, y - box.getHeight() / 2);
	}
	
	public Rectangle getBox() {
		return box;
	}
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
	public Color getColor() {
		return color;
	}
	
	public float getAngle() {
		return (float)angle;
	}

}
