package graphics.listeners;

import controller.ChineseCheckers;
import controller.Players;

import java.awt.event.ActionEvent;

/**
 * Class playerNumberOptionsListener.
 *
 * @author Tong
 * @version 3.10 2014.1.10
 */
class playerNumberOptionsListener extends ListenerFactory {

    int number = 2;

    playerNumberOptionsListener(int number) {
        this.number = number;
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        Players players = Players.getPlayers();

        players.setNumOfPlayers(number);

        ChineseCheckers.setNumberOfPlayersAcquired(true);

        ChineseCheckers.view.menu.disablePlayerNumberOptions();
    }
}
