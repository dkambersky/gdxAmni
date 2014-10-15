package me.xcabbage.amniora.apis;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import me.xcabbage.amniora.GameAmn;

/**
 * The Variables.java class responsible for
 * 
 * @author xCabbage [github.com/xcabbage]
 * 
 * @info for the Amniora project [github.com/xcabbage/amniora] created 29. 9.
 *       2014 12:23:35
 * 
 */
@XmlRootElement
public class Variables {
	@XmlAttribute
	static int[][] tileDirections;

	public static Variables var;

	public static void init() {
		var = new Variables();
		tileDirections = new int[4][80];
		loadDirections();
	}

	public void fill() {
		tileDirections = new int[4][80];
		for (int a = 0; a < 4; a++) {
			for (int b = 0; b < 80; b++) {
				tileDirections[a][b] = 0;

			}

		}

	}

	public static void flushDirections() {
		for (int a = 0; a < 4; a++) {
			for (int b = 0; b < 80; b++) {

				PropertiesHandler.saveProperty("dir." + a + "." + b,
						Variables.getDirection(a, b) + "");
			}
		}

	}

	public static void loadDirections() {
		for (int a = 0; a < 4; a++) {
			for (int b = 0; b < 80; b++) {

				tileDirections[a][b] = PropertiesHandler.getDirection(a, b);
			}
		}

	}

	public static void saveDirection(int direction, int tile, int value,
			boolean modifyInProgram) {
		if (modifyInProgram) {
			setDirection(direction, tile, value);
		}
		PropertiesHandler.saveProperty("dir." + direction + "." + tile, value
				+ "");
	}

	public static void saveDirection(int direction, int tile) {

		PropertiesHandler.saveProperty("dir." + direction + "." + tile,
				tileDirections[direction][tile] + "");
	}

	public static int getDirection(int direction, int tile) {

		try {
			return tileDirections[direction][tile];
		} catch (Exception e) {
			GameAmn.error(e.getStackTrace());
			GameAmn.alert("Wrong parameter passed to getDirection: "
					+ direction + " | " + tile + "\n\tException: "
					+ e.getLocalizedMessage());
			return -1;
		}

	}

	public static void setDirection(int direction, int tile, int value) {

		try {
			tileDirections[direction][tile] = value;
		} catch (Exception e) {
			GameAmn.error(e.getStackTrace());
			GameAmn.alert("Wrong parameter passed to setDirection: "
					+ direction + " | " + tile);

		}

	}

	/**
	 * @return the var
	 */
	@XmlElement(name = "COMMAND")
	public static Variables getVar() {
		GameAmn.alert(tileDirections[0][0] + "SUP");
		return var;
	}

	/**
	 * @param var
	 *            the var to set
	 */
	public static void setVar(Variables var) {
		Variables.var = var;
	}

}
