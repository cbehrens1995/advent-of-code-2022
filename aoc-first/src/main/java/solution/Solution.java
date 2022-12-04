package solution;

import utils.FileUtil;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Solution {

    public static final String INPUT = "input.txt";

    public static void main(String[] args) throws IOException {
        String data = FileUtil.loadData(INPUT);

        HashMap<Integer, BigDecimal> totalCaloriesByElf = getTotalCaloriesByElf(data);

        BigDecimal maxCalories = totalCaloriesByElf.values().stream()
                .max(Comparator.naturalOrder())
                .orElseThrow();

        System.out.printf("The maximum of carried calories are %s%n", maxCalories);

        BigDecimal totalCaloriesOfTopThree = totalCaloriesByElf.values().stream()
                .sorted(Comparator.reverseOrder())
                .limit(3)
                .reduce(BigDecimal::add)
                .orElseThrow();

        System.out.printf("The total carried calories of the top three are %s%n", totalCaloriesOfTopThree);
    }

    private static HashMap<Integer, BigDecimal> getTotalCaloriesByElf(String data) {
        List<String> rows = data.lines().toList();
        HashMap<Integer, BigDecimal> totalCaloriesByElf = new HashMap<>();

        AtomicInteger elfCount = new AtomicInteger(1);
        for (String currentCalorie : rows) {
            if (currentCalorie.isBlank()) {
                elfCount.incrementAndGet();
                continue;
            }
            BigDecimal calorie = new BigDecimal(currentCalorie);

            totalCaloriesByElf.merge(elfCount.get(), calorie, BigDecimal::add);
        }
        return totalCaloriesByElf;
    }
}
