package game;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class Game extends BasicGame {
	
	PlayerShip player;

	public Game(String title) {
		super(title);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void render(GameContainer container, Graphics g) throws SlickException {
		// TODO Auto-generated method stub
		g.setAntiAlias(true);
		player.draw(g);
	}

	@Override
	public void init(GameContainer container) throws SlickException {
		// TODO Auto-generated method stub
		player = new PlayerShip();
	}

	@Override
	public void update(GameContainer container, int delta) throws SlickException {
		// TODO Auto-generated method stub
		player.update(container, delta);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Game game = new Game("Slower Than Light");
		AppGameContainer container;
		try {
			container = new AppGameContainer(game);
			container.setDisplayMode(1600, 800, false);
			container.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

}
