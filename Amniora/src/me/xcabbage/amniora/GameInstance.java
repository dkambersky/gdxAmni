
package me.xcabbage.amniora;

import com.badlogic.gdx.graphics.Color;

import me.xcabbage.amniora.nonstatic.State;
import me.xcabbage.amniora.nonstatic.Tile;
import me.xcabbage.amniora.nonstatic.Type;

/** The GameInstance.java class responsible for keeping track of the current state of everything in the game.  
*
* @author xCabbage [github.com/xcabbage]
*
* @info for the Amniora project [github.com/xcabbage/amniora]
*      created 14. 8. 2014 16:18:59
*/

public class GameInstance {
	public Tile[] tiles = new Tile[80];

	public void initBattlefield() {
		for (Tile tile : tiles) {
			int a = (int) (Math.random() * 5.0);
			switch (a) {
			case 1:
				tile = new Tile(Type.RichEarth, State.EMPTY);
				break;
			case 2:
				tile = new Tile(Type.RaiderShrine, State.EMPTY);
				break;
			case 3:
				tile = new Tile(Type.ExcavationLab, State.EMPTY);
				break;
			case 4:
				tile = new Tile(Type.CombatTemple, State.EMPTY);
				break;
			default:
				tile = new Tile(Type.VanguardShrine, State.EMPTY);
				break;
			}

			System.out.println(tile);
		}

	}

	public boolean setTileColor(Tile tile, Color c) {
		return false;
	}

}
