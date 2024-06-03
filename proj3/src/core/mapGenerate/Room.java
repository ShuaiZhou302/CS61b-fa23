package core.mapGenerate;
import core.World;
import tileengine.TETile;
import tileengine.Tileset;

import java.util.ArrayList;

public class Room {

    private TETile[][] tiles;

    private World world;
    private int width;
    private int height;
    private int startRow; // position of lower wall
    private int startCol; // position of upper wall
    private ArrayList<int[]> floors = new ArrayList<>();


    // 每个room起始绘制点在左下角
    public Room(World world, int width, int height, int startCol, int startRow) {
        this.world = world;
        this.width = width;
        this.height = height;
        this.startRow = startRow;
        this.startCol = startCol;
        this.tiles = world.getTiles();
        for (int i = startRow; i < startRow + height; i++) {
            for (int j = startCol; j < startCol + width; j++) {
                drawer(tiles, i, j);
            }
        }
    }

    // pay attention to the wall already exists
    public void drawer(TETile[][] tile, int row, int col) {
        if (!outOfBound(row, col)) {
            if (positionOfWall(row, col)) { // 判断是不是wall 否则绘制wall
                if (tile[col][row] != Tileset.WALL) {
                    tile[col][row] = Tileset.WALL;  // 若已经有wall 则不再绘制
                }
            } else {
                tile[col][row] = Tileset.FLOOR; // 若不是wall, 就绘制地板
                floors.add(new int[]{col, row});
            }
        }
    }
    public boolean outOfBound(int row, int col) { // 判断是否超过地图边界
        return row < 0 || row >= world.getHeight() || col < 0 || col >= world.getWidth();
    }

    public boolean positionOfWall(int row, int col) { // 判断是否墙壁
        return row == startRow || row == startRow + height - 1 || col == startCol || col == startCol + width - 1;
    }

    public int getH() {
        return height;
    }

    public int getW() {
        return width;
    }

    public int getSR() {
        return startRow;
    }

    public int getSC() {
        return startCol;
    }

    public ArrayList<int[]> getFloors() {
        return floors;
    }
}
