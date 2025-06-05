import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class State {
    private boolean isAccepting;
    private String name;

    private static String[] defaultNames = {"q", "p"};
    private static Map<String, Integer> namingScheme = new HashMap<>();
    private static List<String> namingSchemeKeys;


    public State(boolean isAccepting) {
        this(isAccepting, 0);
    }

    public State(boolean isAccepting, int index) {
        if (namingScheme == null || namingScheme.isEmpty()) {
            initializeNamingScheme();
        }


        StringBuilder builder = new StringBuilder(namingSchemeKeys.get(index));
        builder.append(namingScheme.get(namingSchemeKeys.get(index)));

        namingScheme.replace(namingSchemeKeys.get(index), namingScheme.get(namingSchemeKeys.get(index))+1);

        this.isAccepting = isAccepting;
        this.name = builder.toString();
    }

    private State(boolean isAccepting, String name) {
        this.isAccepting = isAccepting;
        this.name = name;
    }

    private static void initializeNamingScheme(String[] names) {
        namingScheme = new HashMap<>();
        namingSchemeKeys = new ArrayList<>();

        for (int i = 0; i < names.length; i++) {
            namingSchemeKeys.add(names[i]);
            namingScheme.put(names[i], 0);
        }
    }

    private static void initializeNamingScheme() {
        initializeNamingScheme(defaultNames);
    }

    public String getName() {
        return name;
    }

    public boolean isAccepting() {
        return isAccepting;
    }


    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(isAccepting() ? "|" : "");
        builder.append(name);
        if (isAccepting()) {
            builder.append("|");
        }
        return builder.toString();
    }
}
