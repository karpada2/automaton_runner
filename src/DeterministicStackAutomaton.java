import java.util.*;

public class DeterministicStackAutomaton extends DeterministicAutomaton {
    public static class StackConnection {
        private State startState;
        private StackInput input;
        private StackTransition transition;

        public StackConnection(State startState, State endState, String wordLetter, String stackLetter, String[] lettersToPush, boolean isPop) {
            this(startState, new StackInput(wordLetter, stackLetter), new StackTransition(endState, isPop, lettersToPush));
        }

        public StackConnection(State startState, StackInput input, StackTransition transition) {
            this.startState = startState;
            this.input = input;
            this.transition = transition;
        }

        public State getStartState() {
            return startState;
        }

        public State getEndState() {
            return transition.getEndState();
        }

        public String getWordLetter() {
            return input.getWordLetter();
        }

        public String getStackLetter() {
            return input.getStackLetter();
        }

        public StackInput getInput() {
            return input;
        }

        public StackTransition getTransition() {
            return transition;
        }
    }

    // what state to go to and what to do to the stack
    public static class StackTransition {
        private State endState;
        private boolean isPop;
        private String[] lettersToPush;

        public StackTransition(State endState, boolean isPop, String[] lettersToPush) {
            this.endState = endState;
            this.isPop = isPop;
            this.lettersToPush = lettersToPush;
        }

        public State getEndState() {
            return endState;
        }

        public boolean isPop() {
            return isPop;
        }

        public String[] getLettersToPush() {
            return Arrays.copyOf(lettersToPush, lettersToPush.length);
        }

        public void actOnStack(Stack<String> stack) {
            if (isPop()) {
                stack.pop();
            }
            else {
                String[] lettersToPush = getLettersToPush();
                for (int i = 0; i < lettersToPush.length; i++) {
                    stack.push(lettersToPush[i]);
                }
            }
        }
    }

    // what is in the stack and the word
    public static class StackInput {
        public static final String EMPTY_STACK = "⟂";
        private String wordLetter;
        private String stackLetter;

        public StackInput(String wordLetter, String stackLetter) {
            this.wordLetter = wordLetter;
            this.stackLetter = stackLetter;
        }

        public String getWordLetter() {
            return wordLetter;
        }

        public String getStackLetter() {
            return stackLetter;
        }

        public boolean isEqual(String wordLetter, String stackLetter) {
            return this.wordLetter.equals(wordLetter) && this.stackLetter.equals(stackLetter);
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof StackInput) {
                return isEqual(((StackInput) obj).getWordLetter(), ((StackInput) obj).getStackLetter());
            }
            return false;
        }
    }



    private State startState;
    /**
     * such that {@code transitions.get(startState).get(wordLetter).get(stackLetter)} is the relevant transition
     */
    private Map<State, Map<String, Map<String, StackTransition>>> transitions;

    public DeterministicStackAutomaton(Set<String> alphabet, Set<State> states, State startState, StackConnection[] connections) {
        super(alphabet);

        if (!states.contains(startState)) {
            throw new IllegalArgumentException("startState does not appear in states!");
        }

        this.startState = startState;

        State[] givenStates = states.toArray(new State[]{});
        transitions = new HashMap<>(givenStates.length);
        for (int i = 0; i < givenStates.length; i++) {
            transitions.put(givenStates[i], new HashMap<>(alphabet.size()));
        }

        for (int i = 0; i < connections.length; i++) {
            if (!transitions.get(connections[i].getStartState()).containsKey(connections[i].getInput().getWordLetter())) {
                transitions.get(connections[i].getStartState()).put(connections[i].getInput().getWordLetter(), new HashMap<>());
            }
            transitions.get(connections[i].getStartState()).get(connections[i].getInput().getWordLetter()).put(connections[i].getInput().getStackLetter(), connections[i].getTransition());
        }
    }

    @Override
    public State[] getRunOrder(String[] word) {
        if (!isValidWord(word)) {
            throw new IllegalArgumentException("Word is not in the alphabet of this automaton!");
        }
        
        State[] runOrder = new State[word.length+1];
        runOrder[0] = startState;
        
        Stack<String> stack = new Stack<>();

        for (int i = 0; i < word.length; i++) {
            StackInput tempInput = new StackInput(word[i], (!stack.isEmpty() ? stack.peek() : StackInput.EMPTY_STACK));
            if (transitions.get(runOrder[i]).get(tempInput.getWordLetter()).get(tempInput.getStackLetter()) != null) {
                runOrder[i+1] = transitions.get(runOrder[i]).get(tempInput.getWordLetter()).get(tempInput.getStackLetter()).getEndState();
                transitions.get(runOrder[i]).get(tempInput.getWordLetter()).get(tempInput.getStackLetter()).actOnStack(stack);
            }
            else {
                break;
            }
        }

        return runOrder;
    }
}
