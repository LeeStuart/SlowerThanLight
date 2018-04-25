package game;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;

public class EnemyShip extends Ship {
	
	private ArrayList<PlayerPing> pingedby;
	private int cooldown;

	public EnemyShip(float x, float y, float angle) {
		// TODO Auto-generated constructor stub
		super(x, y, angle);
		Random random = new Random();
		color = new Color(random.nextFloat(), random.nextFloat(), random.nextFloat());
		pingedby = new ArrayList<PlayerPing>();
	}
	
	public void update(GameContainer container, int delta) {
		float fwdvel = 0;
		float rotvel = 0;
		
		fwdvel = 1;
		
		cooldown -= delta;
		if (cooldown < delta) {
			cooldown = 0;
			//pingedby.clear();
		}
		
		Iterator<PlayerPing> itr = pingedby.iterator();
		while (itr.hasNext()) {
			PlayerPing p = itr.next();
			if (p.getPing().contains(box)) {
				itr.remove();
			}
		}
		
		if (x < 100 || x > container.getWidth() - 100 || y < 100 || y > container.getHeight() - 100) {
			rotvel = 1;
		}
		
		super.update(delta, rotvel, fwdvel);
	}
	
	public void pinged(PlayerPing p) {
		pingedby.add(p);
	}
	
	public boolean pingedby(PlayerPing p) {
		if (pingedby.contains(p)) {
			return true;
		}
		return false;
	}

}
