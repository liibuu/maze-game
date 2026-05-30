package de.tum.cit.ase.maze;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class CollisionEnemy extends Collision {
    public CollisionEnemy (int mapWidth, int mapHeight) {
        super(mapWidth, mapHeight);
    }

    // identify nearby cells to the left
    @Override
    public int[] nearByLeftIndex(Table table, float x, float y) {

        int leftDownY = (int) (y / 64 - 1);
        int leftUpY = (int) ((y + 64) / 64 - 1);
        int leftLeftX = (int) ((x - 64) / 64 - 1);
        int leftRightX = (int) (x / 64 - 1);

        int leftUpLeftIndex = leftLeftX * mapWidth + leftUpY;
        int leftUpRightIndex = leftRightX * mapWidth + leftUpY;
        int leftDownLeftIndex = leftLeftX * mapWidth + leftDownY;
        int leftDownRightIndex = leftRightX * mapWidth + leftDownY;

        return new int[]{leftUpLeftIndex, leftUpRightIndex,
                leftDownLeftIndex, leftDownRightIndex};
    }

    // identify nearby cells to the right
    @Override
    public int[] nearByRightIndex(Table table, float x, float y) {

        int rightDownY = (int) (y / 64 - 1);
        int rightUpY = (int) ((y + 64) / 64 - 1);
        int rightLeftX = (int) ((x + 64) / 64 - 1);
        int rightRightX = (int) ((x + 128) / 64 - 1);

        int rightUpLeftIndex = rightLeftX * mapWidth + rightUpY;
        int rightUpRightIndex = rightRightX * mapWidth + rightUpY;
        int rightDownLeftIndex = rightLeftX * mapWidth + rightDownY;
        int rightDownRightIndex = rightRightX * mapWidth + rightDownY;

        return new int[]{rightUpLeftIndex, rightUpRightIndex,
                rightDownLeftIndex, rightDownRightIndex};
    }

    // identify nearby cells to the down
    @Override
    public int[] nearByDownIndex(Table table, float x, float y) {

        int downDownY = (int) ((y - 64) / 64 - 1);
        int downUpY = (int) (y / 64 - 1);
        int downLeftX = (int) (x / 64 - 1);
        int downRightX = (int) ((x + 64) / 64 - 1);

        int downDownLeftIndex = downLeftX * mapWidth + downDownY;
        int downDownRightIndex = downRightX * mapWidth + downDownY;
        int downUpLeftIndex = downLeftX * mapWidth + downUpY;
        int downUpRightIndex = downRightX * mapWidth + downUpY;

        return new int[]{downDownLeftIndex, downDownRightIndex, downUpLeftIndex, downUpRightIndex};
    }

    // identify nearby cells to the up
    @Override
    public int[] nearByUpIndex(Table table, float x, float y) {

        int upDownY = (int) ((y + 128) / 64 - 1);
        int upUpY = (int) ((y + 192) / 64 - 1);
        int upLeftX = (int) (x / 64 - 1);
        int upRightX = (int) ((x + 64) / 64 - 1);

        int upDownLeftIndex = upLeftX * mapWidth + upDownY;
        int upDownRightIndex = upRightX * mapWidth + upDownY;
        int upUpLeftIndex = upLeftX * mapWidth + upUpY;
        int upUpRightIndex = upRightX * mapWidth + upUpY;

        return new int[]{upDownLeftIndex, upDownRightIndex, upUpLeftIndex, upUpRightIndex};
    }
}
