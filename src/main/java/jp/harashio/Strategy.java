package jp.harashio;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.time.LocalDateTime;
public class Strategy {
    private static int get_enemy (int target) {
        return target == 1 ? 2 : 1;
    }

    private static List<MyUtil.Put> search_candidate_pos(int target, int[][] board) {
        var candidate_pos = new ArrayList<MyUtil.Put>();

        for (int y=0; y<8; y++) {
            for (int x=0; x<8; x++) {
                // 空いていなければそのマスには置けない
                if (board[y][x] != 0) continue;

                // 空いているマスが実際に置けるかチェック
                int rev_num = reverse(board, target, new MyUtil.Put(x, y, 0), false);
                if (rev_num > 0) {
                    // その場所に石を置いたとして石が1個以上返る場合置ける
                    candidate_pos.add(new MyUtil.Put(x, y, rev_num));
                }
            }
        }
        return candidate_pos;
    }

    private static int reverse (int[][] board, int target, MyUtil.Put pos, boolean do_reverse) {
        List<MyUtil.Put> all_reverse = new ArrayList<MyUtil.Put>();
        int dx[] = {1, 1, 0, -1, -1, -1, 0, 1}, dy[] = {0, 1, 1, 1, 0, -1, -1, -1};
        // 返せる候補の探索
        for (int i=0; i<8; i++) {
            List<MyUtil.Put> curr_reverse = new ArrayList<MyUtil.Put>();
            int nx = pos.x, ny = pos.y;

            int k = 0;
            while (true) {
                nx += dx[i];
                ny += dy[i];
                if (nx<0 || nx>7 || ny<0 || ny>7 || board[ny][nx] == 0) break; // 範囲外
                if (k++ == 0 && board[ny][nx] == target ) break; // すぐ隣が自身の石
                if (board[ny][nx] == target) {
                    // ここまで返すことが可能
                    all_reverse.addAll(curr_reverse);
                    break;
                }
                curr_reverse.add(new MyUtil.Put(nx, ny, 0));
            }
        }
        // 実際に返す処理を行う場合
        if (do_reverse) {
            board[pos.y][pos.x] = target;
            for (var rev : all_reverse) {

                board[rev.y][rev.x] = target;
            }
        }
        // 返した際に裏返せる個数を返す
        return all_reverse.size();
    }

    private static int bfs (int target, int[][] board, int depth, int prevcost) {
        int cost = Integer.MAX_VALUE;
        MyUtil.Put sel;
        // 敵の動き候補
        var candidates = search_candidate_pos(get_enemy(target), board);
        if (depth <= 0 || candidates.size() == 0) {
            return candidates.size();
        }
        if (candidates.size() > prevcost) {
            // 前よりも選択肢が多くなってしまったら打ち止め
            return candidates.size();
        }

        for (var candidate: candidates) {
            // 敵の動きをシミュレーション
            var enemy_board= MyUtil.buildBoard(board);
            reverse(enemy_board, target, candidate, true);

            // 敵の動きの結果、自身がとれる行動
            var next_candidates = search_candidate_pos(target, board);

            for (var next_candidate: next_candidates) {
                var next_board = MyUtil.buildBoard(enemy_board);
                reverse(next_board, target, next_candidate, true);
                int next_cost = bfs(target, next_board.clone(), depth-1, cost);

                if (cost > next_cost) {
                    cost = next_cost;
                    sel  = candidate;
                }
            }
        }
        return cost;
    }
    public static boolean executeComputer (int target, int[][] board) {
        // 置ける候補の探索
        List<MyUtil.Put> candidates = new ArrayList<MyUtil.Put>();
        try {
            candidates = search_candidate_pos(target, board);
        }
        catch (Exception e){
            System.out.println(e);
        }

        if (candidates.size() == 0) {
            MyUtil.print_from_system("置ける場所がないためパスが選択されます");
            return false;
        }

        // 幅優先探索で相手が置けるマスが最小になるようにする
        int depth = 7;
        MyUtil.Put sel = new MyUtil.Put(-1, -1, -1);
        int cost = Integer.MAX_VALUE;       // その選択をとった場合のコスト
        for (var candidate: candidates) {
            var next_board = MyUtil.buildBoard(board);
            reverse(next_board, target, candidate, true);
            int next_cost = bfs(target, next_board, depth, cost);
            if (cost > next_cost) {
                cost = next_cost;
                sel  = candidate;
            }
            break;
        }

        reverse(board, target, sel, true);
        return true;
    }

    public static boolean executePlayer (int target, int[][] board) {
        // 置ける候補の探索
        List<MyUtil.Put> candidates = new ArrayList<MyUtil.Put>();

        try {
            candidates = search_candidate_pos(target, board);
        }
        catch (Exception e){
            System.out.println(e);
        }

        if (candidates.size() == 0) {
            MyUtil.print_from_system("置ける場所がないためパスが選択されます");
            return false;
        }

        // 盤面の出力
        PrintBoard.with_candidate(board, true, candidates);

        MyUtil.print_from_system("石を置く場所を選択してください 例) player> a 3");

        Scanner scanner = new Scanner(System.in);

        MyUtil.Put user_choice;
        while (true) {
            try {
                System.out.print("player> ");
                String[] result = scanner.nextLine().split(" ");

                int x = result[0].charAt(0) - 'a';
                int y = Integer.parseInt(result[1]) - 1;
                int[] curr = {x, y};
                user_choice = new MyUtil.Put(x, y, 0);
            }
            catch (Exception e) {
                MyUtil.print_from_system("無効な形式です、再度入力してください。");
                System.out.println(e);
                continue;
            }
            boolean is_valid = false;
            for(var candidate: candidates) {
                if (candidate.equals(user_choice)) is_valid = true;
            }
            if (is_valid) break;
            MyUtil.print_from_system("その場所には石を置けません。再度入力してください。");
        }

        // 返す
        reverse(board, target, user_choice, true);
        return true;
    }
}
