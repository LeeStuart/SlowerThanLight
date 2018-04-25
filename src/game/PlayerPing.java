package game;

import java.util.ArrayList;
import java.util.Iterator;

import org.newdawn.slick.Graphics;

public class PlayerPing extends Ping {

	private ArrayList<ReturnPing> returnpings;
	private PlayerShip player;
	private Beacon beacon;
	
	public PlayerPing(float x, float y, PlayerShip player) {
		// TODO Auto-generated constructor stub
		super(x, y);
		returnpings = new ArrayList<ReturnPing>();
		this.player = player;
	}
	
	public PlayerPing(float x, float y, Beacon beacon) {
		super(x, y);
		returnpings = new ArrayList<ReturnPing>();
		this.beacon = beacon;
	}
	
	public void draw(Graphics g, boolean visiblePings, boolean visibleReturnPings) {
		if (visiblePings) super.draw(g);
		if (visibleReturnPings) for (ReturnPing p: returnpings) {
			p.draw(g);
		}
	}
	
	public void update(int delta, ArrayList<EnemyShip> enemies) {
		super.update(delta);
		for (EnemyShip e: enemies) {
			if (ping.intersects(e.getBox()) && !e.pingedby(this)) {
				e.pinged(this);
				if (player != null) {
					returnpings.add(new ReturnPing(e.getX(), e.getY(), e, player));
				} else {
					returnpings.add(new ReturnPing(e.getX(), e.getY(), e, beacon));
				}
			}
		}
		Iterator<ReturnPing> itr = returnpings.iterator();
		while (itr.hasNext()) {
			ReturnPing p = itr.next();
			p.update(delta);
			if (p.getExpired()) {
				itr.remove();
			}
		}
	}

}
