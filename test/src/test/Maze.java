package test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.function.BiConsumer;

/**
 * ファイル名:Maze.java 迷宮のマップを穴掘り法で作成するプログラムです。
 *
 * @author 西森無理
 * @modifier @shiracamus
 */
public class Maze {

    /**
     * 迷宮を作って表示する。aaaaaaaaaaaa
     *
     * @param args コマンド引数
     */
    public static void main(String[] args) {
        // 迷宮を作る。
        Maze maze = new Maze(55, 59);
        // 迷宮を表示する。
        maze.print();
    }

    // 定数 (クラス変数)
    private static final int WALL = 0;  // 壁
    private static final int ROAD = 1;  // 道

    // インスタンス変数
    private final int height, width;    // 迷宮の高さと幅
    private final int[][] map;          // 迷宮マップ

    /**
     * 迷宮を作ります。
     *
     * @param height 迷宮の高さ
     * @param width 迷宮の幅
     */
    public Maze(int height, int width) {
        // 高さと幅を奇数に拡大調整する。最小は3。
        this.height = Math.max(3, height | 1);
        this.width = Math.max(3, width | 1);

        // 迷宮マップを作成する。0で初期化されるためすべて壁(WALL)になる。
        map = new int[this.height][this.width];

        // 穴掘り人を雇って迷宮を掘らせる。掘り終わったら解雇(破棄)する。
        new Digger().dig();
    }

    /**
     * 迷宮を表示します。
     */
    public void print() {
        for (int[] row : map) {
            for (int data : row) {
                System.out.print(data == WALL ? "■" : "　");
            }
            System.out.println();
        }
    }

    /**
     * 迷宮の穴掘り人
     */
    private class Digger {

        /**
         * 壁を掘って道を作る
         */
        void dig() {
            // 迷宮で初めに穴を掘る場所。中央付近の柱ではない場所(奇数位置)にする。
            int row = height / 2 | 1, col = width / 2 | 1;

            // 再帰的に壁を掘り進めて道を作る。
            dig(row, col);
        }

        /**
         * 再帰的に迷宮の壁を掘り進んで道を作っていきます。
         *
         * @param row 掘る行位置
         * @param col 掘る列位置
         */
        void dig(int row, int col) {
            // 指定された位置を道にする。
            map[row][col] = ROAD;

            // 掘り進む4方向。Collectionsでシャッフルしたかったのでリストにしました。
            ArrayList<BiConsumer<Integer, Integer>> direction = new ArrayList<>();
            direction.add(this::digRight);
            direction.add(this::digLeft);
            direction.add(this::digDown);
            direction.add(this::digUp);

            // ランダムに方向を決め、進める方向に2マス進んでさらに掘り進める。
            Collections.shuffle(direction);
            direction.stream().forEach(dig -> dig.accept(row, col));
        }

        // 左方向が壁なら道を作り、更に壁を掘り進む
        void digLeft(int row, int col) {
            if (col - 2 > 0 && map[row][col - 2] == WALL) {
                map[row][--col] = ROAD;
                dig(row, --col);
            }
        }

        // 右方向が壁なら道を作り、更に壁を掘り進む
        void digRight(int row, int col) {
            if (col + 2 < width - 1 && map[row][col + 2] == WALL) {
                map[row][++col] = ROAD;
                dig(row, ++col);
            }
        }

        // 上方向が壁なら道を作り、更に壁を掘り進む
        void digDown(int row, int col) {
            if (row + 2 < height - 1 && map[row + 2][col] == WALL) {
                map[++row][col] = ROAD;
                dig(++row, col);
            }
        }

        // 下方向が壁なら道を作り、更に壁を掘り進む
        void digUp(int row, int col) {
            if (row - 2 > 0 && map[row - 2][col] == WALL) {
                map[--row][col] = ROAD;
                dig(--row, col);
            }
        }
    } // end of class Digger
} 
