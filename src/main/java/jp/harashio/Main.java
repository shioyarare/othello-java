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

    // プレイヤのターン
    public static boolean players_turn() {
        return true;
    }

    // コンピュータのターン
    public static boolean computers_turn() {
       return true;
    }
    public static void main(String[] args) {
        // 手番を決定
        boolean is_player_first = Main.r.nextBoolean();

        // 初期状態の盤面を作成
        var board = initialize_board();
        print_board(board, true);

        int turn_count = 0;

        while (true) {
            boolean is_player = is_player_first && turn_count % 2 == 0;
            boolean turn_skipped = true;

            // 各プレイヤの操作を実行
            if (is_player) {
                System.out.println("system> プレイヤのターン");
                turn_skipped &= players_turn();
                print_board(board, true);

                System.out.println("system> コンピュータのターン");
                turn_skipped &= computers_turn();
                print_board(board, true);
            }
            else {
                System.out.println("system> コンピュータのターン");
                turn_skipped &= computers_turn();
                print_board(board, true);

                System.out.println("system> プレイヤのターン");
                turn_skipped &= players_turn();
                print_board(board, true);
            }

            if (turn_skipped) {
                // 両プレイヤーがパスした場合には終了
                System.out.println("system> ゲーム終了");
                break;
            }
        }

    }
}