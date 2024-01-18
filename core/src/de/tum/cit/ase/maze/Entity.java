package de.tum.cit.ase.maze;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;

public class Entity extends Actor {

    protected int mapX, mapY;
    protected Animation animation;
    protected TextureRegion currentRegion;
    protected float time = 0f;
    protected int speed = 6;
    protected boolean collision = false;
}
