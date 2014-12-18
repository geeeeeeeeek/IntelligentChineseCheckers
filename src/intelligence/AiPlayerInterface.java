package intelligence;

public interface AiPlayerInterface {
    public int[] jump(boolean[][] bound, String[][] map, int step);

    public void setWho(int who);
}
