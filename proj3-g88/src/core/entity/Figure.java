package core.entity;

import tileengine.TETile;
import tileengine.Tileset;

import java.util.HashMap;

// prototype
public class Figure {
    private TETile lastPos;
    private int x; // last pos x
    private int y; // last pos y

    private int speed;
    private int HP;


    public int md; // moving direction 1-left 2-right 3-down 4-up very useful for attack
    // Sparta！
    private TETile figure;


    public Figure(int x, int y, TETile pos,TETile fig) {
        this.x = x;
        this.y = y;
        this.lastPos = pos;
        this.figure = fig;
    }

    public void setFigure(TETile figure) {
        this.figure = figure;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    public TETile getLastPos() {
        return lastPos;
    }
    public TETile getFigure() {
        return figure;
    }

    public int getHP() {
        return HP;
    }

    public int getSpeed() {
        return speed;
    }

    public void setLastpos(TETile pos) {
        this.lastPos = pos;
    }

    public void moveFig(int x, int y, TETile pos) {
        this.x = x;
        this.y = y;
        this.lastPos = pos;
    }
}
