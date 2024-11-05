package BusnessLogic;
import Utilities.Roots;

/**
 * Класс прямой со свойствами <b>k</b> и <b>b</b>. Прямая задана уравнением xk+b
 * @author Doskoch Roman
 * @version 1.0
 */
public class Line {

    private int k, b;

    /**
     * Конструктор - создание нового объекта
     * @see Line#Line(int, int)
     */
    public Line(int k, int b) {
        this.k = k;
        this.b = b;
    }

    public int getK() {
        return k;
    }

    public int getB() {
        return b;
    }

    /**
     * Находит точки пересечения прямой с осями координат.
     * @return возвращает точки перечечения через класс
     * @see Roots
     * */
    public Roots PointsOfIntersection() {
        var x = k == 0 ? Integer.MAX_VALUE : 1.0 * -this.b / this.k;
        return new Roots(x, this.b);
    }


    @Override
    public String toString() {
        return "y=" + k + "x+" + b;
    }
}
