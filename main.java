package superTicTacToe;

public class main {
    public static void main(String[] args) {
        game g = new game(3);
        board board = new board(3);
        player p1 = new player('X', 6, 1);
        player p2 = new player('O', 6, 2);
        g.gameTree(board, 0, p1, p2, 0, 0, 0, 0, 0, 0);
    }
}
