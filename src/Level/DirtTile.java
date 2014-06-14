package Level;

import java.awt.Color;
import java.awt.Graphics;

import Core.Game;
import Core.GameResourceLoader;
import Core.Launcher;
import Entities.Player;

public class DirtTile extends Tile {

	public DirtTile(int x, int y, Game game) {
		tileID = GameResourceLoader.Dirt;
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

		Visibility();
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(game.getRes().tiles[tileID], x, y, game);

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
	}

	@Override
	public void onRightClick() {
		if (Player.toolSelected == Player.Hoe)
			editTile((tileY * Launcher.worldSize) + tileX, new PlowedTile(oX, oY, game));
	}
}
