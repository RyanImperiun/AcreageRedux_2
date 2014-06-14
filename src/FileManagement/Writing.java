package FileManagement;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import Core.Game;
import Core.GameResourceLoader;
import Core.Launcher;

public class Writing {
	static String	home	= System.getProperty("user.home");
	static String	dir		= home + "/Desktop";
	Game			g;

	public Writing(Game game) {
		g = game;

		// The name of the file to open. --- C:/Users/Ryan/Desktop/temp2.txt --
	}

	public void saveAllFiles() {
		saveResources();
		saveWorldPeripherals();
		saveWorld();
	}

	public static void outputToDebugFile(String output) {
		String writePath = dir + "/debug.txt";

		try {
			// Assume default encoding.
			FileWriter fileWriter = new FileWriter(writePath, true);

			// Always wrap FileWriter in BufferedWriter.
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

			bufferedWriter.write(output);
			bufferedWriter.newLine();

			// Always close files.
			bufferedWriter.close();
		} catch (IOException ex) {
			System.out.println("Error writing to file " + writePath + " || Writing Class");
		}
	}

	private void saveResources() {
		String writePath = dir + "/playerResources.txt";
		long beforeTime = System.currentTimeMillis();

		try {
			// Assume default encoding.
			FileWriter fileWriter = new FileWriter(writePath);

			// Always wrap FileWriter in BufferedWriter.
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

			for (int i = 0; i < g.getInv().resourceAmounts.length; i++) {
				String toWrite = Integer.toString(g.getInv().resourceAmounts[i]);
				bufferedWriter.write(toWrite);
				bufferedWriter.newLine();
			}

			// Always close files.
			bufferedWriter.close();
		} catch (IOException ex) {
			outputToDebugFile("Error writing to file " + writePath + " || Writing Class");
		}

		long afterTime = System.currentTimeMillis();
		long totalTime = afterTime - beforeTime;
		outputToDebugFile("Resources saved in " + totalTime + " ms");
	}

	private void saveWorldPeripherals() {
		String writePath = dir + "/worldPeripherals.txt";
		long beforeTime = System.currentTimeMillis();

		try {
			// Assume default encoding.
			FileWriter fileWriter = new FileWriter(writePath);

			// Always wrap FileWriter in BufferedWriter.
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

			String toWrite = "";
			for (int i = 0; i < g.getLevel().tiles.length; i++) {
				if (g.getLevel().tiles[i].tileID == GameResourceLoader.Stone && g.getLevel().tiles[i].hasRock) {
					if (!g.getLevel().tiles[i].hasOre) {
						toWrite = "1";
					} else {
						toWrite = "2";
					}
				} else if (g.getLevel().tiles[i].tileID == GameResourceLoader.Grass && g.getLevel().tiles[i].hasTree) {
					toWrite = "3";
				} else {
					toWrite = "0";
				}

				bufferedWriter.write(toWrite);
				bufferedWriter.newLine();
			}

			// Always close files.
			bufferedWriter.close();
		} catch (IOException ex) {
			outputToDebugFile("Error writing to file " + writePath + " || Writing Class");
		}

		long afterTime = System.currentTimeMillis();
		long totalTime = afterTime - beforeTime;
		outputToDebugFile("Peripherals saved in " + totalTime + " ms");
	}

	private void saveWorld() {
		String writePath = dir + "/worldData.txt";
		long beforeTime = System.currentTimeMillis();

		try {
			// Assume default encoding.
			FileWriter fileWriter = new FileWriter(writePath);

			// Always wrap FileWriter in BufferedWriter.
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

			bufferedWriter.write(Integer.toString(Launcher.worldSize));
			bufferedWriter.newLine();

			for (int i = 0; i < g.getLevel().tiles.length; i++) {
				String toWrite = Integer.toString(g.getLevel().tiles[i].tileID);
				bufferedWriter.write(toWrite);
				bufferedWriter.newLine();
			}

			// Always close files.
			bufferedWriter.close();
		} catch (IOException ex) {
			outputToDebugFile("Error writing to file " + writePath + " || Writing Class");
		}

		long afterTime = System.currentTimeMillis();
		long totalTime = afterTime - beforeTime;
		outputToDebugFile("World saved in " + totalTime + " ms");
	}
}