package graphics.listeners;

import controller.ChineseCheckers;

import java.awt.event.ActionEvent;

/**
 * Class exitListener.
 *
 * @author Tong
 * @version 3.10 2014.1.10
 */
class exitListener extends ListenerFactory {
    @Override
    public void actionPerformed(ActionEvent arg0) {
        ChineseCheckers.view.audioPlayer.stop();
        System.exit(0);
    }
}
