package ee.liiser.siim.galaxies;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import ee.liiser.siim.galaxies.calculations.Calculator;
import ee.liiser.siim.galaxies.calculations.Calculator.Method;
import ee.liiser.siim.galaxies.calculations.WorkerThread;
import ee.liiser.siim.galaxies.data.Core;
import ee.liiser.siim.galaxies.data.Galaxy;
import ee.liiser.siim.galaxies.data.ObjectFactory;
import ee.liiser.siim.galaxies.data.Star;
import ee.liiser.siim.galaxies.drawing.Drawable;
import ee.liiser.siim.galaxies.drawing.GraphicsThread;
import ee.liiser.siim.galaxies.drawing.PauseListener;

/**
 * Main entry-point of the application and the main class
 *
 */
public class Main {

	/**
	 * An array to hold all stars in the system
	 */
	private static Star[] stars;
	/**
	 * An array to hold all galaxy cores in the system
	 */
	// FIXME should not be public
	public static Core[] cores;

	/**
	 * Calculation method to use. Value should be one of
	 * {@link Calculator.Method}
	 */
	private static Method METHOD;

	public static void main(String[] args) {
		Galaxy[] galaxies = null;

		if (args.length >= 2) {
			setConf(args[1]);
		} else {
			setConf("conf.txt");
		}

		ObjectFactory factory = new ObjectFactory(METHOD);

		if (args.length >= 1) {
			galaxies = makeGalaxies(args[0], factory);
		} else {
			galaxies = makeGalaxies("galaxies.txt", factory);
		}
		run(galaxies);

	}

	/**
	 * Main entry point of the application. Call this with an array of galaxies
	 * to simulate their movement
	 * 
	 * @param galaxies
	 *            is the array of galaxies to be simulated
	 */
	public static void run(Galaxy[] galaxies) {

		cores = new Core[galaxies.length];
		int starcount = 0;
		for(Galaxy g : galaxies){
			starcount += g.getStars().length;
		}
		stars = new Star[starcount];

		int starIndex = 0;
		for (int i = 0; i < galaxies.length; i++) {
			cores[i] = galaxies[i].getCore();
			System.arraycopy(galaxies[i].getStars(), 0, stars, starIndex,
					galaxies[i].getStars().length);
			starIndex += galaxies[i].getStars().length;
		}

		Drawable[] points = new Drawable[stars.length + cores.length];
		System.arraycopy(cores, 0, points, 0, cores.length);
		System.arraycopy(stars, 0, points, cores.length, stars.length);

		WorkerThread t = new WorkerThread(cores, stars, METHOD);
		new GraphicsThread(points, new PauseListener(t)).start();
		t.start();
	}

	private static Galaxy[] makeGalaxies(String galaxyFile,
			ObjectFactory factory) {

		try {
			BufferedReader r = new BufferedReader(new FileReader(galaxyFile));
			ArrayList<Galaxy> list = new ArrayList<Galaxy>();
			while (true) {
				String line = r.readLine();
				if (line == null)
					break;
				if (line.isEmpty())
					break;
				if (line.startsWith("#"))
					continue;

				list.add(factory.makeGalaxy(line));

			}
			r.close();
			Galaxy[] galaxies = new Galaxy[list.size()];
			galaxies = list.toArray(galaxies);
			return galaxies;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;

	}

	private static void setConf(String confFile) {
		try {
			BufferedReader r = new BufferedReader(new FileReader(confFile));
			String line;
			do {
				line = r.readLine();
			} while (line != null && line.startsWith("#"));
			r.close();
			String[] params = line.split(",");
			if (params[0].toLowerCase().equals("velocity")) {
				METHOD = Method.VELOCITY_VERLET;
			} else if (params[0].toLowerCase().equals("basic")) {
				METHOD = Method.BASIC_VERLET;
			} else {
				throw new IllegalArgumentException(params[0]
						+ " is not a method in " + confFile);
			}
			Calculator.dt = Double.parseDouble(params[1]);
			GraphicsThread.fps = Double.parseDouble(params[2]);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
