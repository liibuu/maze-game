package de.tum.cit.ase.maze;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Entity extends Actor {

    protected float mapX, mapY;
    protected TextureRegion currentRegion;

    protected float time = 0f;
    protected int speed;

    protected Entity (float x, float y) {
        this.setX(x);
        this.setY(y);
    }
}
