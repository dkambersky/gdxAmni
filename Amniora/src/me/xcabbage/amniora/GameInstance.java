package me.xcabbage.amniora;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;

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
	// DEBUG
	// recolor madness
	public boolean recolourInProgress;
	int recolourPos;

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

	public boolean setTileTexture(Tile tile, Texture t) {
		Material mat = screen.globeInstance.materials.get(tile.position);
		mat.clear();
		mat.set(TextureAttribute.createDiffuse(t));

		return true;
	}

	public boolean updateBattlefield() {

		if (recolourInProgress) {
			if (recolourPos == 80)
				recolourPos = 0;
			if ((Math.random() * 100) < 6f) {

				// setTileColor(
				// tiles[recolourPos],
				// Color.RED.cpy().set(((float) Math.random()),
				// ((float) Math.random()),
				// ((float) Math.random()), 1));
				System.out.println("setting " + recolourPos + ", texture");
				setTileTexture(tiles[recolourPos],
						GameplayScreen.tileTextures[recolourPos]);
				recolourPos++;
			}

		}

		return true;
	}
}