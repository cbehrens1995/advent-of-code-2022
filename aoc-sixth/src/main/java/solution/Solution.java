package solution;

import utils.FileUtil;

import java.io.IOException;

public class Solution {

    public static final String INPUT = "input.txt";

    public static void main(String[] args) throws IOException {
        String data = FileUtil.loadData(INPUT);

        int packageSizePartOne = 4;
        int startIndexForPartOne = determineStartIndex(data, packageSizePartOne);
        System.out.printf("The result for part one is %s%n", startIndexForPartOne + packageSizePartOne);

        int packageSizePartTwo = 14;
        int startIndexForPartTwo = determineStartIndex(data, packageSizePartTwo);
        System.out.printf("The result for part one is %s%n", startIndexForPartTwo + packageSizePartTwo);
    }

    private static int determineStartIndex(String data, int packageSize) {
        for (int i = 0; i < data.codePoints().count(); i++) {
            String sequence = data.substring(i, i + packageSize);
            long discountLetterCount = sequence.codePoints().distinct().count();

            if (discountLetterCount == packageSize) {
                return i;
            }
        }
        throw new IllegalStateException();
    }
}
