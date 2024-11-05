package BusnessLogic;

public class DoubleLine {
    Fraction x, y;

    public DoubleLine(Fraction x, Fraction b) {
        this.x = x;
        this.y = b;
    }

    @Override
    public String toString() {
        return "k" + x + " + " + y;
    }
}
