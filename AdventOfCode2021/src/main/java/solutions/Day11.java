package main.java.solutions;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Day11 {
    public static final int FLASH_LEVEL = 9;

    private static int gridSize = 10;
    private static int [][] grid = new int [gridSize][gridSize];
    private static int numSimulations = 0;

    public static void main(String [] args) throws IOException {
        List<String> lines = Files.lines(Path.of(args[0])).collect(Collectors.toList());
        gridSize = Integer.parseInt(args[1]);
        numSimulations = Integer.parseInt(args[2]);

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            for (int j = 0; j < line.length(); j++) {
                grid[i][j] = Integer.parseInt(String.valueOf(line.charAt(j)));
            }
        }

        int currSimFlash = 0;
        for (int sim = 1; sim <= numSimulations; sim++) {
            for (int row = 0; row < gridSize; row ++) {
                for (int col = 0; col < gridSize; col++) {
                    grid[row][col] = grid[row][col] + 1;
                    incrementAdjacent(row,col);
                }
            }
            for (int row = 0; row < gridSize; row ++) {
                for (int col = 0; col < gridSize; col++) {
                    if (grid[row][col] > FLASH_LEVEL) {
                        currSimFlash++;
                        grid[row][col] = 0;
                    }
                }
            }
            if (currSimFlash == (gridSize * gridSize)) {
                System.out.println("Flashing all at simulation number : " + sim);
                return;
            }
        }
    }

    private static void incrementAdjacent(int row, int col) {
        int curr = grid[row][col];
        if (curr == 10) {
            List<Tuple> adj = getAdjacent(row, col);
            for (Tuple<Integer,Integer> t : adj) {
                // only flash recursive if it hasn't flashed before
                if (grid[t.first][t.second] < FLASH_LEVEL + 1) {
                    grid[t.first][t.second] = grid[t.first][t.second] + 1;
                    incrementAdjacent(t.first, t.second);
                }
            }
        }
    }

    private static List<Tuple> getAdjacent(int row, int column) {
        List<Tuple> adjacencies = new ArrayList<>();
        if (row > 0) {
            adjacencies.add(new Tuple(row - 1, column));
        }
        if (row < gridSize - 1) {
            adjacencies.add(new Tuple(row + 1, column));
        }

        if (column > 0) {
            adjacencies.add(new Tuple(row, column - 1));
        }

        if (column < gridSize - 1) {
            adjacencies.add(new Tuple(row, column + 1));
        }

        if (row > 0 && column > 0) {
            adjacencies.add(new Tuple(row - 1, column - 1));
        }

        if (row < gridSize - 1 && column > 0) {
            adjacencies.add(new Tuple(row + 1, column - 1));
        }

        if (row > 0 && column < gridSize - 1) {
            adjacencies.add(new Tuple(row - 1, column + 1));
        }

        if (row < gridSize - 1 && column < gridSize - 1) {
            adjacencies.add(new Tuple(row + 1, column + 1));
        }
        return adjacencies;
    }
}
