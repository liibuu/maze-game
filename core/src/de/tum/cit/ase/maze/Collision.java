package de.tum.cit.ase.maze;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;

public abstract class Collision {

    final int mapWidth;
    final int mapHeight;
    public Collision(int mapWidth, int mapHeight) {
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
    }

    // determine whether the player touches trap
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

    // determine whether the player touches enemy
    public static boolean isEnemyCollision (Table table, float x, float y) {
        Array<Actor> children = table.getChildren();
        for (Actor child : children) {
            if (child.getClass().getSimpleName().equals("Enemy")) {
                Enemy enemy = (Enemy) child;
                if (x > enemy.mapX - 30 // in
                        && x < enemy.mapX + 30 // out
                        && y < enemy.mapY + 35
                        && y > enemy.mapY - 30)
                {return true;}
            }
        }
        return false;
    }

    // determine whether the player touches exit
    public static boolean isExitCollision (Table table, float x, float y) {
        Array<Actor> children = table.getChildren();
        for (Actor child : children) {
            if (child.getClass().getSimpleName().equals("Exit")) {
                Exit exit = (Exit) child;
                if (x > exit.getX() - 30 // in
                        && x < exit.getX() + 30 // out
                        && y < exit.getY() + 35
                        && y > exit.getY() - 30)
                {return true;}
            }
        }
        return false;
    }

    // determine whether the player touches live
    public static int isLiveCollision (Table table, float x, float y) {
        Array<Actor> children = table.getChildren();
        for (int i = 0; i < children.size; i++) {
            Actor child = children.get(i);
            if (child.getClass().getSimpleName().equals("Live")) {
                if (x > child.getX() - 30
                        && x < child.getX() + 30
                        && y < child.getY() + 35
                        && y > child.getY() - 30)
                {return i;}
            }
        }
        return -1;
    }

    // determine whether the player touches key
    public static int isKeyCollision (Table table, float x, float y) {
        Array<Actor> children = table.getChildren();
        for (int i = 0; i < children.size; i++) {
            Actor child = children.get(i);
            if (child.getClass().getSimpleName().equals("Key")) {
                if (x > child.getX() - 30
                        && x < child.getX() + 30
                        && y < child.getY() + 35
                        && y > child.getY() - 30)
                {return i;}
            }
        }
        return -1;
    }

    // abstract classes for the player/enemy to identify nearby cells
    public abstract int[] nearByLeftIndex (Table table, float x, float y);
    public abstract int[] nearByRightIndex (Table table, float x, float y);
    public abstract int[] nearByUpIndex (Table table, float x, float y);
    public abstract int[] nearByDownIndex (Table table, float x, float y);

    // determine whether the player/enemy's nearby cells to the right ARE walls or not
    public boolean isWallCollisionRight (Table table, float x, float y) {
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

    // determine whether the player/enemy's nearby cells to the left ARE walls or not
    public boolean isWallCollisionLeft (Table table, float x, float y) {
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

    // determine whether the player/enemy's nearby cells to the up ARE walls or not
    public boolean isWallCollisionUp (Table table, float x, float y) {
        for (int i: nearByUpIndex(table, x, y)) {
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

    // determine whether the player/enemy's nearby cells to the down ARE walls or not
    public boolean isWallCollisionDown (Table table, float x, float y) {
        for (int i: nearByDownIndex(table, x, y)) {
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
