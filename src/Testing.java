import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Testing {
    public static void main(String[] args) {
        Set<String> alphabet = new HashSet<>(Arrays.asList(new String[]{"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "@", "."}));
        String[] letters = new String[]{"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
        State startState = new State(false);
        State[] states = {
                startState,
                new State(false),
                new State(false),
                new State(false),
                new State(true),
                new State(false)
        };
        Connection[] connections = {
                new Connection(
                        states[0],
                        states[1],
                        letters
                ),
                new Connection(
                        states[1],
                        states[1],
                        letters
                ),
                new Connection(
                        states[1],
                        states[2],
                        new String[]{"."}
                ),
                new Connection(
                        states[2],
                        states[1],
                        letters
                ),
                new Connection(
                        states[1],
                        states[3],
                        new String[]{"@"}
                ),
                new Connection(
                        states[3],
                        states[4],
                        letters
                ),
                new Connection(
                        states[4],
                        states[4],
                        letters
                ),
                new Connection(
                        states[4],
                        states[5],
                        new String[]{"."}
                ),
                new Connection(
                        states[5],
                        states[4],
                        letters
                ),
        };

        DeterministicRegularAutomaton emailChecker = new DeterministicRegularAutomaton(
                alphabet,
                new HashSet<>(Arrays.asList(states)),
                startState,
                connections
        );

        System.out.println(emailChecker.checkWord(toStringArray("gongrossman@gmail.com")));
        System.out.println(emailChecker.checkWord(toStringArray("g@g.g")));
        System.out.println(emailChecker.checkWord(toStringArray("gongrossman@@gmail.com")));
        System.out.println(emailChecker.checkWord(toStringArray("gon..grossman@gmail.com")));
        System.out.println(emailChecker.checkWord(toStringArray("gongrossman@gmail..com")));
    }

    public static String[] toStringArray(String word) {
        String[] out = new String[word.length()];
        for (int i = 0; i < word.length(); i++) {
            out[i] = word.substring(i, i+1);
        }
        return out;
    }
}
