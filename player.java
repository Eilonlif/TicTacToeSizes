package superTicTacToe;

public class player {
    private int numOfPieces;
    private char shape;
    private piece[] pieces;
    private int num;

    public player(char shape, int num) {
        this.shape = shape;
        this.num = num;
        this.numOfPieces = 6;
        this.pieces = new piece[this.numOfPieces];
        for (int i = 0; i < this.numOfPieces; i++) {
            this.pieces[i] = new piece(i, shape);
        }
    }

    public player(char shape, int numOfPieces, int num) {
        this.shape = shape;
        this.num = num;
        this.numOfPieces = numOfPieces;
        this.pieces = new piece[numOfPieces];
        for (int i = 0; i < numOfPieces; i++) {
            this.pieces[i] = new piece(i, shape);
        }
    }

    public player(char shape, int numOfPieces, int num, piece[] pieces) {
        this.shape = shape;
        this.num = num;
        this.numOfPieces = numOfPieces;
        this.pieces = pieces;
    }

    public int getNumOfPieces() {
        return this.numOfPieces;
    }

    public char getShape() {
        return this.shape;
    }

    public piece[] getPieces() {
        return this.pieces;
    }

    public piece getPiece(int i) {
        if (i >= this.pieces.length || i < 0) {
            return null;
        }
        return this.pieces[i];
    }

    public String getPiecesString() {
        String ret = "[";
        for (int i = 0; i < this.numOfPieces - 1; i++) {
            ret += this.pieces[i].getSize() + ", ";
        }
        return ret + this.pieces[this.numOfPieces - 1].getSize() + "]";
    }

    public int getNum() {
        return this.num;
    }

    public player clone() { //TODO idk whats wrong
        piece[] t = this.pieces.clone();
//        piece[] tmp = new piece[this.numOfPieces];
//        for (int i = 0; i < this.numOfPieces; i++) {
//            tmp[i] = new piece(this.pieces[i]);
//        }
//        return new player(this.shape, this.numOfPieces, this.num, tmp);
        return new player(this.shape, this.numOfPieces, this.num, t);
    }
}
