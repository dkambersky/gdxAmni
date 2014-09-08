package me.xcabbage.amniora;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;

import me.xcabbage.amniora.nonstatic.State;
import me.xcabbage.amniora.nonstatic.Tile;
import me.xcabbage.amniora.nonstatic.Type;
import me.xcabbage.amniora.screen.GameplayScreen;

/**
 * The GameInstance.java class responsible for keeping track of the current
 * state of everything in the game.
 * 
 * @author xCabbage [github.com/xcabbage]
 * 
 * @info for the Amniora project [github.com/xcabbage/amniora] created 14. 8.
 *       2014 16:18:59
 */

public class GameInstance {

	//
	public boolean recolourInProgress;

	// Constructor to be able to reach our Game screen
	GameplayScreen screen;

	public GameInstance(GameplayScreen screen) {
		this.screen = screen;
		initBattlefield();
	}

	public Tile[] tiles = new Tile[80];

	public void initBattlefield() {
		int b = 0;
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
			tile.position = b;
			tiles[b] = tile;

			b++;

			System.out.println(tile);
		}
		System.out.println("Battlefield initiated");
	}

	public boolean setTileColor(Tile tile, Color c) {
		Material mat = screen.globeInstance.materials.get(tile.position);
		Color col = Color.ORANGE.cpy();
		col.set(c);

		System.out.println("setting " + tile.position + " | " + col);
		mat.set(ColorAttribute.createDiffuse(col));
		return true;

	}

	public boolean updateBattlefield() {
		if (recolourInProgress) {
			if ((int) Math.random() * 100 < 8) {
				

				setTileColor(
						tiles[(int) (Math.random() * 80)],
						Color.RED.cpy().set(((float) Math.random()),
								((float) Math.random()),
								((float) Math.random()), 1));
			}
		}
		return true;
	}
}