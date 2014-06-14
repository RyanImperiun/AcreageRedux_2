package Level;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import Core.Game;
import Core.GameResourceLoader;
import Core.Launcher;
import Entities.Player;

public class GrassTile extends Tile {
	public GrassTile(int x, int y, Game game) {
		tileID = GameResourceLoader.Grass;
		this.game = game;
		oX = x;
		oY = y;

		setTileBoundaries(x, y, tileSize, tileSize);

		switch (new Random().nextInt(2)) {
		case 0:
			hasTree = true;
			break;
		case 1:
			hasTree = false;
			break;
		}
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
		g.drawImage(game.getRes().tiles[GameResourceLoader.Grass], x, y, game);
		if (hasTree)
			g.drawImage(game.getRes().tiles[GameResourceLoader.Tree], x, y, game);

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
		if (hasTree && Player.toolSelected == Player.Hand) {
			game.getInv().addResource(game.getInv().stickID, 1);
		} else if (hasTree && Player.toolSelected == Player.Axe) {
			game.getInv().addResource(game.getInv().lumberID, 1);
			hasTree = false;
		}
	}

	@Override
	public void onRightClick() {
		if (!hasTree) {
			if (Player.toolSelected == Player.Shovel)
				editTile((tileY * Launcher.worldSize) + tileX, new DirtTile(x, y, game));
			if (Player.toolSelected == Player.Hoe)
				editTile((tileY * Launcher.worldSize) + tileX, new PlowedTile(x, y, game));
		} else {
			editTile((tileY * Launcher.worldSize) + tileX, new DirtTile(oX, oY, game));
			if (Player.toolSelected == Player.Hoe)
				editTile((tileY * Launcher.worldSize) + tileX, new PlowedTile(oX, oY, game));
		}
	}
}
