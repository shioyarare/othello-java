package jp.harashio;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PrintBoard {
    private static String none_base = "\u001b[30;42m-";
    private static String none_candidate = "\u001b[30;43m-";
    private static String white_base = "\u001b[97;42;1m●";
    private static String black_base = "\u001b[30;42;1m●";
    // 最も単純なボードを出力する関数
    public static void simple(int[][] board, boolean isVisibleGuide) {
        int i = 0;
        if (isVisibleGuide) System.out.println("  a b c d e f g h");
        for (int[] row: board) {
            i ++;
            var row_str = Arrays.stream(row)
                    .mapToObj(e -> select_color(e, white_base, black_base, none_base))
                    .collect(Collectors.toList());
            if (isVisibleGuide) System.out.print(Integer.toString(i) + " ");
            System.out.println(String.join(" ", row_str) + "\u001b[00m");
            System.out.println("\u001b[00m");
        }
    }

    public static void with_candidate(int[][] board, boolean isVisibleGuide, List<int[]> candidates) {


        if (isVisibleGuide) System.out.println("  a b c d e f g h");
        for (int y = 0; y < 8; y++) {
            if (isVisibleGuide) System.out.print(Integer.toString(y+1) + " ");
            for (int x = 0; x < 8; x++) {
                boolean is_include_candidate = false;
                int[] tmp_ary = {x, y};
                for(int[] candidate: candidates) is_include_candidate |= Arrays.equals(tmp_ary, candidate);

                String color = is_include_candidate
                                ? select_color(board[y][x], white_base, black_base, none_candidate)
                                : select_color(board[y][x], white_base, black_base, none_base);
                System.out.print(color + Integer.toString(board[y][x]));
            }
            System.out.println("\u001b[00m");
        }
    }

    private static String select_color(int target, String white_color, String black_color, String none_color) {
        switch (target) {
            case 1:
                return black_color;
            case 2:
                return white_color;
            default:
                return none_color;
        }
    }
}
