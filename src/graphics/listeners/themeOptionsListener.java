package graphics.listeners;

import controller.ChineseCheckers;
import graphics.View_2D;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Class themeOptionsListener.
 *
 * @author Tong
 * @version 3.10 2014.1.10
 */
class themeOptionsListener extends ListenerFactory {
    ImageIcon backgroundImage;

    themeOptionsListener(ImageIcon backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        View_2D view = ChineseCheckers.view;
        view.imgBoardToDisplay = backgroundImage;
        view.setBoardAppearance(backgroundImage);
    }
}
