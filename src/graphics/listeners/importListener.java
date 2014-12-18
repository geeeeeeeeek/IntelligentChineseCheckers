package graphics.listeners;

import controller.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Class importListener.
 *
 * @author Tong
 * @version 3.10 2014.1.10
 */
class importListener extends ListenerFactory {

    @Override
    public void actionPerformed(ActionEvent arg0) {
        JFileChooser chooser = new JFileChooser();
        chooser.showOpenDialog(null);

        try {
            File file = chooser.getSelectedFile();
            if (file == null) {
                throw new Exception("FileNotExisted");
            }
            Scanner scanner = new Scanner(file);
            String[] temp = scanner.nextLine().split(",");
            int num = Integer.parseInt(temp[0]);
            char cur = temp[1].charAt(0);
            int curIndex = Integer.parseInt(temp[2]);
            int step = Integer.parseInt(temp[3]);

            Players players = Players.getPlayers();
            ChineseCheckers.setNumberOfPlayersAcquired(true);
            if (!(players.getNumOfPlayers() == num) && !ChineseCheckers.isStartingOfTheGame())
                throw new Exception("PlayersNumberUnmatched");
            players.setNumOfPlayers(num);
            players.setCurrentPlayer(cur);
            players.setCurrentIndex(curIndex);
            Trace.getTrace().setStep(step);
            Diagram.getDiagram().init();

            while (!ChineseCheckers.isStartingOfTheGame()) {
            }
            while (scanner.hasNext()) {
                in(scanner.nextLine());
            }
            ChineseCheckers.view.menu.disablePlayerNumberOptions();
            ChineseCheckers.setANewGame(true);
            ChineseCheckers.setIsPositionToGoToAcquired(true);
        } catch (FileNotFoundException e) {
            // e.printStackTrace();
        } catch (Exception e) {
            Services.msg("Import failed: " + e.getMessage());
        }
    }

    public void in(String line) {
        String[] temp = line.split(",");
        String piece = temp[0] + "  ";
        int i = Integer.parseInt(temp[1]), j = Integer.parseInt(temp[2]);
        Pieces.getPieces().setPiecePos(piece, i, j);
        int[] pos = {i, j};
        Diagram d = Diagram.getDiagram();
        d.setDiagramAt(pos, piece);
        ChineseCheckers.view.pieces.move(piece, pos);
    }
}
