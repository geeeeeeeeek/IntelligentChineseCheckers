package intelligence;

import controller.Diagram;
import controller.Pieces;
import controller.Players;

import static controller.Players.isPieceAtOppo;

/**
 * Created by Tong on 1/14/14.
 */
public class Robot {
    private char offensivePlayer;
    private boolean[][] bound;
    private String[][] map;
    private int initStep;
    private Pieces pieces = Pieces.getPieces();
    private Players players = Players.getPlayers();
    private Diagram diagram = Diagram.getDiagram();

    Robot(int offensivePlayer) {
        this.offensivePlayer = (char) ('A' + offensivePlayer);
    }

    void getMessagesFromServer(boolean[][] severBound, String[][] severMap, int step) {
        boolean[][] localBound = new boolean[17 + 1][17 + 1];
        String[][] localMap = new String[17 + 1][17 + 1];
        for (int i = 0; i < 17; i++) {
            for (int j = 0; j < 17; j++) {
                int[] positionOnSever = {i, j};
                int[] positionOnLocal = transformPositionToLocal(i, j);
                if (!severBound[positionOnSever[0]][positionOnSever[1]])
                    continue;
                localBound[positionOnLocal[0]][positionOnLocal[1]] = severBound[positionOnSever[0]][positionOnSever[1]];
                localMap[positionOnLocal[0]][positionOnLocal[1]] = severMap[positionOnSever[0]][positionOnSever[1]];
            }
        }
        this.bound = localBound.clone();
        this.map = localMap.clone();
        this.initStep = step;
    }

    void initController() {
        for (int i = 1; i <= 17; i++) {
            for (int j = 1; j <= 17; j++) {
                if (!bound[i][j]) {
                    diagram.setDiagramAt(i, j, Diagram.OUTSIDE_MARK);
                    continue;
                }

                if (map[i][j] == "") {
                    diagram.setDiagramAt(i, j, Diagram.EMPTY_MARK);
                    continue;
                }
                String piece = map[i][j] = "" + (char) (map[i][j].charAt(0) - '0' + 'A') + map[i][j].charAt(1) + Diagram.SPACE_MARK;
                pieces.setPiecePos(piece, i, j);
                diagram.setDiagramAt(i, j, piece);
            }
        }
    }

    private int[] transformPositionToLocal(int xOnSever, int yOnSever) {
        int x = xOnSever;
        int y = yOnSever;
        int[] positionOnLocal = {x + y - 7, y + 1};
        return positionOnLocal;
    }

    private int[] transformPositionToSever(int xOnLocal, int yOnLocal) {
        int x = xOnLocal;
        int y = yOnLocal;
        int[] positionOnSever = {x - y + 8, y - 1};
        return positionOnSever;
    }

    int[] returnMessagesToSever(int[] position) {
        position[0] = transformPositionToSever(position[0], position[1])[0];
        position[1] = transformPositionToSever(position[0], position[1])[1];
        position[2] = transformPositionToSever(position[2], position[3])[0];
        position[3] = transformPositionToSever(position[2], position[3])[1];
        return position;
    }

    int[] searchForPossibleSolutions() {
        int depth = 5;
        double maxValue = -1000;
        Record bestSolution = null;
        Record[][][] records = new Record[10][100][depth];

        boolean isPossibleToCasuePassiveCompetition = (initStep > 40) && (Players.getRemainingPiecesAtHomeCount(offensivePlayer) > 0);
        if (isPossibleToCasuePassiveCompetition) {
            bestSolution = avoidPassiveCompetitionSearch();
            if (bestSolution != null)
                return returnMessagesToSever(new int[]{bestSolution.prevPosition[0], bestSolution.prevPosition[1]
                        , bestSolution.postPosition[0], bestSolution.postPosition[1]});
        }

        boolean isPossibleToFailFullOccupation = (initStep > 40) && (Players.getRemainingPiecesAtOppoCount(offensivePlayer) > 5);
        if (isPossibleToFailFullOccupation) {
            bestSolution = occupationPriorSearch();
            if (bestSolution != null)
                return returnMessagesToSever(new int[]{bestSolution.prevPosition[0], bestSolution.prevPosition[1]
                        , bestSolution.postPosition[0], bestSolution.postPosition[1]});
        }

        for (int pieceIndex = 0; pieceIndex < 10; pieceIndex++) {
            String pieceToMove = "" + offensivePlayer + pieceIndex + "  ";
            int[][] allPossibleSolutionsForTheStep
                    = Pieces.Next.getPossiblePosition(pieceToMove);
            int[] prevPosition = pieces.getPiecePos(pieceToMove);


            for (int solutionIndex = 0; allPossibleSolutionsForTheStep[solutionIndex][0] != 0; solutionIndex++) {
                int[] postPosition = allPossibleSolutionsForTheStep[solutionIndex];
                double value = eval(prevPosition, postPosition);
                records[pieceIndex][solutionIndex][0] = new Record(pieceToMove, prevPosition, postPosition, value);
                records[pieceIndex][solutionIndex][0].moveAward();
                char player = offensivePlayer;
                for (int step = 1; step < depth; step++) {
                    player = (player == 'A') ? 'D' : 'A';
                    records[pieceIndex][solutionIndex][step] = distancePriorSearch(player, step);
                    if (records[pieceIndex][solutionIndex][step] == null) {
                        records[pieceIndex][solutionIndex][0] = null;
                        break;
                    }
                    records[pieceIndex][solutionIndex][step].moveAward();
                }
                for (int step = depth - 1; step >= 1; step--) {
                    if (records[pieceIndex][solutionIndex][step] == null) {
                        break;
                    }
                    records[pieceIndex][solutionIndex][step].moveBackward();
                }
                if (records[pieceIndex][solutionIndex][0] != null) records[pieceIndex][solutionIndex][0].moveBackward();
            }
        }

        for (int pieceIndex = 0; pieceIndex < 10; pieceIndex++) {
            for (int solutionIndex = 0; records[pieceIndex][solutionIndex][0] != null; solutionIndex++) {

                double value = records[pieceIndex][solutionIndex][0].value;
                for (int step = 1; step < depth; step++) {
                    value += (step % 2 == 0) ? records[pieceIndex][solutionIndex][step].value : -records[pieceIndex][solutionIndex][step].value;
                }
                if (value > maxValue) {
                    maxValue = value;
                    bestSolution = records[pieceIndex][solutionIndex][0];
                }
            }
        }

        return returnMessagesToSever(new int[]{bestSolution.prevPosition[0], bestSolution.prevPosition[1]
                , bestSolution.postPosition[0], bestSolution.postPosition[1]});
    }

    private Record distancePriorSearch(char player, int step) {
        Record record = null;
        double maxVauleForTheStep = -1000;
        for (int pieceIndex = 0; pieceIndex < 10; pieceIndex++) {
            String pieceToMove = "" + player + pieceIndex + "  ";
            int[][] allPossibleSolutionsForTheStep
                    = Pieces.Next.getPossiblePosition(pieceToMove);
            int[] prevPosition = pieces.getPiecePos(pieceToMove);
            for (int solutionIndex = 0; allPossibleSolutionsForTheStep[solutionIndex][0] != 0; solutionIndex++) {
                int[] postPosition = allPossibleSolutionsForTheStep[solutionIndex];
                double value = eval(prevPosition, postPosition);
                boolean isPossibleToCausePassiveCompetition = (player == offensivePlayer) && (initStep > 58) && (Players.getRemainingPiecesAtHomeCount(offensivePlayer) > 0);
                if (isPossibleToCausePassiveCompetition) continue;
                if (value > maxVauleForTheStep) {
                    maxVauleForTheStep = value;
                    record = new Record(pieceToMove, prevPosition, postPosition, value);
                }
            }
        }
        return record;

    }

    private Record avoidPassiveCompetitionSearch() {
        Record record = null;
        double maxVauleForTheStep = -1000;
        int[][] positionsBelongedToThePlayer = players.getZoneOfEachPlayer(offensivePlayer);
        for (int index = 0; index < 10; index++) {
            int[] position = positionsBelongedToThePlayer[index];
            String pieceToMove = diagram.getDiagramAt(position[0], position[1]);
            if (pieceToMove.charAt(0) == offensivePlayer) {
                int[][] allPossibleSolutionsForTheStep
                        = Pieces.Next.getPossiblePosition(pieceToMove);
                int[] prevPosition = pieces.getPiecePos(pieceToMove);
                for (int solutionIndex = 0; allPossibleSolutionsForTheStep[solutionIndex][0] != 0; solutionIndex++) {
                    int[] postPosition = allPossibleSolutionsForTheStep[solutionIndex];
                    double value = eval(prevPosition, postPosition);
                    if (value > maxVauleForTheStep) {
                        maxVauleForTheStep = value;
                        record = new Record(pieceToMove, prevPosition, postPosition, value);
                    }
                }
                break;
            }
        }
        return record;
    }

    private Record occupationPriorSearch() {
        Record record = null;
        double maxVauleForTheStep = -1000;
        for (int pieceIndex = 0; pieceIndex < 10; pieceIndex++) {
            String pieceToMove = "" + offensivePlayer + pieceIndex + "  ";
            if (isPieceAtOppo(pieceToMove))
                continue;
            int[][] allPossibleSolutionsForTheStep
                    = Pieces.Next.getPossiblePosition(pieceToMove);
            int[] prevPosition = pieces.getPiecePos(pieceToMove);
            for (int solutionIndex = 0; allPossibleSolutionsForTheStep[solutionIndex][0] != 0; solutionIndex++) {
                int[] postPosition = allPossibleSolutionsForTheStep[solutionIndex];
                double value = eval(prevPosition, postPosition);
                boolean isPossibleToCausePassiveCompetition = (initStep > 58) && (Players.getRemainingPiecesAtHomeCount(offensivePlayer) > 0);
                if (isPossibleToCausePassiveCompetition) continue;
                if (value > maxVauleForTheStep) {
                    maxVauleForTheStep = value;
                    record = new Record(pieceToMove, prevPosition, postPosition, value);
                }
            }
        }
        return record;
    }

    private double eval(int[] prevPosition, int[] postPosition) {
        double yDisplacement = postPosition[1] - prevPosition[1];
        double xDisplacement = Math.abs(postPosition[0] - postPosition[1] / 2 - 9 / 2) - Math.abs(prevPosition[0] - prevPosition[1] / 2 - 9 / 2);
        return (offensivePlayer == 'A') ? yDisplacement - xDisplacement / 2
                : -yDisplacement - xDisplacement / 2;
    }

    class Record {
        double value;
        int[] prevPosition, postPosition;
        String pieceToMove;

        Record(String pieceToMove, int[] prevPosition, int[] postPosition, double value) {
            this.value = value;
            this.pieceToMove = pieceToMove;
            this.prevPosition = prevPosition;
            this.postPosition = postPosition;
        }

        void moveAward() {
            Pieces.getPieces().moveAward(pieceToMove, prevPosition, postPosition);
        }

        void moveBackward() {
            Pieces.getPieces().moveAward(pieceToMove, postPosition, prevPosition);
        }
    }
}
