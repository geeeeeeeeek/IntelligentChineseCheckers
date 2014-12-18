package intelligence;


/**
 * Created by Tong on 1/14/14.
 */
public class MyAiPlayer039 implements AiPlayerInterface {
    private Robot robot;
    private int currentPlayer;

    @Override
    public int[] jump(boolean[][] bound, String[][] map, int step) {
        robot = new Robot(currentPlayer);

        robot.getMessagesFromServer(bound, map, step);
        robot.initController();
        System.out.println(step);
        return robot.searchForPossibleSolutions();
    }

    @Override
    public void setWho(int who) {
        this.currentPlayer=who;
    }
}
