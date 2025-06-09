import java.util.*;

public class Testing {
    public static void main(String[] args) {
        DeterministicAutomaton moreAThanB = moreAThanB();

        System.out.println(moreAThanB.checkWord(toStringArray("aba")));
        System.out.println(moreAThanB.checkWord(toStringArray("a")));
        System.out.println(moreAThanB.checkWord(toStringArray("aaa")));
        System.out.println(moreAThanB.checkWord(toStringArray("abb")));
        System.out.println(moreAThanB.checkWord(toStringArray("abba")));
        System.out.println(moreAThanB.checkWord(toStringArray("abaaba")));
    }

    public static DeterministicStackAutomaton moreAThanB() {
        Set<String> alphabet = new HashSet<>(Arrays.asList(new String[]{"a", "b"}));
        State startState = new State(false);
        State[] states = {
                startState,
                new State(true),
                new State(false)
        };

        String[] pushA = {"A"};
        String[] pushB = {"B"};

        String emptyStack = DeterministicStackAutomaton.StackInput.EMPTY_STACK;

        DeterministicStackAutomaton.StackConnection[] connections = {
                new DeterministicStackAutomaton.StackConnection(
                        states[0],
                        states[1],
                        "a",
                        emptyStack,
                        pushA,
                        false
                ),


                new DeterministicStackAutomaton.StackConnection(
                        states[1],
                        states[1],
                        "a",
                        "A",
                        pushA,
                        false
                ),
                new DeterministicStackAutomaton.StackConnection(
                        states[1],
                        states[1],
                        "a",
                        emptyStack,
                        pushA,
                        false
                ),
                new DeterministicStackAutomaton.StackConnection(
                        states[1],
                        states[1],
                        "b",
                        "A",
                        pushA,
                        true
                ),


                new DeterministicStackAutomaton.StackConnection(
                        states[1],
                        states[2],
                        "b",
                        emptyStack,
                        pushB,
                        false
                ),


                new DeterministicStackAutomaton.StackConnection(
                        states[2],
                        states[2],
                        "b",
                        "B",
                        pushB,
                        false
                ),
                new DeterministicStackAutomaton.StackConnection(
                        states[2],
                        states[2],
                        "b",
                        emptyStack,
                        pushB,
                        false
                ),
                new DeterministicStackAutomaton.StackConnection(
                        states[2],
                        states[2],
                        "a",
                        "B",
                        pushA,
                        true
                ),


                new DeterministicStackAutomaton.StackConnection(
                        states[2],
                        states[1],
                        "a",
                        emptyStack,
                        pushA,
                        false
                ),
        };

        return new DeterministicStackAutomaton(
                alphabet,
                new HashSet<>(Arrays.asList(states)),
                startState,
                connections
        );
    }

    public static DeterministicRegularAutomaton emailChecker() {
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

        return new DeterministicRegularAutomaton(
                alphabet,
                new HashSet<>(Arrays.asList(states)),
                startState,
                connections
        );
    }

    public static String[] toStringArray(String word) {
        String[] out = new String[word.length()];
        for (int i = 0; i < word.length(); i++) {
            out[i] = word.substring(i, i+1);
        }
        return out;
    }
}
