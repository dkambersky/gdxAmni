package me.xcabbage.amniora.input;

import me.xcabbage.amniora.GameAmn;
import me.xcabbage.amniora.GameInstance;
import me.xcabbage.amniora.screen.GameplayScreen;
import me.xcabbage.amniora.screen.MainMenuScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

public class AmniInputProcessor implements InputProcessor {
	GameAmn game;
	public Screen activeScreen;
	public boolean rotating;
	static public boolean consoleEnabled, consoleActive;

	static Stage stage;
	static TextField console_textfield;

	public int direction;
	int buttonPixels[][] = { { 116, 290, 236, 320, }, { 160, 370, 302, 408, },

	{ 236, 448, 436, 482, }, { 370, 518, 468, 556 }

	};
	private Camera cam;

	public AmniInputProcessor(final GameAmn gam) {
		game = gam;

	}

	@Override
	public boolean keyDown(int keycode) {
		if (!consoleActive) {
			switch (keycode) {
			case Input.Keys.O:
				rotating = true;
				direction = 1;
				return true;
			case Input.Keys.P:
				rotating = true;
				direction = -1;
				return true;
			case Input.Keys.K:
				rotating = true;
				direction = 2;
				return true;
			case Input.Keys.L:
				rotating = true;
				direction = 3;
				return true;
			case Input.Keys.ESCAPE:
				game.dispose();
				Gdx.app.exit();
				return true;
			default:
				return false;

			}

		}
		return true;
	}

	public void setCamera(Camera cam, CameraInputController controller) {
		this.cam = cam;
	}

	@Override
	public boolean keyUp(int keycode) {
		if (!consoleActive) {
			switch (keycode) {
			case Input.Keys.O:
				rotating = false;
				return true;
			case Input.Keys.P:
				rotating = false;
				return true;
			case Input.Keys.K:
				rotating = false;
				return true;
			case Input.Keys.L:
				rotating = false;
				return true;
			default:
				return false;
			}
		}
		return false;
	}

	@Override
	public boolean keyTyped(char character) {

		if (!consoleActive) {
			switch (character) {
			case 'w':
				scrolled(-1);
				break;
			case 's':
				scrolled(1);
				break;
			case 'r':
				if (game.currentScreen == 2)
					((GameplayScreen) game.getScreen()).updateGame();
				break;
			case ' ':
				try {
					buttonClicked(((MainMenuScreen) activeScreen).activeButton);
				} catch (Exception e) {
					;
				}
				break;
			case 'g':
				try {
					GameInstance instance = ((GameplayScreen) game.getScreen()).instance;
					instance.recolourInProgress = !instance.recolourInProgress;

				} catch (Exception e) {
					GameAmn.error(e.getLocalizedMessage());
					GameAmn.error(e.getStackTrace());
				}

				break;
			case ';':
				consoleActive = !consoleActive;
				System.out.println(consoleActive + ": console");
				if (stage != null && stage.getKeyboardFocus() == null) {
					stage.setKeyboardFocus(console_textfield);

				} else {
					stage.setKeyboardFocus(null);

					console_textfield.setText("Enter commands...");
				}
				break;

			}

		}
		return false;
	}

	public void buttonClicked(int button) {
		switch (button) {
		case 0:
			System.out.println("Initializing Play");
			game.setScreen(new GameplayScreen(game));
			game.currentScreen = 2;
			game.mainMenuScreen.dispose();

			break;

		case 1:
			System.out.println("Opening stats: League for once.");
			System.out.println("Level: " + game.json.getLevel());
			System.out.println("Raw data below. ");
			game.json.dump();

			break;
		case 2:
			System.out.println("Initializing Play");
			break;
		case 3:
			System.out.println();
			System.out.println("Shutting down.");
			Gdx.app.exit();
			break;
		default:
			System.out.println("Unknown button pressed. Wth.");

		}
		((MainMenuScreen) activeScreen).activeButton = button;

	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {

		if (game.currentScreen == 0) {
			for (int a = 0; a < 4; a++) {
				if (hoveringOverButton(a, screenX, screenY)) {
					buttonClicked(a);
				}
			}
			System.out.println("No button was pressed.");
		}

		return false;
	}

	public boolean hoveringOverButton(int button, int screenX, int screenY) {
		if (buttonPixels[button][0] <= screenX
				&& buttonPixels[button][2] >= screenX)
			if (buttonPixels[button][1] <= screenY
					&& buttonPixels[button][3] >= screenY) {

				return true;
			}
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {

		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {

		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {

		return false;
	}

	public void resolveButtons(int screenX, int screenY) {

		try {
			if (game.currentScreen == 1) {
				for (int a = 0; a < 4; a++) {
					if (hoveringOverButton(a, screenX, screenY)
							|| a == ((MainMenuScreen) activeScreen).activeButton) {

						if (game.mainMenuScreen.buttonHighlight[a] <= game.mainMenuScreen.MAX_ALPHA - 0.03f) {
							game.mainMenuScreen.buttonHighlight[a] = game.mainMenuScreen.buttonHighlight[a] + 0.03f;

						} else
							game.mainMenuScreen.buttonHighlight[a] = game.mainMenuScreen.MAX_ALPHA;
					}
				}

			}
		} catch (Exception e) {
			GameAmn.error(e.getStackTrace());
		}

	}

	@Override
	public boolean scrolled(int amount) {

		if (game.currentScreen == 1) {
			try {

				MainMenuScreen activeScreen = (MainMenuScreen) this.activeScreen;
				if (activeScreen.activeButton == -1) {
					activeScreen.activeButton = 0;
					return true;
				} else if (activeScreen.activeButton == activeScreen.buttonCount - 1
						&& amount == 1)
					activeScreen.activeButton = 0;
				else if (activeScreen.activeButton == 0 && amount == -1)
					activeScreen.activeButton = activeScreen.buttonCount - 1;
				else {
					activeScreen.activeButton = activeScreen.activeButton
							+ amount;
					return true;

				}

			} catch (Exception e) {
				GameAmn.error(e.getStackTrace());
			}
		}
		return false;
	}

	public String toString() {
		return "sup";
	}

	public void update() {
		if (rotating)
			rotateCamera();

	}

	public void rotateCamera() {
		if (direction == 1) {
			cam.rotateAround(Vector3.Zero, new Vector3(0, 1, 0), 5f);
		} else if (direction == -1) {
			cam.rotateAround(Vector3.Zero, new Vector3(0, 1, 0), -5f);
		} else if (direction == 2) {
			cam.rotateAround(Vector3.Zero, new Vector3(0, 0, 1), 5f);
		} else if (direction == 3) {
			cam.rotateAround(Vector3.Zero, new Vector3(1, 0, 0), 5f);
		} else {
			System.out.println("Wrong parameter passed. Try, try again.");
		}
		cam.update();

	}

	public static void setStage(Stage stag, TextField field) {
		stage = stag;
		console_textfield = field;

	}

}