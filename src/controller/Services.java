package controller;

import javax.swing.*;

/**
 * Class Services offers several overloading methods to quickly input and output.
 *
 * @author Tong
 * @version 1.30 2013.11.22
 */

public class Services {

    public static void msg(String content) {
        JOptionPane.showMessageDialog(null, content, "Chinese Checkers",
                JOptionPane.INFORMATION_MESSAGE);
    }

    public static String in(String content) {
        return JOptionPane.showInputDialog(null, content, "Chinese Checkers",
                JOptionPane.INFORMATION_MESSAGE);
    }


    public static int[][] concatTwoArrays(int[][] targetArray, int[][] temporaryArray) {
        /** combine two 2D arrays into one. */
        int pointer = 0;// point to the operating index of a1
        while (targetArray[pointer][0] != 0) {
            pointer++;
        }
        for (int j = 0; ; j++) {
            boolean isRepeated = false;
            if (temporaryArray[j][0] == 0)
                break;
            for (int i = 0; i < pointer; i++)
                if (temporaryArray[j][0] == targetArray[i][0] && temporaryArray[j][1] == targetArray[i][1]) {
                    isRepeated = true;
                    break;
                }
            if (!isRepeated)
                targetArray[pointer++] = temporaryArray[j];
        }
        return targetArray;
    }
}