package graphics.listeners;

import controller.ChineseCheckers;
import controller.Pieces;
import controller.Players;
import graphics.CoordinateSystem_2D;
import graphics.View_2D;

import javax.swing.*;
import java.awt.event.MouseEvent;

/**
 * Class pieceActionListener.
 *
 * @author Tong
 * @version 3.10 2014.1.10
 */
class pieceActionListener extends ListenerFactory {
    private final static ImageIcon imgPossiblePosition = new ImageIcon(".\\res\\Piece_Transparant.PNG");
    private static boolean isPieceSelected;
    private static JLabel[] lblPossible = new JLabel[100];
    private int[][] possiblePos = new int[100][2];
    private String piece;
    private View_2D view = ChineseCheckers.view;

    public pieceActionListener(String piece) {
        this.piece = piece + "  ";
    }

    public static void clearPossiblePos() {
        View_2D view = ChineseCheckers.view;
        for (int index = 0; lblPossible[index] != null; index++) {
            view.panel.remove(lblPossible[index]);
        }
        view.frame.repaint();
        isPieceSelected = false;
    }

    @Override
    public void mouseClicked(MouseEvent arg0) {
        Players players = Players.getPlayers();
        if (players.getCurrentPlayer() != piece.charAt(0))
            return;
        if (ChineseCheckers.isGamePaused())
            return;

        clearPossiblePos();
        showPossiblePos();
        isPieceSelected = true;
        ChineseCheckers.setPieceToMove(piece.substring(0, 2));
        ChineseCheckers.setPiecePos(Pieces.getPieces().getPiecePos(piece));
        ChineseCheckers.setPieceToMoveAcquired(true);
    }

    @Override
    public void mouseEntered(MouseEvent arg0) {
        if (ChineseCheckers.isGamePaused())
            return;
        if (Players.getPlayers().getCurrentPlayer() != piece.charAt(0))
            return;
        if (isPieceSelected)
            return;
        showPossiblePos();
    }

    @Override
    public void mouseExited(MouseEvent arg0) {
        if (isPieceSelected)
            return;
        for (int k = 0; lblPossible[k] != null; k++) {
            view.panel.remove(lblPossible[k]);
            view.frame.repaint();
        }
    }

    public void showPossiblePos() {
        CoordinateSystem_2D cs = new CoordinateSystem_2D();
        possiblePos = Pieces.Next.getPossiblePosition(piece);
        for (int k = 0; k < possiblePos.length && possiblePos[k][0] != 0; k++) {
            lblPossible[k] = new JLabel(imgPossiblePosition);
            lblPossible[k].addMouseListener(ListenerFactory.createPossiblePositionActionListener(piece,
                    possiblePos[k]));
            view.panel.add(lblPossible[k], JLayeredPane.MODAL_LAYER);
            int[] positionInCanvas = cs.transformToCanvasPosition(possiblePos[k]);
            lblPossible[k].setBounds(positionInCanvas[0], positionInCanvas[1], 39, 39);
        }
    }
}