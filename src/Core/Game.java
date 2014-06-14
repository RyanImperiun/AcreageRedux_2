package Core;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import Entities.Player;
import FileManagement.Writing;
import Level.LevelClass;

public class Game extends Canvas implements Runnable {
	private static final long	serialVersionUID	= 1L;

	private Thread				AcreageThread;

	private InputHandler		input;
	private GameResourceLoader	res;
	private LevelClass			level;
	private Player				player;
	private Inventory			inv;
	private Debug				debug;
	private Writing				write;

	public Point				mouseP				= new Point(-1, -1);

	public boolean				running				= false;
	private static final String	TITLE				= "Acreage In-Dev 0.0.9";
	private final int			WIDTH				= 640;
	private final int			HEIGHT				= 480;
	BufferedImage				image				= new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	private final Dimension		gameDim				= new Dimension(WIDTH, HEIGHT);
	JFrame						frame;

	public int					xOffset				= 0;
	public int					yOffset				= 0;

	// Variables for the FPS and UPS counter
	private int					ticks				= 0;
	private int					frames				= 0;
	private int					FPS					= 0;
	private int					UPS					= 0;
	public double				delta;

	// Options
	boolean						showDebug			= false;
	public boolean				showGrid			= false;

	// Used in the "run" method to limit the frame rate to the UPS
	boolean						limitFrameRate		= false;
	boolean						shouldRender;

	@Override
	public void run() {
		long lastTime = System.nanoTime();
		double nsPerTick = 1000000000D / 60D;

		long lastTimer = System.currentTimeMillis();
		delta = 0D;

		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / nsPerTick;
			lastTime = now;

			// If you want to limit frame rate, shouldRender = false
			shouldRender = false;

			// If the time between ticks = 1, then various things (shouldRender = true, keeps FPS locked at UPS)
			while (delta >= 1) {
				ticks++;
				tick();
				delta -= 1;
				shouldRender = true;
			}
			if (!limitFrameRate && ticks > 0)
				shouldRender = true;

			// If you should render, render!
			if (shouldRender) {
				frames++;
				render();
			}

			// Reset stuff every second for the new "FPS" and "UPS"
			if (System.currentTimeMillis() - lastTimer >= 1000) {
				lastTimer += 1000;
				FPS = frames;
				UPS = ticks;
				frames = 0;
				ticks = 0;
				frame.setTitle(TITLE + " FPS: " + FPS + " UPS: " + UPS);
			}
		}
	}

	public synchronized void start() {
		running = true;
		AcreageThread = new Thread(this);
		AcreageThread.start();
	}

	public synchronized void stop() {
		running = false;
		try {
			AcreageThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.exit(0);
	}

	public Game() { // Typical stuff
		createFrame();
		requestFocus();

		init();
	}

	private void createFrame() {
		setMinimumSize(gameDim);
		setMaximumSize(gameDim);
		setPreferredSize(gameDim);
		frame = new JFrame(TITLE + " FPS: " + FPS + " UPS: " + UPS);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());

		frame.add(this, BorderLayout.CENTER);
		frame.pack();

		frame.setVisible(true);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);

		frame.addWindowListener(new WindowListener() {

			public void windowActivated(WindowEvent arg0) {
			}

			public void windowClosed(WindowEvent arg0) {
			}

			public void windowClosing(WindowEvent e) {
				getWrite().saveAllFiles();
			}

			public void windowDeactivated(WindowEvent arg0) {
			}

			public void windowDeiconified(WindowEvent arg0) {
			}

			public void windowIconified(WindowEvent arg0) {
			}

			public void windowOpened(WindowEvent arg0) {
			}
		});
	}

	private void init() {
		setRes(new GameResourceLoader());
		setInput(new InputHandler(this));
		setWrite(new Writing(this));
		setLevel(new LevelClass(this));
		setPlayer(new Player(this));
		setInv(new Inventory(this));
		debug = new Debug(this);

	}

	public void tick() {
		getPlayer().tick();
		getLevel().updateLevel(this);
		getInv().tick();
	}

	public void render() {
		BufferStrategy bs = getBufferStrategy();

		if (bs == null) {
			createBufferStrategy(3);
			return;
		}

		Graphics g = bs.getDrawGraphics();

		g.drawImage(image, 0, 0, getWidth(), getHeight(), this);

		getLevel().renderLevel(g);
		getPlayer().render(g);
		getInv().render(g);

		if (showDebug)
			debug.render(g);

		g.setColor(Color.WHITE);
		g.fillRect(103, 0, 160, 33);
		g.drawImage(getRes().toolMap, 103, 0, this);
		g.setColor(Color.BLACK);
		g.drawRect((Player.toolSelected * 32) + 71, 0, 32, 32);
		// Draw tool selected at mouse location
		g.drawImage(getRes().tools[Player.toolSelected - 1], mouseP.x, mouseP.y, this);

		g.dispose();
		bs.show();
	}

	// Getters and Setters
	// Do not venture past this point!

	public InputHandler getInput() {
		return input;
	}

	public void setInput(InputHandler input) {
		this.input = input;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public Inventory getInv() {
		return inv;
	}

	public void setInv(Inventory inv) {
		this.inv = inv;
	}

	public GameResourceLoader getRes() {
		return res;
	}

	public void setRes(GameResourceLoader res) {
		this.res = res;
	}

	public LevelClass getLevel() {
		return level;
	}

	public void setLevel(LevelClass level) {
		this.level = level;
	}

	public Writing getWrite() {
		return write;
	}

	public void setWrite(Writing write) {
		this.write = write;
	}
}