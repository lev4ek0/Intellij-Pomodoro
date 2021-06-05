import groovy.transform.ToString;

import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.Collectors;

public class PomodoroStatistic {
    private static HashMap<Integer, String[]> hashMap = new HashMap<>();
    private static int counter;

    public static void setHashMap(HashMap<Integer, String[]> hashMap) {
        PomodoroStatistic.hashMap = hashMap;
    }

    public static String mapToString() {
        return hashMap.entrySet()
                .stream()
                .map(entry1 -> "      " + entry1.getKey() + ": " + Arrays
                        .stream(entry1.getValue())
                        .map(String::valueOf)
                        .collect(Collectors.joining(", ")))
                .collect(Collectors.joining(",\n","{\n","\n}"));
    }

    public static String getHashMap() {
        return mapToString();
    }

    public static void counterInc() {
        counter++;
    }
}
