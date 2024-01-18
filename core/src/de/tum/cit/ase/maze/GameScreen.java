package de.tum.cit.ase.maze;

import com.badlogic.gdx.*;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.List;

/**
 * The GameScreen class is responsible for rendering the gameplay screen.
 * It handles the game logic and rendering of the game elements.
 */
public class GameScreen implements Screen {

    private final Stage stage;
    private final Table mapTable;
    private final Table backgroundTable;
    private final MazeRunnerGame game;
    private final OrthographicCamera camera;
    private final BitmapFont font;

    private final Player player;

    Sound stepSoundRock;
    Sound stepSoundWater;
    long endPauseTime = 0;
    float sinusInput = 0f;

    /**
     * Constructor for GameScreen. Sets up the camera and font.
     *
     * @param game The main game class, used to access global resources and methods.
     */
    public GameScreen(MazeRunnerGame game) {
        this.game = game;

        // Create and configure the camera for the game view
        camera = new OrthographicCamera();
        camera.setToOrtho(false);
        camera.zoom = 1.5f; // further view

        // Get the font from the game's skin
        font = game.getSkin().getFont("font");

        Viewport viewport = new ScreenViewport(camera); // Create a viewport with the camera
        stage = new Stage(viewport, game.getSpriteBatch()); // Create a stage for UI elements

        // Layout 1: path + wall
        backgroundTable = new Table(); // Create a table for layout
        backgroundTable.setFillParent(true); // Make the table fill the stage
        stage.addActor(backgroundTable); // Add the table to the stage

        for (int i = 0; i < game.getMap().getMapWidth(); i++) { // Add walls and paths
            for (int j = 0; j < game.getMap().getMapHeight(); j++) {
                switch (game.getMap().getTiles()[i][j]) {
                    case 0:
                        Wall wall = new Wall();
                        wall.setX(64 * (i + 1));
                        wall.setY(64 * (j + 1));
                        backgroundTable.addActor(wall);
                        break;
                    default:
                        Path path = new Path();
                        path.setX(64 * (i + 1));
                        path.setY(64 * (j + 1));
                        backgroundTable.addActor(path);
                }
            }
        }

        for (int i = 0; i < game.getMap().getMapWidth()+2; i++) { // Add boundaries
            Tree upBoundary = new Tree();
            Tree downBoundary = new Tree();
            upBoundary.setX(64*i);
            upBoundary.setY(0);
            downBoundary.setX(64*i);
            downBoundary.setY(64*(game.getMap().getMapHeight()+1));
            backgroundTable.addActor(upBoundary);
            backgroundTable.addActor(downBoundary);
        }

        for (int j = 1; j < game.getMap().getMapWidth()+1; j++) { // Add boundaries
            Tree leftBoundary = new Tree();
            Tree rightBoundary = new Tree();
            leftBoundary.setX(0);
            leftBoundary.setY(64*j);
            rightBoundary.setX(64*(game.getMap().getMapWidth()+1));
            rightBoundary.setY(64*j);
            backgroundTable.addActor(leftBoundary);
            backgroundTable.addActor(rightBoundary);
        }

        // Layout 2: trap + entry point + exit + enemy + key + live
        mapTable = new Table(); // Create a table for layout
        mapTable.setFillParent(true); // Make the table fill the stage
        stage.addActor(mapTable); // Add the table to the stage

        int playerInitialX = 64;
        int playerInitialY = 64;
        for (int i = 0; i < game.getMap().getMapWidth(); i++) {
            for (int j = 0; j < game.getMap().getMapHeight(); j++) {

                switch (game.getMap().getTiles()[i][j]) {
                    case 1:
                        playerInitialX = 64*(i + 1);
                        playerInitialY = 64*(j + 1);
                        camera.translate(64*(i + 1), 64*(j + 1));
                        break;
                    case 2:
                        break;
                    case 3:
                        Trap trap = new Trap();
                        trap.setX(64 * (i + 1));
                        trap.setY(64 * (j + 1));
                        mapTable.addActor(trap);
                        break;
                    case 4:
                        Enemy enemy = new Enemy(this, 64*(i+1), 64*(j+1));
                        mapTable.addActor(enemy);
                        break;
                    case 5:
                        Key key = new Key();
                        key.setX(80);
                        key.setY(80);
                        mapTable.addActor(key);
                        break;
                    default:
                        break;
                }
            }
        }

        // Add player
        player = new Player(this, playerInitialX, playerInitialY);
        mapTable.addActor(player);

        // Event sound
        stepSoundRock = Gdx.audio.newSound(Gdx.files.internal("sfx_step_rock_l.mp3"));
        stepSoundWater = Gdx.audio.newSound(Gdx.files.internal("sfx_step_water_l.mp3"));

//
//
//
//        for (int i = 0; i < game.numberLives; i ++) {
//            Live live = new Live();
//            live.setX(512 + 64*i);
//            live.setY(512);
//            mapTable.addActor(live);
//        }
//


        // Lay out 3
        Table table = new Table(); // Create a table for layout
        table.setFillParent(true); // Make the table fill the stage
        stage.addActor(table); // Add the table to the stage

        TextButton goToMenuButton = new TextButton("Back", this.game.getSkin()); // Create and add a button to go to the menu screen
        table.add(goToMenuButton).pad(0,0,0,0).width(300).row();
        goToMenuButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.goToMenu(); // Change to the game screen when button is pressed
            }
        });

        TextArea textArea = new TextArea("Lives: " + game.numberLives, game.getSkin());
        table.add(textArea);

//        if (backgroundTable.hit(128,128,true) instanceof Tile) {
//            System.out.println("meomeo");
//        }

//        System.out.println(backgroundTable.getChild(15*15 - 1 + 1).getX());
//        System.out.println(backgroundTable.getChild(15*15 - 1 + 1).getY());
//        Live live = new Live();
//        live.setX(512);
//        live.setY(512);
//        stage.addActor(live);
//        System.out.println(stage.hit(512,512,false).getClass().getSimpleName());
//        System.out.println(Collision.isTrapCollision(mapTable, 580, 448));
//        System.out.println(key.hit(64, 64, false).getX());

    }

    // Screen interface methods with necessary functionality
    @Override
    public void render(float delta) {

        ScreenUtils.clear(0, 0, 0, 1); // Clear the screen
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f)); // Update the stage
        stage.draw(); // Draw the stage

        camera.update(); // Update the camera

        // Move text in a circular path to have an example of a moving object
//        sinusInput += delta;
//        float textX = (float) (camera.position.x + Math.sin(sinusInput) * 100);
//        float textY = (float) (camera.position.y + Math.cos(sinusInput) * 100);
        // Set up and begin drawing with the sprite batch
//        game.getSpriteBatch().setProjectionMatrix(camera.combined);
        game.getSpriteBatch().begin(); // Important to call this before drawing anything
        font.draw(game.getSpriteBatch(), player.localToStageCoordinates(new Vector2(player.mapX, player.mapY)).toString(), 128, 128);
        game.getSpriteBatch().end(); // Important to call this after drawing everything
    }

    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void show() {
        // Set the input processor so the stage can receive input events
        Gdx.input.setInputProcessor(stage);
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
}
