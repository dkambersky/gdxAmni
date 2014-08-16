/** The State.java class responsible for 
 *
 * @author xCabbage [github.com/xcabbage]
 *
 * @info for the Amniora project [github.com/xcabbage/amniora]
 *      created 14. 8. 2014 17:10:07
 */

package me.xcabbage.amniora.nonstatic;

/**
 * @author David
 * 
 */
public enum State {

	// initial state, vacant
	EMPTY,

	// soil with potential for upgraded structures
	EMPTY_FAST_QUARRY, EMPTY_FAST_RAX,

	// occupied with buildings

	// uniques
	OCCUPIED_UNIQUE_ATTACK, OCCUPIED_UNIQUE_DEFENSE, OCCUPIED_UNIQUE_QUARRY, OCCUPIED_UNIQUE_SIGHT,

	// buildings
	OCCUPIED_FAST_QUARRY, OCCUPIED_FAST_RAX, OCCUPIED_RAX, OCCUPIED_QUARRY, OCCUPIED_GEOLAB, OCCUPIED_BANNER;
}
