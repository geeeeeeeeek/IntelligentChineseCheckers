package graphics;
/**
 * Class Counter.
 *
 * @author Tong
 * @version 3.10 2014.1.10
 */

import javax.swing.*;

public class Counter extends JLabel {

    public Counter() {
        this.setIcon(new ImageIcon(".\\res\\ProcessTile.png"));
    }

    public void setSizeOnRemainingSeconds(int remainingSeconds) {
        this.setSize(90 * remainingSeconds / 30, 12);
    }
}