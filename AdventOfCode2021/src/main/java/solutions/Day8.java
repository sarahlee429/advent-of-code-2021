package main.java.solutions;

import java.io.IOException;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.nio.file.Files;
import java.nio.file.Path;

public class Day8
{
    private static Map<Integer, Integer> distinctSegmentCountMap = populateSegmentCountToOutputMap();

    private static Map<Integer, Integer> populateSegmentCountToOutputMap() {
        Map<Integer, Integer> segmentCount = new HashMap<>();
        segmentCount.put(2, 1);
        segmentCount.put(4, 4);
        segmentCount.put(3, 7);
        segmentCount.put(7, 8);
        return Collections.unmodifiableMap(segmentCount);
    }

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.lines(Path.of(args[0])).collect(Collectors.toList());
        List<Integer> outputNumbers = getOutputNumbers(lines);
        System.out.println(outputNumbers.stream().collect(Collectors.summingInt(Integer::intValue)));
    }

    private static String sortString(String s) {
        char[] tempArray = s.toCharArray();
        Arrays.sort(tempArray);
        return new String(tempArray);
    }

    private static List<Integer> getOutputNumbers(List<String> lines) {
        List<Integer> outputNumbers = new ArrayList<>();
        for (String line : lines) {
            outputNumbers.add(getOutputNumber(line));
        }
        return outputNumbers;
    }

    private static int getOutputNumber(String line) {
        String[] split = line.split("\\|");
        String[] outputPatterns = split[1].trim().split(" ");
        String[] inputPatterns = split[0].trim().split(" ");

        Map<Integer, List<String>> inputSequenceCharCountMap = new HashMap<>();
        Map<String, Integer> inputSequenceToOutputNumberMap = new HashMap<>();
        Arrays.stream(inputPatterns).forEach(s -> inputSequenceCharCountMap.computeIfAbsent(s.length(), k -> new ArrayList<>()).add(s));

        for (String five : inputSequenceCharCountMap.get(5)) {
            five = sortString(five);
            Set<Character> fiveSet = five.chars().mapToObj(e -> Character.valueOf((char)e)).collect(Collectors.toSet());
            Set<Character> twoSet = inputSequenceCharCountMap.get(2).get(0).chars().mapToObj(e -> Character.valueOf((char)e)).collect(Collectors.toSet());
            int diff = findSetDifference(fiveSet, twoSet).size();
            if (diff == 4) {
                Set<Character> fourSet = inputSequenceCharCountMap.get(4).get(0).chars().mapToObj(e -> Character.valueOf((char) e)).collect(Collectors.toSet());
                int diffFour = findSetDifference(fiveSet, fourSet).size();
                if (diffFour == 3) {
                    inputSequenceToOutputNumberMap.put(five, 2);
                } else {
                    inputSequenceToOutputNumberMap.put(five, 5);
                }
            } else {
                inputSequenceToOutputNumberMap.put(five, 3);
            }
        }

        for (String six : inputSequenceCharCountMap.get(6)) {
            six = sortString(six);
            Set<Character> sixSet = six.chars().mapToObj(e -> Character.valueOf((char)e)).collect(Collectors.toSet());
            Set<Character> threeSet = inputSequenceCharCountMap.get(3).get(0).chars().mapToObj(e -> Character.valueOf((char)e)).collect(Collectors.toSet());
            int diff = findSetDifference(sixSet, threeSet).size();
            if (diff == 3) {
                Set<Character> fourSet = inputSequenceCharCountMap.get(4).get(0).chars().mapToObj(e -> Character.valueOf((char) e)).collect(Collectors.toSet());
                int diffFour = findSetDifference(sixSet, fourSet).size();
                if (diffFour == 2) {
                    inputSequenceToOutputNumberMap.put(six, 9);
                } else {
                    inputSequenceToOutputNumberMap.put(six, 0);
                }
            } else {
                inputSequenceToOutputNumberMap.put(six, 6);
            }
        }

        StringBuilder outputNumber = new StringBuilder();
        for (String output : outputPatterns) {
            output = sortString(output);
            if (inputSequenceToOutputNumberMap.containsKey(output)) {
                outputNumber.append(inputSequenceToOutputNumberMap.get(output));
            } else {
                outputNumber.append(distinctSegmentCountMap.get(output.length()).toString());
            }
        }
        return Integer.parseInt(outputNumber.toString());
    }

    private static Set<Character> findSetDifference(Set<Character> bigger, Set<Character> smaller) {
        Set<Character> tmp = new HashSet<>(bigger);
        tmp.removeAll(smaller);
        return tmp;
    }
}