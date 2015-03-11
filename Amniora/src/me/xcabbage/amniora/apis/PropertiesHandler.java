package me.xcabbage.amniora.apis;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

import me.xcabbage.amniora.GameAmn;

/**
 * The Properties.java class responsible for loading and saving property files
 * for the program's persistence Parts copied from
 * [github.com/xcabbage/trololus]'s property handler.
 * 
 * @author xCabbage [github.com/xcabbage]
 * 
 * @info for the Amniora project [github.com/xcabbage/amniora] created 6. 10.
 *       2014 21:25:33
 * 
 */
public class PropertiesHandler {

	static Path savedpropspath = Paths.get(System.getenv("APPDATA")
			+ "\\Amniora\\game.properties"); // path to saved non-default props
	static Properties prop = new Properties();

	public static void init() {
		try {
			// create and load default properties
			Properties defaultProps = new Properties();
			try {
				FileInputStream DIStream = new FileInputStream(
						savedpropspath.toString());
				defaultProps.load(DIStream);
				// defaultProps.list(System.out);

				DIStream.close();
			} catch (FileNotFoundException e1) {
				GameAmn.alert("Could not find properties; creating default.");

				// create folder if it isn't found
				GameAmn.alert("Creating new folder for Amniora at "
						+ savedpropspath);
				File dir = new File(System.getenv("APPDATA") + "\\Amniora");
				dir.mkdir();

				// if they are not found, set the default properties values
				defaultProps.setProperty("musicvolume", "0.18f");
				defaultProps.setProperty("soundvolume", "1f");
				defaultProps.setProperty("speechvolume", "100");
				defaultProps.setProperty("shipScale", "0.1f");
				defaultProps.setProperty("database", "localhost");
				defaultProps.setProperty("dbuser", "defaultuser");

				defaultProps.setProperty("graphicsquality", "high");
				defaultProps.setProperty("fontGreatHeader", "Basica v.2012");
				defaultProps.setProperty("fontNormalHeader", "Freedom");
				defaultProps.setProperty("fontEntry", "Complex");
				defaultProps.setProperty("fontText", "Orena");

				defaultProps.setProperty("optionsEnabled", "true");
				defaultProps.setProperty("vsyncEnabled", "true");
				defaultProps.setProperty("fpsEnabled", "true");
				defaultProps.setProperty("windowDecorated", "true");

				setSides(defaultProps);
				// save default properties to properties folder
				GameAmn.alert("Writing new default properties to "
						+ savedpropspath);
				File DPropsFile = new File(savedpropspath.toString());
				FileOutputStream fileOut = new FileOutputStream(DPropsFile);
				defaultProps.store(fileOut, "Default Game Properties");
				fileOut.close();
			}

			// create application properties with default
			Properties applicationProps = new Properties(defaultProps);

			// now load properties
			// from last invocation
			FileInputStream IStream = new FileInputStream(
					savedpropspath.toString());
			try {
				applicationProps.load(IStream);

				IStream.close();
			} catch (IOException e) {
				GameAmn.error(e.getStackTrace());
			}

			prop.load(new FileInputStream(savedpropspath.toString()));
		} catch (Exception e) {
			GameAmn.error(e.getStackTrace());
		}

	}

	public static String getProperty(String key) {
		// System.out.println("getting property with key: [" + key + "]");
		return prop.getProperty(key);
	}

	public static void saveProperty(String propname, String propvalue) {

		Properties prop = new Properties();

		try {
			// set the properties value
			prop.load(new FileInputStream(new File(savedpropspath.toString())));

			prop.put(propname, propvalue);

			// save properties to project root folder
			prop.store(new FileOutputStream(savedpropspath.toString()), null);

		} catch (IOException ex) {

			GameAmn.error(ex.getStackTrace());
			GameAmn.alert("Failed to set property. Initiating catch sequence");

			try {

				PropertiesHandler.init();
				FileInputStream IStream = new FileInputStream(
						savedpropspath.toString());
				FileOutputStream OStream = new FileOutputStream(
						savedpropspath.toString());
				// set the properties value
				prop.load(IStream);
				prop.setProperty(propname, propvalue);
				GameAmn.alert("Listing new saved Properties");
				prop.list(System.out);
				prop.store(OStream, "Non-Default Props");
			} catch (Exception e) {
				GameAmn.alert("Catch failed. Returning.");
			}
		}
		reloadProperties();
	}

	static void reloadProperties() {
		try {
			prop.load(new FileInputStream(savedpropspath.toString()));
		} catch (IOException e) {

			GameAmn.error(e.getStackTrace());
		}
	}

	public static void loadProperties() {
		prop = new Properties();

		try {
			FileInputStream IStream = new FileInputStream(new File(
					savedpropspath.toString()));

			// load a properties file
			prop.load(IStream);

			// get the property value and print it out
			GameAmn.alert(prop.getProperty("musicvolume"));
			GameAmn.alert(prop.getProperty("soundvolume"));
			GameAmn.alert(prop.getProperty("speechvolume"));
			GameAmn.alert(prop.getProperty("database"));
			GameAmn.alert(prop.getProperty("dbuser"));
			GameAmn.alert(prop.getProperty("graphicsquality"));

		} catch (FileNotFoundException e) {
			GameAmn.error(e.getStackTrace());
		} catch (IOException ex) {
			GameAmn.error(ex.getStackTrace());
		}

	}

	public static int getDirection(int direction, int tile) {

		try {
			return Integer
					.parseInt(getProperty("dir." + direction + "." + tile));
		} catch (Exception e) {
			GameAmn.error(e.getStackTrace());
			GameAmn.alert("Wrong parameter passed to getDirection: "
					+ direction + " | " + tile + "\n\tException: "
					+ e.getLocalizedMessage());
			if (GameAmn.PRINT_EMERGENCY_ERRORS) {
				e.printStackTrace();
			}
			return -1;
		}

	}

	public static void setSides(Properties defaultProps) {
		defaultProps.setProperty("dir.0.0", "0");
		defaultProps.setProperty("dir.0.1", "0");
		defaultProps.setProperty("dir.0.2", "0");
		defaultProps.setProperty("dir.0.3", "0");
		defaultProps.setProperty("dir.0.4", "0");
		defaultProps.setProperty("dir.0.5", "0");
		defaultProps.setProperty("dir.0.6", "0");
		defaultProps.setProperty("dir.0.7", "0");
		defaultProps.setProperty("dir.0.8", "0");
		defaultProps.setProperty("dir.0.9", "0");
		defaultProps.setProperty("dir.0.10", "0");
		defaultProps.setProperty("dir.0.11", "0");
		defaultProps.setProperty("dir.0.12", "0");
		defaultProps.setProperty("dir.0.13", "0");
		defaultProps.setProperty("dir.0.14", "0");
		defaultProps.setProperty("dir.0.15", "0");
		defaultProps.setProperty("dir.0.16", "0");
		defaultProps.setProperty("dir.0.17", "0");
		defaultProps.setProperty("dir.0.18", "0");
		defaultProps.setProperty("dir.0.19", "0");
		defaultProps.setProperty("dir.0.20", "0");
		defaultProps.setProperty("dir.0.21", "0");
		defaultProps.setProperty("dir.0.22", "0");
		defaultProps.setProperty("dir.0.23", "0");
		defaultProps.setProperty("dir.0.24", "0");
		defaultProps.setProperty("dir.0.25", "0");
		defaultProps.setProperty("dir.0.26", "0");
		defaultProps.setProperty("dir.0.27", "0");
		defaultProps.setProperty("dir.0.28", "0");
		defaultProps.setProperty("dir.0.29", "0");
		defaultProps.setProperty("dir.0.30", "0");
		defaultProps.setProperty("dir.0.31", "0");
		defaultProps.setProperty("dir.0.32", "0");
		defaultProps.setProperty("dir.0.33", "0");
		defaultProps.setProperty("dir.0.34", "0");
		defaultProps.setProperty("dir.0.35", "0");
		defaultProps.setProperty("dir.0.36", "0");
		defaultProps.setProperty("dir.0.37", "0");
		defaultProps.setProperty("dir.0.38", "0");
		defaultProps.setProperty("dir.0.39", "0");
		defaultProps.setProperty("dir.0.40", "50");
		defaultProps.setProperty("dir.0.41", "0");
		defaultProps.setProperty("dir.0.42", "0");
		defaultProps.setProperty("dir.0.43", "0");
		defaultProps.setProperty("dir.0.44", "0");
		defaultProps.setProperty("dir.0.45", "0");
		defaultProps.setProperty("dir.0.46", "0");
		defaultProps.setProperty("dir.0.47", "0");
		defaultProps.setProperty("dir.0.48", "0");
		defaultProps.setProperty("dir.0.49", "0");
		defaultProps.setProperty("dir.0.50", "0");
		defaultProps.setProperty("dir.0.51", "0");
		defaultProps.setProperty("dir.0.52", "0");
		defaultProps.setProperty("dir.0.53", "0");
		defaultProps.setProperty("dir.0.54", "0");
		defaultProps.setProperty("dir.0.55", "0");
		defaultProps.setProperty("dir.0.56", "0");
		defaultProps.setProperty("dir.0.57", "0");
		defaultProps.setProperty("dir.0.58", "0");
		defaultProps.setProperty("dir.0.59", "0");
		defaultProps.setProperty("dir.0.60", "0");
		defaultProps.setProperty("dir.0.61", "0");
		defaultProps.setProperty("dir.0.62", "0");
		defaultProps.setProperty("dir.0.63", "0");
		defaultProps.setProperty("dir.0.64", "0");
		defaultProps.setProperty("dir.0.65", "0");
		defaultProps.setProperty("dir.0.66", "0");
		defaultProps.setProperty("dir.0.67", "0");
		defaultProps.setProperty("dir.0.68", "0");
		defaultProps.setProperty("dir.0.69", "0");
		defaultProps.setProperty("dir.0.70", "0");
		defaultProps.setProperty("dir.0.71", "0");
		defaultProps.setProperty("dir.0.72", "0");
		defaultProps.setProperty("dir.0.73", "0");
		defaultProps.setProperty("dir.0.74", "0");
		defaultProps.setProperty("dir.0.75", "0");
		defaultProps.setProperty("dir.0.76", "0");
		defaultProps.setProperty("dir.0.77", "0");
		defaultProps.setProperty("dir.0.78", "0");
		defaultProps.setProperty("dir.0.79", "0");

		defaultProps.setProperty("dir.1.0", "0");
		defaultProps.setProperty("dir.1.1", "0");
		defaultProps.setProperty("dir.1.2", "0");
		defaultProps.setProperty("dir.1.3", "0");
		defaultProps.setProperty("dir.1.4", "0");
		defaultProps.setProperty("dir.1.5", "0");
		defaultProps.setProperty("dir.1.6", "0");
		defaultProps.setProperty("dir.1.7", "0");
		defaultProps.setProperty("dir.1.8", "0");
		defaultProps.setProperty("dir.1.9", "0");
		defaultProps.setProperty("dir.1.10", "0");
		defaultProps.setProperty("dir.1.11", "0");
		defaultProps.setProperty("dir.1.12", "0");
		defaultProps.setProperty("dir.1.13", "0");
		defaultProps.setProperty("dir.1.14", "0");
		defaultProps.setProperty("dir.1.15", "0");
		defaultProps.setProperty("dir.1.16", "0");
		defaultProps.setProperty("dir.1.17", "0");
		defaultProps.setProperty("dir.1.18", "0");
		defaultProps.setProperty("dir.1.19", "0");
		defaultProps.setProperty("dir.1.20", "0");
		defaultProps.setProperty("dir.1.21", "0");
		defaultProps.setProperty("dir.1.22", "0");
		defaultProps.setProperty("dir.1.23", "0");
		defaultProps.setProperty("dir.1.24", "0");
		defaultProps.setProperty("dir.1.25", "0");
		defaultProps.setProperty("dir.1.26", "0");
		defaultProps.setProperty("dir.1.27", "0");
		defaultProps.setProperty("dir.1.28", "0");
		defaultProps.setProperty("dir.1.29", "0");
		defaultProps.setProperty("dir.1.30", "0");
		defaultProps.setProperty("dir.1.31", "0");
		defaultProps.setProperty("dir.1.32", "0");
		defaultProps.setProperty("dir.1.33", "0");
		defaultProps.setProperty("dir.1.34", "0");
		defaultProps.setProperty("dir.1.35", "0");
		defaultProps.setProperty("dir.1.36", "0");
		defaultProps.setProperty("dir.1.37", "0");
		defaultProps.setProperty("dir.1.38", "0");
		defaultProps.setProperty("dir.1.39", "0");
		defaultProps.setProperty("dir.1.40", "0");
		defaultProps.setProperty("dir.1.41", "0");
		defaultProps.setProperty("dir.1.42", "0");
		defaultProps.setProperty("dir.1.43", "0");
		defaultProps.setProperty("dir.1.44", "0");
		defaultProps.setProperty("dir.1.45", "0");
		defaultProps.setProperty("dir.1.46", "0");
		defaultProps.setProperty("dir.1.47", "0");
		defaultProps.setProperty("dir.1.48", "0");
		defaultProps.setProperty("dir.1.49", "0");
		defaultProps.setProperty("dir.1.50", "0");
		defaultProps.setProperty("dir.1.51", "0");
		defaultProps.setProperty("dir.1.52", "0");
		defaultProps.setProperty("dir.1.53", "0");
		defaultProps.setProperty("dir.1.54", "0");
		defaultProps.setProperty("dir.1.55", "0");
		defaultProps.setProperty("dir.1.56", "0");
		defaultProps.setProperty("dir.1.57", "0");
		defaultProps.setProperty("dir.1.58", "0");
		defaultProps.setProperty("dir.1.59", "0");
		defaultProps.setProperty("dir.1.60", "0");
		defaultProps.setProperty("dir.1.61", "0");
		defaultProps.setProperty("dir.1.62", "0");
		defaultProps.setProperty("dir.1.63", "0");
		defaultProps.setProperty("dir.1.64", "0");
		defaultProps.setProperty("dir.1.65", "0");
		defaultProps.setProperty("dir.1.66", "0");
		defaultProps.setProperty("dir.1.67", "0");
		defaultProps.setProperty("dir.1.68", "0");
		defaultProps.setProperty("dir.1.69", "0");
		defaultProps.setProperty("dir.1.70", "0");
		defaultProps.setProperty("dir.1.71", "0");
		defaultProps.setProperty("dir.1.72", "0");
		defaultProps.setProperty("dir.1.73", "0");
		defaultProps.setProperty("dir.1.74", "0");
		defaultProps.setProperty("dir.1.75", "0");
		defaultProps.setProperty("dir.1.76", "0");
		defaultProps.setProperty("dir.1.77", "0");
		defaultProps.setProperty("dir.1.78", "0");
		defaultProps.setProperty("dir.1.79", "0");

		defaultProps.setProperty("dir.2.0", "0");
		defaultProps.setProperty("dir.2.1", "0");
		defaultProps.setProperty("dir.2.2", "0");
		defaultProps.setProperty("dir.2.3", "0");
		defaultProps.setProperty("dir.2.4", "0");
		defaultProps.setProperty("dir.2.5", "0");
		defaultProps.setProperty("dir.2.6", "0");
		defaultProps.setProperty("dir.2.7", "0");
		defaultProps.setProperty("dir.2.8", "0");
		defaultProps.setProperty("dir.2.9", "0");
		defaultProps.setProperty("dir.2.10", "0");
		defaultProps.setProperty("dir.2.11", "0");
		defaultProps.setProperty("dir.2.12", "0");
		defaultProps.setProperty("dir.2.13", "0");
		defaultProps.setProperty("dir.2.14", "0");
		defaultProps.setProperty("dir.2.15", "0");
		defaultProps.setProperty("dir.2.16", "0");
		defaultProps.setProperty("dir.2.17", "0");
		defaultProps.setProperty("dir.2.18", "0");
		defaultProps.setProperty("dir.2.19", "0");
		defaultProps.setProperty("dir.2.20", "0");
		defaultProps.setProperty("dir.2.21", "0");
		defaultProps.setProperty("dir.2.22", "0");
		defaultProps.setProperty("dir.2.23", "0");
		defaultProps.setProperty("dir.2.24", "0");
		defaultProps.setProperty("dir.2.25", "0");
		defaultProps.setProperty("dir.2.26", "0");
		defaultProps.setProperty("dir.2.27", "0");
		defaultProps.setProperty("dir.2.28", "0");
		defaultProps.setProperty("dir.2.29", "0");
		defaultProps.setProperty("dir.2.30", "0");
		defaultProps.setProperty("dir.2.31", "0");
		defaultProps.setProperty("dir.2.32", "0");
		defaultProps.setProperty("dir.2.33", "0");
		defaultProps.setProperty("dir.2.34", "0");
		defaultProps.setProperty("dir.2.35", "0");
		defaultProps.setProperty("dir.2.36", "0");
		defaultProps.setProperty("dir.2.37", "0");
		defaultProps.setProperty("dir.2.38", "0");
		defaultProps.setProperty("dir.2.39", "0");
		defaultProps.setProperty("dir.2.40", "0");
		defaultProps.setProperty("dir.2.41", "0");
		defaultProps.setProperty("dir.2.42", "0");
		defaultProps.setProperty("dir.2.43", "0");
		defaultProps.setProperty("dir.2.44", "0");
		defaultProps.setProperty("dir.2.45", "0");
		defaultProps.setProperty("dir.2.46", "0");
		defaultProps.setProperty("dir.2.47", "0");
		defaultProps.setProperty("dir.2.48", "0");
		defaultProps.setProperty("dir.2.49", "0");
		defaultProps.setProperty("dir.2.50", "0");
		defaultProps.setProperty("dir.2.51", "0");
		defaultProps.setProperty("dir.2.52", "0");
		defaultProps.setProperty("dir.2.53", "0");
		defaultProps.setProperty("dir.2.54", "0");
		defaultProps.setProperty("dir.2.55", "0");
		defaultProps.setProperty("dir.2.56", "0");
		defaultProps.setProperty("dir.2.57", "0");
		defaultProps.setProperty("dir.2.58", "0");
		defaultProps.setProperty("dir.2.59", "0");
		defaultProps.setProperty("dir.2.60", "0");
		defaultProps.setProperty("dir.2.61", "0");
		defaultProps.setProperty("dir.2.62", "0");
		defaultProps.setProperty("dir.2.63", "0");
		defaultProps.setProperty("dir.2.64", "0");
		defaultProps.setProperty("dir.2.65", "0");
		defaultProps.setProperty("dir.2.66", "0");
		defaultProps.setProperty("dir.2.67", "0");
		defaultProps.setProperty("dir.2.68", "0");
		defaultProps.setProperty("dir.2.69", "0");
		defaultProps.setProperty("dir.2.70", "0");
		defaultProps.setProperty("dir.2.71", "0");
		defaultProps.setProperty("dir.2.72", "0");
		defaultProps.setProperty("dir.2.73", "0");
		defaultProps.setProperty("dir.2.74", "0");
		defaultProps.setProperty("dir.2.75", "0");
		defaultProps.setProperty("dir.2.76", "0");
		defaultProps.setProperty("dir.2.77", "0");
		defaultProps.setProperty("dir.2.78", "0");
		defaultProps.setProperty("dir.2.79", "0");

		defaultProps.setProperty("dir.3.0", "0");
		defaultProps.setProperty("dir.3.1", "0");
		defaultProps.setProperty("dir.3.2", "0");
		defaultProps.setProperty("dir.3.3", "0");
		defaultProps.setProperty("dir.3.4", "0");
		defaultProps.setProperty("dir.3.5", "0");
		defaultProps.setProperty("dir.3.6", "0");
		defaultProps.setProperty("dir.3.7", "0");
		defaultProps.setProperty("dir.3.8", "0");
		defaultProps.setProperty("dir.3.9", "0");
		defaultProps.setProperty("dir.3.10", "0");
		defaultProps.setProperty("dir.3.11", "0");
		defaultProps.setProperty("dir.3.12", "0");
		defaultProps.setProperty("dir.3.13", "0");
		defaultProps.setProperty("dir.3.14", "0");
		defaultProps.setProperty("dir.3.15", "0");
		defaultProps.setProperty("dir.3.16", "0");
		defaultProps.setProperty("dir.3.17", "0");
		defaultProps.setProperty("dir.3.18", "0");
		defaultProps.setProperty("dir.3.19", "0");
		defaultProps.setProperty("dir.3.20", "0");
		defaultProps.setProperty("dir.3.21", "0");
		defaultProps.setProperty("dir.3.22", "0");
		defaultProps.setProperty("dir.3.23", "0");
		defaultProps.setProperty("dir.3.24", "0");
		defaultProps.setProperty("dir.3.25", "0");
		defaultProps.setProperty("dir.3.26", "0");
		defaultProps.setProperty("dir.3.27", "0");
		defaultProps.setProperty("dir.3.28", "0");
		defaultProps.setProperty("dir.3.29", "0");
		defaultProps.setProperty("dir.3.30", "0");
		defaultProps.setProperty("dir.3.31", "0");
		defaultProps.setProperty("dir.3.32", "0");
		defaultProps.setProperty("dir.3.33", "0");
		defaultProps.setProperty("dir.3.34", "0");
		defaultProps.setProperty("dir.3.35", "0");
		defaultProps.setProperty("dir.3.36", "0");
		defaultProps.setProperty("dir.3.37", "0");
		defaultProps.setProperty("dir.3.38", "0");
		defaultProps.setProperty("dir.3.39", "0");
		defaultProps.setProperty("dir.3.40", "0");
		defaultProps.setProperty("dir.3.41", "0");
		defaultProps.setProperty("dir.3.42", "0");
		defaultProps.setProperty("dir.3.43", "0");
		defaultProps.setProperty("dir.3.44", "0");
		defaultProps.setProperty("dir.3.45", "0");
		defaultProps.setProperty("dir.3.46", "0");
		defaultProps.setProperty("dir.3.47", "0");
		defaultProps.setProperty("dir.3.48", "0");
		defaultProps.setProperty("dir.3.49", "0");
		defaultProps.setProperty("dir.3.50", "0");
		defaultProps.setProperty("dir.3.51", "0");
		defaultProps.setProperty("dir.3.52", "0");
		defaultProps.setProperty("dir.3.53", "0");
		defaultProps.setProperty("dir.3.54", "0");
		defaultProps.setProperty("dir.3.55", "0");
		defaultProps.setProperty("dir.3.56", "0");
		defaultProps.setProperty("dir.3.57", "0");
		defaultProps.setProperty("dir.3.58", "0");
		defaultProps.setProperty("dir.3.59", "0");
		defaultProps.setProperty("dir.3.60", "0");
		defaultProps.setProperty("dir.3.61", "0");
		defaultProps.setProperty("dir.3.62", "0");
		defaultProps.setProperty("dir.3.63", "0");
		defaultProps.setProperty("dir.3.64", "0");
		defaultProps.setProperty("dir.3.65", "0");
		defaultProps.setProperty("dir.3.66", "0");
		defaultProps.setProperty("dir.3.67", "0");
		defaultProps.setProperty("dir.3.68", "0");
		defaultProps.setProperty("dir.3.69", "0");
		defaultProps.setProperty("dir.3.70", "0");
		defaultProps.setProperty("dir.3.71", "0");
		defaultProps.setProperty("dir.3.72", "0");
		defaultProps.setProperty("dir.3.73", "0");
		defaultProps.setProperty("dir.3.74", "0");
		defaultProps.setProperty("dir.3.75", "0");
		defaultProps.setProperty("dir.3.76", "0");
		defaultProps.setProperty("dir.3.77", "0");
		defaultProps.setProperty("dir.3.78", "0");
		defaultProps.setProperty("dir.3.79", "0");
	}

}