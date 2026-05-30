package de.tum.cit.ase.maze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * The MenuScreen class is responsible for displaying the main menu of the game.
 * It extends the LibGDX Screen class and sets up the UI components for the menu.
 */
public class MenuScreen implements Screen {

    public static Music backgroundMusic;
    private final Stage stage;
    private final SpriteBatch batch;
    private final MazeRunnerGame game;
    private final OrthographicCamera camera;

    private float time = 0f;

    private Animation<TextureRegion> characterAnimationLeft;
    private Animation<TextureRegion> characterAnimationRight;

    /**
     * Constructor for MenuScreen. Sets up the camera, viewport, stage, and UI elements.
     *
     * @param game The main game class, used to access global resources and methods.
     */
    public MenuScreen(MazeRunnerGame game) {
        this.batch = new SpriteBatch();
        this.game = game;
        characterAnimationLeft = Player.animationRight;
        characterAnimationRight = Player.animationLeft;

        // Create and configure the camera for the game view
        camera = new OrthographicCamera();
        camera.setToOrtho(false);
        camera.zoom = 1.5f; // further view

        Viewport viewport = new ScreenViewport(camera); // Create a viewport with the camera
        stage = new Stage(viewport, batch); // Create a stage for UI elements

        Table table = new Table(); // Create a table for layout
        table.setFillParent(true); // Make the table fill the stage
        stage.addActor(table); // Add the table to the stage

        // Add a label as a title
        table.add(new Label("Hello World from the Menu!", this.game.getSkin(), "title")).padBottom(80).row();

        // Create and add a button to go to the game screen
        TextButton goToStartButton = new TextButton("Start", this.game.getSkin());
        table.add(goToStartButton).width(300).row();
        goToStartButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.goToStart(); // Change to the game screen when button is pressed
            }
        });

        // Play some background music
        if (backgroundMusic == null) {
            backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("background.mp3"));
            backgroundMusic.setVolume(0.3f);
            backgroundMusic.setLooping(true);
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Clear the screen
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f)); // Update the stage
        stage.draw(); // Draw the stage

        // Set up and begin drawing with the sprite batch
        time += delta;
        batch.setProjectionMatrix(camera.combined);
        batch.begin(); // Important to call this before drawing anything

        // Draw the character
        if ((time * 100) % 2808 <= 1404) {
            batch.draw(characterAnimationLeft.getKeyFrame(time, true), 36 + (time * 100) % 1404, 504, 64, 128);
        }
        else {
            batch.draw(characterAnimationRight.getKeyFrame(time, true), 1440 - (time * 100) % 1404, 504, 64, 128);
        }
        batch.end(); // Important to call this after drawing everything
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true); // Update the stage viewport on resize
    }

    @Override
    public void dispose() {
        // Dispose of the stage when screen is disposed
        stage.dispose();
        backgroundMusic.stop();
    }

    @Override
    public void show() {
        // Play the music only if it's not already playing
        if (!backgroundMusic.isPlaying()) {
            startBackgroundMusic();
        }
        // Set the input processor so the stage can receive input events
        Gdx.input.setInputProcessor(stage);
    }

    public void startBackgroundMusic() {
        if (backgroundMusic != null && !backgroundMusic.isPlaying()) {
            backgroundMusic.play();
        }
    }

    // The following methods are part of the Screen interface but are not used in this screen.
    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }
}
