import java.util.Set;

public abstract class DeterministicAutomaton extends Automaton {

    public DeterministicAutomaton(Set<String> alphabet) {
        super(alphabet);
    }

    public abstract State getFinishingState(String[] word);

    public abstract State[] getRunOrder(String[] word);
}
