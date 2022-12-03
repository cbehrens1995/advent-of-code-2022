package solution;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Solution {

    public static final String INPUT = "input.txt";

    public static void main(String[] args) throws IOException {
        String data = loadData();

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

    private static String loadData() throws IOException {
        ClassLoader classLoader = Solution.class.getClassLoader();
        File file = new File(classLoader.getResource(INPUT).getFile());
        return FileUtils.readFileToString(file, StandardCharsets.UTF_8);
    }
}
