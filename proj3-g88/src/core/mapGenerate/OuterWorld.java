package core.mapGenerate;

import core.World;
import tileengine.TETile;
import tileengine.Tileset;
import java.util.Random;

public class OuterWorld {
    private World world;
    private TETile[][] tiles;
    private Random random;

    private TETile currWall;

    public OuterWorld(World world, Random random) {
        this.world = world;
        this.tiles = world.getTiles();
        this.random = random;
    }

    // randomly choose from lava, grass or water,
    public void placeOuterWorld() {
        int pick = random.nextInt(3); // depends on the kinds that the element i am gonna make
        switch (pick) {
            case 0:
                oceanTheme();
                break;
            case 1:
                greenTheme();
                break;
            case 2:
                fireTheme();
                break;
            default:
        }
        System.out.println(currWall.description());
    }

    public void oceanTheme() {
        for (int i = 0; i < world.getWidth(); i++) {
            for (int j = 0; j < world.getHeight(); j++) {
                if (tiles[i][j] == Tileset.SAND) {
                    tiles[i][j] = Tileset.Ocean;
                } else if (tiles[i][j] == Tileset.WALL) {
                    tiles[i][j] = Tileset.WATER;
                }
            }
        }
        currWall = Tileset.WATER;
    }

    public void greenTheme() {
        for (int i = 0; i < world.getWidth(); i++) {
            for (int j = 0; j < world.getHeight(); j++) {
                if (tiles[i][j] == Tileset.SAND) {
                    tiles[i][j] = Tileset.TREE;
                } else if (tiles[i][j] == Tileset.WALL) {
                    tiles[i][j] = Tileset.GRASS;
                }
            }
        }
        currWall = Tileset.GRASS;
    }

    public void fireTheme() {
        for (int i = 0; i < world.getWidth(); i++) {
            for (int j = 0; j < world.getHeight(); j++) {
                if (tiles[i][j] == Tileset.SAND) {
                    tiles[i][j] = Tileset.Lava;
                } else if (tiles[i][j] == Tileset.WALL) {
                    tiles[i][j] = Tileset.RedRock;
                }
            }
        }
        currWall = Tileset.RedRock;
    }

    public TETile getCurrWall() {
        return currWall;
    }
}
