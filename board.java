package superTicTacToe;

import java.util.*;


public class board {
    private int boardSize;
    private piece[][] board;

    public board() {
        this.boardSize = 3;
        this.board = new piece[this.boardSize][this.boardSize];
        for (int i = 0; i < this.boardSize; i++) {
            for (int j = 0; j < this.boardSize; j++) {
                this.board[i][j] = new piece();
            }
        }
    }

    public board(int boardSize) {
        this.boardSize = boardSize;
        this.board = new piece[this.boardSize][this.boardSize];
        for (int i = 0; i < this.boardSize; i++) {
            for (int j = 0; j < this.boardSize; j++) {
                this.board[i][j] = new piece();
            }
        }
    }

    public board(piece[][] b, int boardSize) {
        this.boardSize = boardSize;
        this.board = b;
    }

    public boolean isGameEnded(player p1, player p2) {
        for (int i = 0; i < p1.getNumOfPieces(); i++) {
            if (p1.getPieces()[i].getSize() != -1 || p2.getPieces()[i].getSize() != -1) {
                return false;
            }
        }
        return true;
    }

    public piece[][] insert(player player, int i, int j, int pieceSizeIndex) {
        if (0 <= i && i < this.boardSize && 0 <= j && j < this.boardSize) {
            this.board[i][j] = new piece(player.getPiece(pieceSizeIndex));
        }
        for (int k = 0; k < this.boardSize; k++) {
            for (int l = 0; l < this.boardSize; l++) {
                this.board[k][l] = new piece(this.board[k][l]);
            }
        }
        return this.board;
    }

    public String toString() {
        StringBuilder b = new StringBuilder();
        for (int i = 0; i < this.boardSize; i++) {
            b.append("  ");
            for (int j = 0; j < this.boardSize; j++) {
                b.append(this.board[i][j].toString());
                if (j != this.boardSize - 1) {
                    b.append(" | ");
                }
            }
            b.append("\n");
            if (i != this.boardSize - 1) {
                for (int j = 0; j < this.boardSize; j++) {
                    b.append("-------");
                }
                b.append("\n");
            }
        }
        return b.toString(); // b is a string builder
    }

    private String[] toShapeArray(piece[] pieces) {
        String[] arr = new String[pieces.length];
        for (int i = 0; i < pieces.length; i++) {
            arr[i] = pieces[i].shapeToString();
        }
        return arr;
    }

    public char horizontal() {
        for (int i = 0; i < this.boardSize; i++) {
            Set<String> hor = new HashSet<>(Arrays.asList(toShapeArray(this.board[i])));
            String[] reSized = hor.toArray(new String[0]);
            if (reSized.length == 1 && !reSized[0].equals("-")) {
                return reSized[0].charAt(0);
            }
        }
        return 0;
    }

    public char vertical() {
        String[] ver = new String[this.boardSize];
        for (int i = 0; i < this.boardSize; i++) {
            for (int j = 0; j < this.boardSize; j++) {
                ver[j] = this.board[j][i].shapeToString();
            }
            Set<String> check = new HashSet<>(Arrays.asList(ver));
            String[] reSized = check.toArray(new String[0]);
            if (reSized.length == 1 && !reSized[0].equals("-")) {
                return reSized[0].charAt(0);
            }
        }
        return 0;
    }

    public char diagonal() {
        String[][] dia = new String[2][this.boardSize];
        for (int i = 0; i < this.boardSize; i++) {
            dia[0][i] = this.board[i][i].shapeToString();
            dia[1][i] = this.board[this.boardSize - i - 1][i].shapeToString();
        }
        for (String[] shape : dia) {
            Set<String> tmp = new HashSet<>(Arrays.asList(shape));
            String[] reSized = tmp.toArray(new String[0]);
            if (reSized.length == 1 && !reSized[0].equals("-")) {
                return reSized[0].charAt(0);
            }
        }
        return 0;
    }

    public char checkWin() {
        char hor = horizontal();
        char ver = vertical();
        char dia = diagonal();
        return (char) (Math.max(dia, Math.max(hor, ver)));
    }

    public piece get(int i, int j) {
        if (0 > i || i >= this.boardSize || 0 > j || j >= this.boardSize) {
            return null;
        }
        return this.board[i][j];
    }

    public int getBoardSize() {
        return this.boardSize;
    }

    public board clone() { //TODO idk whats wrong
        return new board(this.getBoard().clone(), this.boardSize);
    }

    public piece[][] getBoard() {
        return board;
    }
}
