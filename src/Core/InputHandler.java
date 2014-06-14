package Core;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;
import java.util.List;

import Entities.Player;

public class InputHandler implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener {

	public class Key {

		public boolean	down, clicked;
		private short	absorbs, presses;

		public void tick() {
			if (absorbs < presses) {
				absorbs++;
				clicked = true;
			} else {
				clicked = false;
			}
		}

		public void toggle(boolean pressed) {
			if (pressed != down) {
				down = pressed;
			}

			if (pressed) {
				presses++;
			}
		}

		public Key() {
			keys.add(this);
		}
	}

	Game				game;
	Point				mouseP;

	public List<Key>	keys		= new ArrayList<Key>();

	public Key			left		= new Key();
	public Key			right		= new Key();
	public Key			up			= new Key();
	public Key			down		= new Key();
	public Key			I			= new Key();

	public Key			itemUp		= new Key();
	public Key			itemDown	= new Key();

	public boolean		leftButton	= false;
	public boolean		rightButton	= false;

	public InputHandler(Game game) {
		game.addMouseListener(this);
		game.addMouseMotionListener(this);
		game.addMouseWheelListener(this);
		game.addKeyListener(this);
		this.game = game;
	}

	public void mouseClicked(MouseEvent e) {

	}

	public void mouseEntered(MouseEvent e) {

	}

	public void mouseExited(MouseEvent e) {

	}

	public void mousePressed(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) { // If left button pressed
			leftButton = true;
		}
		if (e.getButton() == MouseEvent.BUTTON3) { // If right button pressed
			rightButton = true;
		}
	}

	public void mouseReleased(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) { // If left button pressed
			leftButton = false;
		}
		if (e.getButton() == MouseEvent.BUTTON3) { // If right button pressed
			rightButton = false;
		}
	}

	public void mouseDragged(MouseEvent e) {
		game.mouseP = e.getPoint();

		if (e.getButton() == MouseEvent.BUTTON1) { // If left button pressed
			leftButton = true;
		}
		if (e.getButton() == MouseEvent.BUTTON3) { // If right button pressed
			rightButton = true;
		}
	}

	public void mouseMoved(MouseEvent e) {
		game.mouseP = e.getPoint(); // Grab mouse position

	}

	public void mouseWheelMoved(MouseWheelEvent e) {
		if (e.getScrollType() == MouseWheelEvent.WHEEL_UNIT_SCROLL && e.getWheelRotation() > 0) { // If scrolling up
			if (game.getInv().itemSelected < game.getInv().resourceAmounts.length - 1) {
				game.getInv().itemSelected++;
			} else {
				game.getInv().itemSelected = 0;
			}
		} else if (e.getScrollType() == MouseWheelEvent.WHEEL_UNIT_SCROLL && e.getWheelRotation() < 0) { // If scrolling down
			if (game.getInv().itemSelected > 0) {
				game.getInv().itemSelected--;
			} else {
				game.getInv().itemSelected = game.getInv().resourceAmounts.length - 1;
			}
		}
	}

	public void tick() {
		for (int i = 0; i < keys.size(); i++) {
			keys.get(i).tick();
		}
	}

	public void releaseAll() {
		for (int i = 0; i < keys.size(); i++) {
			keys.get(i).down = false;
		}
	}

	public void toggle(KeyEvent e, boolean pressed) {
		// Movement
		if (e.getKeyCode() == KeyEvent.VK_A)
			left.toggle(pressed);
		if (e.getKeyCode() == KeyEvent.VK_D)
			right.toggle(pressed);
		if (e.getKeyCode() == KeyEvent.VK_W)
			up.toggle(pressed);
		if (e.getKeyCode() == KeyEvent.VK_S)
			down.toggle(pressed);
	}

	public void keyPressed(KeyEvent e) {
		toggle(e, true);

		// Misc
		if (e.getKeyCode() == KeyEvent.VK_END)
			System.exit(0);

		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			game.frame.dispose();
			game.getWrite().saveAllFiles();
			new Launcher();
		}

		// Select Tools
		if (e.getKeyCode() == KeyEvent.VK_1) {
			Player.toolSelected = Player.Axe;
		}
		if (e.getKeyCode() == KeyEvent.VK_2) {
			Player.toolSelected = Player.Pickaxe;
		}
		if (e.getKeyCode() == KeyEvent.VK_3) {
			Player.toolSelected = Player.Hoe;
		}
		if (e.getKeyCode() == KeyEvent.VK_4) {
			Player.toolSelected = Player.Shovel;
		}
		if (e.getKeyCode() == KeyEvent.VK_5) {
			Player.toolSelected = Player.Hand;
		}
	}

	public void keyReleased(KeyEvent e) {
		toggle(e, false);
	}

	public void keyTyped(KeyEvent e) {

	}
}