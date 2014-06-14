package FileManagement;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import Core.Launcher;

public class Reading {
	String			home				= System.getProperty("user.home");
	String			dir					= home + "/Desktop";

	String			fileName;
	String			line;
	int				i					= 0, ii = 0;
	public int[]	fileTileID			= new int[Launcher.worldSize * Launcher.worldSize];
	public int[]	fileTilePeripherals	= new int[Launcher.worldSize * Launcher.worldSize];

	public Reading(String filePath) {
		fileName = filePath;

		readAll();
	}

	private void readAll() {
		DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		Writing.outputToDebugFile(df.format(date) + "----------");
		
		long beforeTime = System.currentTimeMillis();

		readWorld();
		readPeripherals();

		long afterTime = System.currentTimeMillis();
		long totalTime = afterTime - beforeTime;
		Writing.outputToDebugFile("Total read time: " + totalTime + " ms");
	}

	private void readPeripherals() {
		String readPath = dir + "/worldPeripherals.txt";
		long beforeTime = System.currentTimeMillis();

		try {
			// FileReader reads text files in the default encoding.
			FileReader fileReader = new FileReader(readPath);

			// Always wrap FileReader in BufferedReader.
			BufferedReader bufferedReader = new BufferedReader(fileReader);

			while ((line = bufferedReader.readLine()) != null) {
				fileTilePeripherals[ii] = Integer.parseInt(line);
				ii++;
			}

			// Always close files.
			bufferedReader.close();
		} catch (FileNotFoundException ex) {
			Writing.outputToDebugFile("Unable to open file '" + readPath + "'");
		} catch (IOException ex) {
			Writing.outputToDebugFile("Error reading file '" + readPath + "'");
		}

		long afterTime = System.currentTimeMillis();
		long totalTime = afterTime - beforeTime;
		Writing.outputToDebugFile("Peripherals read in " + totalTime + " ms");
	}

	private void readWorld() {
		String readPath = dir + "/worldData.txt";
		long beforeTime = System.currentTimeMillis();

		try {
			// FileReader reads text files in the default encoding.
			FileReader fileReader = new FileReader(readPath);

			// Always wrap FileReader in BufferedReader.
			BufferedReader bufferedReader = new BufferedReader(fileReader);

			int worldSize = Integer.parseInt(bufferedReader.readLine());
			System.out.println(worldSize);
			fileTileID = new int[worldSize * worldSize];
			fileTilePeripherals = new int[worldSize * worldSize];

			while ((line = bufferedReader.readLine()) != null) {
				fileTileID[i] = Integer.parseInt(line);
				i++;
			}

			// Always close files.
			bufferedReader.close();
		} catch (FileNotFoundException ex) {
			Writing.outputToDebugFile("Unable to open file '" + readPath + "'");
		} catch (IOException ex) {
			Writing.outputToDebugFile("Error reading file '" + readPath + "'");
		}

		long afterTime = System.currentTimeMillis();
		long totalTime = afterTime - beforeTime;
		Writing.outputToDebugFile("World read in " + totalTime + " ms");
	}
}