package core.entity;

import tileengine.TETile;
import tileengine.Tileset;

import java.awt.*;
import java.util.List;
import java.util.*;

public class ShortpathFinder {

    public List<Point> findShortestPath(TETile[][] world, Point start, Point end) {
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}}; // 上下左右四个方向

        Queue<Point> queue = new LinkedList<>();
        Map<Point, Point> parentMap = new HashMap<>();

        queue.add(start);
        parentMap.put(start, null);

        while (!queue.isEmpty()) {
            Point current = queue.poll();

            if (current.x == end.x && current.y == end.y) {
                // 构建路径
                return buildPath(parentMap, start, end);
            }

            for (int[] dir : directions) {
                int newX = current.x + dir[0];
                int newY = current.y + dir[1];

                Point neighbor = new Point(newX, newY);

                if (isValidMove(world, newX, newY) && !parentMap.containsKey(neighbor)) {
                    queue.add(neighbor);
                    parentMap.put(neighbor, current);
                }
            }
        }

        // 如果无法到达终点，返回空列表表示没有路径
        return Collections.emptyList();
    }

    private static boolean isValidMove(TETile[][] world, int x, int y) {
        // 检查是否越界，是否是地板
        return x >= 0 && x < world.length && y >= 0 && y < world[0].length
                && world[x][y] == Tileset.FLOOR;
    }

    private static List<Point> buildPath(Map<Point, Point> parentMap, Point start, Point end) {
        List<Point> path = new ArrayList<>();
        Point current = end;

        while (current != null) {
            path.add(current);
            current = parentMap.get(current);
        }

        Collections.reverse(path);
        return path;
    }

    // 示例用法
    public static void main(String[] args) {
        // 创建表示游戏世界的二维数组
        TETile[][] world = new TETile[5][5];
    }
    
}
