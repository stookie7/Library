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

    
    public static int readIntRange(Scanner sc, String prompt, int min, int max) {
        while (true) {
            int v = readInt(sc, prompt);
            if (v >= min && v <= max) return v;
            System.out.printf("❌ %d ~ %d 사이로 입력하세요.%n", min, max);
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