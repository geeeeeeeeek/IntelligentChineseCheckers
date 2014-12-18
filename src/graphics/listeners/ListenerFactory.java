package graphics.listeners;
/**
 * Class ListenerFactory.
 *
 * @author Tong
 * @version 3.10 2014.1.10
 */

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;

public class ListenerFactory extends MouseAdapter implements ActionListener {

    public static playerNumberOptionsListener createPlayerNumberOptionsListener(int playerNumber) {
        return new playerNumberOptionsListener(playerNumber);
    }

    public static exitListener createExitOptionListener() {
        return new exitListener();
    }

    public static themeOptionsListener createThemeOptionsListener(ImageIcon backgroundImage) {
        return new themeOptionsListener(backgroundImage);
    }

    public static pieceActionListener createPieceActionListener(String piece) {
        return new pieceActionListener(piece);
    }

    public static exportListener createExportListener() {
        return new exportListener();
    }

    public static importListener createImportListener() {
        return new importListener();
    }

    public static pauseListener createPauseListener() {
        return new pauseListener();
    }

    public static musicSwitchListener createMusicSwitchListener() {
        return new musicSwitchListener();
    }

    public static resumeListener createResumeListener() {
        return new resumeListener();
    }

    public static retractListener createRetractListener() {
        return new retractListener();
    }

    public static possiblePositionActionListener createPossiblePositionActionListener(String piece, int[] position) {
        return new possiblePositionActionListener(piece, position);
    }

    public static void setPossiblePositionsClear() {
        pieceActionListener.clearPossiblePos();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }
}

