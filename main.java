package superTicTacToe;

public class main {
    public static void main(String[] args) {
        game g = new game(4,8);
//        board board = new board(3);
//        player p1 = new player('X', 1);
//        player p2 = new player('O', 2);
////        String f = g.gameTree(board, p1, p2);
        g.play();
//        System.out.println("\n\n\n\nGame report:\n" + f);
    }// https://github.com/Eilonlif/TicTacToeSizes
}
