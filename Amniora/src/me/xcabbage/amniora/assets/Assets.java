/** The Assets.java class responsible for managing static assets of the game such as fonts 
 *
 * @author xCabbage [github.com/xcabbage]
 *
 * @info for the Amniora project [github.com/xcabbage/amniora]
 *      created 6. 6. 2014 12:48:13
 */

package me.xcabbage.amniora.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * @author David
 * 
 */
public class Assets {
	static public BitmapFont F_debug;
	static public BitmapFont F_buttonsHighlight;
	static public BitmapFont F_buttonsOutline0;
	static public BitmapFont F_buttonsOutline1;
	static public BitmapFont F_buttonsOutline3;
	static public BitmapFont F_buttonsOutline2;
	static public BitmapFont F_buttons;
	public static Texture texture;
	public static TextureRegion R_background;
	public static TextureRegion R_planet;
	public static Sprite S_background, S_planet;

	public static void init() {
		// Loading Fonts
		F_debug = new BitmapFont();
		F_buttons = new BitmapFont(
				Gdx.files.internal("fonts/starcraftPlain.fnt"),
				Gdx.files.internal("fonts/starcraftPlain.png"), false);
		F_buttonsHighlight = new BitmapFont(
				Gdx.files.internal("fonts/starcraftBlue.fnt"),
				Gdx.files.internal("fonts/starcraftBlue.png"), false);
		F_buttonsOutline0 = new BitmapFont(
				Gdx.files.internal("fonts/starcraftBold0.fnt"),
				Gdx.files.internal("fonts/starcraftBold0.png"), false);
		F_buttonsOutline1 = new BitmapFont(
				Gdx.files.internal("fonts/starcraftBold1.fnt"),
				Gdx.files.internal("fonts/starcraftBold1.png"), false);
		F_buttonsOutline2 = new BitmapFont(
				Gdx.files.internal("fonts/starcraftBold2.fnt"),
				Gdx.files.internal("fonts/starcraftBold2.png"), false);
		F_buttonsOutline3 = new BitmapFont(
				Gdx.files.internal("fonts/starcraftBold3.fnt"),
				Gdx.files.internal("fonts/starcraftBold3.png"), false);

		// Loading SpriteSheets
		texture = new Texture(Gdx.files.internal("menuSmaller2.png"));
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);

		// Creating regions from the base texture
		R_background = new TextureRegion(texture, 0, 0, 2016, 1345);
		R_planet = new TextureRegion(texture, 0, 1365, 950, 686);

		// Sprites 
		S_background = new Sprite(R_background);
		S_background.setScale(0.52f);
		S_background.setOrigin(0, 0);
		S_background.setPosition(0, 0);

		S_planet = new Sprite(R_planet);
		S_planet.setScale(0.32f);
		S_planet.setOrigin(50, 50);
		S_planet.setPosition(540, 200);

	}

}
