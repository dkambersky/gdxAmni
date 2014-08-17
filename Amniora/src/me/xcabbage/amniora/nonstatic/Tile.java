/** The Tile.java class from which individual tile objects are generated. 
 *  A Tile object holds the info for a single tile, parallel to the rendered faces of the icosahedron. 
 *
 * @author xCabbage [github.com/xcabbage]
 *
 * @info for the Amniora project [github.com/xcabbage/amniora]
 *      created 14. 8. 2014 16:22:04
 */

package me.xcabbage.amniora.nonstatic;

/**
 * @author David
 * 
 */
public class Tile {

	// The tile's allegiance to a side; 0 = neutral, 1 = blue, 2 = orange
	int allegiance;

	// The tile's type - generated once in the game's beginning. Can be normal
	// (0) or specials such as rich soil or combat shrines.
	Type type;

	// The immediate state of the tile. Whether any building is currently
	// standing on it, etc
	State state;

	// The friendly forces present on the tile.
	int armyFootman, armySamurai, armyReaver;

	public Tile() {
		this(Type.CombatTemple,State.EMPTY);
	}
	
	
	public Tile(Type type, State state) {
		allegiance = 0;
		type = Type.Normal;
		state = State.EMPTY;
		System.out.println("I'm a fucking tile lol." + type);
	}
}
