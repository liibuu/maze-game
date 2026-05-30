package de.tum.cit.ase.maze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;

public class Player extends Entity {
    private final MazeRunnerGame game;
    private final GameScreen gameScreen;
    private final CollisionPlayer collisionPlayer;
    private int state;
    private final Sound keyCollectSound = Gdx.audio.newSound(Gdx.files.internal("keyCollect.wav"));
    private final Sound livesCollectSound = Gdx.audio.newSound(Gdx.files.internal("livesCollect.mp3"));
    private final Sound damagedSound = Gdx.audio.newSound(Gdx.files.internal("damaged.wav"));
    private static final Texture walkSheet = new Texture(Gdx.files.internal("character.png"));

    public static final Animation<TextureRegion> animationDown = new Animation<>(0.1f,
            new Array<>(new TextureRegion[]{
                    new TextureRegion(walkSheet, 0 + 0 * 16, 0, 16, 32),
                    new TextureRegion(walkSheet, 0 + 1 * 16, 0, 16, 32),
                    new TextureRegion(walkSheet, 0 + 2 * 16, 0, 16, 32),
                    new TextureRegion(walkSheet, 0 + 3 * 16, 0, 16, 32)}));

    public static final Animation<TextureRegion> animationRight = new Animation<>(0.1f,
            new Array<>(new TextureRegion[]{
                    new TextureRegion(walkSheet, 0 + 0 * 16, 32, 16, 32),
                    new TextureRegion(walkSheet, 0 + 1 * 16, 32, 16, 32),
                    new TextureRegion(walkSheet, 0 + 2 * 16, 32, 16, 32),
                    new TextureRegion(walkSheet, 0 + 3 * 16, 32, 16, 32)}));

    public static final Animation<TextureRegion> animationUp = new Animation<>(0.1f,
            new Array<>(new TextureRegion[]{
                    new TextureRegion(walkSheet, 0 + 0 * 16, 64, 16, 32),
                    new TextureRegion(walkSheet, 0 + 1 * 16, 64, 16, 32),
                    new TextureRegion(walkSheet, 0 + 2 * 16, 64, 16, 32),
                    new TextureRegion(walkSheet, 0 + 3 * 16, 64, 16, 32)}));

    public static final Animation<TextureRegion> animationLeft = new Animation<>(0.1f,
            new Array<>(new TextureRegion[]{
                    new TextureRegion(walkSheet, 0 + 0 * 16, 96, 16, 32),
                    new TextureRegion(walkSheet, 0 + 1 * 16, 96, 16, 32),
                    new TextureRegion(walkSheet, 0 + 2 * 16, 96, 16, 32),
                    new TextureRegion(walkSheet, 0 + 3 * 16, 96, 16, 32)}));

    public Player(float x, float y, GameScreen gameScreen) {
        super(x,y);
        this.gameScreen = gameScreen;
        this.game = gameScreen.getGame();
        this.speed = 6;
        mapX = x;
        mapY = y;
        this.collisionPlayer = new CollisionPlayer(game.getMap().getMapWidth(), game.getMap().getMapHeight());
    }

    public static TextureRegion loadPlayer(int state, float time) {
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
        currentRegion = loadPlayer(state, time);

        gameScreen.getCamera().position.x = mapX;
        gameScreen.getCamera().position.y = mapY;
        gameScreen.getMenuTable().setX(gameScreen.getCamera().position.x - 1655);
        gameScreen.getMenuTable().setY(gameScreen.getCamera().position.y + 585);

        // Check for collision with enemy & live & trap & exit
        if (game.numberLives < 1) {game.goToLose();}
        else if (CollisionPlayer.isEnemyCollision(gameScreen.getMapTable(), mapX, mapY) || CollisionPlayer.isTrapCollision(gameScreen.getMapTable(), mapX, mapY)) {
            damagedSound.play();
            game.numberLives--;
            switch (state) {
                case 0: mapY += speed * 7; break;
                case 1: mapX -= speed * 7; break;
                case 2: mapY -= speed * 7; break;
                case 3: mapX += speed * 7; break;
            }
        }
        else if (CollisionPlayer.isLiveCollision(gameScreen.getMapTable(), mapX, mapY) > -1) {
            Actor actor = gameScreen.getMapTable().getChild(CollisionPlayer.isLiveCollision(gameScreen.getMapTable(), mapX, mapY));
            gameScreen.getMapTable().removeActor(actor);
            livesCollectSound.play();
            game.numberLives++;
        }
        else if (CollisionPlayer.isKeyCollision(gameScreen.getMapTable(), mapX, mapY) > -1) {
            Actor actor = gameScreen.getMapTable().getChild(CollisionPlayer.isKeyCollision(gameScreen.getMapTable(), mapX, mapY));
            gameScreen.getMapTable().removeActor(actor);
            keyCollectSound.play();
            game.numberKeys++;
        }
        else if (CollisionPlayer.isExitCollision(gameScreen.getMapTable(), mapX, mapY) && game.numberKeys > 0) {game.goToWin();}
        else if (CollisionPlayer.isExitCollision(gameScreen.getMapTable(), mapX, mapY) && game.numberKeys == 0) {
            switch (state) {
                case 0: mapY += speed * 6; break;
                case 1: mapX -= speed * 6; break;
                case 2: mapY -= speed * 6; break;
                case 3: mapX += speed * 6; break;
            }
        }

        // Check for key press & collision with walls
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            state = 2;
            if (mapY <= 64*game.getMap().getMapHeight()) {
                if (!collisionPlayer.isWallCollisionUp(gameScreen.getBackgroundTable(), mapX, mapY)) {
                    mapY += speed;
                }
            }
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            state = 0;
            if (mapY >= 64) {
                if (!collisionPlayer.isWallCollisionDown(gameScreen.getBackgroundTable(), mapX, mapY)) {
                    mapY -= speed;
                }
            }
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            state = 3;
            if (mapX >= 64) {
                if (!collisionPlayer.isWallCollisionLeft(gameScreen.getBackgroundTable(), mapX, mapY)) {
                    mapX -= speed;
                }
            }
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            state = 1;
            if (mapX <= 64*game.getMap().getMapWidth()) {
                if (!collisionPlayer.isWallCollisionRight(gameScreen.getBackgroundTable(), mapX, mapY)) {
                    mapX += speed;
                }
            }
        }
    }

    /**
     * Instructions for the entity to be drawn on the stage.
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
