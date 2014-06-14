package Entities;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

import Core.Game;
import Core.Launcher;

public class Player {
	Game					game;

	private int				x, y;
	private int				worldX, worldY;
	private int				tileX, tileY;
	private int				width			= 32;
	private int				height			= 32;

	public Rectangle		bounding;
	double					speed			= 2;

	public boolean			canAffectTile	= true;
	public Point			playerCenter;

	// Tools
	public static int		toolSelected	= 1;	// 1 = Axe, 2 = Pickaxe, 3 = Hoe, 4 = Shovel, 5 = Hand
	public static final int	Axe				= 1;
	public static final int	Pickaxe			= 2;
	public static final int	Hoe				= 3;
	public static final int	Shovel			= 4;
	public static final int	Hand			= 5;

	// Moving
	public int				directionFacing	= 0;	// 0 = Down, 1 = Up, 2 = Left, 3 = Right
	public int				Down			= 0;
	public int				Up				= 1;
	public int				Left			= 2;
	public int				Right			= 3;

	// Collision Booleans
	public boolean			canLeft			= true;
	public boolean			canRight		= true;
	public boolean			canUp			= true;
	public boolean			canDown			= true;

	public Player(Game game) {
		this.game = game;
		x = game.getWidth() / 2 - width / 2;
		y = game.getHeight() / 2 - height / 2;
		playerCenter = new Point(getWorldX() - width / 2, getWorldY() - height / 2);

		bounding = new Rectangle(x, y, width, height);
		bounding.setBounds(x, y, width, height);
	}

	public void tick() {
		bounding.setBounds(x, y, width, height);
		speed = 2 * game.delta;

		movement();
		worldEdgeCollision();

		setWorldX(x + game.xOffset);
		setWorldY(y + game.yOffset);
		setTileX(getWorldX() / 32);
		setTileY(getWorldY() / 32);
	}

	private void movement() {
		if (game.getInput().down.down) {
			if (canDown)
				game.yOffset += speed;
			directionFacing = Down;
		}
		if (game.getInput().up.down) {
			if (canUp)
				game.yOffset -= speed;
			directionFacing = Up;
		}
		if (game.getInput().left.down) {
			if (canLeft)
				game.xOffset -= speed;
			directionFacing = Left;
		}
		if (game.getInput().right.down) {
			if (canRight)
				game.xOffset += speed;
			directionFacing = Right;
		}
	}

	private void worldEdgeCollision() {
		if (x <= 0 - game.xOffset) {
			canLeft = false;
		} else {
			canLeft = true;
		}

		if (x + width >= (Launcher.worldSize * 32) - game.xOffset) {
			canRight = false;
		} else {
			canRight = true;
		}

		if (y <= 0 - game.yOffset) {
			canUp = false;
		} else {
			canUp = true;
		}

		if (y + height >= (Launcher.worldSize * 32) - game.yOffset) {
			canDown = false;
		} else {
			canDown = true;
		}
	}

	public void render(Graphics g) {
		g.drawImage(game.getRes().playerFaces[directionFacing], x, y, game);
	}

	// Getters and Setters

	public int getTileX() {
		return tileX;
	}

	public void setTileX(int tileX) {
		this.tileX = tileX;
	}

	public int getTileY() {
		return tileY;
	}

	public void setTileY(int tileY) {
		this.tileY = tileY;
	}

	public int getWorldY() {
		return worldY;
	}

	public void setWorldY(int worldY) {
		this.worldY = worldY;
	}

	public int getWorldX() {
		return worldX;
	}

	public void setWorldX(int worldX) {
		this.worldX = worldX;
	}
}