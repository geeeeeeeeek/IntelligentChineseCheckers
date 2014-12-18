package graphics;

/**
 * Class CoordinateSystem_2D.
 *
 * @author Tong
 * @version 3.10 2014.1.10
 */
public class CoordinateSystem_2D {

    private static int[][] reflection[] = new int[18][18][2];

    public CoordinateSystem_2D() {
        for (int j = 1; j <= 17; j++)
            switch (j) {
                case 1: {
                    for (int i = 5; i <= 5; i++)
                        setSinglePosition(i, j, 378 + 44 * (i - 5), 696 + 0 * (i - 5));
                    break;
                }
                case 2: {
                    for (int i = 5; i <= 6; i++)
                        setSinglePosition(i, j, 378 - 22 + 44 * (i - 5), 696 - 38.5);
                    break;
                }
                case 3: {
                    for (int i = 5; i <= 7; i++)
                        setSinglePosition(i, j, 378 - 22 * 2 + 44 * (i - 5), 696 - 78);
                    break;
                }
                case 4: {
                    for (int i = 5; i <= 8; i++)
                        setSinglePosition(i, j, 378 - 22 * 3 + 44 * (i - 5), 696 - 115);
                    break;
                }
                case 5: {
                    for (int i = 1; i <= 13; i++)
                        setSinglePosition(i, j, 116 + 22 * 0 + 43.5 * (i - 1), 543 - 38.5 * 0);
                    break;
                }
                case 6: {
                    for (int i = 2; i <= 13; i++)
                        setSinglePosition(i, j, 116 + 22 * 1 + 43.5 * (i - 2), 543 - 38.5 * 1);
                    break;
                }
                case 7: {
                    for (int i = 3; i <= 13; i++)
                        setSinglePosition(i, j, 116 + 22 * 2 + 43.5 * (i - 3), 543 - 38.5 * 2);
                    break;
                }
                case 8: {
                    for (int i = 4; i <= 13; i++)
                        setSinglePosition(i, j, 116 + 22 * 3 + 43.5 * (i - 4), 543 - 38.5 * 3);
                    break;
                }
                case 9: {
                    for (int i = 5; i <= 13; i++)
                        setSinglePosition(i, j, 116 + 22 * 4 + 43.5 * (i - 5), 543 - 38.5 * 4);
                    break;
                }
                case 10: {
                    for (int i = 5; i <= 14; i++)
                        setSinglePosition(i, j, 116 + 22 * 3 + 43.5 * (i - 5), 543 - 38.5 * 5);
                    break;
                }
                case 11: {
                    for (int i = 5; i <= 15; i++)
                        setSinglePosition(i, j, 116 + 22 * 2 + 43.5 * (i - 5), 543 - 38.5 * 6);
                    break;
                }
                case 12: {
                    for (int i = 5; i <= 16; i++)
                        setSinglePosition(i, j, 116 + 22 * 1 + 43.5 * (i - 5), 543 - 38.5 * 7);
                    break;
                }
                case 13: {
                    for (int i = 5; i <= 17; i++)
                        setSinglePosition(i, j, 116 + 22 * 0 + 43.5 * (i - 5), 543 - 38.5 * 8);
                    break;
                }
                case 14: {
                    for (int i = 10; i <= 13; i++)
                        setSinglePosition(i, j, 378 - 22 * 3 + 43.5 * (i - 10), 543 - 38.5 * 9);
                    break;
                }
                case 15: {
                    for (int i = 11; i <= 13; i++)
                        setSinglePosition(i, j, 378 - 22 * 2 + 43.5 * (i - 11), 543 - 38.5 * 10);
                    break;
                }
                case 16: {
                    for (int i = 12; i <= 13; i++)
                        setSinglePosition(i, j, 377 - 22 * 1 + 43.5 * (i - 12), 543 - 38.5 * 11);
                    break;
                }
                case 17: {
                    for (int i = 13; i <= 13; i++)
                        setSinglePosition(i, j, 377 - 22 * 0 + 43.5 * (i - 13), 543 - 38.5 * 12);
                    break;
                }
            }
    }

    private void setSinglePosition(int i, int j, double x, double y) {
        reflection[i][j][0] = (int) x;
        reflection[i][j][1] = (int) y;
    }

    public int[] transformToCanvasPosition(int i, int j) {
        return reflection[i][j];
    }

    public int[] transformToCanvasPosition(int[] modelPosition) {
        return reflection[modelPosition[0]][modelPosition[1]];
    }
}
