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
			+ "\\Amniora\\game.properties"); // path to saved non-default
												// props
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

				// if they are not found, set the default properties value
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

				// save default properties to properties folder
				GameAmn.alert("Writing new default properties to "
						+ savedpropspath);
				File DPropsFile = new File(savedpropspath.toString());
				FileOutputStream fileOut = new FileOutputStream(DPropsFile);
				defaultProps.store(fileOut, "Default Game Properties");
				fileOut.close();
				// defaultProps.store(new
				// FileOutputStream(defaultpath.toString()),
				// null);
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
		System.out.println("getting property with key: [" + key + "]");
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
}
