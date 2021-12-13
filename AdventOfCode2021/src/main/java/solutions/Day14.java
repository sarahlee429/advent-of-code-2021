package main.java.solutions;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Day14 {
    public static int numSimulations = 0;
    public static String initialState = "";
    public static Map<String, String> rulesMap = new HashMap<>();
    public static  Map<String, Long> pairMap = new HashMap<>();

    public static void main(String [] args) throws IOException {
        List<String> lines = Files.lines(Path.of(args[0])).collect(Collectors.toList());
        numSimulations = Integer.parseInt(args[1]);
        initialState = lines.get(0).trim();

        for (int i = 2; i < lines.size(); i++) {
            String [] parts = Arrays.stream(lines.get(i).split(" -> ")).map(String::trim).toArray(String[]::new);
            rulesMap.put(parts[0], parts[1]);
        }

        for (int i = 0; i < initialState.length() - 1; i++) {
            String seq = initialState.substring(i, i + 2);
            pairMap.put(seq, pairMap.getOrDefault(seq, 0L) + 1L);
        }

        runSimulations();
        System.out.println(getMostAndLeastCountDiff());
    }

    private static void runSimulations() {
        for (int sim = 0; sim < numSimulations; sim++) {
            Map<String, Long> tmpMap = new HashMap<>();
            for (Map.Entry<String, Long> entry : pairMap.entrySet()) {
                String key = entry.getKey();
                if (rulesMap.containsKey(key)) {
                    long freq = entry.getValue();
                    String rulesValue = rulesMap.get(key);
                    String newPairOne = key.charAt(0) + rulesValue;
                    String newPairTwo = rulesValue + key.charAt(1);
                    tmpMap.put(newPairOne, tmpMap.getOrDefault(newPairOne, 0L) + freq);
                    tmpMap.put(newPairTwo, tmpMap.getOrDefault(newPairTwo, 0L) + freq);
                }
            }
            pairMap = tmpMap;
        }
    }

    private static long getMostAndLeastCountDiff() {
        Map<Character, Long> charCountMap = new HashMap<>();
        for (Map.Entry<String, Long> entry : pairMap.entrySet()) {
            long freq = entry.getValue();
            Character second = entry.getKey().charAt(1);
            charCountMap.put(second, charCountMap.getOrDefault(second, 0L) + freq);
        }

        List<Long> counts = new ArrayList<>(charCountMap.values());
        Collections.sort(counts);
        return((counts.get(counts.size() - 1) - counts.get(0)));
    }
}