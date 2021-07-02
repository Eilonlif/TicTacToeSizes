package superTicTacToe;

public class piece {
    private int size;
    private char shape;

    public piece(int size, char shape) {
        this.size = size;
        this.shape = shape;
    }

    public piece(piece p){
        this.size = p.getSize();
        this.shape = p.getShape();
    }

    public piece() {
        this.size = -1;
        this.shape = '-';
    }

    public boolean compareSizeTo(piece p) {
        return this.size > p.getSize();
    }

    public boolean compareShapeTo(piece p) {
        return this.shape == p.getShape();
    }

    public int getSize() {
        return this.size;
    }

    public char getShape() {
        return this.shape;
    }

    public String toString() {
        return this.shape + "." + this.size;
    }

    public String shapeToString() {
        return "" + this.shape;
    }

    //TODO add clone?
}
