package core;

import core.mapGenerate.PathFinder;
import core.mapGenerate.Room;
import core.mapGenerate.Roomplacement;
import core.mapGenerate.OuterWorld;
import tileengine.TETile;
import tileengine.Tileset;
import java.util.*;

public class World {
    private int width;
    private int height;
    private TETile[][] tiles;
    private Random RANDOM;

    private ArrayList<Room> rooms;

    private TETile currWall;

    public World(int width, int height, long seed) {

        this.height = height;
        this.width = width;
        tiles = new TETile[width][height];
        initializeTiles();

        this.RANDOM = new Random(seed);

        Roomplacement rp = new Roomplacement((long) seed, this);
        rp.generateRandomRooms(); // generate random number of rooms

        PathFinder pf = new PathFinder(this, RANDOM, rp);
        pf.goPathFinder();
        this.rooms = pf.getRemainRooms();

        OuterWorld ow = new OuterWorld(this,RANDOM);
        ow.placeOuterWorld();
        currWall = ow.getCurrWall();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public ArrayList<Room> getRooms() {
        return rooms;
    }

    public TETile[][] getTiles() {
        return tiles;
    }

    public Random getRANDOM() {
        return RANDOM;
    }

    public void initializeTiles() {
        for (int i = 0; i < width; i += 1) {
            for (int j = 0; j < height; j += 1) {
                tiles[i][j] = Tileset.NOTHING;
                tiles[i][j] = Tileset.SAND;
            }
        }
    }

    public TETile getCurrWall() {
        return currWall;
    }
}
