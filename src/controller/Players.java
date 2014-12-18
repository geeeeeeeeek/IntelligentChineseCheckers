package controller;

import graphics.Piece_2D;

/**
 * Class Players encapsulates the information and actions of game opponents.
 *
 * @author Tong
 * @version 1.30 2013.11.22
 */
public class Players {

    char[] series = new char[6];
    int numOfPlayers = 2;
    int currentIndex = 0;
    Diagram diagram = Diagram.getDiagram();

    private static int[][][] zoneOfEachPlayer = {
            {{5, 1}, {5, 2}, {6, 2}, {5, 3}, {6, 3}, {7, 3}, {5, 4}, {6, 4}, {7, 4}, {8, 4}},
            {{1, 5}, {2, 6}, {2, 5}, {3, 7}, {3, 6}, {3, 5}, {4, 8}, {4, 7}, {4, 6}, {4, 5}},
            {{5, 13}, {6, 13}, {5, 12}, {7, 13}, {6, 12}, {5, 11}, {8, 13}, {7, 12}, {6, 11}, {5, 10}},
            {{13, 17}, {13, 16}, {12, 16}, {13, 15}, {12, 15}, {11, 15}, {13, 14}, {12, 14}, {11, 14}, {10, 14}},
            {{17, 13}, {16, 12}, {16, 13}, {15, 11}, {15, 12}, {15, 13}, {14, 10}, {14, 11}, {14, 12}, {14, 13}},
            {{13, 5}, {12, 5}, {13, 6}, {11, 5}, {12, 6}, {13, 7}, {10, 5}, {11, 6}, {12, 7}, {13, 8}}};
    /* Construct a singleton for Players. */
    private static final Players players = new Players();

    private Players() {
    }

    public static Players getPlayers() {
        return players;
    }

    public int[][] getZoneOfEachPlayer(char player) {
        return zoneOfEachPlayer[player - 'A'];
    }

    public void setNumOfPlayers(int num) {
        numOfPlayers = num;
    }

    public void setCurrentPlayer(char cur) {
        currentIndex = cur;
    }

    public int getNumOfPlayers() {
        return numOfPlayers;
    }


    private void initPlayers(char[] players) {
        for (char player : players) {
            if (player == 0)
                return;
            int[][] initPositions = getZoneOfEachPlayer(player);
            for (int index = 0; index < 10; index++) {
                diagram.setDiagramAt(initPositions[index], "" + player + index + "  ");
            }
        }
    }

    public void init(int numOfPlayers) {
        Piece_2D piece = ChineseCheckers.view.pieces;
        switch (numOfPlayers) {
            case 2:
                setSeriesAt(0, 'D');
                setSeriesAt(1, 'A');
                break;
            case 3:
                setSeriesAt(0, 'E');
                setSeriesAt(1, 'A');
                setSeriesAt(2, 'C');
                break;
            case 4:
                setSeriesAt(0, 'F');
                setSeriesAt(1, 'B');
                setSeriesAt(2, 'C');
                setSeriesAt(3, 'E');
                break;
            case 6:
                setSeriesAt(0, 'F');
                setSeriesAt(1, 'A');
                setSeriesAt(2, 'B');
                setSeriesAt(3, 'C');
                setSeriesAt(4, 'D');
                setSeriesAt(5, 'E');
                break;
        }
        setNumOfPlayers(numOfPlayers);
        piece.init(series);
        this.initPlayers(series);
    }

    private void setSeriesAt(int index, char player) {
        series[index] = player;
    }

    public char getSeriesAt(int index) {
        return series[index];
    }

    public char getCurrentPlayer() {
        return series[currentIndex];
    }


    public void setCurrentIndex(int curIndex) {
        currentIndex = curIndex;
    }

    public int getCurrentIndex() {
        return currentIndex;
    }

    public static boolean isWinner(char player) {
        Players players = Players.getPlayers();
        char opponentPlayer = player < 'D' ? (char) ((int) player + 3) : (char) ((int) player - 3);

        int[][] positionsBelongedToThePlayer = players.getZoneOfEachPlayer(opponentPlayer);

        for (int index = 0; index < 10; index++) {
            int[] position = positionsBelongedToThePlayer[index];
            if (ChineseCheckers.diagram.getDiagramAt(position[0], position[1]).charAt(0) != player)
                return false;
        }
        return true;
    }

    public static boolean isPassive(char player) {
        Players players = Players.getPlayers();
        int steps = Trace.getTrace().getStep() / players.getNumOfPlayers();
        int count = getRemainingPiecesAtHomeCount(player);
        return ((count > 5 && steps >= 20) || (count > 2 && steps >= 25) || (count > 0 && steps >= 30));
    }

    public static int getRemainingPiecesAtHomeCount(char player){
        int count = 0;
        int[][] positionsBelongedToThePlayer = players.getZoneOfEachPlayer(player);

        for (int index = 0; index < 10; index++) {
            int[] position = positionsBelongedToThePlayer[index];
            if (ChineseCheckers.diagram.getDiagramAt(position[0], position[1]).charAt(0) == player)
                count++;
        }
        return count;
    }

    public static int getRemainingPiecesAtOppoCount(char player){
        int count = 0;
        char oppo=(player=='A')?'D':'A';
        int[][] positionsBelongedToThePlayer = players.getZoneOfEachPlayer(oppo);

        for (int index = 0; index < 10; index++) {
            int[] position = positionsBelongedToThePlayer[index];
            if (ChineseCheckers.diagram.getDiagramAt(position[0], position[1]).charAt(0) == player)
                count++;
        }
        return count;
    }

    public static boolean isPieceAtOppo(String piece){
        char oppo=(piece.charAt(0)=='A')?'D':'A';
        int[][] positionsBelongedToThePlayer = players.getZoneOfEachPlayer(oppo);

        for (int index = 0; index < 10; index++) {
            int[] position = positionsBelongedToThePlayer[index];
            if (ChineseCheckers.diagram.getDiagramAt(position[0], position[1]) == piece)
                return true;
        }
        return false;
    }
}
