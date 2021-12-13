package main.java.solutions;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Day7 {
    private static List<String> crabs;
    private static List<Integer> crabPositions;

    private static int getMinimumFuelCost(int idxToAlign) {
        Collections.sort(crabPositions);
        int currCost = crabPositions.stream().map(c -> getFuelCost(idxToAlign, c)).collect(Collectors.summingInt(Integer::intValue));
        return currCost;
    }

    private static int getFuelCostSimple(int start, int end) {
        return Math.abs(start - end);
    }

    private static int getFuelCost(int start, int end) {
        int diff = Math.abs(start - end);
        int cost = 0;
        for (int i = 1; i <= diff; i++) {
            cost += i;
        }
        return cost;
    }

    public static void main(String [] args) throws IOException {
        crabs = Files.lines(Paths.get(args[0])).findFirst()
                .map(s -> Arrays.asList(s.split(",")))
                .get();
        crabPositions = crabs.stream().map(s -> Integer.parseInt(s.trim())).collect(Collectors.toList());

        int median = crabPositions.get(crabPositions.size() / 2);
        int mean = (int) Math.floor(crabPositions.stream()
                .mapToDouble(a -> a)
                .average().getAsDouble());

        System.out.println(getMinimumFuelCost(mean));
    }
}
