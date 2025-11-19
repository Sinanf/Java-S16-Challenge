package org.workintech.library.util;

import java.util.Scanner;

/**
 * Konsol üzerinden kullanıcıdan güvenli şekilde veri okumayı kolaylaştıran yardımcı sınıf.
 * Scanner'ı dışarıdan alır, böylece sistemde tek bir Scanner örneği kullanılır.
 */
public class InputUtil {

    /**
     * Kullanıcıdan geçerli bir tam sayı alır.
     */
    public static int readInt(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();

            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("❌ Geçersiz sayı girdiniz. Tekrar deneyin.");
            }
        }
    }

    /**
     * Boş olmayan bir string okur.
     */
    public static String readNonEmpty(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();

            if (!input.isEmpty()) {
                return input;
            }

            System.out.println("❌ Bu alan boş bırakılamaz. Tekrar deneyin.");
        }
    }

    /**
     * Menüde belirli bir aralıkta sayı bekliyorsak kullanılabilir.
     */
    public static int readMenuChoice(Scanner scanner, String prompt, int min, int max) {
        while (true) {
            int choice = readInt(scanner, prompt);
            if (choice >= min && choice <= max) {
                return choice;
            }
            System.out.println("❌ Lütfen " + min + " ile " + max + " arasında bir değer girin.");
        }
    }
}
