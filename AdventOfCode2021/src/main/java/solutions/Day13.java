package main.java.solutions;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Day13 {
    public static int maxXValue = 0;
    public static int maxYValue= 0;
    public static DataTuple[][] grid;

    public static void main(String [] args) throws IOException {
        List<String> lines = Files.lines(Path.of(args[0])).collect(Collectors.toList());
        Set<DataTuple> poundCoordinates = new HashSet<>();
        List<String> folds = new ArrayList<>();
        boolean parsingFolds = false;
        for(String line : lines) {
            if (line.isBlank()) {
                parsingFolds = true;
                continue;
            }
            if (parsingFolds) {
                String [] lineSplit = Arrays.stream(line.split(" ")).map(String::trim).toArray(String[]::new);
                folds.add(lineSplit[2]);
            } else {
                String [] lineSplit = Arrays.stream(line.split(",")).map(String::trim).toArray(String[]::new);
                DataTuple<Integer,Integer,String> tmp = new DataTuple(Integer.parseInt(lineSplit[0]), Integer.parseInt(lineSplit[1]), "#");
                maxXValue = Math.max(maxXValue, tmp.first);
                maxYValue = Math.max(maxYValue, tmp.second);
                poundCoordinates.add(tmp);
            }
        }

        populateGrid(poundCoordinates);
        foldGrid(folds);
        printGrid();
    }

    private static void populateGrid(Set<DataTuple> poundCoordinates){
        grid = new DataTuple[maxXValue + 1][maxYValue + 1];
        for(int y = 0; y <= maxYValue; y++) {
            for (int x = 0; x <= maxXValue; x++) {
                DataTuple<Integer,Integer,String> tmp = new DataTuple<>(x, y, null);
                tmp.third = poundCoordinates.contains(tmp) ? "#" : ".";
                grid[x][y] = tmp;
            }
        }
    }

    private static void foldGrid(List<String> folds) {
        for (String fold : folds) {
            String [] foldSplit = Arrays.stream(fold.split("=")).map(String::trim).toArray(String[]::new);
            int translateValue = Integer.parseInt(foldSplit[1]);
            if (foldSplit[0].equals("y")) {
                for (int y = translateValue + 1; y <= maxYValue; y++) {
                    for (int x = 0; x <= maxXValue; x++) {
                        int currentY = y - translateValue;
                        int targetY = translateValue - currentY;
                        if (grid[x][y].third.equals("#")) {
                            grid[x][targetY] = grid[x][y];
                        }
                    }
                }
                maxYValue = translateValue - 1;
            } else if (foldSplit[0].equals("x")) {
                for (int x = translateValue + 1; x <= maxXValue; x++) {
                    for (int y = 0; y <= maxYValue; y++) {
                        int currentX = x - translateValue;
                        int targetX = translateValue - currentX;
                        if (grid[x][y].third.equals("#")) {
                            grid[targetX][y] = grid[x][y];
                        }
                    }
                }
                maxXValue = translateValue - 1;
            }
        }
    }

    private static int countPounds() {
        int count = 0;
        for (int x = 0; x <= maxXValue; x++) {
            for (int y = 0; y <= maxYValue; y++) {
                if (grid[x][y].third.equals("#")) {
                    count++;
                }
            }
        }
        return count;
    }

    private static void printGrid(){
        System.out.println("=========================");
        for(int y = 0; y <= maxYValue; y++) {
            for (int x = 0; x <= maxXValue; x++) {
                System.out.print(grid[x][y].third);
            }
            System.out.println();
        }
        System.out.println("=========================");
    }
}
