package intelligence;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.List;


public class AiServer {
    public static void main(String[] args) {
        AiServer as = new AiServer();
        as.competition();
    }

    public void competition() {
        AiPlayerInterface player1 = new MyAiPlayer009();
        player1.setWho(0);
        AiPlayerInterface player2 = new MyAiPlayer039();
        player2.setWho(3);

        int rule[] = {3, -1, -1, 0, -1, -1};
        boolean[][] bound = new boolean[17][17];
        String[][] map = new String[17][17];
        for (int i = 0; i < 17; i++) {
            for (int j = 0; j < 17; j++) {
                map[i][j] = "";
                bound[i][j] = false;
            }
        }
        bound[0][12] = true;
        bound[1][11] = true;
        bound[1][12] = true;
        for (int i = 10; i <= 12; i++) {
            bound[2][i] = true;
        }
        for (int i = 9; i <= 12; i++) {
            bound[3][i] = true;
        }
        for (int i = 4; i <= 16; i++) {
            bound[4][i] = true;
        }
        for (int i = 4; i <= 15; i++) {
            bound[5][i] = true;
        }
        for (int i = 4; i <= 14; i++) {
            bound[6][i] = true;
        }
        for (int i = 4; i <= 13; i++) {
            bound[7][i] = true;
        }
        for (int i = 4; i <= 12; i++) {
            bound[8][i] = true;
        }
        for (int i = 3; i <= 12; i++) {
            bound[9][i] = true;
        }
        for (int i = 2; i <= 12; i++) {
            bound[10][i] = true;
        }
        for (int i = 1; i <= 12; i++) {
            bound[11][i] = true;
        }
        for (int i = 0; i <= 12; i++) {
            bound[12][i] = true;
        }
        for (int i = 4; i <= 7; i++) {
            bound[13][i] = true;
        }
        for (int i = 4; i <= 6; i++) {
            bound[14][i] = true;
        }
        for (int i = 4; i <= 5; i++) {
            bound[15][i] = true;
        }
        for (int i = 4; i <= 4; i++) {
            bound[16][i] = true;
        }
        int[][][] position = new int[6][10][2];
        int positionTemp[] = {0, 0, 0, 0, 0, 0};
        for (int i = 0; i < 17; i++) {
            for (int j = 0; j < 17; j++) {
                if (bound[i][j]) {
                    int temp = -1;
                    if (i < 4) {
                        temp = 2;
                    }
                    if (j < 4) {
                        temp = 0;
                    }
                    if (i > 12) {
                        temp = 5;
                    }
                    if (j > 12) {
                        temp = 3;
                    }
                    if ((i >= 4) & (i <= 7) & (i + j <= 11)) {
                        temp = 1;
                    }
                    if ((i >= 9) & (i <= 12) & (i + j >= 21)) {
                        temp = 4;
                    }
                    if (temp >= 0) {
                        position[temp][positionTemp[temp]][0] = i;
                        position[temp][positionTemp[temp]][1] = j;
                        positionTemp[temp]++;
                    }
                }
            }
        }
        for (int i = 0; i < 6; i++) {
            if (rule[i] < 0) {
                continue;
            }
            for (int j = 0; j < 10; j++) {
                int x = position[i][j][0];
                int y = position[i][j][1];
                map[x][y] = i + "" + j;
            }
        }
        Font font = new Font("宋体", 1, 15);
        UIManager.put("Label.font", font);
        JOptionPane jop = new JOptionPane();
        jop.setFont(font);
        String ss = drawJumpUp(map, bound);
        int who = -1;
        for (int i = 0; i < 6; i++) {
            if (rule[i] >= 0) {
                who = i;
                break;
            }
        }
        jop.showMessageDialog(null, ss + "\n It's turn for " + who + " player\n");
        int step = 0;
        while (true) {
            step++;
            int[] positions = null;
            if (who == 0) {
                positions = player1.jump(bound, map, step);
            } else {
                positions = player2.jump(bound, map, step);
            }
            if ((positions == null) || (positions.length < 4)) {
                System.out.println(who + " has return an invalid answer!");
                System.exit(0);
            }
            for (int i = 0; i < 4; i++) {
                if ((positions[i] < 0) || (positions[i] > 16)) {
                    System.out.println(who + " has return an <0 or >16 answer");
                    System.exit(0);
                }
            }
            if ((!bound[positions[0]][positions[1]]) || (!bound[positions[2]][positions[3]])) {
                System.out.println(who + " has return an unbound answer");
                System.exit(0);
            }
            String temp = map[positions[0]][positions[1]];
            if (temp.equals("")) {
                System.out.println(who + " :no piece there");
                System.exit(0);
            }
            if (Integer.parseInt(temp.charAt(0) + "") != who) {
                System.out.println(who + " :the piece is not yours");
                System.exit(0);
            }
            boolean[][] tempReach = getReach(bound, map, positions[0], positions[1]);
            if (!tempReach[positions[2]][positions[3]]) {
                System.out.println(who + " :can't reach there");
                System.exit(0);
            }
            map[positions[2]][positions[3]] = map[positions[0]][positions[1]];
            map[positions[0]][positions[1]] = "";
            ss = drawJumpUp(map, bound);

            boolean success = true;
            for (int i = 0; i < 10; i++) {
                int tempX = position[rule[who]][i][0];
                int tempY = position[rule[who]][i][1];
                if (map[tempX][tempY].equals("")) {
                    success = false;
                    break;
                } else {
                    if (!(map[tempX][tempY].charAt(0) + "").equals(who + "")) {
                        success = false;
                        break;
                    }
                }
            }
            if (success) {
                System.out.println(who + " has win the game");
                System.exit(0);
            }

            if (step >= 39) {
                int count = 0;
                for (int i = 0; i < 10; i++) {
                    int tempX = position[who][i][0];
                    int tempY = position[who][i][1];
                    if ((!map[tempX][tempY].equals("")) && (Integer.parseInt(map[tempX][tempY].charAt(0) + "") == who)) {
                        count++;
                    }
                }
                int atHome = 6;
                if (step >= 49) {
                    atHome = 3;
                }
                if (step >= 59) {
                    atHome = 1;
                }
                if (count >= atHome) {
                    System.out.println(who + " :step=" + step + " has " + count + " pieces at home");
                    System.exit(0);
                }
            }

            int tempInt = (who + 1) % 6;
            while (true) {
                if (rule[tempInt] >= 0) {
                    who = tempInt;
                    break;
                } else {
                    tempInt = (tempInt + 1) % 6;
                }
            }
            //jop.showMessageDialog(null, ss + "\n It's turn for " + who + " player\n");
        }
    }

    private boolean[][] getReach(boolean[][] bound, String[][] map, int i, int j) {
        boolean[][] tempReach = new boolean[17][17];
        List<Integer> listx = new LinkedList<Integer>();
        List<Integer> listy = new LinkedList<Integer>();
        listx.add(i);
        listy.add(j);
        tempReach[i][j] = true;
        floodFill(bound, map, tempReach, listx, listy);
        int directions1[] = {0, -1, -1, 1, 0, 1};
        int directions2[] = {1, 1, 0, 0, -1, -1};
        for (int k = 0; k < 6; k++) {
            if (illegal(map, bound, i + directions1[k], j + directions2[k])) {
                tempReach[i + directions1[k]][j + directions2[k]] = true;
            }
        }
        tempReach[i][j] = false;
        return tempReach;
    }

    private void floodFill(boolean[][] bound, String[][] map,
                           boolean[][] tempReach, List<Integer> listx, List<Integer> listy) {
        while (listx.size() > 0) {
            int x = listx.remove(0);
            int y = listy.remove(0);
            int directions1[] = {0, 0, -1, 1, -1, 1};
            int directions2[] = {1, -1, 0, 0, 1, -1};
            for (int i = 0; i < 6; i++) {
                if ((illegal(map, bound, x + directions1[i] * 2, y + directions2[i] * 2)) && (!tempReach[x + directions1[i] * 2][y + directions2[i] * 2])) {
                    if (!map[x + directions1[i]][y + directions2[i]].equals("")) {
                        tempReach[x + directions1[i] * 2][y + directions2[i] * 2] = true;
                        listx.add(x + directions1[i] * 2);
                        listy.add(y + directions2[i] * 2);
                    }
                }
            }
        }
    }

    private boolean illegal(String[][] map, boolean[][] bound, int i, int j) {
        if (i < 0 | i > 16 | j < 0 | j > 16) {
            return false;
        }
        if (!bound[i][j]) {
            return false;
        }
        if (!map[i][j].equals("")) {
            return false;
        }
        return true;
    }

    private String drawJumpUp(String[][] map, boolean[][] bound) {
        String tempString = "";
        for (int j = 16; j >= 0; j--) {
            for (int i = 0; i < j; i++) {
                tempString = tempString + "  ";
            }
            for (int i = 0; i < 17; i++) {
                if (bound[i][j]) {
                    if (!map[i][j].equals("")) {
                        tempString += "" + map[i][j].charAt(0) + "" + map[i][j].charAt(1);
                    } else {
                        tempString += "**";
                    }
                } else {
                    tempString += "  ";
                }
                tempString += "  ";
            }
            tempString += "\n\n";
        }
        return tempString;
    }
}
