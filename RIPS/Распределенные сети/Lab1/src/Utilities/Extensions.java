package Utilities;

import java.util.*;
import java.util.stream.Collectors;

import BusnessLogic.Line;

public class Extensions {

    /**
     * Вычисляет точку персечения прямых на плоскости.
     *
     * @param lLine - some Line.
     * @param rLine - some Line.
     * @return точку пересечения, если такой нет то возвращает null.
     * @exception NullPointerException - когда lLine == null или rLine == null.
     */
    public static Point GetCrossingPoint(Line lLine, Line rLine) throws NullPointerException {
        if (lLine == null) {
            throw new NullPointerException("lLine");
        }

        if (rLine == null) {
            throw new NullPointerException("rLine");
        }

        if (rLine.getK() == lLine.getK()) {
            return null;
        }

        var x = 1.0 * (lLine.getB() - rLine.getB()) / (rLine.getK() - lLine.getK());
        var y = x * lLine.getK() + lLine.getB();
        return new Point(x, y);
    }

    /**
     * Групирует прямые на паральные.
     *
     * @param lines - множество линий.
     * @return множество ключ - значение. Ключ - коэффицент k, значение - лист паралельных прямых.
     * */
    public static Map<Integer, List<Line>> GetParallelLines(ArrayList<Line> lines) {
        return lines.stream().collect(Collectors.groupingBy(Line::getK));
    }

    /**
     * Групирует прямые на одинаковые.
     *
     * @param lines - множество линий.
     * @return множество ключ - значение. Ключ - линия, значение - лист одинаковых прямых.
     * */
    public static Map<Line, List<Line>> GetInlineLines(ArrayList<Line> lines) {
        return lines.stream().collect(Collectors.groupingBy(l -> l));
    }

    /**
     * Групирует прямые на первпендикулярные.
     *
     * @param lines - множество линий.
     * @return множество ключ - значение. Ключ - коэффицент k, значение - лист перпенликулярных прямых.
     * */
    public static Map<Line, List<Line>> GetPerpendicularLines(ArrayList<Line> lines) {
        var perLines = new HashMap<Line, List<Line>>();
        for (int i = 0; i < lines.size(); i++) {
            var list = new ArrayList<Line>();
            for (int j = 0; j < lines.size(); j++) {
                if (i == j) continue;

                if (lines.get(i).getK() == -lines.get(j).getK()) {
                    list.add(lines.get(j));
                }
            }

            if(list.size() != 0) perLines.put(lines.get(i), list);
        }

        return perLines;
    }
}
