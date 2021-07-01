package superTicTacToe;

import java.util.Scanner;

public class game {
    public board board;
    public player p1;
    public player p2;

    private Scanner in = new Scanner(System.in);

    public game(int boardSize, char p1Shape, char p2Shape, int p1piecesSize, int p2piecesSize) {
        this.p1 = new player(p1Shape, p1piecesSize, 1);
        this.p2 = new player(p2Shape, p2piecesSize, 2);
        this.board = new board(boardSize);
    }

    public game() {
        this.p1 = new player('X', 6, 1);
        this.p2 = new player('O', 6, 2);
        this.board = new board(3);
    }

    public game(int boardSize, int piecesSize) {
        this.p1 = new player('X', piecesSize, 1);
        this.p2 = new player('O', piecesSize, 2);
        this.board = new board(boardSize);
    }

    public game(int boardSize) {
        this.p1 = new player('X', 6, 1);
        this.p2 = new player('O', 6, 2);
        this.board = new board(boardSize);
    }

    public char play() {
        for (int turn = 0; !this.board.isEnd(this.p1, this.p2); turn++) {
            System.out.println(this.board.toString());
            char isWin = this.board.checkWin();
            if (isWin != 0) {
                return isWin;
            } else {
                if (turn % 2 == 0) {
                    insert(this.p1);
                } else {
                    insert(this.p2);
                }
            }
        }
        return 0;
    }

    private void insert(player p) {
        int i, j, pieceSizeIndex;
        do {
            System.out.println(p.getPiecesString());
            System.out.println("Enter i index for player " + p.getNum() + ": ");
            i = in.nextInt();
            System.out.println("Enter j index for player " + p.getNum() + ": ");
            j = in.nextInt();
            System.out.println("Enter piece size for player " + p.getNum() + ": ");
            pieceSizeIndex = in.nextInt();
        } while ((checkProblem(this.board, p, i, j, pieceSizeIndex)));
        this.board.insert(p, i, j, pieceSizeIndex);
    }

    /**
     * @param b              - board
     * @param p              - player
     * @param i              - player i placing index
     * @param j              - player j placing index
     * @param pieceSizeIndex - player piece size
     * @return true if there's a problem, otherwise will return false
     */
    public boolean checkProblem(board b, player p, int i, int j, int pieceSizeIndex) {
        int s = b.getBoardSize();
        boolean check1 = p.getPiece(pieceSizeIndex) == null;
        boolean check2 = 0 > i || i >= s || 0 > j || j >= s;
        boolean check3 = b.get(i, j).getShape() == p.getShape();
//        System.out.println("i: " + i + ", j:" + j);
//        System.out.println(p.getShape() + " " + b.get(i, j).getSize() + ", " + p.getPiecesString() + "," + pieceSizeIndex);
        boolean check4 = p.getPiece(pieceSizeIndex) == null || b.get(i, j).getSize() >= p.getPiece(pieceSizeIndex).getSize();
//        System.out.println("c1: " + check1 + ", c2: " + check2 + ", c3: " + check3 + ", c4: " + check4);
        return check1 || check2 || check3 || check4;
    }



    public String gameTree(board b, player p1, player p2) {
        return gameTree(b, 0, p1, p2, 0, 0, 0, 0, 0, 0);
    }

    /**
     * @param b                - board
     * @param turn             - current turn
     * @param p1               - player 1
     * @param p2               - player 2
     * @param p1i              - player 1 placing i index
     * @param p1j              - player 1 placing j index
     * @param p2i              - player 2 placing i index
     * @param p2j              - player 2 placing j index
     * @param p1pieceSizeIndex - player 1 piece size
     * @param p2pieceSizeIndex - player 2 piece size
     * @return Game tree
     */
    public String gameTree(board b, int turn, player p1, player p2, int p1i, int p1j, int p2i, int p2j, int p1pieceSizeIndex, int p2pieceSizeIndex) {
        String data = "p1: \ti: " + p1i + ", j: " + p1j + ", p1k: " + p1pieceSizeIndex + "\np2: \ti: " + p2i + ", j: " + p2j + ", p2k: " + p2pieceSizeIndex;
        System.out.println(b.toString());
        String ret = "";
        if (turn % 2 == 0) {
            if (checkProblem(b, p1, p1i, p1j, p1pieceSizeIndex) || b.isEnd(p1, p2)) {
                return "";
            }
            b.insert(p1, p1i, p1j, p1pieceSizeIndex);
//            piece[] copy = p1.getPieces().clone();
            p1.getPieces()[p1pieceSizeIndex] = new piece();
            ret += "inserted piece: " + p1.getShape() + "\ti: " + p1i + ", j:" + p1j + ", size index: " + p1pieceSizeIndex + ", current piece sizes: " + p1.getPiecesString() + ":\n" + b.toString();
        } else {
            if (checkProblem(b, p2, p2i, p2j, p2pieceSizeIndex) || b.isEnd(p1, p2)) {
                return "";
            }
            b.insert(p2, p2i, p2j, p2pieceSizeIndex);
            p2.getPieces()[p2pieceSizeIndex] = new piece();
            ret += "inserted piece: " + p2.getShape() + "\ti: " + p2i + ", j:" + p2j + ", size index: " + p2pieceSizeIndex + ", current piece sizes: " + p2.getPiecesString() + ":\n" + b.toString();
        }
//        System.out.println(data + "\n" + b.toString());
        return ret + "\n" + iteration(b, turn + 1, p1, p2, p1i, p1j, p2i, p2j, p1pieceSizeIndex, p2pieceSizeIndex);
    }

    public String iteration(board b, int turn, player p1, player p2, int p1i, int p1j, int p2i, int p2j, int p1pieceSizeIndex, int p2pieceSizeIndex) {
        String ret = "";
        for (int i = 0; i < b.getBoardSize(); i++) {
            for (int j = 0; j < b.getBoardSize(); j++) {
                for (int k = 0; k < p1.getNumOfPieces(); k++) {
                    if (turn % 2 == 0) {
                        ret += gameTree(b, turn, p1, p2, i, j, p2i, p2j, k, p2pieceSizeIndex);
                    } else {
                        ret += gameTree(b, turn, p1, p2, p1i, p1j, i, j, p1pieceSizeIndex, k);
                    }
                }
            }
        }
        return ret;
    }
}
