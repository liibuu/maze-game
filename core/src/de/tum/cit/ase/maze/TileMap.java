package de.tum.cit.ase.maze;

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
