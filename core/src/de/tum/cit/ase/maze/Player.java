package de.tum.cit.ase.maze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;

public class Player extends Entity {

    private MazeRunnerGame game;
    private GameScreen gameScreen;
    private int state;

    public Player() {}

    public Player(GameScreen gameScreen, int mapX, int mapY) {
        this.gameScreen = gameScreen;
        this.game = gameScreen.getGame();
        this.mapX = mapX;
        this.mapY = mapY;
    }

    /**
     * Load animation for player with corresponding state: 0 (front), 1 (left), 2 (up), 3 (right), 4 (down).
     */
    public static Animation<TextureRegion> loadPlayer(int state) {

        int frameX = 0;
        int frameY = 0;
        int frameWidth = 16;
        int frameHeight = 32;
        int animationFrames = 4;

        switch (state) {
            case 0: frameX = 0; frameY = 0;  break;
            case 1: frameX = 0; frameY = 32; break;
            case 2: frameX = 0; frameY = 64;  break;
            case 3: frameX = 0; frameY = 96;  break;
        }

        Texture walkSheet = new Texture(Gdx.files.internal("character.png"));
        Array<TextureRegion> walkFrames = new Array<>(TextureRegion.class); // libGDX internal Array instead of ArrayList because of performance

        // Add all frames to the animation
        for (int col = 0; col < animationFrames; col++) {
            walkFrames.add(new TextureRegion(walkSheet, frameX + col * frameWidth, frameY, frameWidth, frameHeight));
        }

        return new Animation<>(0.1f, walkFrames);
    }

    /**
     * Guide the player to act on the stage.
     */
    @Override
    public void act(float delta){
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Clear the screen

        if (Collision.isTrapCollision(gameScreen.getMapTable(), mapX, mapY)) {game.goToEnd();}
        else if (Collision.isEnemyCollision(gameScreen.getMapTable(), mapX, mapY)) {game.goToEnd();}
        else if (Collision.isLiveCollision(gameScreen.getMapTable(), mapX, mapY)) {game.numberLives++;}

        // Check for key press
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            state = 2;
            if (mapY <= 64*game.getMap().getMapHeight()) {
                if (!Collision.isWallCollisionUp(gameScreen.getBackgroundTable(), mapX, mapY)) {mapY += speed;}
            }
//            gameScreen.getCamera().translate(0, speed);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            state = 0;
            if (mapY >= 64) {
                if (!Collision.isWallCollisionDown(gameScreen.getBackgroundTable(), mapX, mapY)) {mapY -= speed;}
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            state = 3;
            if (mapX >= 64) {
                if (!Collision.isWallCollisionLeft(gameScreen.getBackgroundTable(), mapX, mapY)) {mapX -= speed;}
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            state = 1;
            if (mapX <= 64*game.getMap().getMapWidth()) {
                if (!Collision.isWallCollisionRight(gameScreen.getBackgroundTable(), mapX, mapY)) {mapX += speed;}
            }
        }

        time += delta;
        animation = loadPlayer(state);
        currentRegion = (TextureRegion) animation.getKeyFrame(time, true);
    }

    /**
     * Guide the player to be drawn on the stage.
     */
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(currentRegion, mapX, mapY, 64, 128);
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
