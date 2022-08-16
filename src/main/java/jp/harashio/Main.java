package jp.harashio;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import java.lang.String;
import jp.harashio.MyUtil;
import jp.harashio.PrintBoard;
import java.util.stream.Collectors;
// 0 ... 未配置
// 1 ... 黒石
// 2 ... 白石
public class Main {
    // プレイヤのターン
    public static boolean players_turn(int[][] board) {
        return true;
    }

    // コンピュータのターン
    public static boolean computers_turn(int[][] board) {
       return true;
    }

    public static void main(String[] args) {
        // 手番を決定
        boolean is_player_first = MyUtil.r.nextBoolean();
        MyUtil.print_from_system( is_player_first ? "あなたは`先手`です。" : "あなたは`後手`です。");
        MyUtil.system_wait();

        // 初期状態の盤面を作成
        var board = MyUtil.initialize_board();
        PrintBoard.simple(board, true);

        int turn_count = 0;

        while (true) {
            boolean is_player = is_player_first && turn_count % 2 == 0;
            boolean turn_skipped = true;

            // 各プレイヤの操作を実行
            if (is_player) {
                MyUtil.print_from_system("プレイヤーターン");
                turn_skipped &= players_turn(board);

                MyUtil.print_from_system("コンピュータのターン");
                turn_skipped &= computers_turn(board);
            }
            else {
                MyUtil.print_from_system("コンピュータのターン");
                turn_skipped &= computers_turn(board);

                MyUtil.print_from_system("プレイヤーターン");
                turn_skipped &= players_turn(board);
            }

            if (turn_skipped) {
                // 両プレイヤーがパスした場合には終了
                MyUtil.print_from_system("ゲーム終了");
                break;
            }
        }
    }
}