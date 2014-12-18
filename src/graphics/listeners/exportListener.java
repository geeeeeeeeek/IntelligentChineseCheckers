package graphics.listeners;

import controller.Pieces;
import controller.Players;
import controller.Services;
import controller.Trace;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 * Class exportListener.
 *
 * @author Tong
 * @version 3.10 2014.1.10
 */
class exportListener extends ListenerFactory {

    @Override
    public void actionPerformed(ActionEvent arg0) {
        JFileChooser chooser = new JFileChooser();
        chooser.showOpenDialog(null);
        File file = chooser.getSelectedFile();

        PrintWriter writer;
        try {
            writer = new PrintWriter(file);
            writer.write(new exportListener.Output().s);
            writer.close();
        } catch (FileNotFoundException e) {
            // e.printStackTrace();
        } catch (Exception e) {
            Services.msg("Export failed!");
        }
    }

    class Output {
        String s = "";

        Output() {
            out();
        }

        void out() {
            Players players = Players.getPlayers();
            Pieces pieces = Pieces.getPieces();
            int num = players.getNumOfPlayers();
            int[] position;
            s += num;
            char cur = players.getCurrentPlayer();
            s += "," + cur;
            int curIndex = players.getCurrentIndex();
            s += "," + curIndex;
            int step = Trace.getTrace().getStep();
            s += "," + step;
            char[][] player = {null, null, {'A', 'D'}, {'A', 'C', 'E'},
                    {'B', 'C', 'E', 'F'}, null,
                    {'A', 'B', 'C', 'D', 'E', 'F'}};
            for (int i = 0; i < 10; i++) {
                for (char j : player[num]) {
                    position = pieces.getPiecePos(j - 65, i);
                    s += "\n" + j + i + "," + position[0] + "," + position[1];
                }
            }
        }
    }
}
