package de.tum.cit.ase.maze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class Trap extends Entity {
    // extract the trap image from "basictiles.png"
    private static final Texture walkSheet = new Texture(Gdx.files.internal("basictiles.png"));
    private static final Animation<TextureRegion> animation = new Animation<>(0.1f,
            new Array<>(new TextureRegion[]{
                    new TextureRegion(walkSheet, 80, 48 + 0 * 16, 16, 16),
                    new TextureRegion(walkSheet, 80, 48 + 1 * 16, 16, 16)}));

    public Trap(float x, float y) {
        super(x,y);
    }

    /**
     * Instructions for the entity to act on the stage.
     */
    @Override
    public void act(float delta){
        time += delta;
        currentRegion = animation.getKeyFrame(time, true);
    }

    /**
     * Instructions for the entity to be drawn on the stage.
     */
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(currentRegion, getX(), getY(), 64, 64);
    }
}
