package BusnessLogic;

public class ComplexDigit{
    private int Real, Img;

    /**
     * @param real is a real part of complex number
     * @param img is a imagination part of complex number
     */
    public ComplexDigit(int real, int img) {
        Real = real;
        Img = img;
    }

    /**
     * Преобразует комплексное числа в строковое значение
     *
     * @return комплексное число с форматом a + bi.
     */
    @Override
    public String toString() {
        return Real + " + " + Img + "i";
    }
}
