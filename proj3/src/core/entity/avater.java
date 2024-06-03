package core.entity;

import core.World;
import edu.princeton.cs.algs4.StdDraw;
import tileengine.TERenderer;
import tileengine.TETile;
import tileengine.Tileset;

public class avater extends Figure{

    private World world;
    private TETile[][] game;
    private TERenderer ter;
    private int Hp;

    private Move move;
    public avater(int x, int y, TETile pos, TETile fig, World world, TERenderer ter) {
        super(x, y, pos, fig);
        this.world = world;
        this.game = world.getTiles();
        this.Hp = 3;
        this.ter = ter;
    }

    @Override
    public void moveFig(int x, int y, TETile pos) {
        super.moveFig(x, y, pos);
    }

   public void move() {
        if (StdDraw.hasNextKeyTyped()) {
            char key = StdDraw.nextKeyTyped();
            int x = this.getX();
            int y = this.getY();
            if (key == 'a') {
                if (canMove(x-1,y)) {
                    game[x][y] = this.getLastPos(); // put the TEtile back
                    this.moveFig(x - 1, y, game[x - 1][y]);
                    game[x - 1][y] = Tileset.Walk_left1;
                    setFigure(Tileset.Walk_left1);
                    this.md = 0;
                }
            } else if (key == 'd') {
                if (canMove(x+1,y)) {
                    game[x][y] = this.getLastPos(); // put the TEtile back
                    this.moveFig(x + 1, y, game[x + 1][y]);
                    game[x + 1][y] = Tileset.Walk_right1;
                    setFigure(Tileset.Walk_right1);
                    this.md = 1;
                }
            } else if (key == 's') {
                if (canMove(x,y-1)) {
                    game[x][y] = this.getLastPos(); // put the TEtile back
                    this.moveFig(x, y - 1, game[x][y - 1]);
                    game[x][y - 1] = Tileset.Walk_down1;
                    setFigure(Tileset.Walk_down1);
                    this.md = 2;
                }
            } else if (key == 'w') {
                if (canMove(x,y+1)) {
                    game[x][y] = this.getLastPos(); // put the TEtile back
                    this.moveFig(x, y + 1, game[x][y + 1]);
                    game[x][y + 1] = Tileset.Walk_up1;
                    setFigure(Tileset.Walk_up1);
                    this.md = 3;
                }
            }
        }
    }

    public boolean canMove(int x, int y) {
        if (y >= world.getHeight() || y < 0) {
            return false;
        } else if ( x >= world.getWidth() || x < 0){
            return false;
        } else if (game[x][y] == Tileset.WALL) {
            return false;
        } else {
            return true;
        }
    }
    public void attack() {
        if (StdDraw.hasNextKeyTyped()) {
            char key = StdDraw.nextKeyTyped();
            if (key == 'j') {
                switch (md) {
                    case 0 -> attackDirection(Tileset.Attack_left1,Tileset.Attack_left2,Tileset.Attack_left3,Tileset.Attack_left4);
                    case 1 -> attackDirection(Tileset.Attack_right1,Tileset.Attack_right2,Tileset.Attack_right3,Tileset.Attack_right4);
                    case 2 -> attackDirection(Tileset.Attack_down1,Tileset.Attack_down2,Tileset.Attack_down3,Tileset.Attack_down4);
                    case 3 -> attackDirection(Tileset.Attack_up1,Tileset.Attack_up2,Tileset.Attack_up3,Tileset.Walk_up4);
                    default -> {}
                }
            }
        }
    }

    public void attackDirection(TETile frame1,TETile frame2,TETile frame3,TETile frame4) {
        //attack direction
        int x = this.getX();
        int y = this.getY();
        game[x][y] = frame1;
        ter.renderFrame(game);
        game[x][y] = frame2;
        ter.renderFrame(game);
        game[x][y] = frame3;
        ter.renderFrame(game);
        game[x][y] = frame4;
        ter.renderFrame(game);
        game[x][y] = getFigure();
    }

    @Override
    public TETile getFigure() {
        return super.getFigure();
    }
}
