package com.stay_go;

import java.util.Random;
import java.util.Scanner;


public class MatrixNorm {

    // Метод для вычисления нормы матрицы (максимальная сумма элементов строк)
    public static int calculateNorm(int[][] matrix, int n) {
        int maxSum = 0;

        // Проходим по строкам матрицы
        for (int i = 0; i < n; i++) {
            int rowSum = 0;
            for (int j = 0; j < n; j++) {
                rowSum += Math.abs(matrix[i][j]);
            }
            // Обновляем максимальную сумму строки
            if (rowSum > maxSum) {
                maxSum = rowSum;
            }
        }
        return maxSum;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        // Вводим размерность матрицы n
        System.out.print("Введите размерность матрицы: ");
        int n = scanner.nextInt();

        // Инициализируем матрицу
        int[][] matrix = new int[n][n];

        // Заполняем матрицу случайными числами в диапазоне от -n до n
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] = random.nextInt(2 * n + 1) - n;  // Генерация случайного числа от -n до n
            }
        }

        // Выводим матрицу
        System.out.println("Сгенерированная матрица:");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(matrix[i][j] + "\t");
            }
            System.out.println();
        }

        System.out.println("Норма матрицы: " + calculateNorm(matrix, n));
    }
}