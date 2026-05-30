package de.tum.cit.ase.maze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import games.spooky.gdx.nativefilechooser.NativeFileChooserCallback;
import games.spooky.gdx.nativefilechooser.NativeFileChooserConfiguration;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Properties;

/**
 * The MenuScreen class is responsible for displaying the main menu of the game.
 * It extends the LibGDX Screen class and sets up the UI components for the menu.
 */
public class StartScreen implements Screen {

    private final MazeRunnerGame game;
    private final Stage stage;
    private Sound menuSound;

    /**
     * Constructor for MenuScreen. Sets up the camera, viewport, stage, and UI elements.
     *
     * @param game The main game class, used to access global resources and methods.
     */
    public StartScreen(MazeRunnerGame game) {
        this.game = game;
        var camera = new OrthographicCamera();
        camera.zoom = 1.5f; // further view

        Viewport viewport = new ScreenViewport(camera); // Create a viewport with the camera
        stage = new Stage(viewport, game.getSpriteBatch()); // Create a stage for UI elements

        Table table = new Table(); // Create a table for layout
        table.setFillParent(true); // Make the table fill the stage
        stage.addActor(table); // Add the table to the stage

        // Add a label as a title
        table.add(new Label("Choose the level!", game.getSkin(), "title")).padBottom(80).row();

        // Create and add a button to go to the game screen
        TextButton levelButton = new TextButton("Choose", game.getSkin());
        table.add(levelButton).width(300).row();

        levelButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // Configure
                NativeFileChooserConfiguration conf = new NativeFileChooserConfiguration();

                // Starting from maps' directory
                conf.directory = Gdx.files.absolute("C:\\Users\\Lenovo\\IdeaProjects\\itp2324itp2324projectwork-thu3mu2ryukionishiandbichlienbui\\maps");

                // Filter out all files which do not have the .properties extension
                conf.nameFilter = new FilenameFilter() {
                    @Override
                    public boolean accept(File dir, String name) {
                        return name.endsWith("properties");
                    }
                };

                // Add a nice title
                conf.title = "Choose map file";

                game.getFileChooser().chooseFile(conf, new NativeFileChooserCallback() {
                    @Override
                    public void onFileChosen(FileHandle file) {
                        // Do stuff with file, yay!
                        Properties prop = new Properties();
                        try {
                            prop.load(file.reader());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }

                        game.setMap(new TileMap(prop));
                        game.goToGame();
                    }

                    @Override
                    public void onCancellation() {
                        // Warn user how rude it can be to cancel developer's effort
                    }

                    @Override
                    public void onError(Exception exception) {
                        // Handle error (hint: use exception type)
                    }
                });
            }
        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Clear the screen
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f)); // Update the stage
        stage.draw(); // Draw the stage
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true); // Update the stage viewport on resize
    }

    @Override
    public void dispose() {
        // Dispose of the stage when screen is disposed
        stage.dispose();
    }

    @Override
    public void show() {
        game.stopBackgroundMusic();
        menuSound = Gdx.audio.newSound(Gdx.files.internal("menu.ogg"));
        menuSound.play();

        // Set the input processor so the stage can receive input events
        Gdx.input.setInputProcessor(stage);
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
        if (menuSound != null) {
            menuSound.dispose();
        }
    }
}
