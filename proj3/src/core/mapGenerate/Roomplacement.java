package core.mapGenerate;

import core.World;
import tileengine.TETile;

import java.util.ArrayList;
import java.util.Random;

public class Roomplacement {

    private int[][] grid;
    private final Random random;
    private TETile[][] tiles;

    private World world;

    private final int minSize = 6;

    private final int maxSize = 10;
    private ArrayList<Room> allRooms;

    private ArrayList<int[]> floors;

    private final int numTry = 80;
    public Roomplacement(Long seed, World world) {
        this.random = new Random(seed);
        this.world = world;
        this.tiles = world.getTiles();
        this.floors = new ArrayList<>();
        this.allRooms = new ArrayList<>();
        this.grid = new int[tiles.length][tiles[0].length];
    }

    public boolean canPlace(int x, int y, int width, int height) {
        for (int i = x; i <= x + width; i++) {
            for (int j = y; j <= y + height; j++) {
                if (i < 0 || i >= tiles.length || j < 0 || j >= tiles[0].length || grid[i][j] == 1) {
                    return false; // 房间重叠或超出边界
                }
            }
        }
        return true;
    }

    public void placeRoom(int x, int y, int width, int height) {
        for (int i = x; i < x + width; i++) {
            for (int j = y; j < y + height; j++) {
                grid[i][j] = 1; // 标记房间所占的位置
            }
        }
        Room room = new Room(world, width, height, x, y);
        allRooms.add(room);
        floors.addAll(room.getFloors());
    }

    public void generateRandomRooms() {
        for (int i = 0; i < numTry; i++) {
            int width = random.nextInt(maxSize - minSize + 1) + minSize;
            int height = random.nextInt(maxSize - minSize + 1) + minSize;
            int x = random.nextInt(grid.length);
            int y = random.nextInt(grid[0].length);
            if (canPlace(x, y, width, height)) {
                placeRoom(x, y, width, height);
            }
        }
    }

    public ArrayList<int[]> getFloors() {
        return floors;
    }

    public ArrayList<Room> getAllRooms() {
        return allRooms;
    }
}

