package game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Transform;

public class PlayerShip extends Ship {
	
	protected static final float[] FORWARD_VELOCITY = {0.02f, 0.03f, 0.05f, 0.09f, 0.11f, 0.13f, 0.17f};
	protected static final float[] ROTATION_VELOCITY = {0.02f, 0.03f, 0.05f, 0.09f, 0.11f, 0.13f, 0.17f};
	private ArrayList<EnemyShip> enemies;
	private ArrayList<PlayerPing> sentpings;
	private ArrayList<Beacon> beacons;
	private HashMap<EnemyShip, float[]> enemyLocs;
	private Random random;
	private float cooldown;
	private boolean visiblePings;
	private boolean visibleReturnPings;
	private boolean visibleShips;
	private boolean pinging;
	private boolean following;
	private int followingNo;
	
	public PlayerShip() {
		// TODO Auto-generated constructor stub
		super(320, 240, 10);
		random = new Random();
		enemies = new ArrayList<EnemyShip>();
		sentpings = new ArrayList<PlayerPing>();
		beacons = new ArrayList<Beacon>();
		enemyLocs = new HashMap<EnemyShip, float[]>();
		visiblePings = false;
		visibleReturnPings = false;
		visibleShips = false;
		pinging = false;
		following = false;
	}
	
	public void draw(Graphics g) {
		if (visibleShips) for (EnemyShip e: enemies) {
			e.draw(g);
		}
		for (EnemyShip key : enemyLocs.keySet()) {
			float tempx = enemyLocs.get(key)[0];
			float tempy = enemyLocs.get(key)[1];
			float tempangle = enemyLocs.get(key)[2];
			Shape temp = key.getBox();
			temp = temp.transform(Transform.createRotateTransform((float)Math.toRadians(tempangle), key.getX(), key.getY()));
			temp = temp.transform(Transform.createTranslateTransform(tempx - key.getX(), tempy - key.getY()));
			g.setColor(key.getColor());
			g.fill(temp);
		}
		for (Beacon b : beacons) {
			b.draw(g, visiblePings, visibleReturnPings);
		}
		for (PlayerPing p: sentpings) {
			p.draw(g, visiblePings, visibleReturnPings);
		}
		super.draw(g);
		Shape temp = box.transform(Transform.createRotateTransform((float)Math.toRadians(angle), box.getCenterX(), box.getCenterY()));
		g.fill(temp);
		g.drawString("" + FORWARD_VELOCITY[setting], x + 20, y + 20);
	}
	
	public void update(GameContainer container, int delta) {
		Input input = container.getInput();
		float fwdvel = 0;
		float rotvel = 0;
		if (input.isKeyDown(Input.KEY_W)) {
			fwdvel += 1;
		}
		if (input.isKeyDown(Input.KEY_S)) {
			fwdvel += -1;
		}
		if (input.isKeyDown(Input.KEY_A)) { 
			rotvel += -1;
		}
		if (input.isKeyDown(Input.KEY_D)) {
			rotvel += 1;
		}
		if (enemyLocs.size() > 0 && following) {
			EnemyShip[] temp = new EnemyShip[1];
			temp = enemyLocs.keySet().toArray(temp);
			float xDistance = enemyLocs.get(temp[followingNo])[0] - x;
			float yDistance = enemyLocs.get(temp[followingNo])[1] - y;
			double xangle = Math.toDegrees(Math.atan2(yDistance, xDistance));
			double angleDiff = (xangle - angle + 90) % 360;
			if (angleDiff >= 0 && angleDiff < 180) {
				rotvel = 1;
			} else {
				rotvel = -1;
			}
			if (Math.sqrt(xDistance * xDistance + yDistance * yDistance) > 100) {
				fwdvel = 1;
			}
		}
		super.update(delta, rotvel, fwdvel);
		
		for (EnemyShip e: enemies) {
			e.update(container, delta);
		}
		
		for (Beacon b : beacons) {
			b.update(delta, enemies);
		}
		
		Iterator<PlayerPing> itr = sentpings.iterator();
		while (itr.hasNext()) {
			PlayerPing p = itr.next();
			p.update(delta, enemies);
			if (p.getExpired()) {
				itr.remove();
			}
		}
		
		if (pinging) {
			if (cooldown < 0) {
				sentpings.add(new PlayerPing(x, y, this));
				cooldown = 500;
			} else {
				cooldown -= delta;
			}
		}
		
		if (input.isKeyPressed(Input.KEY_N)) {
			float ranx = random.nextInt(800);
			float rany = random.nextInt(600);
			float ranangle = random.nextInt(360);
			enemies.add(new EnemyShip(ranx, rany, ranangle));
		}
		
		if (input.isKeyPressed(Input.KEY_P)) {
			pinging = !pinging;
		}
		
		if (input.isKeyPressed(Input.KEY_1)) {
			visiblePings = !visiblePings;
		}
		
		if (input.isKeyPressed(Input.KEY_2)) {
			visibleReturnPings = !visibleReturnPings;
		}
		
		if (input.isKeyPressed(Input.KEY_3)) {
			visibleShips = !visibleShips;
		}
		
		if (input.isKeyPressed(Input.KEY_PERIOD) && setting != FORWARD_VELOCITY.length - 1) {
			setting++;
		}
		
		if (input.isKeyPressed(Input.KEY_COMMA)&& setting != 0) {
			setting--;
		}
		
		if (input.isKeyPressed(Input.KEY_F)) {
			following = !following;
			followingNo = 0;
		}
		
		if (input.isKeyPressed(Input.KEY_G)) {
			followingNo++;
			if (followingNo >= enemyLocs.size()) {
				followingNo = 0;
			}
		}
		
		if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)){
			x = input.getMouseX();
			y = input.getMouseY();
		}
		
		if (input.isMousePressed(Input.MOUSE_RIGHT_BUTTON)) {
			beacons.add(new Beacon(this, input.getMouseX(), input.getMouseY()));
		}
	}
	
	public void updateLocation(EnemyShip e, float x, float y, float angle) {
		float[] temp = {x, y, angle};
		enemyLocs.put(e, temp);
	}
	
	public void updateLocations(HashMap<EnemyShip, float[]> newLocs) {
		for (EnemyShip key : newLocs.keySet()) {
			enemyLocs.put(key, newLocs.get(key));
		}
	}

}
