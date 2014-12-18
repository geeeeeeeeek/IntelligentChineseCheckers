package graphics;
/**
 * Class View_2D. Presenting elements in 2 D View.
 *
 * @author Tong
 * @version 3.10 2014.1.10
 */

import controller.Players;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class View_2D {

    public static Timer timer;
    public ImageIcon imgBoardToDisplay = new ImageIcon(".\\res\\Board_default.png");
    public JLayeredPane panel = new JLayeredPane();
    public JFrame frame;
    public Menu menu;
    public AudioPlayer audioPlayer;
    public Piece_2D pieces;
    private JLabel lblBoard, lblBar, lblPlayer, lblTime;
    private Counter counter;
    private ImageIcon imgProgressBar = new ImageIcon(".\\res\\ProcessBar.png");

    public void init() {
        //init and add components
        audioPlayer = new AudioPlayer();

        lblBoard = new JLabel(imgBoardToDisplay);
        lblBoard.setBounds(0, 20, 1366, 768);

        lblPlayer = new JLabel(new ImageIcon());
        lblPlayer.setBounds(1000, 350, 90, 90);

        counter = new Counter();
        counter.setBounds(1000, 450, 90, 12);

        lblBar = new JLabel(imgProgressBar);
        lblBar.setBounds(1000, 450, 90, 12);

        lblTime = new JLabel();
        lblTime.setForeground(Color.GRAY);
        lblTime.setFont(new Font("Georgia", Font.BOLD, 20));
        lblTime.setBounds(1120, 730, 300, 30);

        panel.add(lblBoard, JLayeredPane.DEFAULT_LAYER);
        panel.add(lblTime, JLayeredPane.MODAL_LAYER);

        pieces = new Piece_2D();

        menu = new Menu();
        menu.setRetractOption(false);
        menu.disableGameProcessStatue();

        frame = new Frame();
        frame.setLayeredPane(panel);
        frame.setJMenuBar(menu);
        frame.setVisible(true);

        imgBoardToDisplay = new ImageIcon(".\\res\\Board_Starry.png");

        TimerThread timerThread = new TimerThread("Timer");
        timerThread.start();
    }

    public void display() {
        panel.add(counter, JLayeredPane.MODAL_LAYER);
        panel.add(lblBar, JLayeredPane.MODAL_LAYER);
        panel.add(lblPlayer, JLayeredPane.MODAL_LAYER);

        setBoardAppearance(imgBoardToDisplay);

        menu.setGameProcessStatue(true);

        audioPlayer.start();
    }

    public void setBoardAppearance(ImageIcon path) {
        lblBoard.setIcon(path);
        panel.repaint();
    }

    public void updateCounter() {
        counter.setSizeOnRemainingSeconds((int) timer.listen());
        panel.repaint();
    }

    public void showCurrentPlayer() {
        lblPlayer.setIcon(new ImageIcon(".\\res\\Player_" + Players.getPlayers().getCurrentPlayer() + ".PNG"));
        panel.repaint();
    }

    class TimerThread extends Thread {
        TimerThread(String name) {
            super(name);
        }

        @Override
        public void run() {
            while (true) {
                lblTime.setText(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
                frame.repaint();
                try {
                    Thread.sleep(0500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
