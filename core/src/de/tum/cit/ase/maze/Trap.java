package de.tum.cit.ase.maze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class Trap extends Entity {
    public Trap() {
        int frameX = 80;
        int frameY = 48;
        int frameWidth = 16;
        int frameHeight = 16;
        int animationFrames = 2;

        Texture walkSheet = new Texture(Gdx.files.internal("basictiles.png"));
        Array<TextureRegion> walkFrames = new Array<>(TextureRegion.class);
        for (int col = 0; col < animationFrames; col++) { // Add all frames to the animation
            walkFrames.add(new TextureRegion(walkSheet, frameX, frameY + col * 16, frameWidth, frameHeight));
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
