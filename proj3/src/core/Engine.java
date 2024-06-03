package core;
import core.entity.Figure;
import core.entity.Move;
import core.entity.avater;
import tileengine.TERenderer;
import tileengine.TETile;
import tileengine.Tileset;
import java.util.HashMap;

// prototype
public class Engine {
    private TETile[][] game;
    private int width;
    private int height;

    private avater me;
    private HashMap<int[], Figure> position; // store the position of figure, valid for calculating attack

    private Move move;

    private static final double FRAME_RATE = 60.0;  // 游戏帧率 frame_rate
    private static final double TIME_PER_FRAME = 1.0 / FRAME_RATE;
    private final TERenderer ter = new TERenderer();
    private Engine(World world) {
        this.game = world.getTiles();
        this.width = world.getWidth();
        this.height = world.getHeight();
        this.move = new Move(world, position);
        boolean tmp = false;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (game[i][j] == Tileset.FLOOR) {
                    TETile op = game[i][j];
                    this.me = new avater(i, j, op, Tileset.Walk_down1, world, ter);
                    game[i][j] = Tileset.Walk_down1;
                    tmp = true;
                    break;
                }
            }
            if (tmp) {
                break;
            }
        }
    }


    public void rungame() {
        move.setCurr(me);
        ter.initialize(width, height);
        double lastUpdateTime = 0.0;
        while (true) {
            double currentTime = System.currentTimeMillis() / 1000.0;
            if (currentTime - lastUpdateTime > TIME_PER_FRAME) {
                me.move();
                me.attack();
                ter.renderFrame(game);
                lastUpdateTime = currentTime;
            }
        }
    }

    public static void main(String[] args) {
        Engine engine = new Engine(new World(60, 48, 112));
        engine.rungame();
    }
}
