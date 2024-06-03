package tileengine;

import java.awt.Color;

/**
 * Contains constant tile objects, to avoid having to remake the same tiles in different parts of
 * the code.
 *
 * You are free to (and encouraged to) create and add your own tiles to this file. This file will
 * be turned in with the rest of your code.
 *
 * Ex:
 *      world[x][y] = Tileset.FLOOR;
 *
 * The style checker may crash when you try to style check this file due to use of unicode
 * characters. This is OK.
 */

public class Tileset {
    public static final TETile AVATAR = new TETile('@', Color.white, Color.black, "you");
    public static final TETile WALL = new TETile('#', new Color(216, 128, 128), Color.darkGray,
            "wall");
    public static final TETile FLOOR = new TETile('·', Color.black, Color.black,
            "floor");
    public static final TETile NOTHING = new TETile(' ', Color.black, Color.black, "nothing");
    public static final TETile GRASS = new TETile('"', Color.green, Color.black, "grass","C:\\Users\\111\\cs61b\\fa23-s676\\proj3\\extraTileset\\grass.png");
    public static final TETile WATER = new TETile('≈', Color.blue, Color.black, "water","C:\\Users\\111\\cs61b\\fa23-s676\\proj3\\extraTileset\\Ocean water.png");
    public static final TETile FLOWER = new TETile('❀', Color.magenta, Color.pink, "flower");
    public static final TETile LOCKED_DOOR = new TETile('█', Color.orange, Color.black, "locked door");
    public static final TETile UNLOCKED_DOOR = new TETile('▢', Color.orange, Color.black,
            "unlocked door");
    public static final TETile SAND = new TETile('▒', Color.yellow, Color.black, "sand");
    public static final TETile MOUNTAIN = new TETile('▲', Color.gray, Color.black, "mountain");
    public static final TETile TREE = new TETile('♠', Color.green, Color.green, "tree","C:\\Users\\111\\cs61b\\fa23-s676\\proj3\\extraTileset\\Bush 16x16 (002)1.png");

    public static final TETile Cheems = new TETile('C',Color.black,Color.yellow,"dogge","C:\\Users\\111\\cs61b\\fa23-s676\\proj3\\extraTileset\\Cheems.png");
    public static final TETile Ocean = new TETile('O',Color.blue,Color.blue,"Ocean","C:\\Users\\111\\cs61b\\fa23-s676\\proj3\\extraTileset\\Ocean.png");

    public static final TETile RedRock = new TETile('R',Color.red,Color.red,"redRock","C:\\Users\\111\\cs61b\\fa23-s676\\proj3\\extraTileset\\Fwall.png");
    public static final TETile Lava = new TETile('L',Color.red,Color.red,"Lava","C:\\Users\\111\\cs61b\\fa23-s676\\proj3\\extraTileset\\lava.png");

    public static final TETile Walk_down1 = new TETile('D',Color.WHITE,Color.WHITE,"Justin","C:\\Users\\111\\cs61b\\fa23-s676\\proj3\\extraTileset\\walking\\walk down1.png");
    public static final TETile Walk_down2 = new TETile('D',Color.BLACK,Color.BLACK,"Justin","C:\\Users\\111\\cs61b\\fa23-s676\\proj3\\extraTileset\\walking\\walk down2.png");
    public static final TETile Walk_down3 = new TETile('D',Color.BLACK,Color.BLACK,"Justin","C:\\Users\\111\\cs61b\\fa23-s676\\proj3\\extraTileset\\walking\\walk down3.png");
    public static final TETile Walk_down4 = new TETile('D',Color.BLACK,Color.BLACK,"Justin","C:\\Users\\111\\cs61b\\fa23-s676\\proj3\\extraTileset\\walking\\walk down4.png");

    public static final TETile Walk_left1 = new TETile('L',Color.WHITE,Color.WHITE,"Justin","C:\\Users\\111\\cs61b\\fa23-s676\\proj3\\extraTileset\\walking\\walk left1.png");
    public static final TETile Walk_left2 = new TETile('L',Color.BLACK,Color.BLACK,"Justin","C:\\Users\\111\\cs61b\\fa23-s676\\proj3\\extraTileset\\walking\\walk left2.png");
    public static final TETile Walk_left3 = new TETile('L',Color.BLACK,Color.BLACK,"Justin","C:\\Users\\111\\cs61b\\fa23-s676\\proj3\\extraTileset\\walking\\walk left3.png");
    public static final TETile Walk_left4 = new TETile('L',Color.BLACK,Color.BLACK,"Justin","C:\\Users\\111\\cs61b\\fa23-s676\\proj3\\extraTileset\\walking\\walk left4.png");

    public static final TETile Walk_right1 = new TETile('R',Color.WHITE,Color.WHITE,"Justin","C:\\Users\\111\\cs61b\\fa23-s676\\proj3\\extraTileset\\walking\\walk right1.png");
    public static final TETile Walk_right2 = new TETile('R',Color.BLACK,Color.BLACK,"Justin","C:\\Users\\111\\cs61b\\fa23-s676\\proj3\\extraTileset\\walking\\walk right2.png");
    public static final TETile Walk_right3 = new TETile('R',Color.BLACK,Color.BLACK,"Justin","C:\\Users\\111\\cs61b\\fa23-s676\\proj3\\extraTileset\\walking\\walk right3.png");
    public static final TETile Walk_right4 = new TETile('R',Color.BLACK,Color.BLACK,"Justin","C:\\Users\\111\\cs61b\\fa23-s676\\proj3\\extraTileset\\walking\\walk right4.png");

    public static final TETile Walk_up1 = new TETile('U',Color.WHITE,Color.WHITE,"Justin","C:\\Users\\111\\cs61b\\fa23-s676\\proj3\\extraTileset\\walking\\walk up1.png");
    public static final TETile Walk_up2 = new TETile('U',Color.BLACK,Color.BLACK,"Justin","C:\\Users\\111\\cs61b\\fa23-s676\\proj3\\extraTileset\\walking\\walk up2.png");
    public static final TETile Walk_up3 = new TETile('U',Color.BLACK,Color.BLACK,"Justin","C:\\Users\\111\\cs61b\\fa23-s676\\proj3\\extraTileset\\walking\\walk up3.png");
    public static final TETile Walk_up4 = new TETile('U',Color.BLACK,Color.BLACK,"Justin","C:\\Users\\111\\cs61b\\fa23-s676\\proj3\\extraTileset\\walking\\walk up4.png");

    public static final TETile Attack_down1 = new TETile('D',Color.BLACK,Color.BLACK,"Justin","C:\\Users\\111\\cs61b\\fa23-s676\\proj3\\extraTileset\\attack\\attack down1.png");
    public static final TETile Attack_down2 = new TETile('D',Color.BLACK,Color.BLACK,"Justin","C:\\Users\\111\\cs61b\\fa23-s676\\proj3\\extraTileset\\attack\\attack down2.png");
    public static final TETile Attack_down3 = new TETile('D',Color.BLACK,Color.BLACK,"Justin","C:\\Users\\111\\cs61b\\fa23-s676\\proj3\\extraTileset\\attack\\attack down3.png");
    public static final TETile Attack_down4 = new TETile('D',Color.BLACK,Color.BLACK,"Justin","C:\\Users\\111\\cs61b\\fa23-s676\\proj3\\extraTileset\\attack\\attack down4.png");

    public static final TETile Attack_left1 = new TETile('L',Color.BLACK,Color.BLACK,"Justin","C:\\Users\\111\\cs61b\\fa23-s676\\proj3\\extraTileset\\attack\\attack left1.png");
    public static final TETile Attack_left2 = new TETile('L',Color.BLACK,Color.BLACK,"Justin","C:\\Users\\111\\cs61b\\fa23-s676\\proj3\\extraTileset\\attack\\attack left2.png");
    public static final TETile Attack_left3 = new TETile('L',Color.BLACK,Color.BLACK,"Justin","C:\\Users\\111\\cs61b\\fa23-s676\\proj3\\extraTileset\\attack\\attack left3.png");
    public static final TETile Attack_left4 = new TETile('L',Color.BLACK,Color.BLACK,"Justin","C:\\Users\\111\\cs61b\\fa23-s676\\proj3\\extraTileset\\attack\\attack left4.png");

    public static final TETile Attack_right1 = new TETile('R',Color.BLACK,Color.BLACK,"Justin","C:\\Users\\111\\cs61b\\fa23-s676\\proj3\\extraTileset\\attack\\attack right1.png");
    public static final TETile Attack_right2 = new TETile('R',Color.BLACK,Color.BLACK,"Justin","C:\\Users\\111\\cs61b\\fa23-s676\\proj3\\extraTileset\\attack\\attack right2.png");
    public static final TETile Attack_right3 = new TETile('R',Color.BLACK,Color.BLACK,"Justin","C:\\Users\\111\\cs61b\\fa23-s676\\proj3\\extraTileset\\attack\\attack right3.png");
    public static final TETile Attack_right4 = new TETile('R',Color.BLACK,Color.BLACK,"Justin","C:\\Users\\111\\cs61b\\fa23-s676\\proj3\\extraTileset\\attack\\attack right4.png");

    public static final TETile Attack_up1 = new TETile('U',Color.BLACK,Color.BLACK,"Justin","C:\\Users\\111\\cs61b\\fa23-s676\\proj3\\extraTileset\\attack\\attack up1.png");
    public static final TETile Attack_up2 = new TETile('U',Color.BLACK,Color.BLACK,"Justin","C:\\Users\\111\\cs61b\\fa23-s676\\proj3\\extraTileset\\attack\\attack up2.png");
    public static final TETile Attack_up3 = new TETile('U',Color.BLACK,Color.BLACK,"Justin","C:\\Users\\111\\cs61b\\fa23-s676\\proj3\\extraTileset\\attack\\attack up3.png");
    public static final TETile Attack_up4 = new TETile('U',Color.BLACK,Color.BLACK,"Justin","C:\\Users\\111\\cs61b\\fa23-s676\\proj3\\extraTileset\\attack\\attack up4.png");

    public static final TETile Source = new TETile('S',Color.black,Color.black,"source","C:\\Users\\111\\cs61b\\fa23-s676\\proj3\\extraTileset\\source.png");
    public static final TETile LY1 = new TETile('S',new Color(44,62,195),new Color(44,62,195),"light");
    public static final TETile LY2 = new TETile('S',new Color(33,49,147),new Color(33,49,147),"light");
    public static final TETile LY3 = new TETile('S',new Color(14,28,113),new Color(14,28,113),"light");
    public static final TETile LY4 = new TETile('S',new Color(9,19,88),new Color(9,19,88),"light");

    public static final TETile Bullet = new TETile('O',Color.white,Color.black,"bullet","C:\\Users\\111\\cs61b\\fa23-s676\\proj3\\extraTileset\\bullet.png");
}



