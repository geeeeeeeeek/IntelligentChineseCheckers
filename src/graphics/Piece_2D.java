package graphics;
/**
 * Class Piece_2D.
 *
 * @author Tong
 * @version 3.10 2014.1.10
 */

import controller.ChineseCheckers;
import controller.Players;
import graphics.listeners.ListenerFactory;

import javax.swing.*;

public class Piece_2D {

    private JLabel[][] lblPiece = new JLabel[6][10];

    public void move(String pieceToMove, int[] targetPosition) {
        char playerOnTheMove = pieceToMove.charAt(0);
        int index = pieceToMove.charAt(1) - '0';
        int x = new CoordinateSystem_2D().transformToCanvasPosition(targetPosition)[0];
        int y = new CoordinateSystem_2D().transformToCanvasPosition(targetPosition)[1];

        lblPiece[playerOnTheMove - 'A'][index].setBounds(x, y, 39, 39);
    }

    public void init(char[] playerSeries) {
        for (char player : playerSeries) {
            if (player == 0)
                return;
            initPlayer(player);
        }
    }

    private void initPlayer(char player) {
        for (int pieceIndex = 0; pieceIndex < 10; pieceIndex++) {
            initPiece(player, pieceIndex);
        }
    }

    private void initPiece(char player, int pieceIndex) {
        CoordinateSystem_2D cs = new CoordinateSystem_2D();
        int playerIndex = player - 'A';
        int[] pieceSeries = Players.getPlayers().getZoneOfEachPlayer(player)[pieceIndex];
        int x = cs.transformToCanvasPosition(pieceSeries[0], pieceSeries[1])[0];
        int y = cs.transformToCanvasPosition(pieceSeries[0], pieceSeries[1])[1];

        lblPiece[playerIndex][pieceIndex] = new JLabel(new ImageIcon(".\\res\\Piece_" + player + ".PNG"));
        lblPiece[playerIndex][pieceIndex]
                .addMouseListener(ListenerFactory.createPieceActionListener("" + player + pieceIndex));

        lblPiece[playerIndex][pieceIndex].setBounds(x, y, 39, 39);
        ChineseCheckers.view.panel.add(lblPiece[playerIndex][pieceIndex], JLayeredPane.MODAL_LAYER);
    }
}