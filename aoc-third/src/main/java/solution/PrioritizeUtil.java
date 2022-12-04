package solution;

import java.math.BigDecimal;

public class PrioritizeUtil {

    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";

    public static BigDecimal determinePriority(String priorityString) {
        BigDecimal basePriority = new BigDecimal(ALPHABET.indexOf(priorityString.toLowerCase()) + 1);

        if (priorityString.toUpperCase().equals(priorityString)) {
            return basePriority.add(new BigDecimal(26));
        }
        return basePriority;
    }
}
