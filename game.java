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

    public String play() {
        for (int turn = 0; !this.board.isGameEnded(this.p1, this.p2); turn++) {
            System.out.println(this.board.toString());
            char whoWon = this.board.checkWin();
            if (whoWon != 0) {
                return whoWon == p1.getShape() ? p1.getShape() + " Won!" : p2.getShape() + " Won!";
            } else {
                if (turn % 2 == 0) {
                    insert(this.p1);
                } else {
                    insert(this.p2);
                }
            }
        }
        return "Draw!";
    }

    private void insert(player p) {
        int i, j, pieceSizeIndex;
        do {
            System.out.println(p.getPiecesString());
            System.out.println("Enter i index for player " + p.getNum() + " (" + p.getShape() + "): ");
            i = in.nextInt();
            System.out.println("Enter j index for player " + p.getNum() + " (" + p.getShape() + "): ");
            j = in.nextInt();
            System.out.println("Enter piece size for player " + p.getNum() + " (" + p.getShape() + "): ");
            pieceSizeIndex = in.nextInt();
        } while ((checkProblem(this.board, p, i, j, pieceSizeIndex)));
        this.board.insert(p, i, j, pieceSizeIndex);
        p.getPieces()[pieceSizeIndex] = new piece();
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
        return (p.getPiece(pieceSizeIndex) == null)
                || (0 > i || i >= s || 0 > j || j >= s)
                || (b.get(i, j).getShape() == p.getShape())
                || (p.getPiece(pieceSizeIndex) == null)
                || (b.get(i, j).getSize() >= p.getPiece(pieceSizeIndex).getSize())
                || (p.getPiece(pieceSizeIndex).getSize() < 0);
    }

    public String gameTree() { return gameTree(this.board.clone(), 0, this.p1.clone(), this.p2.clone(), 0, 0, 0, 0, 0, 0); }

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
     * @param p1pieceSizeIndex - player 1 piece size index
     * @param p2pieceSizeIndex - player 2 piece size index
     * @return Game tree
     */
    public String gameTree(board b, int turn, player p1, player p2, int p1i, int p1j, int p2i, int p2j, int p1pieceSizeIndex, int p2pieceSizeIndex) {
        System.out.println(b.toString());
        String ret = "";
        if (b.isGameEnded(p1, p2)) {
            return "";
        }
        if (turn % 2 == 0) {
            if (checkProblem(b, p1, p1i, p1j, p1pieceSizeIndex)) {
                return "";
            }
            b.insert(p1, p1i, p1j, p1pieceSizeIndex); //TODO something is wrong
//            b = new board(b.insert(p1, p1i, p1j, p1pieceSizeIndex), b.getBoardSize()); //TODO idk whats wrong
            p1.getPieces()[p1pieceSizeIndex] = new piece();
            ret += "inserted piece: " + p1.getShape() + "\ti: " + p1i + ", j:" + p1j + ", size index: " + p1pieceSizeIndex + ", current piece sizes: " + p1.getPiecesString() + ":\n" + b.toString();
        } else {
            if (checkProblem(b, p2, p2i, p2j, p2pieceSizeIndex)) {
                return "";
            }
            b.insert(p2, p2i, p2j, p2pieceSizeIndex);
//            b = new board(b.insert(p1, p1i, p1j, p1pieceSizeIndex), b.getBoardSize()); //TODO idk whats wrong
            p2.getPieces()[p2pieceSizeIndex] = new piece();
            ret += "inserted piece: " + p2.getShape() + "\ti: " + p2i + ", j:" + p2j + ", size index: " + p2pieceSizeIndex + ", current piece sizes: " + p2.getPiecesString() + ":\n" + b.toString();
        }
        return ret + "\n" + iteration(b.clone(), turn + 1, p1.clone(), p2.clone(), p1i, p1j, p2i, p2j, p1pieceSizeIndex, p2pieceSizeIndex);
    }

    public String iteration(board b, int turn, player p1, player p2, int p1i, int p1j, int p2i, int p2j, int p1pieceSizeIndex, int p2pieceSizeIndex) {
        String ret = "";
        for (int i = 0; i < b.getBoardSize(); i++) {
            for (int j = 0; j < b.getBoardSize(); j++) {
                for (int k = 0; k < p1.getNumOfPieces(); k++) {
                    if (turn % 2 == 0) {
                        ret += gameTree(b.clone(), turn, p1.clone(), p2.clone(), i, j, p2i, p2j, k, p2pieceSizeIndex);
                    } else {
                        ret += gameTree(b.clone(), turn, p1.clone(), p2.clone(), p1i, p1j, i, j, p1pieceSizeIndex, k);
                    }
                }
            }
        }
        return ret;
    }
}
