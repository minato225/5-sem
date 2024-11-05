package BusnessLogic;

public class ComplexLine {
    ComplexDigit x, y;

    public ComplexLine(ComplexDigit x, ComplexDigit b) {
        this.x = x;
        this.y = b;
    }

    @Override
    public String toString() {
        return "k * " + x + " + " + y;
    }
}
