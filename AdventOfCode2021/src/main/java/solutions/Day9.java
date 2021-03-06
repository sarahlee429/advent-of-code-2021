package main.java.solutions;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Day9 {
    public static final int MAX_HEIGHT = 9;

    private static int numRows = 0;
    private static int numColumns = 0;
    private static Set<Tuple> lowPoints = new HashSet<>();
    private static List<Integer> basinSizes = new ArrayList<>();
    private static List<List<Integer>> heightValues = null;
    private static Set<Tuple> visited = new HashSet<>();

    private static List<Integer> getHeights(String s) {
        List<Integer> heights = new ArrayList<>();
        for (int i = 0; i < s.length(); i++) {
            heights.add(Integer.parseInt(String.valueOf(s.charAt(i))));
        }
        return heights;
    }

    public static void main(String [] args) throws IOException {
        List<String> lines = Files.lines(Path.of(args[0])).collect(Collectors.toList());
        heightValues = lines.stream().map(s -> getHeights(s)).collect(Collectors.toList());
        numRows = heightValues.size();;
        numColumns = heightValues.get(0).size();

        int riskValue = 0;
        for (int i = 0; i < heightValues.size(); i++) {
            List<Integer> row = heightValues.get(i);
            for (int j = 0; j < row.size(); j++) {
                int value = row.get(j);
                boolean isLow = true;
                if (j - 1 >= 0) {
                    isLow &= value < row.get(j - 1);
                }
                if (j + 1 <= row.size() - 1) {
                    isLow &= value < row.get(j + 1);
                }
                if (i - 1 >= 0) {
                    isLow &= value < heightValues.get(i - 1).get(j);
                }
                if (i + 1 <= heightValues.size() - 1) {
                    isLow &= value < heightValues.get(i + 1).get(j);
                }
                if (isLow) {
                    riskValue += value + 1;
                    lowPoints.add(new Tuple(i, j));
                }
            }
        }
        System.out.println("Risk value : " + riskValue);
        System.out.println("Basin values : " + getBasinValues());
    }

    private static int getBasinValues() {
        for (Tuple c : lowPoints) {
            basinSizes.add(getBasin(c));
        }
        Collections.sort(basinSizes, Collections.reverseOrder());
        return basinSizes.get(0) * basinSizes.get(1) * basinSizes.get(2);
    }

    private static int getBasin(Tuple<Integer,Integer> points) {
        visited.clear();
        int currValue = heightValues.get(points.first).get(points.second);
        return getBasinHelper(points.first, points.second, currValue);
    }

    private static int getBasinHelper(int row, int column, int adjValue) {
        Tuple<Integer, Integer> currCoord = new Tuple(row, column);

        if (visited.contains(currCoord)) return 0;

        if (row >= numRows || column >= numColumns || row < 0 || column < 0) {
            return 0;
        }

        int currValue = heightValues.get(row).get(column);
        if (currValue > adjValue && currValue != MAX_HEIGHT) {
            visited.add(currCoord);
            int neighbor1 = getBasinHelper(row - 1, column, currValue);
            int neighbor2 = getBasinHelper(row + 1, column, currValue);
            int neighbor3 = getBasinHelper(row, column - 1, currValue);
            int neighbor4 = getBasinHelper(row, column + 1, currValue);
            return 1 + neighbor1 + neighbor2 + neighbor3 + neighbor4;
        }
        return 0;
    }
}
