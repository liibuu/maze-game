package de.tum.cit.ase.maze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class TileMap {

    private int[][] tiles;
    private int mapWidth;
    private int mapHeight;

    public TileMap(Properties prop) {
        mapWidth = prop.keySet().stream().mapToInt(k -> Integer.parseInt(k.toString().split(",")[0])).max().orElse(0) + 1;
        mapHeight = prop.keySet().stream().mapToInt(k -> Integer.parseInt(k.toString().split(",")[1])).max().orElse(0) + 1;

        this.tiles = new int[mapWidth][mapHeight];

        prop.stringPropertyNames()
                .stream()
                .forEach(k -> tiles[Integer.parseInt(k.split(",")[0])][Integer.parseInt(k.split(",")[1])] = Integer.parseInt(prop.getProperty(k)));
    }

    public int getMapWidth() {
        return mapWidth;
    }
    public int getMapHeight() {
        return mapHeight;
    }
    public int[][] getTiles() {
        return tiles;
    }
}
