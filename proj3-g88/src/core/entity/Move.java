package core.entity;

import core.World;
import tileengine.TETile;
import tileengine.Tileset;

import java.util.HashMap;

public class Move {
    private World world;
    private TETile[][] tiles;
    private Figure curr;
    private HashMap<int[],Figure> figures;

    public Move(World world,HashMap<int[],Figure> figures) {
        this.world = world;
        this.tiles = world.getTiles();
        this.figures = figures;
    }

    public void setCurr(Figure curr) {
        this.curr = curr;
    }

    public boolean canMove(int x, int y) {
        int[] next = new int[2];
        next[0] = curr.getX()+x;   // 这里顺序有问题 先检查有没有出界
        next[1] = curr.getY() +y;
        if (curr.getY() + y >= world.getHeight() || curr.getY() + y <0) {
            return false;
        } else if (curr.getX() + x >= world.getWidth() || curr.getX() +x < 0){
            return false;
        } else if (tiles[curr.getX() + x][curr.getY() + y] == Tileset.WALL || figures.containsKey(next)){
            return false;
        } else {
            return true;
        }
    }
}
