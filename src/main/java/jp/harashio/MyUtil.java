package jp.harashio;

import java.util.Arrays;
import java.util.Random;
import java.lang.String;
import java.util.logging.*;

public class MyUtil {
    public static final Logger logger = Logger.getLogger("Debug");
    public static class Put {
        Put(int x, int y, int value) {
            this.x = x;
            this.y = y;
            this.value = value;
        }

        public boolean equals(MyUtil.Put x) {
            return this.x == x.x && this.y == x.y;
        }
        public int x;     // x 座標
        public int y;     // y 座標
        public int value; // ここに置くことで返せる個数 (0は未入力とする)
    }
    public static Random r = new Random();

    public static void print_from_system(String message) {
        System.out.println("system> " + message);
    }

    public static int[][] copy_board(int[][] board) {
        var new_board = new int[8][8];
        for(int y=0; y<8; y++) {
            System.arraycopy(board[y], 0, new_board[y], 0, 8);
        }
        return new_board;
    }
    // ゲームの表示をみやすくするため定時間進行を止める
    public static void system_wait(int millis) {
        try {
            Thread.sleep(millis);
        }
        catch (Exception e) {
            logger.warning(e.toString());
        }
    }

    public static int[][] initialize_board() {
        var board = new int[8][8];
        for (int[] row: board) Arrays.fill(row, 0);
        board[3][3] = board[4][4] = 2; // 白石の配置
        board[4][3] = board[3][4] = 1; // 黒石の配置
        return board;
    }
}
