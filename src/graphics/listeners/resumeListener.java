package graphics.listeners;

import controller.ChineseCheckers;
import graphics.View_2D;

import java.awt.event.ActionEvent;

/**
 * Class resumeListener.
 *
 * @author Tong
 * @version 3.10 2014.1.10
 */
class resumeListener extends ListenerFactory {

    @Override
    public void actionPerformed(ActionEvent arg0) {
        View_2D.timer.resume();

        ChineseCheckers.view.audioPlayer.start();

        ChineseCheckers.view.menu.setGameProcessStatue(true);
        ChineseCheckers.view.menu.setRetractOption(true);
        ChineseCheckers.setGamePaused(false);
    }

}
