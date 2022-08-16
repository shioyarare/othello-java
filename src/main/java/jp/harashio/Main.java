package jp.harashio;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import java.lang.String;
import jp.harashio.PrintBoard;
import java.util.stream.Collectors;
// 0 ... 未配置
// 1 ... 黒石
// 2 ... 白石
public class Main {

    public static Random r = new Random();

    public static void print_from_system(String message) {
        System.out.println("system> " + message);
    }

    public static List<int[]> search_candidate_pos(int[][] board, int target) {
        var candidate_pos = new ArrayList<int[]>();
        int dx[] = {1, 1, 0, -1, -1, -1, 0, 1}, dy[] = {0, 1, 1, 1, 0, -1, -1, -1};

        for (int y=0; y<8; y++) {
            for (int x=0; x<8; x++) {
                // 空いていなければそのマスには置けない
                if (board[y][x] != 0) continue;

                // 空いているマスが実際に置けるかチェック
                for (int i=0; i<9; i++) {
                    int nx = x + dx[i], ny = y + dy[i];
                    if (nx<0 && nx>8 && ny<0 && ny>8) continue; // 範囲外
                    if (board[ny][nx] == target) continue; // すぐ隣が自身の石だった場合置けない

                    // 隣が敵の石だった場合、その先に自身の石があればおける
                    while (true) {
                        nx += dx[i];
                        ny += dy[i];
                        if (nx<0 && nx>8 && ny<0 && ny>8) break; // 範囲外
                        if (board[ny][nx] == target) {
                            int[] candidate = {x, y};
                            candidate_pos.add(candidate);
                            break;
                        }
                        else if (board[ny][nx] == 0) break;

                    }
                }
            }
        }

        return candidate_pos;
    }

    // ゲームの表示をみやすくするため定時間進行を止める
    public static void system_wait() {
        try {
            Thread.sleep(6000);
        }
        catch (InterruptedException e) {

        }
    }

    public static int[][] initialize_board() {
        var board = new int[8][8];
        for (int[] row: board) Arrays.fill(row, 0);
        board[3][3] = board[4][4] = 2; // 白石の配置
        board[4][3] = board[3][4] = 1; // 黒石の配置
        return board;
    }



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
        boolean is_player_first = Main.r.nextBoolean();
        print_from_system( is_player_first ? "あなたは`先手`です。" : "あなたは`後手`です。");
        system_wait();

        // 初期状態の盤面を作成
        var board = initialize_board();
        PrintBoard.simple(board, true);

        int turn_count = 0;

        while (true) {
            boolean is_player = is_player_first && turn_count % 2 == 0;
            boolean turn_skipped = true;

            // 各プレイヤの操作を実行
            if (is_player) {
                print_from_system("プレイヤーターン");
                turn_skipped &= players_turn(board);

                print_from_system("コンピュータのターン");
                turn_skipped &= computers_turn(board);
            }
            else {
                print_from_system("コンピュータのターン");
                turn_skipped &= computers_turn(board);

                print_from_system("プレイヤーターン");
                turn_skipped &= players_turn(board);
            }

            if (turn_skipped) {
                // 両プレイヤーがパスした場合には終了
                print_from_system("ゲーム終了");
                break;
            }
        }
    }
}