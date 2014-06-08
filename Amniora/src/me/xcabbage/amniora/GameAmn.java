package me.xcabbage.amniora;

import net.enigmablade.riotapi.constants.Region;
import me.xcabbage.amniora.apis.Json;
import me.xcabbage.amniora.input.AmniInputProcessor;
import me.xcabbage.amniora.screen.*;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;

public class GameAmn extends Game {

	public MainMenuScreen mainMenuScreen;
	public GameplayScreen gameplayScreen;
	public InputMultiplexer multiplexer;
	public int currentScreen = -1;
	public AmniInputProcessor standardProcessor;
	public Json json;

	@Override
	public void create() {
		// Riot API

		json = new Json();
		json.setSummoner("Davefin", Region.EUNE);

		// Input processing
		standardProcessor = new AmniInputProcessor(this);
		multiplexer = new InputMultiplexer();
		multiplexer.addProcessor(standardProcessor);

		Gdx.input.setInputProcessor(multiplexer);

		// Screen creation
		mainMenuScreen = new MainMenuScreen(this);

		gameplayScreen = new GameplayScreen(this);
		standardProcessor.activeScreen = mainMenuScreen;
		setScreen(mainMenuScreen);

	}

	@Override
	public void dispose() {

	}

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
}
