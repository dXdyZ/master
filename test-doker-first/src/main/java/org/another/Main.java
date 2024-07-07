package org.another;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String message = scanner.nextLine();
        for (int i = 0; i < 100; i++) {
            System.out.println(message + " " + i);
        }
    }
}