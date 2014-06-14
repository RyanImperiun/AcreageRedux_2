package Level;

import java.awt.Graphics;
import java.util.Random;

import Core.Game;
import Core.Launcher;
import FileManagement.Writing;

public class LevelClass {
	Game				game;
	public Tile	tiles[];
	int					totalWorldTiles;

	public LevelClass(Game game) {
		this.game = game;
		totalWorldTiles = Launcher.worldSize * Launcher.worldSize;
		tiles = new Tile[totalWorldTiles];
	} // End constructor

	private void determinePeripherals(Tile t, int periphID) {
		switch (periphID) {
		case 0:
			t.hasTree = false;
			t.hasRock = false;
			t.hasOre = false;
			break;
		case 1:
			t.hasTree = false;
			t.hasRock = true;
			t.hasOre = false;
			break;
		case 2:
			t.hasTree = false;
			t.hasRock = true;
			t.hasOre = true;
			break;
		case 3:
			t.hasTree = true;
			t.hasRock = false;
			t.hasOre = false;
			break;
		}
	}

	public void generateLevel(int[] tileIDs, int[] tilePeriph) {
		int x = 0, y = 0;

		for (int i = 0; i < totalWorldTiles; i++) {
			switch (tileIDs[i]) { // Random int from 0 - 2, use as tileID
			case 0:
				tiles[i] = new DirtTile(x * 32, y * 32, game); // Place dirt
				break;
			case 2:
				tiles[i] = new StoneTile(x * 32, y * 32, game); // Place stone
				determinePeripherals(tiles[i], tilePeriph[i]);
				break;
			case 3:
				tiles[i] = new GrassTile(x * 32, y * 32, game); // Place grass
				determinePeripherals(tiles[i], tilePeriph[i]);
				break;
			default:
				tiles[i] = new GrassTile(x * 32, y * 32, game); // Place grass
				determinePeripherals(tiles[i], tilePeriph[i]);
				break;
			}
			x++;
			if (x == Launcher.worldSize) {
				x = 0;
				y++;
			}
		}
	}

	public void generateLevel() {
		// Tile generator

		long beginTime = System.currentTimeMillis();

		int x = 0, y = 0;

		for (int i = 0; i < totalWorldTiles; i++) {
			switch (new Random().nextInt(3)) { // Random int from 0 - 2, use as tileID
			case 0:
				tiles[i] = new DirtTile(x * 32, y * 32, game); // Place dirt
				break;
			case 1:
				tiles[i] = new StoneTile(x * 32, y * 32, game); // Place stone
				break;
			case 2:
				tiles[i] = new GrassTile(x * 32, y * 32, game); // Place grass
				break;
			default:
				tiles[i] = new GrassTile(x * 32, y * 32, game); // Place grass
				break;
			}
			x++;
			if (x == Launcher.worldSize) {
				x = 0;
				y++;
			}
		}

		long endTime = System.currentTimeMillis();
		long resultTime = endTime - beginTime;
		Writing.outputToDebugFile("It took " + resultTime + "ms to generate the level!");
	} // End generateLevel

	public void updateLevel(Game game) {

		for (Tile t : tiles) {
			t.tick(game);

			if (game.getInput().rightButton && t.containsMouse)
				t.onRightClick();

			if (game.getInput().leftButton && t.containsMouse)
				t.onLeftClick();
		}
		game.getInput().leftButton = false;
		game.getInput().rightButton = false;
	} // End update

	public void renderLevel(Graphics g) {
		// Tile loops
		for (Tile t : tiles) {
			if (t.isVisible && t != null)
				t.render(g);
		}
	} // End render
}
