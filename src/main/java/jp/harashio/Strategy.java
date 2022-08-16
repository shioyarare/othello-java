package jp.harashio;

import java.util.ArrayList;
import java.util.List;

public class Strategy {
    private static List<int[]> search_candidate_pos(int target, int[][] board) {
        var candidate_pos = new ArrayList<int[]>();
        int dx[] = {1, 1, 0, -1, -1, -1, 0, 1}, dy[] = {0, 1, 1, 1, 0, -1, -1, -1};

        for (int y=0; y<8; y++) {
            for (int x=0; x<8; x++) {
                // 空いていなければそのマスには置けない
                if (board[y][x] != 0) continue;

                // 空いているマスが実際に置けるかチェック
                for (int i=0; i<8; i++) {
                    int nx = x + dx[i], ny = y + dy[i];
                    if (nx<0 || nx>7 || ny<0 || ny>7) continue; // 範囲外
                    if (board[ny][nx] == target || board[ny][nx] == 0) continue; // すぐ隣が敵の石ではない場合置けない

                    // 隣が敵の石だった場合、その先に自身の石があればおける
                    while (true) {
                        nx += dx[i];
                        ny += dy[i];
                        if (nx<0 || nx>7 || ny<0 || ny>7) break; // 範囲外
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

    public static boolean executeComputer (int target, int[][] board) {
        // 置ける候補の探索
        List<int[]> candidates = new ArrayList<int[]>();
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
        return false;
    }
}
