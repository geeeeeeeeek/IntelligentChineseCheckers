package graphics.listeners;

import controller.ChineseCheckers;
import controller.Pieces;

import java.awt.event.MouseEvent;

/**
 * Class possiblePositionActionListener.
 *
 * @author Tong
 * @version 3.10 2014.1.10
 */
class possiblePositionActionListener extends ListenerFactory {

    private String piece;
    private int[] position = new int[2];

    possiblePositionActionListener(String piece, int[] position) {
        this.piece = piece;
        this.position = position;
    }

    @Override
    public void mouseClicked(MouseEvent arg0) {
        if (ChineseCheckers.isGamePaused())
            return;

        Pieces pieces = Pieces.getPieces();
        pieces.moveAward(piece, pieces.getPiecePos(piece), position);
        ChineseCheckers.view.pieces.move(piece, position);
        pieceActionListener.clearPossiblePos();
        ChineseCheckers.setPosToGoTo(position);
        ChineseCheckers.setIsPositionToGoToAcquired(true);
    }

}
