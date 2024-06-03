package core.mapGenerate;
import core.World;
import tileengine.TETile;
import tileengine.Tileset;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class PathFinder {
    private World world;
    private TETile[][] tiles;
    private int[][] graph;
    private Random RANDOM;

    private UnionFind uf;

    private ArrayList<Room> allRooms;

    private ArrayList<Room> remainRooms;
    private ArrayList<int[]> floors;
    private boolean[][] wheAllHallWayConnectToRoom;
    private ArrayList<Integer> currPos;
    private ArrayList<PathFinder.Hallway> allHallways;
    private int parentRoomIndex;
    private int countNum;
    private final int floorset = 99; //all floors (hallway+room) set to 99
    private final int squareset = 100; // all other squares are set to 100
    public PathFinder(World world, Random randDOM, Roomplacement rp) {
        this.world = world;
        this.tiles = world.getTiles();
        this.RANDOM = randDOM;
        this.allRooms = rp.getAllRooms();
        this.floors = rp.getFloors();
        this.uf = new UnionFind(allRooms.size());
        this.currPos = new ArrayList<>();
        this.allHallways = new ArrayList<>();
        this.countNum = 0;
    }

    public void goPathFinder() {
        storeAllRooms();
        findPath(); // make path (iterate every room twice, finding a path when iterating a room)
        findOnePath(); // find whether all rooms are connected by hallways
        findAllFloorsAndRooms();
        deleteDeadEndHallway();
        finalRender();
    }

    public void storeAllRooms() { // all inner part of the room are set to 1 and other parts are set to 0
        this.graph = new int[world.getWidth()][world.getHeight()];
        for (int i = 0; i < world.getWidth(); i++) {
            for (int j = 0; j < world.getHeight(); j++) {
                graph[i][j] = 0;
            }
        }
        for (Room room : allRooms) {
            for (int i = room.getSC() + 1; i <= room.getSC() + room.getW() - 2; i++) {
                for (int j = room.getSR() + 1; j <= room.getSR() + room.getH() - 2; j++) {
                    graph[i][j] = 1;
                }
            }
        }

        this.wheAllHallWayConnectToRoom = new boolean[world.getWidth()][world.getHeight()];
        for (int i = 0; i < world.getWidth(); i++) {
            for (int j = 0; j < world.getHeight(); j++) {
                this.wheAllHallWayConnectToRoom[i][j] = false;
            }
        }
    }

    public void findPath() {
        int startX = 1;
        int startY = 1;
        currPos.add(startX);
        currPos.add(startY);

        int i = 0;
        while (i < 3) { // iterate all rooms
            i++;
            findAllPath();
        }
    }

    public void findAllPath() { // helper function

        for (Room room : allRooms) {

            int[] theRoomBorder = roomBorder(room);

            int x = theRoomBorder[0];
            int y = theRoomBorder[1];
            currPos.set(0, x); // store current position
            currPos.set(1, y);

            int count = 1;
            while (count < 8) {
                count++;

                int tileNum = RANDOM.nextInt(4); // randomly pick a direction
                switch (tileNum) {
                    case 0 -> walkRight(currPos.get(0), currPos.get(1));
                    case 1 -> walkUp(currPos.get(0), currPos.get(1));
                    case 2 -> walkLeft(currPos.get(0), currPos.get(1));
                    case 3 -> walkDown(currPos.get(0), currPos.get(1));
                    default -> {
                        // 不需要执行任何操作，因为我们知道默认情况不会发生
                    }
                }
            }
        }
    }

    public void walkRight(int startX, int startY) { // walk right

        if (!crossBorder(startX + 1, startY) && !crossBorder(startX + 2, startY)) {
            // walking right at least 2 steps and check validity
            if (tiles[startX + 1][startY] == Tileset.FLOOR || tiles[startX + 2][startY] == Tileset.FLOOR) {
                return;
            }
        } else {
            return;
        }

        int x = startX + 1;

        while (!crossBorder(x, startY) && tiles[x][startY] != Tileset.FLOOR) {
            int checkX = x;
            if (whetherToTurnAround(checkX + 1, startY) || whetherToTurnAround(checkX + 2, startY)) {
                break; // whether to turn around
            }

            if (!whetherValidRight(x, startY)) {
                break; // whether to go right another step
            }
            tiles[x][startY] = Tileset.FLOOR;
            floors.add(new int[]{x, startY}); // keep track of all floors
            findAdjToWall(x, startY); // set adjacent squares into walls

            currPos.set(0, x); // keep track of current position
            currPos.set(1, startY);
            x++; // next step

        }
        if (whetherCollideRight(x, startY)) { // whether valid to make intersection
            tiles[x][startY] = Tileset.FLOOR; // collide the wall and set it into floor
            tiles[x + 1][startY] = Tileset.FLOOR;
        }
    }

    public Boolean whetherCollideRight(int x, int y) {
        if (tiles[x][y] == Tileset.WALL) {
            if (!crossBorder(x + 1, y) && !crossBorder(x + 1, y + 1) && !crossBorder(x + 1, y - 1)) {
                return tiles[x + 1][y] == Tileset.WALL
                        && tiles[x + 1][y + 1] == Tileset.WALL
                        && tiles[x + 1][y - 1] == Tileset.WALL;
            }
        }
        return false;
    }

    public Boolean whetherValidRight(int x, int y) { // whether there are enough space to go forward

        if (tiles[x][y + 1] != Tileset.FLOOR && tiles[x][y - 1] != Tileset.FLOOR) {
            if (tiles[x + 1][y] == Tileset.SAND) {
                return tiles[x + 1][y - 1] == Tileset.SAND && tiles[x + 1][y + 1] == Tileset.SAND;
            }
        }

        return false;
    }

    public void walkLeft(int startX, int startY) { // walk left
        if (!crossBorder(startX - 1, startY) && !crossBorder(startX - 2, startY)) {
            if (tiles[startX - 1][startY] == Tileset.FLOOR || tiles[startX - 2][startY] == Tileset.FLOOR) {
                return;
            }
        } else {
            return;
        }

        int x = startX - 1;

        while (!crossBorder(x, startY) && tiles[x][startY] != Tileset.FLOOR) {
            int checkX = x;
            if (whetherToTurnAround(checkX - 1, startY) || whetherToTurnAround(checkX - 2, startY)) {
                break;
            }

            if (!whetherValidLeft(x, startY)) {
                break;
            }

            tiles[x][startY] = Tileset.FLOOR;
            floors.add(new int[]{x, startY});
            findAdjToWall(x, startY);

            currPos.set(0, x);
            currPos.set(1, startY);
            x--;

        }
        if (whetherCollideLeft(x, startY)) {
            tiles[x][startY] = Tileset.FLOOR;
            tiles[x - 1][startY] = Tileset.FLOOR;
        }
    }

    public Boolean whetherCollideLeft(int x, int y) {
        if (tiles[x][y] == Tileset.WALL) {
            if (!crossBorder(x - 1, y)
                    && !crossBorder(x - 1, y + 1)
                    && !crossBorder(x - 1, y - 1)) {
                return tiles[x - 1][y] == Tileset.WALL
                        && tiles[x - 1][y + 1] == Tileset.WALL
                        && tiles[x - 1][y - 1] == Tileset.WALL;
            }
        }
        return false;
    }

    public Boolean whetherValidLeft(int x, int y) {
        if (tiles[x][y - 1] != Tileset.FLOOR && tiles[x][y + 1] != Tileset.FLOOR) {
            if (tiles[x - 1][y] == Tileset.SAND) {
                return tiles[x - 1][y - 1] == Tileset.SAND && tiles[x - 1][y + 1] == Tileset.SAND;
            }
        }
        return false;
    }

    public void walkUp(int startX, int startY) { // walk up
        if (!crossBorder(startX, startY + 1) && !crossBorder(startX, startY + 2)) {
            if (tiles[startX][startY + 1] == Tileset.FLOOR || tiles[startX][startY + 2] == Tileset.FLOOR) {
                return;
            }
        } else {
            return;
        }

        int y = startY + 1;

        while (!crossBorder(startX, y) && tiles[startX][y] != Tileset.FLOOR) {
            int checkY = y;
            if (whetherToTurnAround(startX, checkY + 1)) {
                break;
            }

            if (!whetherValidUp(startX, y)) {
                break;
            }

            tiles[startX][y] = Tileset.FLOOR;
            floors.add(new int[]{startX, y});
            findAdjToWall(startX, y);

            currPos.set(0, startX);
            currPos.set(1, y);
            y++;

        }
        if (whetherCollideUp(startX, y)) {
            tiles[startX][y] = Tileset.FLOOR;
            tiles[startX][y + 1] = Tileset.FLOOR;
        }
    }

    public Boolean whetherCollideUp(int x, int y) {

        if (tiles[x][y] == Tileset.WALL) {
            if (!crossBorder(x, y)
                    && !crossBorder(x + 1, y + 1)
                    && !crossBorder(x - 1, y + 1)) {
                return tiles[x][y + 1] == Tileset.WALL
                        && tiles[x + 1][y + 1] == Tileset.WALL
                        && tiles[x - 1][y + 1] == Tileset.WALL;
            }
        }
        return false;
    }

    public Boolean whetherValidUp(int x, int y) {
        if (!crossBorder(x - 1, y) && !crossBorder(x + 1, y)) {
            if (tiles[x - 1][y] != Tileset.FLOOR && tiles[x + 1][y] != Tileset.FLOOR) {
                if (tiles[x][y + 1] == Tileset.SAND) {
                    return tiles[x - 1][y + 1] == Tileset.SAND && tiles[x + 1][y + 1] == Tileset.SAND;
                }
            }
        }
        return false;
    }

    public void walkDown(int startX, int startY) { // walk down
        if (!crossBorder(startX, startY - 1)
                && !crossBorder(startX, startY - 2)) { // check whether the next square is cross border
            if (tiles[startX][startY - 1] == Tileset.FLOOR || tiles[startX][startY - 2] == Tileset.FLOOR) {
                return;
            }
        } else {
            return;
        }

        int y = startY - 1;

        while (!crossBorder(startX, y) && tiles[startX][y] != Tileset.FLOOR) {

            int checkY = y;
            if (whetherToTurnAround(startX, checkY - 1) || whetherToTurnAround(startX, checkY - 2)) {
                break; // check whether to turn around
            }

            if (!whetherValidDown(startX, y)) { // check whether to go down
                break;
            }

            tiles[startX][y] = Tileset.FLOOR;
            floors.add(new int[]{startX, y}); // floors keep track of all floors(rooms and hallways)
            findAdjToWall(startX, y); // set adjacent to walls

            currPos.set(0, startX); // keep track of current position
            currPos.set(1, y);
            y--; // next square

        }
        if (whetherCollideDown(startX, y)) { // whether it is valid to intersect with a hallway
            tiles[startX][y] = Tileset.FLOOR; // collide the wall and set it into floor(make intersections)
            tiles[startX][y - 1] = Tileset.FLOOR;
        }
    }

    public Boolean whetherCollideDown(int x, int y) {
        // when it is a hallway-like edge,meaning it is valid to intersect, return true
        if (tiles[x][y] == Tileset.WALL) {
            if (!crossBorder(x + 1, y)
                    && !crossBorder(x - 1, y - 1)
                    && !crossBorder(x + 1, y - 1)) {
                return tiles[x][y - 1] == Tileset.WALL
                        && tiles[x + 1][y - 1] == Tileset.WALL
                        && tiles[x - 1][y - 1] == Tileset.WALL;
            }
        }
        return false;
    }

    public Boolean whetherValidDown(int x, int y) { // whether it is valid to go down
        if (tiles[x - 1][y] != Tileset.FLOOR && tiles[x + 1][y] != Tileset.FLOOR) {
            if (tiles[x][y - 1] == Tileset.SAND) {
                return tiles[x - 1][y - 1] == Tileset.SAND && tiles[x + 1][y - 1] == Tileset.SAND;
            }
        }
        return false;
    }

    //    是否过界
    public Boolean crossBorder(int x, int y) {
        return x < 0 || x >= world.getWidth() || y < 0 || y >= world.getHeight();
    }

    //    要不要转弯
    public Boolean whetherToTurnAround(int x, int y) {
        if (crossBorder(x, y)) {
            return true;
        }

        // when meet a floor, turn around
        return tiles[x][y] == Tileset.FLOOR;
    }
    public int[] roomBorder(Room room) { // helper function
        int[] cor = new int[2];
        int num = RANDOM.nextInt(4); // randomly pick a square on the border of the inner place of a room
        switch (num) {
            case 0 -> { // 4 cases
                return new int[]{room.getSC() + 1, room.getSR() + RANDOM.nextInt(room.getH() - 2) + 1};
            }
            case 1 -> {
                return new int[]{room.getSC() + room.getW() - 2, room.getSR() + RANDOM.nextInt(room.getH() - 2) + 1};
            }
            case 2 -> {
                return new int[]{room.getSC() + RANDOM.nextInt(room.getW() - 2) + 1, room.getSR() + 1};
            }
            case 3 -> {
                return new int[]{room.getSC() + RANDOM.nextInt(room.getW() - 2) + 1, room.getSR() + room.getH() - 2};
            }
            default -> {
                //  no need any operation cause we know it will never happened
            }
        }
        return cor;
    }
    //    找相邻块 变成wall
    public void findAdjToWall(int x, int y) {
        if (tiles[x][y - 1] == Tileset.SAND) {
            tiles[x][y - 1] = Tileset.WALL;
        }
        if (tiles[x][y + 1] == Tileset.SAND) {
            tiles[x][y + 1] = Tileset.WALL;
        }
        if (tiles[x + 1][y] == Tileset.SAND) {
            tiles[x + 1][y] = Tileset.WALL;
        }
        if (tiles[x - 1][y] == Tileset.SAND) {
            tiles[x - 1][y] = Tileset.WALL;
        }
        if (tiles[x - 1][y - 1] == Tileset.SAND) {
            tiles[x - 1][y - 1] = Tileset.WALL;
        }
        if (tiles[x - 1][y + 1] == Tileset.SAND) {
            tiles[x - 1][y + 1] = Tileset.WALL;
        }
        if (tiles[x + 1][y - 1] == Tileset.SAND) {
            tiles[x + 1][y - 1] = Tileset.WALL;
        }
        if (tiles[x + 1][y + 1] == Tileset.SAND) {
            tiles[x + 1][y + 1] = Tileset.WALL;
        }
    }

    public void findOnePath() {
        int[][] diffNumsRoomGraph = new int[world.getWidth()][world.getHeight()]; // create a int[][] of the same size
        for (int i = 0; i < world.getWidth(); i++) {
            for (int j = 0; j < world.getHeight(); j++) {
                if (floors.contains(new int[]{i, j})) {
                    diffNumsRoomGraph[i][j] = floorset; //all floors (hallway+room) set to 99
                } else {
                    diffNumsRoomGraph[i][j] = squareset; // all other squares are set to 100(walls, unused space)
                }
            }
        }
        for (Room room1 : allRooms) { // change different rooms into different numbers
            for (int i = room1.getSC() + 1; i <= room1.getSC() + room1.getW() - 2; i++) {
                for (int j = room1.getSR() + 1; j <= room1.getSR() + room1.getH() - 2; j++) {
                    diffNumsRoomGraph[i][j] = allRooms.indexOf(room1);
                    // give different rooms different index based on allrooms
                }
            }
        }


        // after
        for (int i = 0; i < allRooms.size(); i++) {
            for (int j = i + 1; j < allRooms.size(); j++) {
                roomsConnected(allRooms.get(i), allRooms.get(j), diffNumsRoomGraph);
            }
        }

        int[] data = uf.getData();
        int minNum = data[0];
        int index = 0;
        for (int i = 0; i < data.length; i++) {
            if (data[i] < minNum && data[i] != -1) {
                minNum = data[i];
                index = i;
            }
        }

        this.parentRoomIndex = index;

        //before
        this.remainRooms = new ArrayList<>();
        for (int i = 0; i < allRooms.size(); i++) {
            if (!uf.connected(index, i)) { //find the disconnected rooms
                deleteDisconnectedRoom(i);
            } else {
                remainRooms.add(allRooms.get(i));
            }
        }
    }


    public ArrayList<Room> getRemainRooms() {
        return remainRooms;
    }

    public void roomsConnected(Room room1, Room room2, int[][] diffNumsRoomGraph) {
        countNum = 0;
        Boolean[][] whetherWalkBefore = new Boolean[world.getWidth()][world.getHeight()];
        // to keep track of whether the floor has been walked before
        for (int i = 0; i < world.getWidth(); i++) {
            for (int j = 0; j < world.getHeight(); j++) {
                whetherWalkBefore[i][j] = false;
                // default value of every floor are set to false(have not been walked before)
            }
        }
        findPathFromRoomCornerToRoom(room1, room1.getSC() + 1,
                room1.getSR() + 1, room2, diffNumsRoomGraph, whetherWalkBefore);
        // find the path between two rooms
    }

    public void deleteDisconnectedRoom(int index) { // delete the disconnected room
        Room room = allRooms.get(index);

        for (int i = room.getSC(); i <= room.getSC() + room.getW(); i++) {
            for (int j = room.getSR(); j <= room.getSR() + room.getH(); j++) {
                graph[i][j] = 3;
                tiles[i][j] = Tileset.WALL; // set the floor to wall
            }
        }
    }

    public void findAllFloorsAndRooms() {

        for (int i = 0; i < world.getWidth(); i++) {
            for (int j = 0; j < world.getHeight(); j++) {
                graph[i][j] = 0;
                if (tiles[i][j] == Tileset.FLOOR) {
                    graph[i][j] = 1; //所有floor改成1（包括rooms和hallways）
                } else if (tiles[i][j] == Tileset.WALL) {
                    graph[i][j] = 3; // 所有wall改成3
                }
            }
        }

        for (Room room : allRooms) {
            for (int i = room.getSC() + 1; i <= room.getSC() + room.getW() - 2; i++) {
                for (int j = room.getSR() + 1; j <= room.getSR() + room.getH() - 2; j++) {
                    graph[i][j] = 2; //所有rooms内部是2，hallways是1 separate rooms and hallways
                }
            }
        }

        for (int i = 0; i < world.getWidth(); i++) {
            for (int j = 0; j < world.getHeight(); j++) {
                if (graph[i][j] == 1) {
                    allHallways.add(new PathFinder.Hallway(i, j)); // collection of hallways
                }
            }
        }
    }


    public class Hallway {
        private int col;
        private int row;

        public Hallway(int x, int y) {
            this.col = x;
            this.row = y;
        }

        public int getCol() {
            return col;
        }

        public int getRow() {
            return row;
        }
    }


    public void deleteDeadEndHallway() { // delete dead end hallways
        for (int i = 0; i < allHallways.size(); i++) {
            for (PathFinder.Hallway hallway : allHallways) {

                if (adjWalls(hallway) >= 3) { // all dead end hallways has 3 adjacent walls
                    graph[hallway.col][hallway.row] = 3;
                    tiles[hallway.col][hallway.row] = Tileset.WALL; // set the dead end hallway into wall
                }
            }
            for (int h = 0; h < world.getWidth(); h++) {
                for (int j = 0; j < world.getHeight(); j++) {
                    if (graph[h][j] == 1) {
                        if (discreteHallways(new PathFinder.Hallway(h, j))) {

                            graph[h][j] = 3;
                            tiles[h][j] = Tileset.WALL;
                        }
                    }
                }
            }
        }
    }

    public Boolean discreteHallways(PathFinder.Hallway hallway) {
        if (adjConnect(hallway)) {

            return false;
        }
        Boolean[][] whetherWalkBefore = new Boolean[world.getWidth()][world.getHeight()];
        // to keep track of whether the floor has been walked before

        for (int i = 0; i < world.getWidth(); i++) {
            for (int j = 0; j < world.getHeight(); j++) {
                whetherWalkBefore[i][j] = false;
                // default value of every floor are set to false(have not been walked before)
            }
        }
        countNum = 0;

        if (hallwayConnectToRoom(hallway, whetherWalkBefore)) {

            return false;
        }
        return true;
    }

    public boolean adjConnect(PathFinder.Hallway hallway) { // whether adj hallways are connected
        int col = hallway.col;
        int row = hallway.row;
        if (!crossBorder(col + 1, row) && wheAllHallWayConnectToRoom[col + 1][row]) {
            wheAllHallWayConnectToRoom[col][row] = true;
            return true;
        }
        if (!crossBorder(col - 1, row) && wheAllHallWayConnectToRoom[col - 1][row]) {
            wheAllHallWayConnectToRoom[col][row] = true;
            return true;
        }
        if (!crossBorder(col, row + 1) && wheAllHallWayConnectToRoom[col][row + 1]) {
            wheAllHallWayConnectToRoom[col][row] = true;
            return true;
        }
        if (!crossBorder(col, row - 1) && wheAllHallWayConnectToRoom[col][row - 1]) {
            wheAllHallWayConnectToRoom[col][row] = true;
            return true;
        }
        return false;
    }

    public Boolean hallwayConnectToRoom(PathFinder.Hallway iniHallWay, Boolean[][] whetherWalkBefore) {

        Stack<PathFinder.Hallway> stack = new Stack<>();
        stack.push(iniHallWay);

        while (!stack.isEmpty()) {
            PathFinder.Hallway current = stack.pop();
            int col = current.col;
            int row = current.row;

            // Check bounds
            if (crossBorder(col, row) || whetherWalkBefore[col][row]) {
                continue;
            }

            // Mark the cell as visited
            whetherWalkBefore[col][row] = true;

            if (wheAllHallWayConnectToRoom[col][row]) {
                wheAllHallWayConnectToRoom[iniHallWay.col][iniHallWay.row] = true;
                return true;
            }

            // If the cell is a room, return true
            if (graph[col][row] == 2) {
                wheAllHallWayConnectToRoom[col][row] = true;
                wheAllHallWayConnectToRoom[iniHallWay.col][iniHallWay.row] = true;
                return true;
            }

            // If the cell is a hallway, add the adjacent cells to the stack
            if (graph[col][row] == 1) {
                stack.push(new PathFinder.Hallway(col + 1, row));
                stack.push(new PathFinder.Hallway(col - 1, row));
                stack.push(new PathFinder.Hallway(col, row + 1));
                stack.push(new PathFinder.Hallway(col, row - 1));
            }
        }
        return false;
    }

    public void finalRender() {
        for (int i = 0; i < world.getWidth(); i++) {
            for (int j = 0; j < world.getHeight(); j++) {
                if (tiles[i][j] != Tileset.FLOOR) {
                    if (whetherAdjFloor(i, j)) { // when the wall is not neighbour of a floor, set it into SAND
                        tiles[i][j] = Tileset.WALL; // delete dead end neighbour which is set to wall
                    } else {
                        tiles[i][j] = Tileset.SAND;
                    }
                }
            }
        }
    }

    public Boolean whetherAdjFloor(int x, int y) { // check whether it is neighbour of a floor
        if (!crossBorder(x + 1, y) && tiles[x + 1][y] == Tileset.FLOOR) {
            return true;
        }
        if (!crossBorder(x - 1, y) && tiles[x - 1][y] == Tileset.FLOOR) {
            return true;
        }
        if (!crossBorder(x, y + 1) && tiles[x][y + 1] == Tileset.FLOOR) {
            return true;
        }
        return !crossBorder(x, y - 1) && tiles[x][y - 1] == Tileset.FLOOR;
    }

    public int adjWalls(PathFinder.Hallway hallway) {
        int x = hallway.getCol();
        int y = hallway.getRow();

        int count = 0;
        if (graph[x + 1][y] == 3) {
            count++;
        }
        if (graph[x - 1][y] == 3) {
            count++;
        }
        if (graph[x][y + 1] == 3) {
            count++;
        }
        if (graph[x][y - 1] == 3) {
            count++;
        }
        return count;
    }


    public void findPathFromRoomCornerToRoom(Room room1, int x, int y,
                                             Room room2, int[][] diffNumsRoomGraph, Boolean[][] whetherWalkBefore) {

        whetherWalkBefore[x][y] = true; // keep track of the square(x, y) and set to true
        countNum++; // avoid from infinite recursion
        int numOfRoom2 = allRooms.indexOf(room2);
        int numOfRoom1 = allRooms.indexOf(room1);

        if (diffNumsRoomGraph[x][y] == numOfRoom2) {
            // if the square is the inner part of the room, stop recursion
            if (!uf.connected(numOfRoom1, numOfRoom2)) {

                uf.union(numOfRoom1, numOfRoom2);
                return;
            }
        }
        if (diffNumsRoomGraph[x][y] != floorset && diffNumsRoomGraph[x][y] != squareset) {
            if (!uf.connected(numOfRoom1, diffNumsRoomGraph[x][y])) {

                uf.union(numOfRoom1, diffNumsRoomGraph[x][y]);
                // when finding the path, may find the path to other rooms, still store the connection

            }
        }
        if (countNum >= floors.size()) {
            return;
        }
        if (!crossBorder(x + 1, y) && tiles[x + 1][y] == Tileset.FLOOR && !whetherWalkBefore[x + 1][y]) { // go right
            findPathFromRoomCornerToRoom(room1, x + 1, y, room2, diffNumsRoomGraph, whetherWalkBefore);
        }
        if (!crossBorder(x - 1, y) && tiles[x - 1][y] == Tileset.FLOOR && !whetherWalkBefore[x - 1][y]) { // go left
            findPathFromRoomCornerToRoom(room1, x - 1, y, room2, diffNumsRoomGraph, whetherWalkBefore);
        }
        if (!crossBorder(x, y + 1) && tiles[x][y + 1] == Tileset.FLOOR && !whetherWalkBefore[x][y + 1]) { // go up
            findPathFromRoomCornerToRoom(room1, x, y + 1, room2, diffNumsRoomGraph, whetherWalkBefore);
        }
        if (!crossBorder(x, y - 1) && tiles[x][y - 1] == Tileset.FLOOR && !whetherWalkBefore[x][y - 1]) { // go down
            findPathFromRoomCornerToRoom(room1, x, y - 1, room2, diffNumsRoomGraph, whetherWalkBefore);
        }
    }
}

