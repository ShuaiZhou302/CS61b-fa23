package core;
import core.entity.Figure;
import core.entity.Move;
import core.entity.ShortpathFinder;
import core.entity.avater;
import core.mapGenerate.Room;
import tileengine.TERenderer;
import tileengine.TETile;
import tileengine.Tileset;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

// prototype
public class Engine {
    private TETile[][] game;
    private int width;
    private int height;

    private avater me;
    private Figure monster;
    private HashMap<int[], Figure> position; // store the position of figure, valid for calculating attack

    private Move move;

    private static final double FRAME_RATE = 60.0;  // 游戏帧率 frame_rate
    private static final double TIME_PER_FRAME = 1.0 / FRAME_RATE;
    private final TERenderer ter = new TERenderer();
    private ArrayList<Room> rooms;

    private TETile currWall;
    private Engine(World world) {
        this.game = world.getTiles();
        this.width = world.getWidth();
        this.height = world.getHeight();
        this.move = new Move(world, position);
        this.rooms = world.getRooms();
        this.currWall = world.getCurrWall();
        Room born = rooms.get(0);
        TETile op = game[born.getSC() + 1][born.getSR() + 1];
        this.me = new avater(born.getSC() + 1, born.getSR() + 1, op, Tileset.Walk_down1, world, ter);
        game[born.getSC() + 1][born.getSR() + 1] = Tileset.Walk_down1;
        Room born1 = rooms.get(1);
        TETile op2 = game[born1.getSC() + 1][born1.getSR() + 1];
        this.monster = new Figure(born1.getSC() + 1, born1.getSR() + 1, op2, Tileset.Walk_down1);
        game[born1.getSC() + 1][born1.getSR() + 1] = Tileset.Walk_down1;
    }


    public void rungame() {
        move.setCurr(me);
        ter.initialize(width, height);
        ShortpathFinder sp = new ShortpathFinder();
        double lastUpdateTime = 0.0;
        while (true) {
            double currentTime = System.currentTimeMillis() / 1000.0;
            if (currentTime - lastUpdateTime > TIME_PER_FRAME) {
                Point a = new Point(me.getX(), me.getY());
                Point b = new Point(monster.getX(), monster.getY());
                List<Point> path = sp.findShortestPath(game,a,b);
                for (Point p: path) {
                    System.out.println("x: "+p.x + "y: " + p.y);
                    if (game[p.x][p.y] != me.getFigure() && game[p.x][p.y] != monster.getFigure()) {
                        game[p.x][p.y] = Tileset.Bullet;
                    }
                }
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
