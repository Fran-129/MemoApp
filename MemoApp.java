import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MemoApp {
    // メモを保存するファイル名
    private static final String FILE_NAME = "memos.txt";

    // メモを一時的に保持するリスト
    private static List<String> memos = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // 起動時にファイルからメモを読み込む
        loadMemos();

        System.out.println("=================================");
        System.out.println("   Simple Memo App へようこそ");
        System.out.println("=================================");

        boolean running = true;

        while (running) {
            showMenu();
            System.out.print("メニュー番号を入力してください: ");
            String input = scanner.nextLine();

            switch (input) {
                case "1":
                    showMemos();
                    break;
                case "2":
                    addMemo(scanner);
                    break;
                case "3":
                    deleteMemo(scanner);
                    break;
                case "4":
                    saveMemos();
                    break;
                case "5":
                    saveMemos();
                    System.out.println("アプリを終了します。");
                    running = false;
                    break;
                default:
                    System.out.println("無効な入力です。1〜5を入力してください。");
            }

            System.out.println();
        }

        scanner.close();
    }

    // メニュー表示
    private static void showMenu() {
        System.out.println("------ メニュー ------");
        System.out.println("1. メモ一覧を見る");
        System.out.println("2. メモを追加する");
        System.out.println("3. メモを削除する");
        System.out.println("4. メモを保存する");
        System.out.println("5. 終了する");
        System.out.println("----------------------");
    }

    // メモ一覧表示
    private static void showMemos() {
        if (memos.isEmpty()) {
            System.out.println("メモはまだありません。");
            return;
        }

        System.out.println("------ メモ一覧 ------");
        for (int i = 0; i < memos.size(); i++) {
            System.out.println((i + 1) + ". " + memos.get(i));
        }
    }

    // メモ追加
    private static void addMemo(Scanner scanner) {
        System.out.print("追加するメモを入力してください: ");
        String memo = scanner.nextLine().trim();

        if (memo.isEmpty()) {
            System.out.println("空のメモは追加できません。");
            return;
        }

        memos.add(memo);
        System.out.println("メモを追加しました。");
    }

    // メモ削除
    private static void deleteMemo(Scanner scanner) {
        if (memos.isEmpty()) {
            System.out.println("削除できるメモがありません。");
            return;
        }

        showMemos();
        System.out.print("削除するメモ番号を入力してください: ");
        String input = scanner.nextLine();

        try {
            int index = Integer.parseInt(input);

            if (index < 1 || index > memos.size()) {
                System.out.println("その番号のメモは存在しません。");
                return;
            }

            String removedMemo = memos.remove(index - 1);
            System.out.println("削除しました: " + removedMemo);

        } catch (NumberFormatException e) {
            System.out.println("数字を入力してください。");
        }
    }

    // ファイルからメモ読み込み
    private static void loadMemos() {
        Path path = Paths.get(FILE_NAME);

        if (!Files.exists(path)) {
            System.out.println("保存ファイルはまだありません。新しく開始します。");
            return;
        }

        try {
            memos = Files.readAllLines(path, StandardCharsets.UTF_8);
            System.out.println("メモを " + memos.size() + " 件読み込みました。");
        } catch (IOException e) {
            System.out.println("メモの読み込みに失敗しました。");
            System.out.println("エラー: " + e.getMessage());
        }
    }

    // ファイルへメモ保存
    private static void saveMemos() {
        Path path = Paths.get(FILE_NAME);

        try {
            Files.write(path, memos, StandardCharsets.UTF_8);
            System.out.println("メモを保存しました。");
        } catch (IOException e) {
            System.out.println("メモの保存に失敗しました。");
            System.out.println("エラー: " + e.getMessage());
        }
    }
}
