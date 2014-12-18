package graphics;
/**
 * Class Menu.
 *
 * @author Tong
 * @version 3.10 2014.1.10
 */

import graphics.listeners.ListenerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class Menu extends JMenuBar {
    private final static ImageIcon imgBoard_Wodden = new ImageIcon(".\\res\\Board_Wodden.png"),
            imgBoard_Starry = new ImageIcon(".\\res\\Board_Starry.png"),
            imgBoard_Grass = new ImageIcon(".\\res\\Board_Grass.png"),
            imgBoard_Stars = new ImageIcon(".\\res\\Board_Stars.png");
    private JMenu mFile = new JMenu("File"),
            mNew = new JMenu("New game"),
            mTheme = new JMenu("Theme"),
            mControl = new JMenu("Control");
    private JMenuItem mTwoPlayers = new JMenuItem("Two players"),
            mThreePlayers = new JMenuItem("Three players"),
            mFourPlayers = new JMenuItem("Four players"),
            mSixPlayers = new JMenuItem("Six players"),
            mThemeWodden = new JMenuItem("Wodden Board"),
            mThemeStarryNight = new JMenuItem("Starry Night"),
            mThemeGrass = new JMenuItem("Grass Land"),
            mThemeStars = new JMenuItem("Glorious Stars"),
            mImport = new JMenuItem("Import...", KeyEvent.VK_I),
            mExport = new JMenuItem("Export...", KeyEvent.VK_E),
            mExit = new JMenuItem("Exit", KeyEvent.VK_X),
            mRetract = new JMenuItem("Retract", KeyEvent.VK_R),
            mPauseGame = new JMenuItem("Pause", KeyEvent.VK_P),
            mResumeGame = new JMenuItem("Resume", KeyEvent.VK_S),
            mMusicSwitch = new JMenuItem("Music off", KeyEvent.VK_O);
    private JMenuItem[] playerNumberMenuItemList = {mTwoPlayers, mThreePlayers, mFourPlayers, mSixPlayers},
            themeMenuItemList = {mThemeWodden, mThemeStarryNight, mThemeGrass, mThemeStars};

    public Menu() {
        arrangeMenuItems();
        setMenuGroundColor();
        addActionListeners();
    }

    private void arrangeMenuItems() {
        mFile.setMnemonic(KeyEvent.VK_F);
        mNew.setMnemonic(KeyEvent.VK_N);
        mTheme.setMnemonic(KeyEvent.VK_T);
        mControl.setMnemonic(KeyEvent.VK_C);
        for (JMenuItem playerNumberOptions : playerNumberMenuItemList) {
            mNew.add(playerNumberOptions);
        }
        for (JMenuItem themeOptions : themeMenuItemList) {
            mTheme.add(themeOptions);
        }
        this.add(mFile);
        this.add(mControl);

        mFile.add(mNew);
        mFile.add(mTheme);
        mFile.addSeparator();
        mFile.add(mImport);
        mFile.add(mExport);
        mFile.addSeparator();
        mFile.add(mExit);

        mControl.add(mRetract);
        mControl.addSeparator();
        mControl.add(mPauseGame);
        mControl.add(mResumeGame);
        mControl.add(mMusicSwitch);
    }

    private void setMenuGroundColor() {
        this.setBackground(Color.black);
        mFile.setForeground(Color.white);
        mControl.setForeground(Color.white);
    }

    private void addActionListeners() {
        mTwoPlayers.addActionListener(ListenerFactory.createPlayerNumberOptionsListener(2));
        mThreePlayers.addActionListener(ListenerFactory.createPlayerNumberOptionsListener(3));
        mFourPlayers.addActionListener(ListenerFactory.createPlayerNumberOptionsListener(4));
        mSixPlayers.addActionListener(ListenerFactory.createPlayerNumberOptionsListener(6));
        mThemeWodden.addActionListener(ListenerFactory.createThemeOptionsListener(imgBoard_Wodden));
        mThemeStarryNight.addActionListener(ListenerFactory.createThemeOptionsListener(imgBoard_Starry));
        mThemeGrass.addActionListener(ListenerFactory.createThemeOptionsListener(imgBoard_Grass));
        mThemeStars.addActionListener(ListenerFactory.createThemeOptionsListener(imgBoard_Stars));
        mExit.addActionListener(ListenerFactory.createExitOptionListener());
        mExport.addActionListener(ListenerFactory.createExportListener());
        mImport.addActionListener(ListenerFactory.createImportListener());
        mRetract.addActionListener(ListenerFactory.createRetractListener());
        mPauseGame.addActionListener(ListenerFactory.createPauseListener());
        mResumeGame.addActionListener(ListenerFactory.createResumeListener());
        mMusicSwitch.addActionListener(ListenerFactory.createMusicSwitchListener());
    }

    public void disablePlayerNumberOptions() {
        mNew.setEnabled(false);
    }

    public void setRetractOption(boolean isRetractable) {
        mRetract.setEnabled(isRetractable);
    }

    public void setGameProcessStatue(boolean isGameInProcess) {
        mPauseGame.setEnabled(isGameInProcess);
        mResumeGame.setEnabled(!isGameInProcess);
    }

    public void disableGameProcessStatue() {
        mPauseGame.setEnabled(false);
        mResumeGame.setEnabled(false);
    }

    public void setMusicSwitchStatue(String statueSignature) {
        mMusicSwitch.setText(statueSignature);
    }
}