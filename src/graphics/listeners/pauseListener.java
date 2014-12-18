package graphics.listeners;

import controller.ChineseCheckers;
import graphics.View_2D;

import java.awt.event.ActionEvent;

/**
 * Class pauseListener.
 *
 * @author Tong
 * @version 3.10 2014.1.10
 */
class pauseListener extends ListenerFactory {

    @Override
    public void actionPerformed(ActionEvent arg0) {
        View_2D.timer.pause();

        ChineseCheckers.view.audioPlayer.stop();

        ChineseCheckers.view.menu.setGameProcessStatue(false);
        ChineseCheckers.view.menu.setRetractOption(false);
    }

}
