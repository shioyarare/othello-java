package jp.harashio;

import java.util.List;

public class PrintBoard {
    private static final String bg_green  = "42";
    private static final String bg_yellow = "43";
    private static final String bg_red    = "45";
    private static final String fg_black  = "30";
    private static final String fg_white  = "97";

    private static String make_escape_seq (String fg, String bg) {
        return String.format("\u001b[%s;%sm", fg, bg);
    }

    private static String make_cell_str (int target, String bg) {
        return switch (target) {
            case 1 -> make_escape_seq(fg_black, bg) + "● ";
            case 2 -> make_escape_seq(fg_white, bg) + "● ";
            default -> make_escape_seq(fg_black, bg) + "- ";
        };
    }
    // 最も単純なボードを出力する関数
    public static void simple(int[][] board) {
        System.out.println("  a b c d e f g h");
        for (int y = 0; y < 8; y++) {
            System.out.print(y + 1 + " ");
            for (int x = 0; x < 8; x++) {
                System.out.print( make_cell_str(board[y][x], bg_green) );
            }
            System.out.println("\u001b[00m");
        }
    }

    public static void with_select(int[][] board, MyUtil.Put sel) {
        System.out.println("  a b c d e f g h");
        for (int y = 0; y < 8; y++) {
            System.out.print(y + 1 + " ");
            for (int x = 0; x < 8; x++) {
                String bg_color = sel.x == x && sel.y == y ? bg_red : bg_green;
                System.out.print(make_cell_str(board[y][x], bg_color));
            }
            System.out.println("\u001b[00m");
        }
    }

    public static void with_candidate(int[][] board, List<MyUtil.Put> candidates) {
        System.out.println("  a b c d e f g h");
        for (int y = 0; y < 8; y++) {
            System.out.print(y + 1 + " ");
            for (int x = 0; x < 8; x++) {
                boolean is_include_candidate = false;
                for(MyUtil.Put candidate: candidates) is_include_candidate |= candidate.equals(new MyUtil.Put(x, y, 0));
                String bg_color = is_include_candidate ? bg_yellow : bg_green;
                System.out.print( make_cell_str(board[y][x], bg_color) );
            }
            System.out.println("\u001b[00m");
        }
    }

    public static void with_mixed(int[][] board, List<MyUtil.Put> candidates, MyUtil.Put sel) {
        System.out.println("  a b c d e f g h");
        for (int y = 0; y < 8; y++) {
            System.out.print(y + 1 + " ");
            for (int x = 0; x < 8; x++) {
                boolean is_include_candidate = false;
                for(MyUtil.Put candidate: candidates) is_include_candidate |= candidate.equals(new MyUtil.Put(x, y, 0));
                // 次に置くことができる候補かどうか
                String bg_color = is_include_candidate ? bg_yellow : bg_green;

                // 選択された位置かどうか
                if (sel.x == x && sel.y == y) bg_color = bg_red;

                System.out.print( make_cell_str(board[y][x], bg_color) );
            }
            System.out.println("\u001b[00m");
        }
    }
}
