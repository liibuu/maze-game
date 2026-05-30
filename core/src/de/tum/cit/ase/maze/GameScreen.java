package de.tum.cit.ase.maze;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * The GameScreen class is responsible for rendering the gameplay screen.
 * It handles the game logic and rendering of the game elements.
 */
public class GameScreen implements Screen {

    private final Stage stage;
    private final Table mapTable;
    private final Table backgroundTable;
    private final Table menuTable;
    private final MazeRunnerGame game;
    private final OrthographicCamera camera;
    private final BitmapFont font;

    private final Player player;
    float time = 0f;
    final int initialViewPortWidth;
    final int initialViewPortHeight;

    final TextArea textLives;
    final TextArea textKeys;

    /**
     * Constructor for GameScreen. Sets up the camera and font.
     *
     * @param game The main game class, used to access global resources and methods.
     */
    public GameScreen(MazeRunnerGame game) {
        this.game = game;

        /* Step 1: Set up the screen */

        // Create and configure the camera for the game view
        camera = new OrthographicCamera();
        camera.setToOrtho(false);
        camera.zoom = 1.5f; // further view

        // Get the font from the game's skin
        font = game.getSkin().getFont("font");

        Viewport viewport = new ScreenViewport(camera); // Create a viewport with the camera
        stage = new Stage(viewport, game.getSpriteBatch()); // Create a stage for UI elements
        initialViewPortWidth = viewport.getScreenWidth();
        initialViewPortHeight = viewport.getScreenHeight();

        /* Step 2: Add UI elements based on the map */

        // Layout 1 - background entities

        backgroundTable = new Table(); // Create a table for layout
        backgroundTable.setFillParent(true); // Make the table fill the stage
        stage.addActor(backgroundTable); // Add the table to the stage

        for (int i = 0; i < game.getMap().getMapWidth(); i++) { // Add walls and paths
            for (int j = 0; j < game.getMap().getMapHeight(); j++) {
                switch (game.getMap().getTiles()[i][j]) {
                    case 0: // if code equals 0 then add a wall
                        backgroundTable.addActor(new Wall(64 * (i + 1), 64 * (j + 1)));
                        break;
                    default: // else add a path
                        backgroundTable.addActor(new Path(64 * (i + 1), 64 * (j + 1)));
                }
            }
        }

        for (int i = 0; i < game.getMap().getMapWidth()+2; i++) { // Add trees (boundaries)
            backgroundTable.addActor(new Tree(64*i, 0)); // bottom of the map
            backgroundTable.addActor(new Tree(64*i, 64*(game.getMap().getMapHeight()+1))); // top of the map
        }

        for (int j = 1; j < game.getMap().getMapWidth()+1; j++) { // Add boundaries
            backgroundTable.addActor(new Tree(0, 64*j)); // left edge of the map
            backgroundTable.addActor(new Tree(64*(game.getMap().getMapWidth()+1), 64*j)); // right edge of the map
        }

        // Layout 2 - live entities

        mapTable = new Table(); // Create a table for layout
        mapTable.setFillParent(true); // Make the table fill the stage
        stage.addActor(mapTable); // Add the table to the stage

        int playerInitialX = 0; // Initialize x,y for the player
        int playerInitialY = 0;

        for (int i = 0; i < game.getMap().getMapWidth(); i++) { // Add dynamic things
            for (int j = 0; j < game.getMap().getMapHeight(); j++) {

                switch (game.getMap().getTiles()[i][j]) {
                    case 1: // if code equals 1 then add an entry
                        playerInitialX = 64*(i + 1); // the player starts at the entry point
                        playerInitialY = 64*(j + 1);
                        mapTable.addActor(new Entry(playerInitialX, playerInitialY));
                        camera.translate(playerInitialX, playerInitialY); // camera focuses on the player
                        break;
                    case 2: // if code equals 2 then add an exit
                        mapTable.addActor(new Exit(64 * (i + 1), 64 * (j + 1)));
                        break;
                    case 3: // if code equals 3 then add a trap
                        mapTable.addActor(new Trap(64 * (i + 1), 64 * (j + 1)));
                        break;
                    case 4: // if code equals 4 then add an enemy
                        mapTable.addActor(new Enemy(64 * (i + 1), 64 * (j + 1), this));
                        break;
                    case 5: // if code equals 5 then add a key
                        mapTable.addActor(new Key(64 * (i + 1), 64 * (j + 1)));
                        break;
                    case 6: // if code equals 6 then add a live
                        mapTable.addActor(new Live(64 * (i + 1), 64 * (j + 1)));
                        break;
                    default:
                        break;
                }
            }
        }

        // Add player
        player = new Player(playerInitialX, playerInitialY, this); // Add player
        mapTable.addActor(player);

        // Layout 3 - menu & information

        menuTable = new Table(); // Create a table for layout
        menuTable.setFillParent(true); // Make the table fill the stage
        stage.addActor(menuTable); // Add the table to the stage

        textLives = new TextArea("Lives:  ", game.getSkin());
        menuTable.addActor(textLives);
        textLives.setX(670);
        textLives.setY(-15);
        textLives.setHeight(60);

        textKeys = new TextArea("Keys:  ", game.getSkin());
        menuTable.addActor(textKeys);
        textKeys.setX(textLives.getX());
        textKeys.setY(textLives.getY()-60);
        textKeys.setHeight(60);
    }


    // Screen interface methods with necessary functionality
    @Override
    public void render(float delta) {

        ScreenUtils.clear(0, 0, 0, 1); // Clear the screen
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f)); // Update the stage
        // Update labels with current game data
        textLives.setText("Lives: " + game.numberLives);
        textKeys.setText("Keys: " + game.numberKeys);
        stage.draw(); // Draw the stage

        camera.update(); // Update the camera
    }

    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false);
        stage.getViewport().update(width,height,true);

        menuTable.getChild(0).setX(670 + initialViewPortWidth/1.3f - Gdx.graphics.getWidth()/1.3f);
        menuTable.getChild(0).setY(-15 - initialViewPortHeight/1.3f + Gdx.graphics.getHeight()/1.3f);
        menuTable.getChild(1).setX(menuTable.getChild(0).getX());
        menuTable.getChild(1).setY(menuTable.getChild(0).getY() - 60);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void show() {
        MenuScreen.backgroundMusic.play();

        // Set the input processor so the stage can receive input events
        Gdx.input.setInputProcessor(stage);

        // Set up an input processor to listen for key presses
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {
                if (keycode == Input.Keys.ESCAPE) {
                    // Go to the pause screen when 'Esc' is pressed
                    game.goToPause();
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        // Dispose of the stage when screen is disposed
        stage.dispose();
    }

    // Additional methods and logic can be added as needed for the game screen

    public Stage getStage() {
        return stage;
    }
    public OrthographicCamera getCamera() {
        return camera;
    }
    public MazeRunnerGame getGame() {
        return game;
    }
    public Table getMapTable() {
        return mapTable;
    }
    public Table getBackgroundTable() {
        return backgroundTable;
    }
    public Table getMenuTable() {
        return menuTable;
    }
}