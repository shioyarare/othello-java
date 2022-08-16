package jp.harashio;

import java.util.ArrayList;
import java.util.List;

public class Strategy {
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
}
