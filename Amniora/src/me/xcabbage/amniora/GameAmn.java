package me.xcabbage.amniora;

import net.enigmablade.riotapi.constants.Region;
import me.xcabbage.amniora.apis.Json;
import me.xcabbage.amniora.input.AmniInputProcessor;
import me.xcabbage.amniora.screen.*;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;

public class GameAmn extends Game {
	// Global constants
	public static final boolean PRINT_ERRORS = false;
	public static final boolean DEBUG_TEXTURES = true;
	public static final boolean PRINT_ALERTS = true;

	public MainMenuScreen mainMenuScreen;
	public GameplayScreen gameplayScreen;
	public InputMultiplexer multiplexer;
	public int currentScreen = -1;
	public AmniInputProcessor standardProcessor;
	public Json json;
	public static GameInstance inst;
	private static int regDirection;
	private static boolean regActive;

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

	// CONSOLE
	/**
	 * @param text
	 */
 	public static void sendConsole(String text) {

		// Chop up the args
		String[] args = text.split(" ");

		// Handle the command
		switch (args[0]) {
		// / Debugging print
		case "ping":
			if (args.length == 1) {
				// Bukkit's console easter egg tribute
				System.out.println("Pong. I hear Thrax likes cute asian boys.");
			} else {
				int pingArgs = 0;
				try {
					pingArgs = Integer.parseInt(args[1]);
				} catch (NumberFormatException e) {
					System.out
							.println("Wrong parameter passed to ping command");
				} finally {
					for (int a = 0; a < pingArgs; a++) {
						System.out.println("Pong!");
					}
				}
			}
			break;
		// / Kill the app
		case "kill":
			System.out.println("Killing myself");
			Gdx.app.exit();
			break;

		case "reg":
			regActive = true;
			if (args[1].equalsIgnoreCase("end")) {
				regActive = false;

			} else {

				try {
					regDirection = Integer.parseInt(args[1]);
				} catch (NumberFormatException e) {
					GameAmn.error(e.getStackTrace());
					GameAmn.alert("Wrong parameter passed to reg command: "
							+ args[1]);
				}
			}
			System.out.println("Reg " + regActive + " in direction of "
					+ regDirection);
			break;

		}
	}

	// ERRORS & ALERTS
	public static void alert(String text) {
		// TODO as with errors - fluff, popups?
		if (PRINT_ALERTS) {
			System.out.println(text);
		}
	}

	/**
	 * @param string
	 */
	public static void error(String string) {
		// TODO popups? logs? fluff?
		if (PRINT_ERRORS) {
			System.out.println(string);
		}

	}

	/**
	 * @param string
	 */
	public static void error(StackTraceElement[] trace) {
		// TODO popups? logs? fluff?
		if (PRINT_ERRORS) {

			System.out.println(trace);
		}

	}

}