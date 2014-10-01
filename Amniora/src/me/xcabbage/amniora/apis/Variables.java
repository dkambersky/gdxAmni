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
		var.fill();
	}

	public void fill() {
		tileDirections = new int[4][80];
		for (int a = 0; a < 4; a++) {
			for (int b = 0; b < 80; b++) {
				tileDirections[a][b] = 0;

			}

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
