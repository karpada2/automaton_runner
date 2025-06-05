import java.util.Arrays;

public class Connection {
    private State startState;
    private State endState;
    private String[] letters;

    public Connection(State startState, State endState, String... letters) {
        this.startState = startState;
        this.endState = endState;
        this.letters = letters;
    }

    public State getStartState() {
        return startState;
    }

    public State getEndState() {
        return endState;
    }

    public String[] getLetters() {
        return Arrays.copyOf(letters, letters.length);
    }

    public static String[][] breakConnectionsToLetters(Connection[] arr) {
        String[][] out = new String[arr.length][1];
        for (int i = 0; i < arr.length; i++) {
            out[i] = arr[i].getLetters();
        }
        return out;
    }

    public static State[][] breakConnectionsToStatePairs(Connection[] arr) {
        State[][] out = new State[arr.length][1];
        for (int i = 0; i < arr.length; i++) {
            out[i] = new State[]{arr[i].getStartState(), arr[i].getEndState()};
        }
        return out;
    }
}
