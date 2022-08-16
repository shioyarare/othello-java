package jp.harashio;

import java.util.Arrays;
import java.util.Random;
import java.lang.String;
import java.util.stream.Collectors;
// 0 ... 未配置
// 1 ... 黒石
// 2 ... 白石
public class Main {
    public static Random r = new Random();
    public static int[][] initialize_board() {
        var board = new int[8][8];
        for (int[] row: board) Arrays.fill(row, 0);
        board[3][3] = board[4][4] = 2; // 白石の配置
        board[4][3] = board[3][4] = 1; // 黒石の配置
        return board;
    }

    // 最も単純なボードを出力する関数
    public static void print_board(int[][] board, boolean isVisibleGuide) {
        int i = 0;
        if (isVisibleGuide) System.out.println("  a b c d e f g h");
        for (int[] row: board) {
            i ++;
            var row_str = Arrays.stream(row)
                    .mapToObj(e -> e == 1 ? "\u001b[30;42;1m●" : e == 2 ? "\u001b[97;42;1m●" : "\u001b[30;42m-")
                    .collect(Collectors.toList());
            if (isVisibleGuide) System.out.print(Integer.toString(i) + " ");
            System.out.println(String.join(" ", row_str) + "\u001b[00m");
            System.out.println("\u001b[00m");
        }
    }

    public static void main(String[] args) {
        // 手番を決定
        boolean is_player_first = Main.r.nextBoolean();

        var board = initialize_board();
        print_board(board, true);

    }
}