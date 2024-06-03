package knightworld;

import tileengine.TERenderer;
import tileengine.TETile;
import tileengine.Tileset;

/**
 * Draws a world consisting of knight-move holes.
 */
public class KnightWorld {

    private TETile[][] tiles;
    public KnightWorld(int width, int height, int holeSize) {
        tiles = new TETile[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                tiles[i][j] = Tileset.LOCKED_DOOR;
            }
        }
        holeCreator(tiles, holeSize, width, height, 0);
    }
    public void holeCreator(TETile[][] tiles, int holeSize, int width, int height, int currline) {
        for (int i = 0; i < width; i += 5 * holeSize) {
            blockcreator(tiles, i, currline, holeSize, width, height);
        }
        blockcreator(tiles,width - width % (5 * holeSize), currline ,holeSize, width, height);
        if (currline + 5 * holeSize < height) {
            holeCreator(tiles,holeSize, width, height, currline + 5 * holeSize);
        }
    }
    public void blockcreator(TETile[][] tiles, int w, int t, int size, int width, int height) {
        hole(tiles,   w, t + 3 * size, size, width, height);
        hole(tiles,w + size, t, size, width, height);
        hole(tiles,w + 2 * size, t + 2 * size, size, width, height);
        hole(tiles,w + 3 * size, t + 4 * size, size, width, height);
        hole(tiles,w + 4 * size, t + size, size, width, height);
    }
    public void hole(TETile[][] tiles, int i, int line, int size, int width, int height) {
        for (int m = i; m < i + size; m++) {
            for (int n = line; n < line + size; n++) {
                if (m >= width || n >= height ){
                    continue;
                }
                tiles[m][n] = Tileset.NOTHING;
            }
        }
    }

    /** Returns the tiles associated with this KnightWorld. */
    public TETile[][] getTiles() {
        return tiles;
    }

    public static void main(String[] args) {
        // Change these parameters as necessary
        int width = 60;
        int height = 40;
        int holeSize = 4;

        KnightWorld knightWorld = new KnightWorld(width, height, holeSize);

        TERenderer ter = new TERenderer();
        ter.initialize(width, height);
        ter.renderFrame(knightWorld.getTiles());

    }
}
