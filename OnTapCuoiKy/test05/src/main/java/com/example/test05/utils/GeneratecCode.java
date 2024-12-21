package com.example.test05.utils;

import lombok.RequiredArgsConstructor;

import java.util.Random;

@RequiredArgsConstructor
public class GeneratecCode {
    private static Random random;

    public static String generate() {
        random = new Random();
        StringBuilder code = new StringBuilder();
        code.append(random.nextBoolean() ? "123" : "456");
        for (int i = 0; i < 7; i++) {
            code.append(random.nextInt(10));
        }
        return code.toString();
    }

    public static boolean check(String code) {
        int[] digits = code.chars().map(c -> c - '0').toArray();
        int odd = 0;
        int even = 0;
        for (int i = 0; i < digits.length; i++) {
            if (i % 2 != 0) {
                int doubled = digits[i];
                odd += doubled > 10 ? (doubled / 10 + doubled % 10) : doubled;
            } else {
                even += digits[i];
            }
        }
        return odd+even>10;
    }

    public static String getID(){
        while (true){
            String code = generate();
            if (check(code)){
                return code;
            }
        }
    }
}
