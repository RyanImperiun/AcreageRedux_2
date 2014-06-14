package Core;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import FileManagement.Reading;
import FileManagement.Writing;

public class Launcher extends JFrame implements ActionListener, ItemListener {
	private static final long	serialVersionUID	= 1L;

	// Path to load game from, if chosen
	// private String filePath;
	public static Reading		readFile;

	// For the frame
	int							screenWidth			= 275;
	int							screenHeight		= 220;

	// Buttons
	private JButton				newGame, loadGame, exit;
	String[]					buttonTexts			= { "Start New Game", "Load Previous Game", "Exit" };

	// Button details
	int							buttonWidth			= 150;
	int							buttonHeight		= 40;

	// Selecting world dimensions
	JComboBox<?>				selectDimensions;
	String[]					possibleDimensions	= { "100x100", "200x200", "300x300", "400x400", "500x500" };
	public static int			worldSize			= 100;

	public Launcher() {
		// Set layout to null, so each button can be placed arbitrarily (chosen coords)
		getContentPane().setLayout(null);

		// Calling methods for buttons
		addButtons();

		// JFrame Stuff
		pack();
		setVisible(true);
		setLocationRelativeTo(null);
		setSize(screenWidth, screenHeight);
		setTitle("Acreage Menu");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
	}

	private void addButtons() {
		newGame = new JButton("Start New Game");
		loadGame = new JButton("Load Previous Game");
		exit = new JButton("Exit");
		selectDimensions = new JComboBox<Object>(possibleDimensions);

		// Setting Bounds and Placing Buttons
		selectDimensions.setBounds((screenWidth - buttonWidth) / 2, 5, buttonWidth, buttonHeight);
		newGame.setBounds((screenWidth - buttonWidth) / 2, 50, buttonWidth, buttonHeight);
		loadGame.setBounds((screenWidth - buttonWidth) / 2, 95, buttonWidth, buttonHeight);
		exit.setBounds((screenWidth - buttonWidth) / 2, 140, buttonWidth, buttonHeight);

		// Adding Buttons to Frame
		getContentPane().add(selectDimensions);
		getContentPane().add(newGame);
		getContentPane().add(loadGame);
		getContentPane().add(exit);

		selectDimensions.addItemListener(this);
		newGame.addActionListener(this);
		loadGame.addActionListener(this);
		exit.addActionListener(this);
	}

	public void itemStateChanged(ItemEvent e) {
		if (e.getStateChange() == ItemEvent.SELECTED) {
			updateMapSize();
		}
	}

	private void updateMapSize() {
		switch (selectDimensions.getSelectedIndex()) {
		case 0:
			worldSize = 100;
			break;
		case 1:
			worldSize = 200;
			break;
		case 2:
			worldSize = 300;
			break;
		case 3:
			worldSize = 400;
			break;
		case 4:
			worldSize = 500;
			break;
		}
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == newGame) {
			Game g = new Game();
			DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date date = new Date();
			Writing.outputToDebugFile(df.format(date) + "----------");
			g.getLevel().generateLevel();
			g.start();
			dispose();
		} else if (e.getSource() == loadGame) {
			String home = System.getProperty("user.home");
			readFile = new Reading(home + "/Desktop/temp.txt");
			Game g = new Game();
			g.getLevel().generateLevel(Launcher.readFile.fileTileID, Launcher.readFile.fileTilePeripherals);
			g.start();
			dispose();
		} else if (e.getSource() == exit) {
			System.exit(0);
		}
	}

	public static void main(String[] args) {
		// Set a UI look/feel to the JButton
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

		new Launcher();
	}
}