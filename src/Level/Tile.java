package Level;

import java.awt.Graphics;
import java.awt.Rectangle;

import Core.Game;

public abstract class Tile {
	/*
	 * Math to find tile in a single-dimensional array
	 * 
	 * tileArray[tileY*worldWidth] + tileX
	 */

	protected Rectangle	tileBoundaries;
	boolean containsMouse;

	Game				game;
	protected int		x, y, tileX, tileY;
	public int			tileID;
	protected int		oX, oY;				// Original x,y coordinates when created
	protected final int	tileSize	= 32;		// Size of tiles

	public boolean		hasTree		= false;	// Grass tile
	public boolean		hasRock		= false;	// Stone tile
	public boolean		hasOre		= false;	// Stone tile
	protected int		oreAmount	= 0;		// Amount of Ore, if any within Vein

	// Misc
	public boolean		isVisible;				// If the tile is within the JFrame area

	public abstract void tick(Game game);

	public abstract void render(Graphics g);

	public abstract void onLeftClick();
	public abstract void onRightClick();

	protected void Visibility() { // Check if the tile is within the JFrame area
		if (x >= 0 - 32 && x <= game.getWidth() + 32 && y >= 0 - 32 && y <= game.getHeight() + 32) {
			isVisible = true;
		} else {
			isVisible = false;
		}
	}

	public void editTile(int tilePositionInArray, Tile newTile) {
		game.getLevel().tiles[tilePositionInArray] = newTile;
	}

	protected void setTilePos() {
		tileX = x / 32;
		tileY = y / 32;
	}

	// Getters and Setters

	public int getTileID() {
		return tileID;
	}

	public void setTileID(int tileID) {
		this.tileID = tileID;
	}

	public int getoX() {
		return oX;
	}

	public void setoX(int oX) {
		this.oX = oX;
	}

	public int getoY() {
		return oY;
	}

	public void setoY(int oY) {
		this.oY = oY;
	}

	public int getTileSize() {
		return tileSize;
	}

	public Rectangle getTileBoundaries() {
		return tileBoundaries;
	}

	public void setTileBoundaries(int x, int y, int width, int height) {
		this.tileBoundaries = new Rectangle(x, y, width, height);
	}
}
