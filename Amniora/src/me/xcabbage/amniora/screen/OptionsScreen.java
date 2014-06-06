/** The OptionsScreen.java class responsible for showing / modifying settings of the game 
 *
 * @author xCabbage [github.com/xcabbage]
 *
 * @info for the Amniora project [github.com/xcabbage/amniora]
 *      created 6. 6. 2014 12:44:14
 */

package me.xcabbage.amniora.screen;

import me.xcabbage.amniora.GameAmn;
import me.xcabbage.amniora.input.AmniInputProcessor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.Shader;

/**
 * @author David
 * 
 */
public class OptionsScreen implements Screen {

	public final float MAX_ALPHA = .38f;
	public final int buttonCount = 4;

	public int activeButton = -1;

	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Texture texture;
	private Sprite S_background, S_planet;
	private TextureRegion R_background, R_planet;
	BitmapFont F_debug, F_buttons, F_buttonsHighlight, F_buttonsOutline0,
			F_buttonsOutline1, F_buttonsOutline2, F_buttonsOutline3;
	public Shader shader;
	GameAmn game;
	private boolean fontHighlight;
	public float[] buttonHighlight = { 0f, 0f, 0f, 0f };

	public OptionsScreen(final GameAmn gam) {
		// Initialization
		game = gam;
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();

		// Camera & Batch work
		batch = new SpriteBatch();
		camera = new OrthographicCamera();

		camera.setToOrtho(false, w, h);
		camera.update();

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

		// Initializing sprites from textures
		S_background = new Sprite(R_background);
		S_background.setScale(0.52f);
		S_background.setOrigin(0, 0);
		S_background.setPosition(0, 0);

		S_planet = new Sprite(R_planet);
		S_planet.setScale(0.32f);
		S_planet.setOrigin(50, 50);
		S_planet.setPosition(540, 200);

	}

	@Override
	public void render(float delta) {

		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

		batch.setProjectionMatrix(camera.combined);
		batch.begin();

		S_background.draw(batch);
		S_planet.draw(batch);
		F_debug.draw(batch,
				"X: " + Gdx.input.getX() + ", Y: " + (Gdx.input.getY()) + ".",
				5, 20);

		F_buttonsOutline3.setColor(1, 1, 1, buttonHighlight[0]);
		F_buttonsOutline3.draw(batch, "Play", 124, 397);
		F_buttonsOutline1.setColor(1, 1, 1, buttonHighlight[1]);
		F_buttonsOutline1.draw(batch, "Stats", 162, 310);
		F_buttonsOutline2.setColor(1, 1, 1, buttonHighlight[2]);
		F_buttonsOutline2.draw(batch, "Settings", 238, 240);
		F_buttonsOutline0.setColor(1, 1, 1, buttonHighlight[3]);
		F_buttonsOutline0.draw(batch, "Exit", 376, 166);

		F_buttons.draw(batch, "Play", 124, 396);
		F_buttons.draw(batch, "Stats", 162, 310);
		F_buttons.draw(batch, "Settings", 238, 240);
		F_buttons.draw(batch, "Exit", 376, 166);

		// Button highlight handling

		game.standardProcessor.resolveButtons(Gdx.input.getX(),
				Gdx.input.getY());
		for (int a = 0; a < 4; a++)
			if (game.mainMenuScreen.buttonHighlight[a] >= 0.005f)

				game.mainMenuScreen.buttonHighlight[a] = game.mainMenuScreen.buttonHighlight[a] - 0.005f;
			else
				game.mainMenuScreen.buttonHighlight[a] = 0f;

		batch.end();

	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void show() {

	}

	@Override
	public void hide() {

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose() {
		texture.dispose();
		batch.dispose();
		F_buttons.dispose();
		F_buttonsHighlight.dispose();
		F_debug.dispose();

	}

}
