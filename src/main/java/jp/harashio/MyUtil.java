package jp.harashio;

import java.util.Arrays;
import java.util.Random;
import java.lang.String;

public class MyUtil {
    public static Random r = new Random();

    public static void print_from_system(String message) {
        System.out.println("system> " + message);
    }

    // ゲームの表示をみやすくするため定時間進行を止める
    public static void system_wait() {
        try {
            Thread.sleep(3000);
        }
        catch (Exception e) {
            System.out.println(e);
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
