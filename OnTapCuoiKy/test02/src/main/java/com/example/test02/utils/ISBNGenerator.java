package com.example.test02.utils;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class ISBNGenerator {
    private static final Set<String> generatedISBNs = new HashSet<>();
    private static final Random random = new Random();

    public static String generateISBN() {
        String isbn;
        do {
            // 1. Phát sinh 3 ký tự đầu (978 hoặc 979)
            String prefix = random.nextBoolean() ? "978" : "979";

            // 2. Phát sinh 9 ký tự tiếp theo
            StringBuilder body = new StringBuilder();
            for (int i = 0; i < 9; i++) {
                body.append(random.nextInt(10)); // Thêm số ngẫu nhiên từ 0-9
            }

            // 3. Tính ký tự kiểm tra
            String partialISBN = prefix + body;
            int checkDigit = calculateCheckDigit(partialISBN);

            // 4. Hoàn thiện ISBN
            isbn = partialISBN + checkDigit;

        } while (generatedISBNs.contains(isbn)); // Đảm bảo không trùng

        // Thêm ISBN vào danh sách đã tạo
        generatedISBNs.add(isbn);
        return isbn;
    }

    private static int calculateCheckDigit(String isbn) {
        int sum = 0;
        for (int i = 0; i < isbn.length(); i++) {
            int digit = Character.getNumericValue(isbn.charAt(i));
            sum += (i % 2 == 0) ? digit : digit * 3; // Nhân 1 nếu vị trí lẻ, nhân 3 nếu vị trí chẵn
        }
        int remainder = sum % 10;
        return (remainder == 0) ? 0 : 10 - remainder;
    }

    public static void main(String[] args) {
        // Phát sinh 5 số ISBN ví dụ
        for (int i = 0; i < 5; i++) {
            System.out.println(generateISBN());
        }
    }
}
