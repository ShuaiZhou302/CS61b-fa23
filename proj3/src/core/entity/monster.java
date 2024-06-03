package core.entity;

import tileengine.TETile;

public class monster extends Figure{
    public monster(int x, int y, TETile pos, TETile fig) {
        super(x, y, pos, fig);
    }

    @Override
    public void moveFig(int x, int y, TETile pos) {
        super.moveFig(x, y, pos);
    }

}
