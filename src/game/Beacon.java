package game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Circle;

public class Beacon {
	
	private PlayerShip sender;
	private float x;
	private float y;
	private Circle image;
	private ArrayList<PlayerPing> sentpings;
	private ArrayList<Packet> packets;
	private HashMap<EnemyShip, float[]> enemyLocs;
	private float cooldown;

	public Beacon(PlayerShip sender, float x, float y) {
		this.x = x;
		this.y = y;
		this.sender = sender;
		image = new Circle(x, y, 2);
		sentpings = new ArrayList<PlayerPing>();
		packets = new ArrayList<Packet>();
		enemyLocs = new HashMap<EnemyShip, float[]>();
	}
	
	public void draw(Graphics g, boolean visiblePings, boolean visibleReturnPings) {
		for (PlayerPing p : sentpings) {
			p.draw(g, visiblePings, visibleReturnPings);
		}
		for (Packet p : packets) {
			p.draw(g);
		}
		g.fill(image);
	}
	
	public void update(int delta, ArrayList<EnemyShip> enemies) {
		float xDistance = sender.getX() - x;
		float yDistance = sender.getY() - y;
		double angle = Math.toDegrees(Math.atan2(yDistance, xDistance));
		
		if (cooldown < 0) {
			sentpings.add(new PlayerPing(x, y, this));
			packets.add(new Packet(x, y, angle, sender, enemyLocs));
			cooldown = 500;
		} else {
			cooldown -= delta;
		}
		
		Iterator<Packet> pitr = packets.iterator();
		while (pitr.hasNext()) {
			Packet p = pitr.next();
			p.update(delta);
			if (p.getExpired()) {
				pitr.remove();
			}
		}
		
		Iterator<PlayerPing> itr = sentpings.iterator();
		while (itr.hasNext()) {
			PlayerPing p = itr.next();
			p.update(delta, enemies);
			if (p.getExpired()) {
				itr.remove();
			}
		}
	}
	
	public void updateLocation(EnemyShip e, float x, float y, float angle) {
		float[] temp = {x, y, angle};
		enemyLocs.put(e, temp);
	}
	
	public Circle getCircle() {
		return image;
	}

}
