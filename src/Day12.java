import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Day12 {
    public static void main(String [] args) throws IOException {
        List<String> lines = Files.lines(Path.of(args[0])).collect(Collectors.toList());
        for (String line : lines) {
            String [] graphEdge = Arrays.stream(line.split("-")).map(String::trim).toArray(String[]::new);
            Graph.addAdjacency(graphEdge[0], graphEdge[1]);
            Graph.addAdjacency(graphEdge[1], graphEdge[0]);
        }
        Set<List<String>> myPaths = Graph.getPaths("start", "end");
        System.out.println(myPaths.size());
    }
}
