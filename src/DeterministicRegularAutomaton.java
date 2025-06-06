import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class DeterministicRegularAutomaton extends DeterministicAutomaton {
    private State startState;
    // the starting state

    private Map<State, Map<String, State>> transitions;
    // each state is a key for a map that describes it's connections,
    // such that for a letter P and state S, connections.get(S).get(P) is the state that's connected by P

    /**
     * Constructs a new DeterministicRegularAutomaton based on the given inputs:
     * {@code alphabet} - The alphabet of this automaton<p>
     * {@code states} - The states that this automaton has<p>
     * {@code startState} - The starting state of this automaton<p>
     * {@code connects} - An array of the form [<br>[q0, q1], <br>[q0, q0], <br>[q0, q2], <br>[q2, q1], <br>[q1, q0]<br>]<p>
     * {@code lettersOfConnects} - An array of an array of all letters that are in the alphabet and describe connects<p>
     * {@code connects} and {@code lettersOfConnects} are such that {@code connects[i][0]} connects to <br>{@code connects[i][1]} via all elements in {@code lettersOfConnects[i]}
     */
    public DeterministicRegularAutomaton(Set<String> alphabet, Set<State> states, State startState, State[][] connects, String[][] lettersOfConnects) {
        super(alphabet);

        if (!states.contains(startState)) {
            throw new IllegalArgumentException("startState does not appear in states!");
        }
        if (lettersOfConnects.length != connects.length) {
            throw new IllegalArgumentException("lettersOfConnects and connects must be the same length!");
        }

        this.startState = startState;

        State[] givenStates = states.toArray(new State[]{});
        transitions = new HashMap<>(givenStates.length);
        for (int i = 0; i < givenStates.length; i++) {
            transitions.put(givenStates[i], new HashMap<>(alphabet.size()));
        }

        for (int i = 0; i < connects.length; i++) {
            for (int j = 0; j < lettersOfConnects[i].length; j++) {
                transitions.get(connects[i][0]).put(lettersOfConnects[i][j], connects[i][1]);
            }
        }
    }

    public DeterministicRegularAutomaton(Set<String> alphabet, Set<State> states, State startState, Connection[] connects) {
        this(alphabet, states, startState, Connection.breakConnectionsToStatePairs(connects), Connection.breakConnectionsToLetters(connects));
    }

    @Override
    public boolean checkWord(String[] word) {
        State[] runOrder = getRunOrder(word);
        return runOrder.length - 1 == word.length &&
                    !Arrays.asList(runOrder).contains(null) &&
                    runOrder[runOrder.length-1].isAccepting();
    }

    @Override
    public State getFinishingState(String[] word) {
        State[] runOrder = getRunOrder(word);
        for (int i = 1; i < runOrder.length-1; i++) {
            if (runOrder[i] == null) {
                return runOrder[i+1];
            }
        }
        return null;
    }

    @Override
    public State[] getRunOrder(String[] word) {
        if (!isValidWord(word)) {
            throw new IllegalArgumentException("Word is not in the alphabet of this automaton!");
        }

        // since runOrder contains the startState, we have to increase its size by 1
        State[] runOrder = new State[word.length+1];

        runOrder[0] = startState;

        for (int i = 0; i < word.length; i++) {
            if (transitions.get(runOrder[i]).get(word[i]) != null) {
                runOrder[i+1] = transitions.get(runOrder[i]).get(word[i]);
            }
            else {
                break;
            }
        }

        return runOrder;
    }
}
