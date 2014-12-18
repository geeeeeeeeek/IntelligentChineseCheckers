package graphics;
/**
 * Class Frame.
 *
 * @author Tong
 * @version 3.10 2014.1.10
 */

import javax.swing.*;
import java.awt.*;

public class Frame extends JFrame {

    public Frame() {
        setLayout(null);
        setSize(1366, 768);
        setMinimumSize(new Dimension(1366, 768));
        setUndecorated(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Chinese Checkers developed by TongZhongyi, Version3.0");
    }
}