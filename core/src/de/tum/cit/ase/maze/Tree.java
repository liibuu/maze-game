package de.tum.cit.ase.maze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Tree extends Entity {
    // extract the tree image from "basictiles.png"
    private static final TextureRegion currentRegion = new TextureRegion(
            new Texture(Gdx.files.internal("basictiles.png")), 64, 144, 16, 16);

    public Tree(float x, float y) {
        super(x,y);
    }

    /**
     * Instructions for the entity to be drawn on the stage.
     */
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(currentRegion, getX(), getY(), 64, 64);
    }
}
