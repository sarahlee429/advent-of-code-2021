import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

public class Day10 {
    public static void main(String  [] args) throws IOException {
        List<String> lines = Files.lines(Path.of(args[0])).collect(Collectors.toList());
        List<Long> incompleteScores = new ArrayList<>();
        for (String line : lines) {
            long tmp = checkLine(line);
            if (tmp != 0) {
                incompleteScores.add(tmp);
            }
        }
        Collections.sort(incompleteScores);
        System.out.println("Median incomplete score : " + incompleteScores.get(incompleteScores.size() / 2));
    }

    public static long checkLine(String line) {
        Stack<Character> parens = new Stack<>();
        for (int i = 0; i < line.length(); i++) {
            Character c = line.charAt(i);
            if (c == '{' || c == '[' || c == '(' || c == '<') {
                parens.push(c);
            } else {
                Character topOpen = parens.pop();
                if (!matches(topOpen, c)) {
                    return 0;
                }
            }
        }
        long score = 0;
        if (!parens.isEmpty()) {
            while (!parens.isEmpty()) {
                Character open = parens.pop();
                Character matchingClose = getMatching(open);
                score = score * 5L + getScore(matchingClose);
            }
            return score;
        }
        return score;
    }

    public static Character getMatching(Character open) {
        if (open == '(') return ')';
        if (open == '[') return ']';
        if (open == '{') return '}';
        if (open == '<') return '>';
        return '0';
    }

    public static long getScore(Character c) {
        if (c == ')') return 1;
        if (c == ']') return 2;
        if (c == '}') return 3;
        if (c == '>') return 4;
        return 0;
    }

    public static boolean matches(Character c1, Character c2) {
        return ((c1 == '{' && c2 == '}') ||
                (c1 == '[' && c2 == ']') ||
                (c1 == '(' && c2 == ')') ||
                (c1 == '<' && c2 == '>'));
    }
}
