package core;

//import core.entity.Figure;
import core.entity.Figure;
//import edu.princeton.cs.algs4.In;
import core.mapGenerate.AntiWorld;
import core.mapGenerate.Room;
import edu.princeton.cs.algs4.StdDraw;
import tileengine.TERenderer;
import tileengine.TETile;
import tileengine.Tileset;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;


public class SeparateMoves {

    private int width = 70;
    private int height = 38;
    private int numOfMove;
    private Figure me;
    private Figure shade;

    private Figure bulletR; // 3rd version

    private Figure bulletA;
    private TETile[][] game = new TETile[width][height];

    private TETile[][] antigame = new TETile[width][height];

    private TETile[][] currTile;

    private boolean whetherQuit = false;
    private String path = "./PreGameInfo.txt";
    private Long seed = 999L;
    private static final double FRAME_RATE = 60.0;  // 游戏帧率 frame_rate
    private static final double TIME_PER_FRAME = 1.0 / FRAME_RATE;
    private boolean whetherStart = false;
    private ArrayList<Character> seedList = new ArrayList<>();
    private double TILE_SIZE = 16;
    private World world;

    private TETile currWall;

    private ArrayList<Room> rooms;
    private HashMap<Integer, TETile> dMap = new HashMap<>();

    private int md; // direction

    private int bd;

    private boolean lighton;
    private boolean fireon;

    public SeparateMoves() {
        //        String currentDir = System.getProperty("user.dir");
        //        System.out.println(currentDir);
        //        String relativePath = "./src/core/preGameInfo.txt";
        //        this.path = currentDir + File.separator + relativePath;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                game[i][j] = Tileset.NOTHING; // initial game set to Nothing
                antigame[i][j] = Tileset.NOTHING;
            }
        }
        mapInitalize();
    }

    public void mapInitalize() {
        dMap.put(0, Tileset.Walk_left1);
        dMap.put(1, Tileset.Walk_right1);
        dMap.put(2, Tileset.Walk_down1);
        dMap.put(3, Tileset.Walk_up1);
    }

    public void createNewWorld() { // completely new
        this.world = new World(width, height, seed);
        this.game = world.getTiles();
        this.rooms = world.getRooms();
        this.md = 0;
        this.currWall = world.getCurrWall();
        Room bornRoom = rooms.get(0);
        TETile op = game[bornRoom.getSC() + 1][bornRoom.getSR() + 1]; // initial pos of AVATAR
        this.me = new Figure(bornRoom.getSC() + 1, bornRoom.getSR() + 1, op, dMap.get(md));
        game[bornRoom.getSC() + 1][bornRoom.getSR() + 1] = dMap.get(md);
        this.shade = new Figure(bornRoom.getSC() + 1, bornRoom.getSR() + 1, op, dMap.get(md));
        AntiWorld at = new AntiWorld(world, rooms, shade);
        this.antigame = at.getTiles();
        this.lighton = false; // set the light off at first
        this.fireon = false; // means no fire is on
        this.bd = 4; // means no direction decided
    }

    public void createPreWorld(ArrayList<Integer> pos) {
        this.world = new World(width, height, seed); // previous seed
        //        System.out.println(width + " " + height + " " + seed);
        this.game = world.getTiles();
        this.rooms = world.getRooms();
        this.currWall = world.getCurrWall();
        TETile op = game[pos.get(0)][pos.get(1)];
        this.me = new Figure(pos.get(0), pos.get(1), op, dMap.get(md)); // previous pos of AVATAR
        game[pos.get(0)][pos.get(1)] = dMap.get(md);
        this.shade = new Figure(pos.get(0), pos.get(1), op, dMap.get(md)); // previous pos of AVATAR
        AntiWorld at = new AntiWorld(world, rooms, shade);
        this.antigame = at.getTiles();
        this.fireon = false; // means no fire is on
        this.bd = 4; // means no direction decided
    }

    public void separateMove() {
        if (StdDraw.hasNextKeyTyped()) {
            char key = StdDraw.nextKeyTyped();
            operate(key);
        }
    }


    public void operate(char key) {
        int x = me.getX();
        int y = me.getY();
        if (key == 'A' || key == 'a') {
            if (canMove(x - 1, y)) {
                game[x][y] = me.getLastPos(); // put the TEtile back
                me.moveFig(x - 1, y, game[x - 1][y]);
                md = 0;
                me.setFigure(dMap.get(md));
                game[x - 1][y] = me.getFigure();

                antigame[x][y] = shade.getLastPos(); // put the TEtile back
                shade.moveFig(x - 1, y, antigame[x - 1][y]);
                shade.setFigure(dMap.get(md));
                antigame[x - 1][y] = shade.getFigure();
            }
        } else if (key == 'D' || key == 'd') {
            if (canMove(x + 1, y)) {
                game[x][y] = me.getLastPos(); // put the TEtile back
                me.moveFig(x + 1, y, game[x + 1][y]);
                this.md = 1;
                me.setFigure(dMap.get(md));
                game[x + 1][y] = me.getFigure();

                antigame[x][y] = shade.getLastPos(); // put the TEtile back
                shade.moveFig(x + 1, y, antigame[x + 1][y]);
                shade.setFigure(dMap.get(md));
                antigame[x + 1][y] = shade.getFigure();
            }
        } else if ((key == 'S' && !whetherStart) || (key == 's' && !whetherStart)) {
            if (canMove(x, y - 1)) {
                game[x][y] = me.getLastPos(); // put the TEtile back
                me.moveFig(x, y - 1, game[x][y - 1]);
                md = 2;
                me.setFigure(dMap.get(md));
                game[x][y - 1] = me.getFigure();

                antigame[x][y] = shade.getLastPos(); // put the TEtile back
                shade.moveFig(x, y - 1, antigame[x][y - 1]);
                shade.setFigure(dMap.get(md));
                antigame[x][y - 1] = shade.getFigure();
            }
        } else if (key == 'W' || key == 'w') {
            if (canMove(x, y + 1)) {
                game[x][y] = me.getLastPos(); // put the TEtile back
                me.moveFig(x, y + 1, game[x][y + 1]);
                md = 3;
                me.setFigure(dMap.get(md));
                game[x][y + 1] = me.getFigure();

                antigame[x][y] = shade.getLastPos(); // put the TEtile back
                shade.moveFig(x, y + 1, antigame[x][y + 1]);
                shade.setFigure(dMap.get(md));
                antigame[x][y + 1] = shade.getFigure();
            }
        } else if (key == 'm'|| key == 'M') {
            lighton = !lighton;
        } else if (key == 'j'|| key == 'J') {
            if (!fireon) {
                if (md == 0) {
                    bulletR = new Figure(x - 1, y, game[x - 1][y], Tileset.Bullet);
                    bulletA = new Figure(x - 1, y, antigame[x - 1][y], Tileset.Bullet);
                    game[x - 1][y] = Tileset.Bullet;
                    antigame[x - 1][y] = Tileset.Bullet;
                } else if (md == 1) {
                    bulletR = new Figure(x + 1, y, game[x + 1][y], Tileset.Bullet);
                    bulletA = new Figure(x + 1, y, antigame[x + 1][y], Tileset.Bullet);
                    game[x + 1][y] = Tileset.Bullet;
                    antigame[x + 1][y] = Tileset.Bullet;
                } else if (md == 2) {
                    bulletR = new Figure(x, y - 1, game[x][y - 1], Tileset.Bullet);
                    bulletA = new Figure(x, y - 1, antigame[x][y - 1], Tileset.Bullet);
                    game[x][y - 1] = Tileset.Bullet;
                    antigame[x][y - 1] = Tileset.Bullet;
                } else if (md == 3) {
                    bulletR = new Figure(x, y + 1, game[x][y + 1], Tileset.Bullet);
                    bulletA = new Figure(x, y + 1, antigame[x][y + 1], Tileset.Bullet);
                    game[x][y + 1] = Tileset.Bullet;
                    antigame[x][y + 1] = Tileset.Bullet;
                }
                fireon = true;
                bd = md;
            }
        } else if (key == ':') {
            whetherQuit = true;
        } else if (whetherQuit) {
            if (key == 'Q' || key == 'q') { // : and Q should be adjacency
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
                    writer.write("Seed: " + seed + "\n"); // write info into txt file
                    writer.write("last_pos: " + x + " " + y + "\n");
                    writer.write("last_direction: " + md + "\n");
                    writer.write("lighton?: " + lighton);
                    writer.flush();
                    System.exit(0);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            whetherQuit = false;
        } else if (key == 'L' || key == 'l') {
            wheLoading();
        } else if (key == 'N' || key == 'n') {
            whetherStart = true;
        } else if (whetherStart) { // find N and s
            if (key == 's' || key == 'S') {
                StringBuilder sb = new StringBuilder();
                for (Character ch : seedList) {
                    sb.append(ch);
                }
                this.seed = Long.parseLong(sb.toString());
                whetherStart = false;
                createNewWorld();
            } else {
                seedList.add(key); // collect seed
                displaySeed();
            }
        }
    }

    public void wheLoading() { // get the info of previous version
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            int i = 0;
            String nextLine;
            ArrayList<Integer> pos = new ArrayList<>();
            while ((nextLine = reader.readLine()) != null) {
                i++;
                String[] splitLine = nextLine.split(" ");
                if (i == 1) {
                    this.seed = Long.parseLong(splitLine[1]);
                } else if (i == 2) {
                    pos.add(Integer.valueOf(splitLine[1])); // previous pos of AVATAR
                    pos.add(Integer.valueOf(splitLine[2]));
                } else if (i == 3) {
                    this.md = Integer.parseInt(splitLine[1]);
                } else if (i == 4) {
                    this.lighton = Boolean.parseBoolean(splitLine[1]);
                    createPreWorld(pos);
                }
            }
            clearAll();
            if (i == 0) {
                System.exit(0);
            }
        } catch (IOException e) {
            //            System.out.println("meijinqu");
            System.out.println(e);
        }
    }

    public void clearAll() { // clear txt file
        try (FileWriter writer = new FileWriter(path, false)) {
            System.out.println("just for check style");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void displayInfo(double x, double y) {
        Font font = new Font("Monaco", Font.BOLD, 14);
        StdDraw.setFont(font);
        int tilex = (int) (x / 1); // get the exact tile position
        int tiley = (int) (y / 1);
        if (tilex < width && tiley < height) {
            String info = new String();
            if (lighton) {
                info = "Current tile:  " + antigame[tilex][tiley].description();
            } else {
                info = "Current tile:  " + game[tilex][tiley].description();
            }
            int posx = 63;
            int posy = 39;
            StdDraw.setPenColor(255, 255, 255);
            StdDraw.text(posx, posy, info);

            StdDraw.show();
            StdDraw.pause(20);

        }
    }

    public void initialize() {
        TETile op = game[0][0]; // initialize the tetile
        this.me = new Figure(0, 0, op, Tileset.NOTHING);
        this.shade = new Figure(0, 0, op, Tileset.NOTHING);
    }

    public void menu() {
        // 有键盘输入，退出等待
        while (!StdDraw.hasNextKeyTyped()) {
            // 暂停一小段时间，避免过于频繁的检查
            Font font1 = new Font("title", Font.ITALIC, 2 * height);
            StdDraw.setFont(font1);
            String title = "61B DEMO";
            StdDraw.setPenColor(255, 255, 255);
            StdDraw.text(33, 30, title);
            Font font2 = new Font("title", Font.ITALIC,  height);
            StdDraw.setFont(font2);
            StdDraw.text(33, 20, "New game: (N)");
            StdDraw.text(33, 15, "Load game (L)");
            StdDraw.text(33, 10, "Quit game (:Q)");
            StdDraw.show();
            StdDraw.pause(20);
        }
    }

    public void fire() {
        if (fireon) {
            int x = bulletR.getX();
            int y = bulletR.getY();
            game[x][y] = bulletR.getLastPos();
            antigame[x][y] = bulletA.getLastPos();
            if (bd == 0) {
                if (canMove(x - 1, y)) {
                    bulletR.moveFig(x - 1, y, game[x - 1][y]);
                    bulletA.moveFig(x - 1, y, antigame[x - 1][y]);
                    game[x - 1][y] = Tileset.Bullet;
                    antigame[x - 1][y] = Tileset.Bullet;
                } else {
                    fireon = false;
                    game[x][y] = bulletR.getLastPos();
                    antigame[x][y] = bulletA.getLastPos();
                }
            } else if (bd == 1) {
                if (canMove(x + 1, y)) {
                    bulletR.moveFig(x + 1, y, game[x + 1][y]);
                    bulletA.moveFig(x + 1, y, antigame[x + 1][y]);
                    game[x + 1][y] = Tileset.Bullet;
                    antigame[x + 1][y] = Tileset.Bullet;
                } else {
                    fireon = false;
                    game[x][y] = bulletR.getLastPos();
                    antigame[x][y] = bulletA.getLastPos();
                }
            } else if (bd == 2) {
                if (canMove(x, y - 1)) {
                    bulletR.moveFig(x, y - 1, game[x][y - 1]);
                    bulletA.moveFig(x, y - 1, antigame[x][y - 1]);
                    game[x][y - 1] = Tileset.Bullet;
                    antigame[x][y - 1] = Tileset.Bullet;
                } else {
                    fireon = false;
                    game[x][y] = bulletR.getLastPos();
                    antigame[x][y] = bulletA.getLastPos();
                }
            } else if (bd == 3) {
                if (canMove(x, y + 1)) {
                    bulletR.moveFig(x, y + 1, game[x][y + 1]);
                    bulletA.moveFig(x, y + 1, antigame[x][y + 1]);
                    game[x][y + 1] = Tileset.Bullet;
                    antigame[x][y + 1] = Tileset.Bullet;
                } else {
                    fireon = false;
                    game[x][y] = bulletR.getLastPos();
                    antigame[x][y] = bulletA.getLastPos();
                }
            }
        }
    }

    public void displaySeed() {
        while (!StdDraw.hasNextKeyTyped()) {
            // display seed
            Font font1 = new Font("title", Font.ITALIC,  3 * height / 2);
            StdDraw.setFont(font1);
            StringBuilder seedString = new StringBuilder();
            for (char c: seedList) {
                seedString.append(c);
            }
            StdDraw.setPenColor(255, 255, 255);
            StdDraw.text(33, 25, "Your Seed: " + seedString.toString());
            Font font2 = new Font("title", Font.ITALIC,  height);
            StdDraw.setFont(font2);
            StdDraw.text(33, 15, "End your seed with S");
            StdDraw.show();
            StdDraw.pause(20);
        }
    }
    public void rungame() {
        initialize();
        TERenderer ter = new TERenderer();
        ter.initialize(width, height + 2);
        ter.renderFrame(game);
        menu(); // MENU
        double lastUpdateTime = 0.0;
        int counter = 0; // counter for move of bullet and enemy
        while (true) {
            double currentTime = System.currentTimeMillis() / 1000.0;
            if (currentTime - lastUpdateTime > TIME_PER_FRAME) {
                separateMove(); // keyboard move
                counter++;
                if (counter == 3) {
                    fire();
                    counter = 0;
                }
                double mouseX = StdDraw.mouseX();
                double mouseY = StdDraw.mouseY();
                displayInfo(mouseX, mouseY); // display the info of a tile where the mouse on
                if (lighton) {
                    ter.renderFrame(antigame);
                } else {
                    ter.renderFrame(game);
                }
                lastUpdateTime = currentTime;
            }
        }
    }

    public TETile[][] getTiles() {
        return world.getTiles();
    }

    public boolean canMove(int x, int y) {
        return game[x][y] != currWall;
    }



    public static void main(String[] args) {
        SeparateMoves myworld = new SeparateMoves();
        myworld.rungame();
    }
}
