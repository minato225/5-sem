import BusnessLogic.*;
import Utilities.Extensions;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Function;

public class Main {
    public static void main(String[] args) {

        var testLine = new Line(-3, 2);
        System.out.println("Точки пересечения прямой с осями координат:");
        System.out.println(testLine.PointsOfIntersection());

        var lines = new ArrayList<Line>();
        for (int i = 0; i < 20; ++i) {
            lines.add(new Line(rand(-10, 10), rand(-10, 10)));
        }

        System.out.println("Точка пересечения");
        System.out.println(Extensions.GetCrossingPoint(new Line(1, 2), new Line(-10, -3)));

        System.out.println("Паралельные прямые");
        printLines(lines, Extensions::GetParallelLines);
        System.out.println("Совпадающие прямые");
        printLines(lines, Extensions::GetInlineLines);
        System.out.println("Перпендикулярные прямые");
        printLines(lines, Extensions::GetPerpendicularLines);

        System.out.println("Комплексные прямые.");
        System.out.println(new ComplexLine(new ComplexDigit(2, 3), new ComplexDigit(2, 4)));
        System.out.println("Прямые с дробями.");
        System.out.println(new DoubleLine(new Fraction(10, 3), new Fraction(3,3)));
    }

    public static <T> void printLines(ArrayList<Line> lines, Function<ArrayList<Line>, Map<T, List<Line>>> func) {
        var groupLines = func.apply(lines);

        for (var item : groupLines.entrySet()) {

            System.out.println(item.getKey());

            for (var line : item.getValue()) {
                System.out.println("\t" + line);
            }
            System.out.println();
        }
    }

    public static int rand(int min, int max) {
        return new Random().nextInt(max - min) + min;
    }
}
