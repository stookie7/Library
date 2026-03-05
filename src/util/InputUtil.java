package util;

import java.util.Scanner;

public class InputUtil {
    private InputUtil() {}

    public static int readInt(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = sc.nextLine().trim();
            try {
                return Integer.parseInt(s);
            } catch (NumberFormatException e) {
                System.out.println("❌ 숫자만 입력하세요.");
            }
        }
    }

    public static String readNonBlank(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = sc.nextLine().trim();
            if (!s.isBlank()) return s;
            System.out.println("❌ 빈 값은 안 됩니다.");
        }
    }
}