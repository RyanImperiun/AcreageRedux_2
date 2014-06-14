package Level;

import java.awt.Color;
import java.awt.Graphics;

import Core.Game;
import Core.GameResourceLoader;

public class PlowedTile extends Tile {

	boolean	hasPlants		= false;
	int		plantGrowth		= 0;
	int		growthTime		= 4;
	int		plantGrowTicks	= 0;

	public PlowedTile(int x, int y, Game game) {
		tileID = GameResourceLoader.Plowed;
		this.game = game;
		oX = x;
		oY = y;

		setTileBoundaries(x, y, tileSize, tileSize);
	}

	public void tick(Game game) {
		x = oX - game.xOffset; // Current x after movement, Offset, etc
		y = oY - game.yOffset; // Current y after movement, Offset, etc
		tileBoundaries.setBounds(x, y, tileSize, tileSize);
		setTilePos();

		// If tile contains mouse
		if (tileBoundaries.contains(game.mouseP)) {
			containsMouse = true;
		} else {
			containsMouse = false;
		}

		plantGrowth();
		Visibility();
	}

	private void plantGrowth() {
		if (hasPlants && plantGrowth < growthTime) {
			if (plantGrowTicks / 120 == 1) {
				plantGrowth++;
				System.out.println("Plant Ticked");
			}
			plantGrowTicks++;
		} else if (!hasPlants) {
			plantGrowth = 0;
			plantGrowTicks = 0;
		}
	}

	public void render(Graphics g) {
		if (hasPlants) {
			g.drawImage(game.getRes().plants[plantGrowth], x, y, game);
		} else if (!hasPlants) {
			g.drawImage(game.getRes().tiles[tileID], x, y, game);
		}

		if (game.showGrid) { // If the player wants to draw grids
			g.setColor(Color.WHITE); // White color
			g.drawRect(x, y, tileSize - 1, tileSize - 1); // Draw a border around tile
		}

		if (containsMouse) { // If it is allowed to show borders
			g.setColor(Color.BLACK); // Black color
			g.drawRect(x, y, tileSize - 1, tileSize - 1); // Draw a border around image
		}
	}

	@Override
	public void onLeftClick() {
		if (!hasPlants) {
			hasPlants = true;
			game.getInv().resourceAmounts[game.getInv().seedID] -= 1;
		} else if (hasPlants && plantGrowth == growthTime) {
			game.getInv().addResource(game.getInv().wheatID, 1);
			hasPlants = false;
			plantGrowth = 0;
		}
	}

	@Override
	public void onRightClick() {
	}

}
