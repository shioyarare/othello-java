package jp.harashio;

import java.lang.String;

// 0 ... 未配置
// 1 ... 黒石
// 2 ... 白石
public class Main {
    public static void main(String[] args) {
        // 手番を決定
        boolean is_player_first = MyUtil.r.nextBoolean();
        MyUtil.print_from_system( is_player_first ? "あなたは`先手`です。" : "あなたは`後手`です。");
        MyUtil.system_wait();

        // 初期状態の盤面を作成
        var board = MyUtil.initialize_board();
        PrintBoard.simple(board, true);

        while (true) {
            boolean turn_executed;

            // 各プレイヤの操作を実行
            if (is_player_first) {
                MyUtil.print_from_system("プレイヤーの手番（黒）");
                turn_executed = Strategy.executePlayer(1, board);
                PrintBoard.simple(board, true);

                MyUtil.print_from_system("コンピューターの手番（白）");
                turn_executed |= Strategy.executeComputer(2, board);
                PrintBoard.simple(board, true);
            }
            else {
                MyUtil.print_from_system("コンピューターの手番（黒）");
                turn_executed = Strategy.executeComputer(1, board);
                PrintBoard.simple(board, true);

                MyUtil.print_from_system("プレイヤーの手番（白）");
                turn_executed |= Strategy.executePlayer(2, board);
                PrintBoard.simple(board, true);
            }

            if (!turn_executed) {
                // 両プレイヤーがパスした場合には終了
                MyUtil.print_from_system("ゲーム終了");
                break;
            }
        }
    }
}