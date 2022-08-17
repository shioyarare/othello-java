package jp.harashio;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.lang.String;

public class Strategy {
    private static int get_enemy (int target) {
        return target == 1 ? 2 : 1;
    }
    private static List<MyUtil.Put> search_candidate_pos(int target, int[][] board) {
        var candidate_pos = new ArrayList<MyUtil.Put>();
        for (int y=0; y<8; y++) {
            for (int x=0; x<8; x++) {
                if (board[y][x] != 0) continue; // 既に置かれている
                // 実際に置けるかチェック
                int rev_num = reverse_control(board, target, new MyUtil.Put(x, y, 0), false);
                // そこに置いて敵の駒が返せれば置ける場所
                if (rev_num > 0) { candidate_pos.add(new MyUtil.Put(x, y, rev_num)); }
            }
        }
        return candidate_pos;
    }

    private static int reverse_control(int[][] board, int target, MyUtil.Put pos, boolean do_reverse) {
        var all_reverse_pos = new ArrayList<MyUtil.Put>();
        int[] dx = {1, 1, 0, -1, -1, -1, 0, 1}, dy = {0, 1, 1, 1, 0, -1, -1, -1};

        // dfs で裏返せる駒のチェック
        for (int i=0; i<8; i++) {
            var curr_reverse = new ArrayList<MyUtil.Put>();
            int nx = pos.x, ny = pos.y, k = 0;
            while (true) {
                nx += dx[i]; ny += dy[i];
                if (nx<0 || nx>7 || ny<0 || ny>7 || board[ny][nx] == 0) break; // 範囲外
                if (k++ == 0 && board[ny][nx] == target ) break; // すぐ隣が自身の石
                // 0個以上の裏返せるポイントの確定
                if (board[ny][nx] == target) {
                    all_reverse_pos.addAll(curr_reverse);
                    break;
                }
                curr_reverse.add(new MyUtil.Put(nx, ny, 0));
            }
        }
        // 実際に返す処理を行う場合
        if (do_reverse) {
            board[pos.y][pos.x] = target;
            for (var reverse_pos: all_reverse_pos) {
                board[reverse_pos.y][reverse_pos.x] = target;
            }
        }
        // 返した際に裏返せる個数を返す
        return all_reverse_pos.size();
    }

    private static MyUtil.Put execute (int target, int[][] board, List<MyUtil.Put> candidates) {
        return candidates.get(0);
    }
    public static boolean executeComputer (int target, int[][] board) {
        // 置ける候補の探索
        List<MyUtil.Put> candidates = search_candidate_pos(target, board);

        if (candidates.size() == 0) {
            MyUtil.print_from_system("置ける場所がないためパスが選択されます");
            return false;
        }

        // 配置地点の決定
        MyUtil.Put sel = execute(target, board, candidates);

        MyUtil.print_from_system( (char) ('a' + sel.x) + " " + (sel.y + 1));
        reverse_control(board, target, sel, true);
        PrintBoard.with_all(board, search_candidate_pos(get_enemy(target), board), sel);
        return true;
    }

    public static boolean executePlayer (int target, int[][] board) {
        // 置ける候補の探索
        List<MyUtil.Put> candidates = search_candidate_pos(target, board);

        if (candidates.size() == 0) {
            MyUtil.print_from_system("置ける場所がないためパスが選択されます");
            return false;
        }

        // 候補地点の表示
        PrintBoard.with_candidate(board, candidates);

        MyUtil.print_from_system("石を置く場所を選択してください 例) player> a 3");

        Scanner scanner = new Scanner(System.in);

        // ユーザとの対話により駒を置く地点の決定
        MyUtil.Put user_choice;
        while (true) {
            try {
                System.out.print("player> ");
                String[] result = scanner.nextLine().split(" ");

                int x = result[0].charAt(0) - 'a';
                int y = Integer.parseInt(result[1]) - 1;
                user_choice = new MyUtil.Put(x, y, 0);
            }
            catch (Exception e) {
                MyUtil.print_from_system("無効な形式です、再度入力してください。");
                MyUtil.logger.warning(e.toString());
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
        reverse_control(board, target, user_choice, true);
        PrintBoard.with_select(board, user_choice);
        return true;
    }
}
