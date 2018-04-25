package game;

import java.util.HashMap;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Transform;

public class Packet {
	
	private final static float VELOCITY = 0.5f;
	private float x;
	private float y;
	private double angle;
	private PlayerShip player;
	private Rectangle image;
	private HashMap<EnemyShip, float[]> enemyLocs;
	private boolean expired;

	public Packet(float x, float y, double angle, PlayerShip player, HashMap<EnemyShip, float[]> currentLocs) {
		// TODO Auto-generated constructor stub
		this.x = x;
		this.y = y;
		this.angle = angle;
		this.player = player;
		enemyLocs = new HashMap<EnemyShip, float[]>();
		enemyLocs.putAll(currentLocs);
		expired = false;
		image = new Rectangle(x, y, 5, 2);
	}

	public void draw(Graphics g) {
		Shape temp = image.transform(Transform.createRotateTransform((float)Math.toRadians(angle), image.getCenterX(), image.getCenterY()));
		if (enemyLocs.size() > 0) {
			g.setColor(Color.red);
		}
		g.draw(temp);
	}
	
	public void update(int delta) {
		float xDistance = player.getX() - x;
		float yDistance = player.getY() - y;
		angle = Math.toDegrees(Math.atan2(yDistance, xDistance));
		
		float vel = VELOCITY * delta;
		float xvel = (float) (vel * Math.cos(Math.toRadians(angle)));
		float yvel = (float) (vel * Math.sin(Math.toRadians(angle)));
		x += xvel;
		y += yvel;
		image.setLocation(x, y);
		
		if (image.intersects(player.getBox())) {
			player.updateLocations(enemyLocs);
			expired = true;
		}
	}
	
	public boolean getExpired() {
		return expired;
	}
}
