package com.example.Abacus.utility;

public class RegisterNoGenerator {

    private static int current = 100;
    private static final int MAX = 999999;

    public static synchronized String generateRegisterNo() {
        if (current > MAX) {
            throw new IllegalStateException("Register number limit reached!");
        }
        return String.valueOf(current++);
    }
}
