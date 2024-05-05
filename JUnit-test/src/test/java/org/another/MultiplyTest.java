package org.another;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MultiplyTest {
    Calculator calculator;

    @BeforeEach //Выполняется перед каждым методом
    void setUp() {
        calculator = new Calculator();
    }

    @Test // Определяет метод теста
    @DisplayName("Simple multiplication should work") // Определяет имя метода теста
    void testMultiply() {
        assertEquals(20, calculator.multiply(4, 5),
                "Regular multiplication should work");
    }

    @RepeatedTest(5) // Указывает кол-во раз выполнения
    @DisplayName("Ensure correct handling of zero")
    void testMultiplyWithZero() {
        assertEquals(0, calculator.multiply(0, 5), "Multiple with zero should be zero");
        assertEquals(0, calculator.multiply(5, 0), "Multiple with zero should be zero");
    }

    @Test
    @DisplayName("Test messages")
    void testMessage() {
        assertEquals("Пошел нахуй Тимур", calculator.message("Тимур"), "Hello");
        assertNotEquals("Пошел наху Боб", calculator.message("God"));
    }
}
