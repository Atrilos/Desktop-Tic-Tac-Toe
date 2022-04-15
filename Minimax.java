package tictactoe;

import java.util.ArrayList;

public class Minimax {

    char[] botBoard;
    char nextTurnSymbol;

    Minimax(char[] board, char nextTurnSymbol) {
        this.botBoard = board.clone();
        this.nextTurnSymbol = nextTurnSymbol;
    }

    private int minimax(char[] board, char nextTurnSymbol, boolean isAI) {
        var availIndexes = emptyIndexes(board);
        if (isAI && isWin(board, nextTurnSymbol)) {
            return 10;
        } else if (!isAI && isWin(board, nextTurnSymbol)) {
            return -10;
        } else if (availIndexes.isEmpty()) {
            return 0;
        }
        int bestScore;
        if (isAI) {
            bestScore = -10000;
            for (Integer availIndex : availIndexes) {
                board[availIndex] = nextTurnSymbol;
                int score = minimax(board.clone(), anotherTurnSymbol(nextTurnSymbol), false);
                board[availIndex] = ' ';
                bestScore = Math.max(score, bestScore);
            }
        } else {
            bestScore = 10000;
            for (Integer availIndex : availIndexes) {
                board[availIndex] = nextTurnSymbol;
                int score = minimax(board.clone(), anotherTurnSymbol(nextTurnSymbol), true);
                board[availIndex] = ' ';
                bestScore = Math.min(score, bestScore);
            }
        }
        return bestScore;
    }

    private ArrayList<Integer> emptyIndexes(char[] board) {
        ArrayList<Integer> result = new ArrayList<>();
        for (int i = 0; i < board.length; i++) {
            if (board[i] == ' ') result.add(i);
        }
        return result;
    }

    private char anotherTurnSymbol(char currentSymbol) {
        if (currentSymbol == 'X') return 'O';
        else return 'X';
    }

    public static int bestMove(char[] board, char nextTurnSymbol, boolean isAI) {
        int bestScore;
        int result = -1;
        Minimax newBoard = new Minimax(board, nextTurnSymbol);
        var availIndexes = newBoard.emptyIndexes(board);
        if (isAI) {
            bestScore = -10000;
            for (Integer availIndex : availIndexes) {
                board[availIndex] = nextTurnSymbol;
                int score = newBoard.minimax(board.clone(), newBoard.anotherTurnSymbol(nextTurnSymbol), false);
                board[availIndex] = ' ';
                if (score > bestScore) {
                    bestScore = score;
                    result = availIndex;
                }
            }
        }
        return result;
    }

    protected static boolean isWin(char[] grid, char nextTurnSymbol) {
        for (int i = 0; i < 3; i++) {
            if (grid[i * 3] == nextTurnSymbol && grid[i * 3 + 1] == nextTurnSymbol
                    && grid[i * 3 + 2] == nextTurnSymbol) return true;
            if (grid[i] == nextTurnSymbol && grid[i + 3] == nextTurnSymbol
                    && grid[i + 6] == nextTurnSymbol) return true;
        }
        if (grid[0] == nextTurnSymbol && grid[4] == nextTurnSymbol
                && grid[8] == nextTurnSymbol) return true;
        return grid[2] == nextTurnSymbol && grid[4] == nextTurnSymbol
                && grid[6] == nextTurnSymbol;
    }

}
