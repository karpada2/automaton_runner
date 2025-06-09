import java.util.Arrays;
import java.util.Set;

public abstract class DeterministicAutomaton extends Automaton {

    public DeterministicAutomaton(Set<String> alphabet) {
        super(alphabet);
    }

    @Override
    public boolean checkWord(String[] word) {
        State[] runOrder = getRunOrder(word);
        return runOrder.length - 1 == word.length &&
                !Arrays.asList(runOrder).contains(null) &&
                runOrder[runOrder.length-1].isAccepting();
    }

    public State getFinishingState(String[] word) {
        State[] runOrder = getRunOrder(word);
        for (int i = 1; i < runOrder.length-1; i++) {
            if (runOrder[i] == null) {
                return runOrder[i+1];
            }
        }
        return null;
    }

    public abstract State[] getRunOrder(String[] word);
}
