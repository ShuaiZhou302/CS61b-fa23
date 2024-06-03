package game2048;

import java.lang.reflect.Array;
import java.util.Formatter;


/** The state of a game of 2048.
 *  @author P. N. Hilfinger + Josh Hug
 */
public class Model {
    /** Current contents of the board. */
    private final Board board;
    /** Current score. */
    private int score;
    /** Maximum score so far.  Updated when game ends. */
    private int maxScore;

    /* Coordinate System: column C, row R of the board (where row 0,
     * column 0 is the lower-left corner of the board) will correspond
     * to board.tile(c, r).  Be careful! It works like (x, y) coordinates.
     */

    /** Largest piece value. */
    public static final int MAX_PIECE = 2048;

    /** A new 2048 game on a board of size SIZE with no pieces
     *  and score 0. */
    public Model(int size) {
        board = new Board(size);
        score = maxScore = 0;
    }

    /** A new 2048 game where RAWVALUES contain the values of the tiles
     * (0 if null). VALUES is indexed by (row, col) with (0, 0) corresponding
     * to the bottom-left corner. Used for testing purposes. */
    public Model(int[][] rawValues, int score, int maxScore) {
        board = new Board(rawValues);
        this.score = score;
        this.maxScore = maxScore;
    }

    /** Return the current Tile at (COL, ROW), where 0 <= ROW < size(),
     *  0 <= COL < size(). Returns null if there is no tile there.
     *  Used for testing. */
    public Tile tile(int col, int row) {
        return board.tile(col, row);
    }

    /** Return the number of squares on one side of the board. */
    public int size() {
        return board.size();
    }

    /** Return the current score. */
    public int score() {
        return score;
    }

    /** Return the current maximum game score (updated at end of game). */
    public int maxScore() {
        return maxScore;
    }

    /** Clear the board to empty and reset the score. */
    public void clear() {
        score = 0;
        board.clear();
    }

    /** Add TILE to the board. There must be no Tile currently at the
     *  same position. */
    public void addTile(Tile tile) {
        board.addTile(tile);
        checkGameOver();
    }

    /** Return true iff the game is over (there are no moves, or
     *  there is a tile with value 2048 on the board). */
    public boolean gameOver() {
        return maxTileExists(board) || !atLeastOneMoveExists(board);
    }

    /** Checks if the game is over and sets the maxScore variable
     *  appropriately.
     */
    private void checkGameOver() {
        if (gameOver()) {
            maxScore = Math.max(score, maxScore);
        }
    }
    
    /** Returns true if at least one space on the Board is empty.
     *  Empty spaces are stored as null.
     * */
    public static boolean emptySpaceExists(Board b) {
        // TODO: Fill in this function.
        boolean flag = false;
        for (int r = 0; r < b.size(); r++ ){
            for(int c=0;c<b.size();c++){
                if (b.tile(r,c)== null){
                    flag = true;
                    break;
                }
            }
        }

        return flag;
    }

    /**
     * Returns true if any tile is equal to the maximum valid value.
     * Maximum valid value is given by this.MAX_PIECE. Note that
     * given a Tile object t, we get its value with t.value().
     */
    public static boolean maxTileExists(Board b) {
        // TODO: Fill in this function.
        boolean flag = false;
        for(int r =0;r<b.size();r++){
            for(int c=0;c<b.size();c++){
                if ((b.tile(r,c)!= null)&&(b.tile(r,c).value() == MAX_PIECE)){
                    flag = true;
                    break;
                }
            }
        }

        return flag;
    }

    /**
     * Returns true if there are any valid moves on the board.
     * There are two ways that there can be valid moves:
     * 1. There is at least one empty space on the board.
     * 2. There are two adjacent tiles with the same value.
     */
    public static boolean atLeastOneMoveExists(Board b) {
        // TODO: Fill in this function.
       boolean flag = false;
       if(Model.emptySpaceExists(b)){
           flag = true;
       } else if (Model.adjacentSamevalue(b)) {
           flag = true;
       }
        return flag;
    }

    /**
     * Help method for atLeastOneMoveExists(Board b)
     **/
    public static boolean adjacentSamevalue(Board b) {
        boolean flag = false;
        int size = b.size();
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                // go over whole list
                if (b.tile(row, col) == null) {
                    continue;
                }
                // check up element
                if (row > 0) {
                    Tile aboveTile = b.tile(row - 1, col);
                    if (aboveTile != null && b.tile(row, col).value() == aboveTile.value()) {
                        flag = true;
                        break;
                    }
                }
                // check down element
                if (row < size - 1) {
                    Tile belowTile = b.tile(row + 1, col);
                    if (belowTile != null && b.tile(row, col).value() == belowTile.value()) {
                        flag = true;
                        break;
                    }
                }
                // check left element
                if (col > 0) {
                    Tile leftTile = b.tile(row, col - 1);
                    if (leftTile != null && b.tile(row, col).value() == leftTile.value()) {
                        flag = true;
                        break;
                    }
                }
                // check right element
                if (col < size - 1) {
                    Tile rightTile = b.tile(row, col +1);
                    if (rightTile != null && b.tile(row, col).value() == rightTile.value()) {
                        flag = true;
                        break;
                    }
                }
            }
            if(flag){
                break;
            }
        }
        return flag;
    }


    /** Tilt the board toward SIDE.
     *
     * 1. If two Tile objects are adjacent in the direction of motion and have
     *    the same value, they are merged into one Tile of twice the original
     *    value and that new value is added to the score instance variable
     * 2. A tile that is the result of a merge will not merge again on that
     *    tilt. So each move, every tile will only ever be part of at most one
     *    merge (perhaps zero).
     * 3. When three adjacent tiles in the direction of motion have the same
     *    value, then the leading two tiles in the direction of motion merge,
     *    and the trailing tile does not.
     * */
    public void tilt(Side side) {
        // TODO: Modify this.board (and if applicable, this.score) to account
        // for the tilt to the Side SIDE.

        boolean[][] bod = new boolean[board.size()][board.size()];
        for (int col = 0; col < board.size(); col++) {
            for (int row = 0; row < board.size(); row++)
                bod[col][row] = true;
        } // tag if the tile double merge

        if (side == Side.NORTH) {
            for (int col = 0; col < board.size(); col++) {
                for (int row = board.size() - 2; row > -1; row--) {  // handle form the second roll
                    if (board.tile(col, row) != null) {
                        Up(col, row, bod);
                    }
                }
            }
        } else if (side == Side.SOUTH) {
            for (int col = 0; col < board.size(); col++) {
                for (int row = 1; row < board.size(); row++) {  // handle form the second roll
                    if (board.tile(col, row) != null) {
                        Down(col, row, bod);
                    }
                }
            }
        } else if (side == Side.WEST) {
            for (int row = 0; row < board.size(); row++) {
                for (int col = 1; col < board.size(); col++) {  // handle form the second roll
                    if (board.tile(col, row) != null) {
                        Left(col, row, bod);
                    }
                }
            }
        } else if (side == Side.EAST) {
            for (int row = 0; row < board.size(); row++) {
                for (int col = board.size() - 2; col > -1; col--) {  // handle form the second roll
                    if (board.tile(col, row) != null) {
                        Right(col, row, bod);
                    }
                }
            }
        }
        checkGameOver();
        }

    private void Right(int col, int row, boolean[][] bod) {
        int currentValue = board.tile(col, row).value();
        Tile t = board.tile(col, row);
        if(board.tile(col+1,row)==null&&(col+1==board.size()-1)){
            board.move(col+1, row , t);
        } else if (board.tile(col+1, row ) == null) {
            board.move(col+1, row , t);
            Right(col+1,row,bod);
        } else if (board.tile(col+1, row ).value() == currentValue && bod[col+1][row]) {
            score += 2*currentValue;
            board.move(col+1, row , t);
            bod[col+1][row ] = false;
        }

    }

    private void Left(int col, int row, boolean[][] bod) {

        int currentValue = board.tile(col, row).value();
        Tile t = board.tile(col, row);
        if(board.tile(col-1,row)==null&&(col-1==0)){
            board.move(col-1, row, t);
        } else if (board.tile(col-1, row) == null) {
            board.move(col-1, row, t);
            Left(col-1,row,bod);
        } else if (board.tile(col-1, row ).value() == currentValue && bod[col-1][row]) {
            score += 2*currentValue;
            board.move(col-1, row , t);
            bod[col-1][row ] = false;
        }


    }

    private void Down(int col, int row, boolean[][] bod) {
        int currentValue = board.tile(col, row).value();
        Tile t = board.tile(col, row);
        if(board.tile(col,row-1)==null&&(row-1==0)){
            board.move(col, row -1, t);
        } else if (board.tile(col, row -1) == null) {
            board.move(col, row -1, t);
            Down(col,row-1,bod);
        } else if (board.tile(col, row -1).value() == currentValue && bod[col][row -1]) {
            score += 2*currentValue;
            board.move(col, row -1, t);
            bod[col][row -1] = false;
        }
    }

    private void Up ( int col, int row, boolean[][] bod){
            int currentValue = board.tile(col, row).value();
            Tile t = board.tile(col, row);
            if(board.tile(col,row+1)==null&&(row+1==board.size()-1)){
                board.move(col, row + 1, t);
            } else if (board.tile(col, row + 1) == null) {
                board.move(col, row + 1, t);
                Up(col,row+1,bod);
            } else if (board.tile(col, row + 1).value() == currentValue && bod[col][row + 1]) {
                score += 2*currentValue;
                board.move(col, row + 1, t);
                bod[col][row + 1] = false;
            }
    }



    @Override
    public String toString() {
        Formatter out = new Formatter();
        out.format("%n[%n");
        for (int row = size() - 1; row >= 0; row -= 1) {
            for (int col = 0; col < size(); col += 1) {
                if (tile(col, row) == null) {
                    out.format("|    ");
                } else {
                    out.format("|%4d", tile(col, row).value());
                }
            }
            out.format("|%n");
        }
        String over = gameOver() ? "over" : "not over";
        out.format("] %d (max: %d) (game is %s) %n", score(), maxScore(), over);
        return out.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        } else if (getClass() != o.getClass()) {
            return false;
        } else {
            return toString().equals(o.toString());
        }
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }
}
