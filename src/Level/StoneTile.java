package Level;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import Core.Game;
import Core.GameResourceLoader;
import Core.Launcher;
import Entities.Player;

public class StoneTile extends Tile {
	public StoneTile(int x, int y, Game game) {
		tileID = GameResourceLoader.Stone;
		this.game = game;
		oX = x;
		oY = y;

		setTileBoundaries(x, y, tileSize, tileSize);

		generateRockOre();
	}

	private void generateRockOre() {
		switch (new Random().nextInt(2)) { // Switch a random int, 0 or 1 for a Rock
		case 0:
			hasRock = true;
			break;
		case 1:
			hasRock = false;
			break;
		}

		if (hasRock) {
			switch (new Random().nextInt(2)) { // Switch a random int, 0 or 1 for Ore
			case 0:
				hasOre = true;
				break;
			case 1:
				hasOre = false;
				break;
			}
		}

		if (hasOre) {
			oreAmount = new Random().nextInt(5);
		}
	} // End Generate RockOre

	public void tick(Game game) {
		x = oX - game.xOffset; // Current x after movement, Offset, etc
		y = oY - game.yOffset; // Current y after movement, Offset, etc
		tileBoundaries.setBounds(x, y, tileSize, tileSize);
		setTilePos();

		if (oreAmount <= 0) {
			hasOre = false;
		}

		// If tile contains mouse
		if (tileBoundaries.contains(game.mouseP)) {
			containsMouse = true;
		} else {
			containsMouse = false;
		}

		if (oreAmount <= 0) {
			hasOre = false;
		}
		Visibility();
	}

	@Override
	public void render(Graphics g) {
		if (hasRock && !hasOre) {
			g.drawImage(game.getRes().tiles[GameResourceLoader.Rock], x, y, game);
		} else if (hasRock && hasOre) {
			g.drawImage(game.getRes().tiles[GameResourceLoader.Metal], x, y, game);
		} else {
			g.drawImage(game.getRes().tiles[GameResourceLoader.Stone], x, y, game);
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
		if (hasRock && Player.toolSelected == Player.Pickaxe && !hasOre) {
			game.getInv().addResource(game.getInv().stoneID, 1);
			hasRock = false;
		} else if (hasOre && Player.toolSelected == Player.Pickaxe) {
			game.getInv().addResource(game.getInv().oreID, 1);
			oreAmount--;
		}
	}

	@Override
	public void onRightClick() {
		if (Player.toolSelected == Player.Pickaxe) {
			if (!hasRock)
				editTile((tileY * Launcher.worldSize) + tileX, new DirtTile(x, y, game));
		}
	}
}
