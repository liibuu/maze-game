package de.tum.cit.ase.maze;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;

public class Collision {

    private static int[] nearByDownCellIndex(Table table, float x, float y) {

        int downDownY = (int) ((y - 64) / 64 - 1);
        int downUpY = (int) (y / 64 - 1);
        int downLeftX = (int) (x / 64 - 1);
        int downRightX = (int) ((x + 64) / 64 - 1);

        int downDownLeftIndex = downLeftX * 15 + downDownY;
        int downDownRightIndex = downRightX * 15 + downDownY;
        int downUpLeftIndex = downLeftX * 15 + downUpY;
        int downUpRightIndex = downRightX * 15 + downUpY;

        return new int[]{downDownLeftIndex, downDownRightIndex, downUpLeftIndex, downUpRightIndex};
    }

    private static int[] nearByUpCellIndex(Table table, float x, float y) {

        int upDownY = (int) ((y + 128) / 64 - 1);
        int upUpY = (int) ((y + 192) / 64 - 1);
        int upLeftX = (int) (x / 64 - 1);
        int upRightX = (int) ((x + 64) / 64 - 1);

        int upDownLeftIndex = upLeftX * 15 + upDownY;
        int upDownRightIndex = upRightX * 15 + upDownY;
        int upUpLeftIndex = upLeftX * 15 + upUpY;
        int upUpRightIndex = upRightX * 15 + upUpY;

        return new int[]{upDownLeftIndex, upDownRightIndex, upUpLeftIndex, upUpRightIndex};
    }

    private static int[] nearByRightIndex (Table table, float x, float y) {

        int rightDownY = (int) (y / 64 - 1);
        int rightMidY = (int) ((y + 64) / 64 - 1);
        int rightUpY = (int) ((y + 128) / 64 - 1);
        int rightLeftX = (int) ((x + 64) / 64 - 1);
        int rightRightX = (int) ((x + 128) / 64 - 1);

        int rightUpLeftIndex = rightLeftX * 15 + rightUpY;
        int rightUpRightIndex = rightRightX * 15 + rightUpY;
        int rightMidLeftIndex = rightLeftX * 15 + rightMidY;
        int rightMidRightIndex = rightRightX * 15 + rightMidY;
        int rightDownLeftIndex = rightLeftX * 15 + rightDownY;
        int rightDownRightIndex = rightRightX * 15 + rightDownY;

        return new int[]{rightUpLeftIndex, rightUpRightIndex,
                        rightMidLeftIndex, rightMidRightIndex,
                        rightDownLeftIndex, rightDownRightIndex};
    }

    private static int[] nearByLeftIndex (Table table, float x, float y) {

        int leftDownY = (int) (y / 64 - 1);
        int leftMidY = (int) ((y + 64) / 64 - 1);
        int leftUpY = (int) ((y + 128) / 64 - 1);
        int leftLeftX = (int) ((x - 64) / 64 - 1);
        int leftRightX = (int) (x / 64 - 1);

        int leftUpLeftIndex = leftLeftX * 15 + leftUpY;
        int leftUpRightIndex = leftRightX * 15 + leftUpY;
        int leftMidLeftIndex = leftLeftX * 15 + leftMidY;
        int leftMidRightIndex = leftRightX * 15 + leftMidY;
        int leftDownLeftIndex = leftLeftX * 15 + leftDownY;
        int leftDownRightIndex = leftRightX * 15 + leftDownY;

        return new int[]{leftUpLeftIndex, leftUpRightIndex,
                        leftMidLeftIndex, leftMidRightIndex,
                        leftDownLeftIndex, leftDownRightIndex};
    }

    public static boolean isTrapCollision (Table table, float x, float y) {
        Array<Actor> children = table.getChildren();
        for (Actor child : children) {
            if (child.getClass().getSimpleName().equals("Trap")) {
                if (x > child.getX() - 30 // in
                    && x < child.getX() + 30 // out
                    && y < child.getY() + 35
                    && y > child.getY() - 30)
                {return true;}
            }
        }
        return false;
    }

    public static boolean isEnemyCollision (Table table, float x, float y) {
        Array<Actor> children = table.getChildren();
        for (Actor child : children) {
            if (child.getClass().getSimpleName().equals("Enemy")) {
                Enemy enemy = (Enemy) child;
                if (x > enemy.getMapX() - 30 // in
                        && x < enemy.getMapX() + 30 // out
                        && y < enemy.getMapY() + 35
                        && y > enemy.getMapY() - 30)
                {return true;}
            }
        }
        return false;
    }

    public static boolean isWallCollisionRight (Table table, float x, float y) {
        for (int i: nearByRightIndex(table, x, y)) {
            Actor child = table.getChild(i);
            if (child.getClass().getSimpleName().equals("Wall")
                    && x > child.getX() - 70 // in
                    && x < child.getX() + 50 // out
                    && y < child.getY() + 35
                    && y > child.getY() - 66)
            {return true;}
        }
        return false;
    }

    public static boolean isWallCollisionLeft (Table table, float x, float y) {
        for (int i: nearByLeftIndex(table, x, y)) {
            Actor child = table.getChild(i);
            if (child.getClass().getSimpleName().equals("Wall")
                    && x > child.getX() - 50 // out
                    && x < child.getX() + 70 // in
                    && y < child.getY() + 35
                    && y > child.getY() - 66)
            {return true;}
        }
        return false;
    }

    public static boolean isWallCollisionUp (Table table, float x, float y) {
        for (int i: nearByUpCellIndex(table, x, y)) {
            Actor child = table.getChild(i);
            if (child.getClass().getSimpleName().equals("Wall")
                    && x < child.getX() + 50
                    && x > child.getX() - 50
                    && y > child.getY() - 80 // in
                    && y < child.getY() + 30) // out
            {return true;}
        }
        return false;
    }

    public static boolean isWallCollisionDown (Table table, float x, float y) {
        for (int i: nearByDownCellIndex(table, x, y)) {
            Actor child = table.getChild(i);
            if (child.getClass().getSimpleName().equals("Wall")
                    && x < child.getX() + 50
                    && x > child.getX() - 50
                    && y > child.getY() - 30 // out
                    && y < child.getY() + 50) // in
            {return true;}
        }
        return false;
    }

}
