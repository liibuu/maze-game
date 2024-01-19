package de.tum.cit.ase.maze;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;

public class Collision {

    public static boolean isTrapCollisionRight (Table table, float x, float y) {
        Array<Actor> children = table.getChildren();
        for (Actor child : children) {
            if (child.getClass().getSimpleName().equals("Trap")
                    && x > child.getX() - 70 // in
                    && x < child.getX() + 50 // out
                    && y < child.getY() + 35
                    && y > child.getY() - 30)
                return true;
        }
        return false;
    }

    public static boolean isTrapCollisionLeft (Table table, float x, float y) {
        Array<Actor> children = table.getChildren();
        for (Actor child : children) {
            if (child.getClass().getSimpleName().equals("Trap")
                    && x > child.getX() - 50 // out
                    && x < child.getX() + 70 // in
                    && y < child.getY() + 35
                    && y > child.getY() - 30)
                return true;
        }
        return false;
    }

    public static boolean isTrapCollisionUp (Table table, float x, float y) {
        Array<Actor> children = table.getChildren();
        for (Actor child : children) {
            if (child.getClass().getSimpleName().equals("Trap")
                    && x < child.getX() + 50
                    && x > child.getX() - 50
                    && y > child.getY() - 40 // in
                    && y < child.getY() + 30) // out
                return true;
        }
        return false;
    }

    public static boolean isTrapCollisionDown (Table table, float x, float y) {
        Array<Actor> children = table.getChildren();
        for (Actor child : children) {
            if (child.getClass().getSimpleName().equals("Trap")
                    && x < child.getX() + 50
                    && x > child.getX() - 50
                    && y > child.getY() - 30 // out
                    && y < child.getY() + 50) // in
                return true;
        }
        return false;
    }

    public static boolean isWallCollisionRight (Table table, float x, float y) {
        Array<Actor> children = table.getChildren();
        for (Actor child : children) {
            if (child.getClass().getSimpleName().equals("Wall")
                    && x > child.getX() - 70 // in
                    && x < child.getX() + 50 // out
                    && y < child.getY() + 35
                    && y > child.getY() - 30)
                return true;
        }
        return false;
    }

    public static boolean isWallCollisionLeft (Table table, float x, float y) {
        Array<Actor> children = table.getChildren();
        for (Actor child : children) {
            if (child.getClass().getSimpleName().equals("Wall")
                    && x > child.getX() - 50 // out
                    && x < child.getX() + 70 // in
                    && y < child.getY() + 35
                    && y > child.getY() - 30)
                return true;
        }
        return false;
    }

    public static boolean isWallCollisionUp (Table table, float x, float y) {
        Array<Actor> children = table.getChildren();
        for (Actor child : children) {
            if (child.getClass().getSimpleName().equals("Wall")
                    && x < child.getX() + 50
                    && x > child.getX() - 50
                    && y > child.getY() - 40 // in
                    && y < child.getY() + 30) // out
                return true;
        }
        return false;
    }

    public static boolean isWallCollisionDown (Table table, float x, float y) {
        Array<Actor> children = table.getChildren();
        for (Actor child : children) {
            if (child.getClass().getSimpleName().equals("Wall")
                    && x < child.getX() + 50
                    && x > child.getX() - 50
                    && y > child.getY() - 30 // out
                    && y < child.getY() + 50) // in
                return true;
        }
        return false;
    }
}
