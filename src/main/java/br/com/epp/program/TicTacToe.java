package br.com.epp.program;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class TicTacToe {
    private static final int MAXIMUM_AXIS_LENGTH = 3;
    private static final int MINIMUM_AXIS_LENGTH = 1;
    private static final int SIZE = 3;
    private Character lastPlayer = null;

    private Character[][] board = {{null, null, null}, {null, null, null}, {null, null, null}};

    public String play(@NotNull Character player, @NotNull Integer xAxis, @NotNull Integer yAxis) {
        checkAxis(xAxis);
        checkAxis(yAxis);
        checkIfBoxIsFree(xAxis, yAxis);
        checkIfIsPlayerTurn(player);
        place(player, xAxis, yAxis);

        if (isWin())
            return String.format("%s is the winner", lastPlayer);
        else if (isDraw())
            return "The result is draw";
        return "No winner";
    }

    public Character nexPlayer() {
        if (isTheFirstTurn())
            return 'X';
        return lastPlayer.equals('X') ? 'O' : 'X';
    }

    private boolean isWin() {
        Character[] diagonalTopLeft = {board[0][0], board[1][1], board[2][2]};
        Character[] diagonalBottomLeft = {board[2][0], board[1][1], board[0][2]};

        for (int index = 0; index < SIZE; index++) {
            Character[] row = board[index];
            Character[] column = {board[0][index], board[1][index], board[2][index]};
            if (allBoxesBelongToPlayerIn(row) || allBoxesBelongToPlayerIn(column))
                return true;
        }

        return allBoxesBelongToPlayerIn(diagonalTopLeft) || allBoxesBelongToPlayerIn(diagonalBottomLeft);
    }

    private boolean allBoxesBelongToPlayerIn(Character[] row) {
        if (thereAreNoEmptyBoxesIn(row)) {
            if (Arrays.stream(row).allMatch(b -> b.equals(lastPlayer)))
                return true;
        }
        return false;
    }

    private boolean thereAreNoEmptyBoxesIn(Character[] sequence) {
        for (Character aSequence : sequence) {
            if (aSequence == null)
                return false;
        }
        return true;
    }

    private void checkIfIsPlayerTurn(Character player) {
        if (player.equals(lastPlayer))
            throw new RuntimeException(String.format("It is player %s turn", player));
    }

    private void place(Character player, Integer xAxis, Integer yAxis) {
        board[xAxis - 1][yAxis - 1] = player;
        lastPlayer = player;
    }

    private void checkIfBoxIsFree(Integer xAxis, Integer yAxis) {
        if (board[xAxis - 1][yAxis - 1] != null)
            throw new RuntimeException(String.format("Box (%d,%d) is occupied", xAxis, yAxis));
    }

    private void checkAxis(int axis) {
        if (axis < MINIMUM_AXIS_LENGTH || axis > MAXIMUM_AXIS_LENGTH)
            throw new RuntimeException("Is outside the board.");
    }

    private boolean isTheFirstTurn() {
        return lastPlayer == null;
    }


    private boolean isDraw() {
        return thereAreNoEmptyBoxesIn(board[0]) && thereAreNoEmptyBoxesIn(board[1]) && thereAreNoEmptyBoxesIn(board[2]);
    }
}
