package game;

import org.newdawn.slick.Graphics;

public class ReturnPing extends Ping {
	
	private EnemyShip ship;
	private PlayerShip player;
	private Beacon beacon;
	private float angle;

	public ReturnPing(float x, float y, EnemyShip ship, PlayerShip player) {
		// TODO Auto-generated constructor stub
		super(x, y);
		this.ship = ship;
		this.angle = ship.getAngle();
		this.player = player;
	}
	
	public ReturnPing(float x, float y, EnemyShip ship, Beacon beacon) {
		super(x, y);
		this.ship = ship;
		this.angle = ship.getAngle();
		this.beacon = beacon;
	}
	
	public void draw(Graphics g) {
		g.setColor(ship.getColor());
		g.draw(ping);
	}
	
	public void update(int delta) {
		super.update(delta);
		if (player != null) {
			if (ping.intersects(player.getBox())) {
				player.updateLocation(ship, x, y, angle);
				expired = true;
			}
		} else {
			if (ping.intersects(beacon.getCircle())) {
				beacon.updateLocation(ship, x, y, angle);
				expired = true;
			}
		}
	}

}
