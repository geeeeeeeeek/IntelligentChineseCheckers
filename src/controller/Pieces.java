package controller;

/**
 * Class Pieces encapsulates the information and actions of pieces.
 *
 * @author Tong
 * @version 3.10 2014.1.10
 */

public class Pieces {

    /* Construct a singleton for Diagram.*/
    private static final Pieces piecesInstance = new Pieces();
    private int[][][] pieces = new int[6][10][2];
    // [0 to 5] player A to F.
    // [0 to 9] Pieces 0 to 9.
    // [0 to 1] coordinates(i,j).
    private static Diagram diagram = Diagram.getDiagram();

    private Pieces() {
    }

    public static Pieces getPieces() {
        return piecesInstance;
    }

    public void init() {
        for (int i = 1; i <= 17; i++)
            for (int j = 1; j <= 17; j++) {
                String currentPos = diagram.getDiagramAt(i, j);
                // ?is the first letter is from 'A' to 'F'
                if (isAPiece(currentPos)) {
                    setPiecePos(diagram.getDiagramAt(i, j), i, j);
                }
            }
    }

    public void setPiecePos(String piece, int value_i, int value_j) {
        int player = piece.charAt(0) - 65;
        int index = piece.charAt(1) - 48;
        pieces[player][index][0] = value_i;
        pieces[player][index][1] = value_j;
    }

    public void setPiecePos(String piece, int[] value) {
        int player = piece.charAt(0) - 65;
        int index = piece.charAt(1) - 48;
        pieces[player][index] = value;
    }

    public int[] getPiecePos(int player, int index) {
        return new int[]{pieces[player][index][0], pieces[player][index][1]};
    }

    public int[] getPiecePos(String piece) {
        int player = piece.charAt(0) - 65;
        int index = piece.charAt(1) - 48;
        return new int[]{pieces[player][index][0], pieces[player][index][1]};
    }

    private boolean isAPiece(String currentPosition) {
        return currentPosition.charAt(0) >= 65
                && currentPosition.charAt(0) <= 70;
    }

    public void moveAward(String piece, int[] prevPos, int[] postPos) {
        setPiecePos(piece, postPos);
        diagram.setDiagramAt(prevPos, Diagram.EMPTY_MARK);// clear old position
        diagram.setDiagramAt(postPos, piece + Diagram.SPACE_MARK);// moveAward to new position
    }

    public void moveBackward(String piece, int[] prevPos, int[] postPos) {
        setPiecePos(piece, postPos);
        diagram.setDiagramAt(prevPos, Diagram.EMPTY_MARK);// clear old position
        diagram.setDiagramAt(postPos, piece + Diagram.SPACE_MARK);// moveAward to new position
    }

    /**
     * Class Next is invoked to decide the next step that the piece can possibly
     * moveAward to. Supports adjacent hops, distant hops and consecutive hops.
     *
     * @author Tong
     * @version 1.31 2013.12.25
     */

    public static class Next {

        private static Diagram diagram = Diagram.getDiagram();
        private static Pieces pieces = getPieces();

        public static int[][] getPossiblePosition(String pieceToMove) {
            /**
             * get all possible positions via adjacent hop/distant/consecutive hops.
             */
            int[] piecePosition = pieces.getPiecePos(pieceToMove);
            int[][] possiblePosition = distantHopping(piecePosition);

            diagram.setDiagramAt(piecePosition, "    ");
            // apply Breadth-First-Search to accomplish consecutive hops
            for (int k = 0; possiblePosition[k][0] != 0; k++) {
                int[][] recursion = distantHopping(possiblePosition[k]);
                possiblePosition = Services.concatTwoArrays(possiblePosition, recursion);
            }
            possiblePosition = Services.concatTwoArrays(possiblePosition, adjacentHopping(piecePosition));
            diagram.setDiagramAt(piecePosition, pieceToMove);
            return possiblePosition;
        }

        public static int getPossiblePositionCount(String pieceToMove) {
            int count = 0;
            int[][] possiblePosition = getPossiblePosition(pieceToMove);

            for (int k = 0; possiblePosition[k][0] != 0; k++) {
                count++;
            }
            return count;
        }

        private static int[][] adjacentHopping(int[] piecePosition) {

            int[][] possiblePositions = new int[100][2];
            int pointer = 0;
            int[] displacementAdjacent = {-1, 0, 1};
            // stores possible displacements of coordinates in each direction
            // (-1,-1)(-1,0)(0,-1)(0,1)(1,0)(1,1)

            for (int i : displacementAdjacent) {
                for (int j : displacementAdjacent) {
                    // check whether the adjacent position is empty
                    String current = diagram.getDiagramAt(piecePosition[0] + i,
                            piecePosition[1] + j);
                    if (Diagram.isPosAvailable(current) && i != -j) {
                        possiblePositions[pointer][0] = piecePosition[0] + i;
                        possiblePositions[pointer][1] = piecePosition[1] + j;
                        pointer++;
                    }
                }
            }
            return possiblePositions;
        }

        private static int[][] distantHopping(int[] piecePosition) {
            int[][] possiblePos = new int[100][2];
            int[] displacement = {-1, 0, 1};
            // stores possible direction

            for (int x : displacement) {
                for (int y : displacement) {
                    possiblePos = Services.concatTwoArrays(possiblePos,
                            distantHoppingForOneDirection(x, y, piecePosition, true));
                }
            }

            return possiblePos;
        }

        private static int[][] distantHoppingForOneDirection(int x, int y,
                                                             int[] piecePos, boolean isDistantHoppingDisabled) {
            /*
             * x: indicates up or down moveAward in x direction. y: indicates up or down
             * moveAward in x direction
             */
            int[][] possiblePos = new int[100][2];
            int[] displacement = (isDistantHoppingDisabled)?new int[]{1}:new int[]{1, 2, 3, 4, 5, 6, 7, 8};
            // stores possible displacements of coordinates in each direction
            int pointer = 0;
            boolean isDeadDirection;

            for (int i : displacement) {
                // avoid illegal direction
                if (x * y == -1)
                    continue;
                // avoid array index out of bound
                boolean isiInside = (x == 0) || ((x == -1) ? piecePos[0] > 1 + 1 : piecePos[0] < 17 - 1);
                boolean isjInside = (y == 0) || ((y == -1) ? piecePos[1] > 1 + 1 : piecePos[1] < 17 - 1);
                boolean isInside = isiInside
                        && isjInside
                        && Diagram.isPosInsideDiagram(diagram.getDiagramAt(
                        piecePos[0] + i * 2 * x - 1 * x, piecePos[1] + i
                        * 2 * y - 1 * y))
                        && Diagram.isPosInsideDiagram(diagram.getDiagramAt(
                        piecePos[0] + i * 2 * x, piecePos[1] + i * 2 * y));
                if (!isInside)
                    break;
                boolean isAvailable = (isDeadDirection = !Diagram
                        .isPosAvailable(diagram.getDiagramAt(piecePos[0] + i * x,
                                piecePos[1] + i * y)))
                        && Diagram.isPosAvailable(diagram.getDiagramAt(piecePos[0]
                        + i * 2 * x, piecePos[1] + i * 2 * y));

                label1:
                if (isAvailable) {
                    // position between object position and hopped piece
                    // ought to be empty
                    for (int ii = i + 1; ii < 2 * i; ii++) {
                        if (!Diagram.isPosAvailable(diagram.getDiagramAt(piecePos[0]
                                + ii * x, piecePos[1] + ii * y))) {
                            isDeadDirection = true;
                            break label1;
                        }
                    }
                    possiblePos[pointer][0] = piecePos[0] + i * 2 * x;
                    possiblePos[pointer][1] = piecePos[1] + i * 2 * y;
                    pointer++;
                }

                if (isDeadDirection)
                    break;
            }
            return possiblePos;
        }

    }
}
