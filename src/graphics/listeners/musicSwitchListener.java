package graphics.listeners;

import controller.ChineseCheckers;
import graphics.AudioPlayer;

import java.awt.event.ActionEvent;

/**
 * Class musicSwitchListener.
 *
 * @author Tong
 * @version 3.10 2014.1.10
 */
class musicSwitchListener extends ListenerFactory {

    @Override
    public void actionPerformed(ActionEvent arg0) {

        AudioPlayer audioPlayer = ChineseCheckers.view.audioPlayer;
        if (audioPlayer.isPlaying()) audioPlayer.stop();
        else audioPlayer.start();

    }

}
