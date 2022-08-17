package jp.harashio;

import java.util.List;

public class PrintBoard {
    private static final String none_base = "\u001b[30;42m-";
    private static final String none_candidate = "\u001b[30;43m-";
    private static final String white_base = "\u001b[97;42m●";
    private static final String white_select_base = "\u001b[97;45m●";
    private static final String black_base = "\u001b[30;42m●";
    private static final String black_select_base = "\u001b[30;45m●";
    // 最も単純なボードを出力する関数
    public static void simple(int[][] board) {
        System.out.println("  a b c d e f g h");
        for (int y = 0; y < 8; y++) {
            System.out.print(y + 1 + " ");
            for (int x = 0; x < 8; x++) {
                String str = select_base(board[y][x], none_base);
                System.out.print(str + " ");
            }
            System.out.println("\u001b[00m");
        }
    }

    public static void with_select(int[][] board, MyUtil.Put sel) {
        System.out.println("  a b c d e f g h");
        for (int y = 0; y < 8; y++) {
            System.out.print(y + 1 + " ");
            for (int x = 0; x < 8; x++) {

                String str = sel.x == x && sel.y == y
                        ? select_sel(board[y][x], true)
                        : select_sel(board[y][x], false);
                System.out.print(str + " ");
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

                String str = is_include_candidate
                                ? select_base(board[y][x], none_candidate)
                                : select_base(board[y][x], none_base);
                System.out.print(str + " ");
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
                String str = is_include_candidate
                        ? select_base(board[y][x], none_candidate)
                        : select_base(board[y][x], none_base);
                // 選択された石だったら色を変える
                if (sel.x == x && sel.y == y) str = select_sel(board[y][x], true);
                System.out.print(str + " ");
            }
            System.out.println("\u001b[00m");
        }
    }
    private static String select_base(int target, String none_color) {
        return switch (target) {
            case 1 -> PrintBoard.black_base;
            case 2 -> PrintBoard.white_base;
            default -> none_color;
        };
    }

    private static String select_sel(int target, boolean is_select) {
        return switch (target) {
            case 1 -> is_select ? PrintBoard.black_select_base : PrintBoard.black_base;
            case 2 -> is_select ? PrintBoard.white_select_base : PrintBoard.white_base;
            default -> PrintBoard.none_base;
        };
    }
}
