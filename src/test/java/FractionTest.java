import org.fpmi.Fraction;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FractionTest {

    @Test
    @Tag("Smoke")
    @DisplayName("Сложение двух положительных дробных чисел")
    void simpleFraction() {
        Fraction first = new Fraction(1, 2);
        Fraction second = new Fraction(2, 3);
        Fraction expectedSum = new Fraction(7, 6);
        assertEquals(expectedSum, Fraction.sum(first, second), "Суммы должны быть одинаковыми");
    }

    @Test
    @Tag("Smoke")
    @DisplayName("Сложение отрицательных дробных чисел")
    void negativeFraction() {
        Fraction first = new Fraction(1, 2);
        Fraction second = new Fraction(-2, 3);
        Fraction expectedSum = new Fraction(-1, 6);
        assertEquals(expectedSum, Fraction.sum(first, second), "Суммы должны быть одинаковыми");
    }

    @Test
    @Tag("Negative")
    @DisplayName("Нулевой числитель")
    void zeroNumFraction() {
        Fraction first = new Fraction(1, 2);
        Fraction second = new Fraction(-2, 4);
        Fraction expectedSum = new Fraction(0, 8);
        assertEquals(expectedSum, Fraction.sum(first, second), "Суммы должны быть одинаковыми");
    }

    @Test
    @Tag("Negative")
    @DisplayName("Нулевой знаменатель")
    void zeroDenFraction() {
        assertThrows(ArithmeticException.class, () -> new Fraction(1, 0),
                "Должно быть выброшено исключение ArithmeticException");
    }

    @Test
    @Tag("Negative")
    @DisplayName("Не заполнены числитель и знаменатель")
    void nullFraction() {
        assertThrows(NullPointerException.class, () -> new Fraction(null, null),
                "Должно быть выброшено исключение NullPointerException");
    }

    @Test
    @Tag("Negative")
    @DisplayName("Переполнение")
    void longFraction() {
        Fraction first = new Fraction(1, 2);
        Fraction second = new Fraction(1, Integer.MAX_VALUE);
        assertThrows(RuntimeException.class, () -> Fraction.sum(first, second),
                "Должно быть выброшено исключение RuntimeException");
    }
}
