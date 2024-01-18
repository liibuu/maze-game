package de.tum.cit.ase.maze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class Wall extends Entity {

    public Wall() {
        int code = 0;
        int frameX = 0;
        int frameY = 0;
        int frameWidth = 16;
        int frameHeight = 16;
        int animationFrames = 1;

        switch (code) {
            case 0: frameX = 16;  frameY = 0;   break;
            case 1: frameX = 48;  frameY = 112; break;
            case 2: frameX = 48;  frameY = 96;  break;
            case 3: frameX = 112; frameY = 48;  break;
            case 4: frameX = 64;  frameY = 112; animationFrames = 2; break;
            case 5: frameX = 48;  frameY = 48;  break;
            case -1:frameX = 64;  frameY = 144; break;
            default: frameX = 0;  frameY = 128;
        }

        Texture walkSheet = new Texture(Gdx.files.internal("basictiles.png"));
        Array<TextureRegion> walkFrames = new Array<>(TextureRegion.class);
        for (int col = 0; col < animationFrames; col++) { // Add all frames to the animation
            walkFrames.add(new TextureRegion(walkSheet, frameX + col * 16, frameY, frameWidth, frameHeight));
        }

        animation = new Animation<>(0.1f, walkFrames);
    }

    /**
     * Guide the tile to act on the stage.
     */
    @Override
    public void act(float delta){
        time += delta;
        currentRegion = (TextureRegion) animation.getKeyFrame(time, true);
    }

    /**
     * Guide the tile to be drawn on the stage.
     */
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(currentRegion, getX(), getY(), 64, 64);
    }
}
