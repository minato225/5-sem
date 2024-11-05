package BusnessLogic;

public class Fraction {
    private int n, d;

    /**
     * @param n is a numerator of BusnessLogic.Fraction
     * @param d is a divider of BusnessLogic.Fraction
     */
    public Fraction(int n, int d) throws ArithmeticException {
        if (d == 0) throw new ArithmeticException("wrong ");
        int divisor = gcd(n, d);
        this.n = n / divisor;
        this.d = d / divisor;
    }

    /**
     * @param n is a numerator of BusnessLogic.Fraction
     * @param d is a divider of BusnessLogic.Fraction
     * @return the greatest common divisor of numerator and divider
     */
    private static int gcd(int n, int d) {
        return (d == 0 ? n : gcd(d, n % d));
    }

    /**
     * Преобразование дроби в строковое значение.
     *
     * @return дробь.
     */
    public String toString() {
        return n + "/" + d;
    }
}