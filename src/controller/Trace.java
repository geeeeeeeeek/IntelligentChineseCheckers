package controller;

/**
 * Class Trace traces the process of the game, used to record and retract.
 *
 * @author Tong
 * @version 1.30 2013.11.22
 */

public class Trace {

    private static final int MAX_STACK_SIZE = 1000;
    static int step = 1;
    private static Pieces pieces = Pieces.getPieces();
    private static Trace trace = new Trace();
    private boolean isSkipped;
    private String[] pieceStack = new String[MAX_STACK_SIZE];
    private int[][] prevPosStack = new int[MAX_STACK_SIZE][2];
    private int[][] postPosStack = new int[MAX_STACK_SIZE][2];

    private Trace() {
        // no-arg constructor
    }

    public static Trace getTrace() {
        return trace;
    }

    public boolean isSkipped() {
        return isSkipped;
    }

    public void setSkipped(boolean isSkipped) {
        this.isSkipped = isSkipped;
    }

    public void recordStep(String piece, int[] prev, int[] post) {
        int step = getStep();
        pieceStack[step] = piece;
        prevPosStack[step] = prev;
        postPosStack[step] = post;
    }

    public void retract() {
        retract(1);
    }

    public void retract(int steps) {
        int step = getStep();
        isSkipped = true;

        for (int i = step; i > step - steps; i--) {
            pieces.moveBackward(pieceStack[i - 1], postPosStack[i - 1], prevPosStack[i - 1]);
            ChineseCheckers.view.pieces.move(pieceStack[i - 1], prevPosStack[i - 1]);
        }
        backStep(steps);
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public void nextStep() {
        Players.getPlayers().setCurrentIndex(Trace.step % Players.getPlayers().getNumOfPlayers());
    }

    public void increaseStep() {
        ++step;
    }

    public void backStep(int steps) {
        step -= steps;
    }

}
