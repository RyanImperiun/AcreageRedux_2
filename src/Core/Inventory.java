package Core;

import java.awt.Color;
import java.awt.Graphics;

public class Inventory {
	Game		game;
	public int	itemSelected		= 0;

	public int	seedID				= 0;
	public int	wheatID				= 1;
	public int	stoneID				= 2;
	public int	stickID				= 3;
	public int	lumberID			= 4;
	public int	oreID				= 5;
	
	public int	resourceAmounts[]	= { 100, 0, 0, 0, 0, 0 };
	String		resourceNames[]		= { "Seed", "Wheat", "Stone", "Sticks", "Lumber", "Ore" };

	public Inventory(Game game) {
		this.game = game;
	}

	public void tick() {
	}

	public void addResource(int resourceID, int amount) {
		resourceAmounts[resourceID] += amount;
	}

	public void render(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(game.getWidth() - 100, game.getHeight() - 123, 100, 135);

		g.setColor(Color.BLACK);
		for (byte i = 0; i < resourceNames.length; i++) {
			g.drawString(resourceNames[i] + ": " + resourceAmounts[i], game.getWidth() - 100, i * 10 + game.getHeight() - 134 + 21);
		}

		g.drawRect(game.getWidth() - 100, itemSelected * 10 + game.getHeight() - 134 + 11, 100, 10);
	}
}