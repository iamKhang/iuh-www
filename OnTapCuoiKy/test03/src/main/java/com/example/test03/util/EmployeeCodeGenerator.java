package com.example.test03.util;

import java.util.Random;

public class EmployeeCodeGenerator {
    private static final Random random = new Random();

    public static String generateEmployeeCode() {
        while (true) {
            String code = generateCode();
            if (isValidCode(code)) {
                return code;
            }
        }
    }

    private static String generateCode() {
        StringBuilder code = new StringBuilder();
        // 3 số đầu (123 hoặc 456)
        code.append(random.nextBoolean() ? "123" : "456");
        
        // 7 số ngẫu nhiên
        for (int i = 0; i < 7; i++) {
            code.append(random.nextInt(10));
        }
        return code.toString();
    }

    private static boolean isValidCode(String code) {
        int[] digits = code.chars().map(c -> c - '0').toArray();
        
        // Bước 1: Tính tổng các số lẻ nhân 2
        int sumOdd = 0;
        for (int digit : digits) {
            if (digit % 2 != 0) {
                int doubled = digit * 2;
                sumOdd += doubled > 9 ? (doubled / 10 + doubled % 10) : doubled;
            }
        }
        
        // Bước 2: Tính tổng các số chẵn
        int sumEven = 0;
        for (int digit : digits) {
            if (digit % 2 == 0) {
                sumEven += digit;
            }
        }
        
        // Bước 3: Kiểm tra tổng chia hết cho 10
        return (sumOdd + sumEven) % 10 == 0;
    }
} 