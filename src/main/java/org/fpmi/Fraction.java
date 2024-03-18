package org.fpmi;

import java.util.Objects;

public class Fraction {
    Integer num;
    Integer den;

    public Integer getNum() {
        return num;
    }

    public Integer getDen() {
        return den;
    }

    public Fraction(Integer num, Integer den) {
        if (num == null || den == null) {
            throw new NullPointerException("Values should be not null");
        }
        if (den == 0) {
            throw new ArithmeticException("Cannot divide to zero");
        }
        this.num = num;
        this.den = den;
    }

    public static Fraction sum(Fraction first, Fraction second) {
        long bigCommonDen =  ((long) first.getDen() * (long) second.getDen());
        if (bigCommonDen > Integer.MAX_VALUE) {
            throw new RuntimeException("Common denominator is too big!");
        }
        Integer commonDen = first.getDen() * second.getDen();
        Integer sumNum = first.getNum() * second.getDen() + second.getNum() * first.getDen();
        return new Fraction(sumNum, commonDen);
    }

    @Override
    public String toString() {
        return num + "/" + den;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Fraction fraction = (Fraction) o;
        return Objects.equals(num, fraction.num) && Objects.equals(den, fraction.den);
    }

    @Override
    public int hashCode() {
        return Objects.hash(num, den);
    }
}
