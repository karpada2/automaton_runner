import java.util.Set;

public abstract class Automaton {
    private Set<String> alphabet;
    public Automaton(Set<String> alphabet) {
        this.alphabet = alphabet;
    }

    public boolean isInAlphabet(String letter) {
        return alphabet.contains(letter);
    }

    public Set<String> getAlphabet() {
        return Set.copyOf(alphabet);
    }

    // checks if all letters in the word appear in the alphabet
    public boolean isValidWord(String[] word) {
        for (int i = 0; i < word.length; i++) {
            if (!isInAlphabet(word[i])) {
                return false;
            }
        }
        return true;
    }

    public abstract boolean checkWord(String[] word);
}
