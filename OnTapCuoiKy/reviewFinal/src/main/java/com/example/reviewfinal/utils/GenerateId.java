package com.example.reviewfinal.utils;

import java.util.Random;

public class GenerateId {

    private static Random random;

    public static String getID() {
        random = new Random();
        StringBuilder code = new StringBuilder();
        code.append(random.nextBoolean() ? "21" : "22");

        for (int i = 0; i < 6; i++) {
            code.append(random.nextInt(10));
        }
        return code.toString();
    }

    public static String generateId(){
        return getID();
    }
}
