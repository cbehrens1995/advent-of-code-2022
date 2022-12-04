package solution;

import org.apache.commons.collections4.ListUtils;
import utils.FileUtil;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

public class SolutionPartTwo {

    private static final String INPUT = "input.txt";

    public static void main(String[] args) throws IOException {
        String data = FileUtil.loadData(INPUT);

        List<String> ruckSacks = data.lines().toList();
        List<List<String>> rucksackGroups = ListUtils.partition(ruckSacks, 3);
        BigDecimal totalSumOfPriorities = rucksackGroups.stream()
                .map(SolutionPartTwo::determinePriorityString)
                .map(PrioritizeUtil::determinePriority)
                .reduce(BigDecimal::add)
                .orElseThrow();

        System.out.printf("Total sum is %s%n", totalSumOfPriorities);
    }

    private static String determinePriorityString(List<String> strings) {
        String firstRucksack = strings.get(0);
        String secondRucksack = strings.get(1);
        String thirdRucksack = strings.get(2);

        List<String> duplicatedLetters = firstRucksack.codePoints()
                .mapToObj(c -> String.valueOf((char) c))
                .map(letter -> secondRucksack.contains(letter) ? letter : null)
                .filter(Objects::nonNull)
                .distinct()
                .map(letter -> thirdRucksack.contains(letter) ? letter : null)
                .filter(Objects::nonNull)
                .distinct()
                .toList();

        if (duplicatedLetters.size() != 1) {
            throw new IllegalStateException("No duplicated letter found!");
        }

        return duplicatedLetters.get(0);
    }
}
