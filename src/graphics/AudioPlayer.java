package graphics;
/**
 * Class AudioPlayer.
 *
 * @author Tong
 * @version 3.10 2014.1.10
 */

import controller.ChineseCheckers;
import sun.audio.AudioStream;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class AudioPlayer {
    private AudioStream as;
    private boolean status;

    public AudioPlayer() {
        try {
            as = new AudioStream(new FileInputStream(new File(".\\res\\audio.wav")));
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

    }

    public void start() {
        sun.audio.AudioPlayer.player.start(as);
        this.status = true;
        ChineseCheckers.view.menu.setMusicSwitchStatue("Music off");
    }

    public void stop() {
        sun.audio.AudioPlayer.player.stop(as);
        this.status = false;
        ChineseCheckers.view.menu.setMusicSwitchStatue("Music on");
    }

    public boolean isPlaying() {
        return status;
    }
}
