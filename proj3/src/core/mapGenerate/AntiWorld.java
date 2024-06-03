package core.mapGenerate;

import core.World;
import core.entity.Figure;
import tileengine.TERenderer;
import tileengine.TETile;
import tileengine.Tileset;

import java.util.ArrayList;
import java.util.Random;

public class AntiWorld {

    ///"class to generate world with light"
    private TETile[][] tiles;

    private World world;
    private ArrayList<Room> rooms;

    private Random random;

    private Figure me; // shade of real me in another world

    private TETile wall;

    public AntiWorld(World world, ArrayList<Room> rooms, Figure me) {
        this.world = world;
        this.rooms = rooms;
        this.tiles = new TETile[world.getWidth()][world.getHeight()];
        TETile[][] tmptiles = world.getTiles();
        for (int i = 0; i < world.getWidth(); i++) {
            for (int j = 0; j < world.getHeight(); j++) {
                this.tiles[i][j] = tmptiles[i][j];
            }
        }
        this.random = world.getRANDOM();
        this.me = me;
        this.wall = world.getCurrWall();
        lightUp();
    }

    // light up rooms
    public void lightUp() {
        for (Room r: rooms) {
            int x = random.nextInt(r.getW() - 2) + r.getSC() + 1;
            int y = random.nextInt(r.getH() - 2) + r.getSR() + 1;
            putlight(x, y, r);
        }
    }

    // put up light
    public void putlight(int x, int y, Room r) {
        // place the point
        if (canPut(x, y, r)) {
            if (tiles[x][y] == me.getFigure()) {
                me.setLastpos(Tileset.Source);
            } else {
                tiles[x][y] = Tileset.Source;
            }
        }
        // place the lawyer 1
        for (int i = x - 1; i <= x + 1; i++) {
            if (canPut(i, y + 1, r)) {
                if (tiles[i][y + 1] == me.getFigure()) {
                    me.setLastpos(Tileset.LY1);
                } else {
                    tiles[i][y + 1] = Tileset.LY1;
                }
            }
            if (canPut(i, y - 1, r)) {
                if (tiles[i][y - 1] == me.getFigure()) {
                    me.setLastpos(Tileset.LY1);
                } else {
                    tiles[i][y - 1] = Tileset.LY1;
                }
            }
        }

        for (int j = y - 1; j <= y + 1; j++) {
            if (canPut(x - 1, j, r)) {
                if (tiles[x - 1][j] == me.getFigure()) {
                    me.setLastpos(Tileset.LY1);
                } else {
                    tiles[x - 1][j] = Tileset.LY1;
                }
            }
            if (canPut(x + 1, j, r)) {
                if (tiles[x + 1][j] == me.getFigure()) {
                    me.setLastpos(Tileset.LY1);
                } else {
                    tiles[x + 1][j] = Tileset.LY1;
                }
            }
        }
        // place the lawyer2
        for (int i = x - 2; i <= x + 2; i++) {
            if (canPut(i, y + 2, r)) {
                if (tiles[i][y + 2] == me.getFigure()) {
                    me.setLastpos(Tileset.LY2);
                } else {
                    tiles[i][y + 2] = Tileset.LY2;
                }
            }
            if (canPut(i, y - 2, r)) {
                if (tiles[i][y - 2] == me.getFigure()) {
                    me.setLastpos(Tileset.LY2);
                } else {
                    tiles[i][y - 2] = Tileset.LY2;
                }
            }
        }

        for (int j = y - 2; j <= y + 2; j++) {
            if (canPut(x - 2, j, r)) {
                if (tiles[x - 2][j] == me.getFigure()) {
                    me.setLastpos(Tileset.LY2);
                } else {
                    tiles[x - 2][j] = Tileset.LY2;
                }
            }
            if (canPut(x + 2, j, r)) {
                if (tiles[x + 2][j] == me.getFigure()) {
                    me.setLastpos(Tileset.LY2);
                } else {
                    tiles[x + 2][j] = Tileset.LY2;
                }
            }
        }
        // place the lawyer3
        for (int i = x - 3; i <= x + 3; i++) {
            if (canPut(i, y + 3, r)) {
                if (tiles[i][y + 3] == me.getFigure()) {
                    me.setLastpos(Tileset.LY3);
                } else {
                    tiles[i][y + 3] = Tileset.LY3;
                }
            }
            if (canPut(i, y - 3, r)) {
                if (tiles[i][y - 3] == me.getFigure()) {
                    me.setLastpos(Tileset.LY3);
                } else {
                    tiles[i][y - 3] = Tileset.LY3;
                }
            }
        }

        for (int j = y - 3; j <= y + 3; j++) {
            if (canPut(x - 3, j, r)) {
                if (tiles[x - 3][j] == me.getFigure()) {
                    me.setLastpos(Tileset.LY3);
                } else {
                    tiles[x - 3][j] = Tileset.LY3;
                }
            }
            if (canPut(x + 3, j, r)) {
                if (tiles[x + 3][j] == me.getFigure()) {
                    me.setLastpos(Tileset.LY3);
                } else {
                    tiles[x + 3][j] = Tileset.LY3;
                }
            }
        }
        // place the lawyer4
        for (int j = y - 4; j <= y + 4; j++) {
            if (canPut(x - 4, j, r)) {
                if (tiles[x - 4][j] == me.getFigure()) {
                    me.setLastpos(Tileset.LY4);
                } else {
                    tiles[x - 4][j] = Tileset.LY4;
                }
            }
            if (canPut(x + 4, j, r)) {
                if (tiles[x + 4][j] == me.getFigure()) {
                    me.setLastpos(Tileset.LY4);
                } else {
                    tiles[x + 4][j] = Tileset.LY4;
                }
            }
        }
        for (int i = x - 4; i <= x + 4; i++) {
            if (canPut(i, y + 4, r)) {
                if (tiles[i][y + 4] == me.getFigure()) {
                    me.setLastpos(Tileset.LY4);
                } else {
                    tiles[i][y + 4] = Tileset.LY4;
                }
            }
            if (canPut(i, y - 4, r)) {
                if (tiles[i][y - 4] == me.getFigure()) {
                    me.setLastpos(Tileset.LY4);
                } else {
                    tiles[i][y - 4] = Tileset.LY4;
                }
            }
        }
    }

    // decide if can put light
    public boolean canPut(int x, int y, Room room) {
        if (x < 0 || x >= world.getWidth() || y < 0 || y >= world.getHeight() ) {
            return false;
        } else if (tiles[x][y] == wall) {
            return false;
        } else if (x <= room.getSC() || x >= room.getSC() + room.getW() - 1) {
            return false;
        } else if (y <= room.getSR() || y >= room.getSR() + room.getH() - 1) {
            return false;
        } else {
            return true;
        }
    }
    public TETile[][] getTiles() {
        return tiles;
    }

    public static void main(String[] args) {
        World w = new World(70, 38, 112);  // 112
        TERenderer ter = new TERenderer();
        ter.initialize(70, 38);
        AntiWorld at = new AntiWorld(w,w.getRooms(),new Figure(0,0,Tileset.FLOOR,Tileset.Walk_down1));
        ter.renderFrame(at.getTiles());
    }
}
