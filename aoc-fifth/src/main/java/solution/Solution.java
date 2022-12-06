package solution;

import org.apache.commons.lang3.StringUtils;
import utils.FileUtil;

import java.io.IOException;
import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

public class Solution {

    public static final String MOVES = "moves.txt";

    public static void main(String[] args) throws IOException {
        Map<BigInteger, List<String>> stringsPerStack = new HashMap<>();
        stringsPerStack.put(BigInteger.valueOf(1), new ArrayList<>(List.of("B", "S", "V", "Z", "G", "P", "W")));
        stringsPerStack.put(BigInteger.valueOf(2), new ArrayList<>(List.of("J", "V", "B", "C", "Z", "F")));
        stringsPerStack.put(BigInteger.valueOf(3), new ArrayList<>(List.of("V", "L", "M", "H", "N", "Z", "D", "C")));
        stringsPerStack.put(BigInteger.valueOf(4), new ArrayList<>(List.of("L", "D", "M", "Z", "P", "F", "J", "B")));
        stringsPerStack.put(BigInteger.valueOf(5), new ArrayList<>(List.of("V", "F", "C", "G", "J", "B", "Q", "H")));
        stringsPerStack.put(BigInteger.valueOf(6), new ArrayList<>(List.of("G", "F", "Q", "T", "S", "L", "B")));
        stringsPerStack.put(BigInteger.valueOf(7), new ArrayList<>(List.of("L", "G", "C", "Z", "V")));
        stringsPerStack.put(BigInteger.valueOf(8), new ArrayList<>(List.of("N", "L", "G")));
        stringsPerStack.put(BigInteger.valueOf(9), new ArrayList<>(List.of("J", "F", "H", "C")));

        String movesData = FileUtil.loadData(MOVES);


        //solvePartOne(stringsPerStack, movesData);
        solvePartTwo(stringsPerStack, movesData);
    }

    private static void solvePartOne(Map<BigInteger, List<String>> stringsPerStack, String movesData) {
        movesData.lines().forEach(row -> processRowReversingOrder(stringsPerStack, row));

        String finalResult = stringsPerStack.values().stream()
                .map(stack -> stack.get(stack.size() - 1))
                .collect(Collectors.joining());

        System.out.printf("The result for part one is %s%n", finalResult);
    }

    private static void solvePartTwo(Map<BigInteger, List<String>> stringsPerStack, String movesData) {
        movesData.lines().forEach(row -> processRowPreservingOrder(stringsPerStack, row));

        String finalResult = stringsPerStack.values().stream()
                .map(stack -> stack.get(stack.size() - 1))
                .collect(Collectors.joining());

        System.out.printf("The result for part two is %s%n", finalResult);
    }

    private static void processRowReversingOrder(Map<BigInteger, List<String>> stringsPerStack, String row) {
        List<String> rowParts = Arrays.stream(StringUtils.split(row)).toList();
        if (rowParts.size() != 6) {
            throw new IllegalStateException();
        }

        BigInteger itemCountToProcess = new BigInteger(rowParts.get(1));
        BigInteger startingStackNumber = new BigInteger(rowParts.get(3));
        BigInteger endingStackNumber = new BigInteger(rowParts.get(5));

        for (int i = 0; i < itemCountToProcess.intValue(); i++) {
            List<String> startingStack = stringsPerStack.get(startingStackNumber);
            String itemToMove = startingStack.get(startingStack.size() - 1);

            startingStack.remove(startingStack.size() - 1);
            stringsPerStack.replace(startingStackNumber, startingStack);

            List<String> endingStack = stringsPerStack.get(endingStackNumber);
            endingStack.add(itemToMove);

            stringsPerStack.replace(endingStackNumber, endingStack);
        }
    }

    private static void processRowPreservingOrder(Map<BigInteger, List<String>> stringsPerStack, String row) {
        List<String> rowParts = Arrays.stream(StringUtils.split(row)).toList();
        if (rowParts.size() != 6) {
            throw new IllegalStateException();
        }

        BigInteger itemCountToProcess = new BigInteger(rowParts.get(1));
        BigInteger startingStackNumber = new BigInteger(rowParts.get(3));
        BigInteger endingStackNumber = new BigInteger(rowParts.get(5));

        List<String> tempStack = new ArrayList<>();

        for (int i = 0; i < itemCountToProcess.intValue(); i++) {
            List<String> startingStack = stringsPerStack.get(startingStackNumber);
            String itemToMove = startingStack.get(startingStack.size() - 1);

            startingStack.remove(startingStack.size() - 1);
            stringsPerStack.replace(startingStackNumber, startingStack);

            tempStack.add(itemToMove);
        }
        Collections.reverse(tempStack);

        List<String> endingStack = stringsPerStack.get(endingStackNumber);
        endingStack.addAll(tempStack);

        stringsPerStack.replace(endingStackNumber, endingStack);
    }
}
