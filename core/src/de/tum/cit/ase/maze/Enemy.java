package de.tum.cit.ase.maze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class Enemy extends Entity {
    private GameScreen gameScreen;
    private int state;

    public Enemy() {}

    public Enemy(GameScreen gameScreen, int mapX, int mapY) {
        this.gameScreen = gameScreen;
        this.mapX = mapX;
        this.mapY = mapY;
    }

    /**
     * Load animation for player with corresponding state: 0 (front), 1 (left), 2 (up), 3 (right), 4 (down).
     */
    public static Animation<TextureRegion> loadEnemy(int state) {

        int frameX = 0;
        int frameY = 0;
        int frameWidth = 16;
        int frameHeight = 16;
        int animationFrames = 3;

        switch (state) {
            case 0: frameX = 96; frameY = 80; break;
            case 1: frameX = 96; frameY = 96; break;
        }

        Texture walkSheet = new Texture(Gdx.files.internal("mobs.png"));
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
        time += delta;
        animation = loadEnemy(state);
        currentRegion = (TextureRegion) animation.getKeyFrame(time, true);
    }

    /**
     * Guide the tile to be drawn on the stage.
     */
    public void draw(Batch batch, float parentAlpha) {
        gameScreen.sinusInput += parentAlpha;
        super.draw(batch, parentAlpha);

        if (gameScreen.sinusInput % 600 <= 299) {
            state = 1;
            batch.draw(currentRegion, mapX + gameScreen.sinusInput % 300, mapY, 64, 64);
        }
        else {
            state = 0;
            batch.draw(currentRegion, mapX + 300 - gameScreen.sinusInput % 300, mapY, 64, 64);
        }
    }
}
