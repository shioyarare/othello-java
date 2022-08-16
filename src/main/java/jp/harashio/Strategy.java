package jp.harashio;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Strategy {

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
        board[pos.y][pos.x] = target;

        List<MyUtil.Put> all_reverse = new ArrayList<MyUtil.Put>();
        int dx[] = {1, 1, 0, -1, -1, -1, 0, 1}, dy[] = {0, 1, 1, 1, 0, -1, -1, -1};

        // 返せる候補の探索
        for (int i=0; i<8; i++) {
            List<MyUtil.Put> curr_reverse = new ArrayList<MyUtil.Put>();
            int nx = pos.x, ny = pos.y;

            curr_reverse.add(new MyUtil.Put(nx, ny, 0));
            while (true) {
                nx += dx[i];
                ny += dy[i];
                if (nx<0 || nx>7 || ny<0 || ny>7) continue; // 範囲外
                if (board[ny][nx] == target) {
                    // ここまで返すことが可能
                    all_reverse.addAll(curr_reverse);
                    break;
                }
                curr_reverse.add(new MyUtil.Put(nx, ny, 0));
            }
        }

        // 実際にひっくり返す処理を行う場合
        if (do_reverse) {
            for (var rev : all_reverse) {
                board[rev.y][rev.x] = target;
            }
        }

        // ひっくり返した際に裏返せる個数を返す
        return all_reverse.size();
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

        // 盤面の出力
        PrintBoard.with_candidate(board, true, candidates);

        return false;
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
        System.out.println( reverse(board, target, user_choice, true));
        return false;
    }
}
