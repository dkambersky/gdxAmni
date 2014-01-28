/** The MainMenuScreen.java class responsible for 
 *
 * @author xCabbage [github.com/xcabbage]
 *
 * @info for the Amniora project [github.com/xcabbage/amniora]
 *      created 1. 10. 2013 10:08:59
 */

package me.xcabbage.amniora.screen;

import me.xcabbage.amniora.GameAmn;
import me.xcabbage.amniora.input.AmniInputProcessor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * @author David
 * 
 */
public class MainMenuScreen implements Screen {
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Texture texture;
	private Sprite S_background, S_planet;
	private TextureRegion R_background, R_planet;
	BitmapFont F_debug, F_buttons, F_buttonsHighlight;
	GameAmn game;
	private boolean fontHighlight;

	public MainMenuScreen(final GameAmn gam) {
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
		F_debug.draw(batch, "X: " + Gdx.input.getX() + ", Y: "
				+ (690 - Gdx.input.getY()) + ".", 5, 20);

		F_buttons.draw(batch, "Play", 124, 396);
		F_buttons.draw(batch, "Stats", 162, 310);
		F_buttons.draw(batch, "Settings", 238, 240);
		F_buttons.draw(batch, "Exit", 376, 166);
		System.out.println(Gdx.input.getInputProcessor());
		batch.end();

		if (Gdx.input.isKeyPressed(Keys.LEFT)) {
			game.setScreen(new GameplayScreen(game));
			dispose();
		}

		if (Gdx.input.isKeyPressed(Keys.F)) {
			fontHighlight = !fontHighlight;

		}
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
