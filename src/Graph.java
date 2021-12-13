import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

//Utility class for handling directed graph
public class Graph {
    public static Map<String, List<String>> adjList = new HashMap<>();
    public static void addAdjacency(String source, String dest) {
        adjList.computeIfAbsent(source, k -> new ArrayList<>()).add(dest);
    }

    public static Set<List<String>> getPaths(String source, String dest) {
        Set<List<String>> finalSolution = new HashSet<>();
        for (String s : adjList.keySet()) {
            if (!s.equals(source) && !s.equals(dest) && !isBigCave(s)) {
                getPathsHelper(source, dest, s, new ArrayList<>(), finalSolution);
            }
        }
        return finalSolution;
    }

    public static void getPathsHelper(String current, String dest, String doubleSmallCave, List<String> currentPath, Set<List<String>> allPaths) {
        if (current.equals(dest)) {
            currentPath.add(current);
            allPaths.add(currentPath);
            return;
        }
        if (isBigCave(current) || !currentPath.contains(current) || (current.equals(doubleSmallCave) && Collections.frequency(currentPath, current) < 2)) {
            if (adjList.containsKey(current)) {
                for (String adj : adjList.get(current)) {
                    List<String> tmp = new ArrayList<>(currentPath);
                    tmp.add(current);
                    getPathsHelper(adj, dest, doubleSmallCave, tmp, allPaths);
                }
            }
        }
    }

    public static boolean isBigCave(String node) {
        return Character.isUpperCase(node.charAt(0));
    }
}
