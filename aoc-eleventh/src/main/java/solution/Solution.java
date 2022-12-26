package solution;

import utils.FileUtil;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Comparator;
import java.util.List;

public class Solution {

    public static final String INPUT = "input.txt";

    public static void main(String[] args) throws IOException {
        String data = FileUtil.loadData(INPUT);

        MonkeyService monkeyService = new MonkeyService();
        List<MonkeyService.Monkey> monkeys = monkeyService.getMonkeys(data);

        printSolutionForPartOne(monkeyService, monkeys);

        printSolutionForPartTwo(monkeyService, monkeys);
    }

    private static void printSolutionForPartTwo(MonkeyService monkeyService, List<MonkeyService.Monkey> monkeys) {
        List<MonkeyService.Monkey> monkeysAfterRounds = monkeyService.getMonkeysAfterRounds(monkeys, 10000, false);

        BigInteger level = getMonkeyBusinessLevel(monkeysAfterRounds);

        System.out.printf("The level of monkey business for PART TWO is %s !%n", level);
    }

    private static void printSolutionForPartOne(MonkeyService monkeyService, List<MonkeyService.Monkey> monkeys) {
        List<MonkeyService.Monkey> monkeysAfterRounds = monkeyService.getMonkeysAfterRounds(monkeys, 20, true);

        BigInteger level = getMonkeyBusinessLevel(monkeysAfterRounds);

        System.out.printf("The level of monkey business is %s !%n", level);
    }

    private static BigInteger getMonkeyBusinessLevel(List<MonkeyService.Monkey> monkeysAfterRounds) {
        List<BigInteger> sortedMonkeys = monkeysAfterRounds.stream()
                .sorted(Comparator.comparing(MonkeyService.Monkey::getInspectedItemCount).reversed())
                .map(MonkeyService.Monkey::getInspectedItemCount)
                .toList();

        return sortedMonkeys.get(0).multiply(sortedMonkeys.get(1));
    }
}