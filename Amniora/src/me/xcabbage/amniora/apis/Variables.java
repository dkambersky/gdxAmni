package me.xcabbage.amniora.apis;

/**
 * The Variables.java class responsible for
 * 
 * @author xCabbage [github.com/xcabbage]
 * 
 * @info for the Amniora project [github.com/xcabbage/amniora] created 29. 9.
 *       2014 12:23:35
 * 
 */
public class Variables {
	int[][] tileDirections;
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
}
