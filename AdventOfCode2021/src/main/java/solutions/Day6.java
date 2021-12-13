package main.java.solutions;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Day6 {
    public static class Fish {
        int timer;
        public Fish(int timer) {
            this.timer = timer;
        }
        public String toString() {
            return String.valueOf(this.timer);
        }
    }

    private static List<Fish> fishes = new ArrayList<>();
    private static int numSimulations = 0;

    private static long simulateFishes() {
        int currentDay = 0;
        Map<Integer, Long> fishCount = new HashMap<>();
        for (Fish f : fishes) {
            fishCount.put(f.timer, fishCount.getOrDefault(f.timer, 0L) + 1L);
        }

        while (currentDay < numSimulations) {
            Map<Integer, Long> newFishMap = new HashMap<>();
            long newSixes = 0;
            long newEights = 0;
            for (Integer key : fishCount.keySet()) {
                if (key == 0) {
                    newEights += fishCount.get(key);
                    newSixes += fishCount.get(key);
                } else {
                    newFishMap.put(key - 1, fishCount.get(key));
                }
            }
            newFishMap.put(6, newFishMap.getOrDefault(6, 0L) + newSixes);
            newFishMap.put(8, newEights);
            fishCount = newFishMap;
            currentDay++;
        }
        return fishCount.values().stream().collect(Collectors.summingLong(Long::longValue));
    }

    public static void main(String [] args) throws IOException {
        fishes = Files.lines(Paths.get(args[0])).findFirst()
                .map(s1 -> Arrays.asList(s1.split(","))).get()
                .stream().map(s2 -> new Fish(Integer.parseInt(s2))).collect(Collectors.toList());
        numSimulations = Integer.parseInt(args[1]);
        System.out.println(simulateFishes());
    }
}
