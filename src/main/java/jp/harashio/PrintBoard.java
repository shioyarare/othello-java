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
        if (isVisibleGuide) System.out.println("  a b c d e f g h");
        for (int y = 0; y < 8; y++) {
            if (isVisibleGuide) System.out.print(Integer.toString(y+1) + " ");
            for (int x = 0; x < 8; x++) {
                String str = select(board[y][x], white_base, black_base, none_base);
                System.out.print(str + " ");
            }
            System.out.println("\u001b[00m");
        }
    }

    public static void with_candidate(int[][] board, boolean isVisibleGuide, List<MyUtil.Put> candidates) {
        if (isVisibleGuide) System.out.println("  a b c d e f g h");
        for (int y = 0; y < 8; y++) {
            if (isVisibleGuide) System.out.print(Integer.toString(y+1) + " ");
            for (int x = 0; x < 8; x++) {
                boolean is_include_candidate = false;
                int[] tmp_ary = {x, y};
                for(MyUtil.Put candidate: candidates) is_include_candidate |= candidate.equals(new MyUtil.Put(x, y, 0));

                String str = is_include_candidate
                                ? select(board[y][x], white_base, black_base, none_candidate)
                                : select(board[y][x], white_base, black_base, none_base);
                System.out.print(str + " ");
            }
            System.out.println("\u001b[00m");
        }
    }

    private static String select(int target, String white_color, String black_color, String none_color) {
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
