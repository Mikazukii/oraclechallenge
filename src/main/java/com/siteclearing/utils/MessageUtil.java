package com.siteclearing.utils;

public class MessageUtil {

    public static void displayMessage(String message) {
        System.out.print(message);
    }

    public static void displayMessageLn(String message) {
        System.out.println(message);
    }

    public static void displayMessageLn() {
        System.out.println();
    }

    public static void displayWithFormat(String format, Object... args) {
        System.out.format(format, args);
        displayMessageLn();
    }
}
