package controller;

import graphics.Timer;
import graphics.View_2D;
import graphics.listeners.ListenerFactory;

/*
 * @(#)ChineseCheckers.java 1.30 13/11/22
 * 
 * Copyright (c) Tong, Student ID 13302010039.
 * 
 * This is a project for 'Introduction to Java Programming'.
 */

/**
 * Class ChineseCheckers controls the whole game.
 *
 * @author Tong
 * @version 1.30 2013.11.22
 */
public class ChineseCheckers {

    public static View_2D view = new View_2D();
    static Diagram diagram = Diagram.getDiagram();
    static Pieces pieces = Pieces.getPieces();
    static Players players = Players.getPlayers();
    static Trace trace = Trace.getTrace();
    private static int[] piecePos;
    private static String pieceToMove;
    private static int[] posToGoTo;
    private static boolean isNumberOfPlayersAcquired = false;
    private static boolean isPieceToMoveAcquired = false;
    private static boolean isPositionToGoToAcquired = false;
    private static boolean isANewGame = false;
    private static boolean isTheStartingOfTheGame = false;
    private static boolean isGamePaused = false;

    public static void main(String args[]) throws InterruptedException {
        /** Initialization **/
        /* Initialize Diagram */
        diagram.init();
        view.init();

        while (!isNumberOfPlayersAcquired) {
            Thread.sleep(0500);
        }
        setNumberOfPlayersAcquired(false);

        view.display();

        int numOfPlayers = players.getNumOfPlayers();
        players.init(numOfPlayers);

		/* Initialize Pieces */
        pieces.init();

		/* Allocate space for each player */


        /** Start **/
        isTheStartingOfTheGame = true;

        while (true) {
            trace.setSkipped(false);
            /* determine the player to go */
            trace.nextStep();

            view.showCurrentPlayer();
            View_2D.timer = new Timer();

			/* get the position to go */

			/* move */

            while (!isPositionToGoToAcquired) {
                view.updateCounter();
                if (View_2D.timer.listen() <= 0) {
                    if (isPieceToMoveAcquired)
                        ListenerFactory.setPossiblePositionsClear();
                    setPieceToMove(players.getCurrentPlayer() + "0");
                    setPiecePos(pieces.getPiecePos("A0  "));
                    setPosToGoTo(piecePos);
                    Services.msg("Player " + players.getCurrentPlayer() + " time exceeded!");
                    break;
                }
                if (trace.isSkipped())
                    break;
                Thread.sleep(0500);
            }
            if (trace.isSkipped()) continue;
            setIsPositionToGoToAcquired(false);
            if (isANewGame) {
                setANewGame(false);
                view.menu.setRetractOption(false);
                continue;
            }
            /*set trace record */
            pieces.moveAward(pieceToMove, piecePos, posToGoTo);
            trace.recordStep(pieceToMove, piecePos, posToGoTo);

            view.menu.setRetractOption(true);
            /* check if any side wins or loses */
            isGameOver(numOfPlayers);
            trace.increaseStep();
        }

    }

    private static void isGameOver(int numOfPlayers) {
        for (int order = 0; order < numOfPlayers; order++) {
            if (Players.isWinner(players.getSeriesAt(order))) {
                Services.msg("Player " + players.getSeriesAt(order) + " wins!");
                System.exit(0);
            }
            if (Players.isPassive(players.getSeriesAt(order))) {
                Services.msg("Passive Competing! Player " + players.getSeriesAt(order) + " loses!");
                System.exit(0);
            }
        }
    }

    public static void setNumberOfPlayersAcquired(boolean numberOfPlayersAcquired) {
        ChineseCheckers.isNumberOfPlayersAcquired = numberOfPlayersAcquired;
    }

    public static void setPieceToMoveAcquired(boolean pieceToMoveAcquired) {
        ChineseCheckers.isPieceToMoveAcquired = pieceToMoveAcquired;
    }

    public static void setIsPositionToGoToAcquired(boolean positionToGoToAcquired) {
        ChineseCheckers.isPositionToGoToAcquired = positionToGoToAcquired;
    }

    public static void setANewGame(boolean aNewGame) {
        ChineseCheckers.isANewGame = aNewGame;
    }

    public static boolean isStartingOfTheGame() {
        return isTheStartingOfTheGame;
    }

    public static boolean isGamePaused() {
        return isGamePaused;
    }

    public static void setGamePaused(boolean gamePaused) {
        ChineseCheckers.isGamePaused = gamePaused;
    }

    public static void setPiecePos(int[] piecePos) {
        ChineseCheckers.piecePos = piecePos;
    }

    public static void setPieceToMove(String pieceToMove) {
        ChineseCheckers.pieceToMove = pieceToMove;
    }

    public static void setPosToGoTo(int[] posToGoTo) {
        ChineseCheckers.posToGoTo = posToGoTo;
    }
}
