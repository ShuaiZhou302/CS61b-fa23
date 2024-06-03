import core.AutograderBuddy;
import edu.princeton.cs.algs4.StdDraw;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import tileengine.TERenderer;
import tileengine.TETile;

import java.util.Random;

public class WorldGenTests {
    @Test
    public void basicTest() {
        // put different seeds here to test different worlds
        TETile[][] tiles = AutograderBuddy.getWorldFromInput("n1234567890123456789s");
        TERenderer ter = new TERenderer();
        ter.initialize(tiles.length, tiles[0].length);
        ter.renderFrame(tiles);
        StdDraw.pause(5000); // pause for 5 seconds so you can see the output
    }

    public void multitimetest() {
        Random rand = new Random();
        for (int i = 0; i < 10; i++) {
            TETile[][] tiles = AutograderBuddy.getWorldFromInput("n1234567890123456789s");
            TERenderer ter = new TERenderer();
            ter.initialize(tiles.length, tiles[0].length);
            ter.renderFrame(tiles);
            StdDraw.pause(5000); // pause for 5 seconds so you can see the output
        }
    }
    @Test
    public void basicInteractivityTest() {
        System.out.println("ok1");
        TETile[][] tile1 = AutograderBuddy.getWorldFromInput("N999SDDDWWWDDD");
        System.out.println("ok2");
        AutograderBuddy.getWorldFromInput("N999SDDD:Q");
        System.out.println("ok3");
        TETile[][] tile2 = AutograderBuddy.getWorldFromInput("LWWW:Q");
        System.out.println("ok4");
        if (tile1 == tile2) {
            System.out.println("Win!");
        } else {
            System.out.println("oh no!");
        }
    }

    @Test
    public void basicSaveTest() {
        // TODO: write a test that calls getWorldFromInput twice, with "n123swasd:q" and with "lwasd"
    }
}
