package solution;

import utils.FileUtil;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

public class Solution {

    private static final String INPUT = "input.txt";

    public static void main(String[] args) throws IOException {
        String data = FileUtil.loadData(INPUT);

        BigDecimal totalSumOfPriorities = data.lines()
                .map(Solution::determinePriorityString)
                .map(PrioritizeUtil::determinePriority)
                .reduce(BigDecimal::add)
                .orElseThrow();

        System.out.printf("Total sum is %s%n", totalSumOfPriorities);
    }

    private static String determinePriorityString(String rucksackString) {
        int itemCount = rucksackString.length();

        if (itemCount % 2 != 0) {
            throw new IllegalStateException("Rucksack has not an even amount of items!");
        }

        String firstHalf = rucksackString.substring(0, itemCount / 2);
        String secondHalf = rucksackString.substring(itemCount / 2, itemCount);

        List<String> duplicatedLetters = firstHalf.codePoints()
                .mapToObj(c -> String.valueOf((char) c))
                .map(letter -> secondHalf.contains(letter) ? letter : null)
                .filter(Objects::nonNull)
                .distinct()
                .toList();

        if (duplicatedLetters.size() != 1) {
            throw new IllegalStateException("No duplicated letter found!");
        }

        return duplicatedLetters.get(0);
    }
}
