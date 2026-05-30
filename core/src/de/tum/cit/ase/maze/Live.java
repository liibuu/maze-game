package de.tum.cit.ase.maze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class Live extends Entity {
    // extract the life image from "objects.png"
    private static final Texture walkSheet = new Texture(Gdx.files.internal("objects.png"));
    private static final Animation<TextureRegion> animation = new Animation<>(0.1f,
            new Array<>(new TextureRegion[]{
                    new TextureRegion(walkSheet, 0 + 0 * 16, 48, 16, 16),
                    new TextureRegion(walkSheet, 0 + 1 * 16, 48, 16, 16),
                    new TextureRegion(walkSheet, 0 + 2 * 16, 48, 16, 16),
                    new TextureRegion(walkSheet, 0 + 3 * 16, 48, 16, 16)}));

    public Live(float x, float y) {
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
