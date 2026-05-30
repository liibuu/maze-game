package de.tum.cit.ase.maze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class Enemy extends Entity {
    private final MazeRunnerGame game;
    private final GameScreen gameScreen;
    private final CollisionEnemy collisionEnemy;
    private int state;
    private static final Texture walkSheet = new Texture(Gdx.files.internal("mobs.png"));

    // extract the enemy image pointing at left from "mobs.png"
    private static final Animation<TextureRegion> animationLeft = new Animation<>(0.1f,
            new Array<>(new TextureRegion[]{
                    new TextureRegion(walkSheet, 96 + 0 * 16, 80, 16, 16),
                    new TextureRegion(walkSheet, 96 + 1 * 16, 80, 16, 16),
                    new TextureRegion(walkSheet, 96 + 2 * 16, 80, 16, 16)}));

    // extract the enemy image pointing at right from "mobs.png"
    private static final Animation<TextureRegion> animationRight = new Animation<>(0.1f,
            new Array<>(new TextureRegion[]{
                    new TextureRegion(walkSheet, 96 + 0 * 16, 96, 16, 16),
                    new TextureRegion(walkSheet, 96 + 1 * 16, 96, 16, 16),
                    new TextureRegion(walkSheet, 96 + 2 * 16, 96, 16, 16)}));

    // extract the enemy image pointing at down from "mobs.png"
    private static final Animation<TextureRegion> animationDown = new Animation<>(0.1f,
            new Array<>(new TextureRegion[]{
                    new TextureRegion(walkSheet, 96 + 0 * 16, 64, 16, 16),
                    new TextureRegion(walkSheet, 96 + 1 * 16, 64, 16, 16),
                    new TextureRegion(walkSheet, 96 + 2 * 16, 64, 16, 16)}));

    // extract the enemy image pointing at up from "mobs.png"
    private static final Animation<TextureRegion> animationUp = new Animation<>(0.1f,
            new Array<>(new TextureRegion[]{
                    new TextureRegion(walkSheet, 96 + 0 * 16, 112, 16, 16),
                    new TextureRegion(walkSheet, 96 + 1 * 16, 112, 16, 16),
                    new TextureRegion(walkSheet, 96 + 2 * 16, 112, 16, 16)}));

    public Enemy(float x, float y, GameScreen gameScreen) {
        super(x,y);
        this.gameScreen = gameScreen;
        this.game = gameScreen.getGame();
        this.speed = 4;
        mapX = x;
        mapY = y;
        this.collisionEnemy = new CollisionEnemy(game.getMap().getMapWidth(), game.getMap().getMapHeight());
    }

    public static TextureRegion loadEnemy(int state, float time) {
        switch (state) {
            case 0: return animationDown.getKeyFrame(time, true);
            case 1: return animationRight.getKeyFrame(time, true);
            case 2: return animationUp.getKeyFrame(time, true);
            case 3: return animationLeft.getKeyFrame(time, true);
        }
        return null;
    }

    /**
     * Instructions for the entity to act on the stage.
     */
    @Override
    public void act(float delta){

        time += delta;
        currentRegion = loadEnemy(state, time);

        // Check for collision with walls
        if (state == 2) {
            if (mapY <= 64*game.getMap().getMapHeight() - 64) {
                if (!collisionEnemy.isWallCollisionUp(gameScreen.getBackgroundTable(), mapX, mapY)) {
                    mapY += speed;
                }
            }
        }
        else if (state == 0) {
            if (mapY >= 64 + 64) {
                if (!collisionEnemy.isWallCollisionDown(gameScreen.getBackgroundTable(), mapX, mapY)) {
                    mapY -= speed;
                }
            }
        }
        else if (state == 3) {
            if (mapX >= 64 + 64) {
                if (!collisionEnemy.isWallCollisionLeft(gameScreen.getBackgroundTable(), mapX, mapY)) {
                    mapX -= speed;
                }
            }
        }
        else if (state == 1) {
            if (mapX <= 64*game.getMap().getMapWidth() - 64) {
                if (!collisionEnemy.isWallCollisionRight(gameScreen.getBackgroundTable(), mapX, mapY)) {
                    mapX += speed;
                }
            }
        }
    }

    /**
     * Instructions for the entity to be drawn on the stage.
     */
    public void draw(Batch batch, float parentAlpha) {
        gameScreen.time += parentAlpha;
        super.draw(batch, parentAlpha);

        if (gameScreen.time % (parentAlpha * 16) == 0) {
            state = (int) (Math.random() * 4);
        }

        batch.draw(currentRegion, mapX, mapY, 64, 64);
    }
}
