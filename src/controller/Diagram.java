package controller;

/**
 * Class diagram. Model.
 *
 * @author Tong
 * @version 3.10 2014.1.10
 */

public class Diagram {

    public final static String EMPTY_MARK = "**  ";
    public final static String OUTSIDE_MARK = "    ";
    public final static String SPACE_MARK = "  ";
    /* Construct a singleton for Diagram.*/
    private static Diagram diagram = new Diagram();
    private Cell[][] cell = new Cell[17 + 1][17 + 1];

    private Diagram() {
        // Instanlize every element in Cell.
        for (int i = 1; i <= 17; i++) {
            for (int j = 1; j <= 17; j++)
                cell[i][j] = new Cell();
        }
    }

    public static Diagram getDiagram() {
        return diagram;
    }

    public static boolean isPosInsideDiagram(String position) {
        return !(position == OUTSIDE_MARK || position == null);
    }

    public boolean isPosInsideDiagram(int[] position) {
        String positionString=getDiagramAt(position[0],position[1]);
        return isPosInsideDiagram(positionString);
    }
    public static boolean isPosAvailable(String pos) {
        return pos == EMPTY_MARK;
    }

    public void setDiagramAt(int i, int j, String piece) {
        try {
            cell[i][j].setPiece(piece);
        } catch (Exception e) {
        }
    }

    public void setDiagramAt(int[] pos, String piece) {
        try {
            cell[pos[0]][pos[1]].setPiece(piece);
        } catch (Exception e) {
        }
    }

    public String getDiagramAt(int i, int j) {
        try {
            return cell[i][j].getPiece();
        } catch (Exception npe) {
            return null;
        }
    }

    public void init() {
        // Mark positions out of the diagram
        for (int i = 1; i <= 17; i++) {
            for (int j = 1; j <= 17; j++) {
                setDiagramAt(i, j, OUTSIDE_MARK);
            }
        }
        // Mark positions inside the diagram
        for (int j = 1; j <= 4; j++) {
            for (int i = 5; i <= j + 4; i++)
                setDiagramAt(i, j, EMPTY_MARK);
        }
        for (int j = 5; j <= 8; j++) {
            for (int i = j - 4; i <= 13; i++)
                setDiagramAt(i, j, EMPTY_MARK);
        }
        for (int j = 9; j <= 13; j++) {
            for (int i = 5; i <= j + 4; i++)
                setDiagramAt(i, j, EMPTY_MARK);
        }
        for (int j = 14; j <= 17; j++) {
            for (int i = j - 4; i <= 13; i++)
                setDiagramAt(i, j, EMPTY_MARK);
        }
        // got an entirely empty diagram
    }

    // Inner class Cell.
    class Cell {
        private String piece = "";

        String getPiece() {
            return this.piece;
        }

        void setPiece(String piece) {
            this.piece = piece;
        }
    }
}
