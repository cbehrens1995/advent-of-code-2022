package solution;

import org.apache.commons.lang3.tuple.Pair;

import java.math.BigInteger;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class TreeVisibilityService {

    public BigInteger countVisibleTrees(String data) {
        List<String> columns = data.lines().toList();

        Map<BigInteger, List<Pair<BigInteger, BigInteger>>> entriesByColumn = getEntriesByColumn(columns);

        AtomicInteger count = new AtomicInteger();
        for (Map.Entry<BigInteger, List<Pair<BigInteger, BigInteger>>> entry : entriesByColumn.entrySet()) {
            BigInteger rowIndex = entry.getKey();
            List<Pair<BigInteger, BigInteger>> treesByIndex = entry.getValue();

            for (Pair<BigInteger, BigInteger> treeByIndex : treesByIndex) {
                BigInteger indexOfTree = treeByIndex.getLeft();
                BigInteger tree = treeByIndex.getRight();

                boolean isVisible = isTreeVisible(tree, indexOfTree, rowIndex, entriesByColumn, BigInteger.valueOf(treesByIndex.size()));

                if (isVisible) {
                    count.incrementAndGet();
                }
            }
        }

        return BigInteger.valueOf(count.get());
    }

    public BigInteger determineHighestScenicScore(String data) {
        List<String> columns = data.lines().toList();

        List<BigInteger> scores = new ArrayList<>();
        Map<BigInteger, List<Pair<BigInteger, BigInteger>>> entriesByColumn = getEntriesByColumn(columns);

        for (Map.Entry<BigInteger, List<Pair<BigInteger, BigInteger>>> entry : entriesByColumn.entrySet()) {
            BigInteger rowIndex = entry.getKey();
            List<Pair<BigInteger, BigInteger>> treesByIndex = entry.getValue();

            for (Pair<BigInteger, BigInteger> treeByIndex : treesByIndex) {
                BigInteger indexOfTree = treeByIndex.getLeft();
                BigInteger tree = treeByIndex.getRight();

                boolean isVisible = isTreeVisible(tree, indexOfTree, rowIndex, entriesByColumn, BigInteger.valueOf(treesByIndex.size()));

                if (isVisible) {
                    BigInteger score = determineScenicScore(tree, indexOfTree, rowIndex, entriesByColumn, BigInteger.valueOf(treesByIndex.size()));
                    scores.add(score);
                }
            }
        }

        return scores.stream()
                .max(Comparator.naturalOrder())
                .orElseThrow();
    }

    private BigInteger determineScenicScore(BigInteger currentTreeHeight, BigInteger indexOfTree, BigInteger rowIndex, Map<BigInteger, List<Pair<BigInteger, BigInteger>>> entriesByColumn, BigInteger valueOf) {
        List<Pair<BigInteger, BigInteger>> currentRow = entriesByColumn.get(rowIndex);

        BigInteger leftScore = getLeftScore(currentTreeHeight, indexOfTree, currentRow);
        BigInteger rightScore = getRightScore(currentTreeHeight, indexOfTree, currentRow);

        BigInteger aboveScore = getAboveScore(currentTreeHeight, indexOfTree, rowIndex, entriesByColumn);
        BigInteger underScore = getUnderScore(currentTreeHeight, indexOfTree, rowIndex, entriesByColumn);

        return leftScore.multiply(rightScore).multiply(aboveScore).multiply(underScore);
    }

    private BigInteger getAboveScore(BigInteger currentTreeHeight, BigInteger indexOfTree, BigInteger rowIndex, Map<BigInteger, List<Pair<BigInteger, BigInteger>>> entriesByColumn) {
        List<BigInteger> treesAbove = entriesByColumn.entrySet().stream()
                .filter(entry -> entry.getKey().compareTo(rowIndex) < 0)
                .map(Map.Entry::getValue)
                .flatMap(Collection::stream)
                .filter(pair -> pair.getLeft().equals(indexOfTree))
                .map(Pair::getRight)
                .toList();


        return countTrees(currentTreeHeight, treesAbove, true);
    }

    private BigInteger getUnderScore(BigInteger currentTreeHeight, BigInteger indexOfTree, BigInteger rowIndex, Map<BigInteger, List<Pair<BigInteger, BigInteger>>> entriesByColumn) {
        List<BigInteger> treesUnder = entriesByColumn.entrySet().stream()
                .filter(entry -> entry.getKey().compareTo(rowIndex) > 0)
                .map(Map.Entry::getValue)
                .flatMap(Collection::stream)
                .filter(pair -> pair.getLeft().equals(indexOfTree))
                .map(Pair::getRight)
                .toList();

        return countTrees(currentTreeHeight, treesUnder, false);
    }

    private BigInteger getRightScore(BigInteger currentTreeHeight, BigInteger indexOfTree, List<Pair<BigInteger, BigInteger>> currentRow) {
        List<BigInteger> treesRight = currentRow.stream()
                .filter(treeByIndex -> treeByIndex.getLeft().compareTo(indexOfTree) > 0)
                .map(Pair::getRight)
                .toList();

        return countTrees(currentTreeHeight, treesRight, false);
    }

    private BigInteger getLeftScore(BigInteger currentTreeHeight, BigInteger indexOfTree, List<Pair<BigInteger, BigInteger>> currentRow) {
        List<BigInteger> treesLeft = currentRow.stream()
                .filter(treeByIndex -> treeByIndex.getLeft().compareTo(indexOfTree) < 0)
                .map(Pair::getRight)
                .toList();

        return countTrees(currentTreeHeight, treesLeft, true);
    }

    private BigInteger countTrees(BigInteger currentTreeHeight, List<BigInteger> trees, boolean isReverseOrder) {
        if (trees.isEmpty()) {
            return BigInteger.ZERO;
        }

        AtomicInteger counter = new AtomicInteger(0);
        if (isReverseOrder) {
            for (int i = trees.size() - 1; i >= 0; i--) {
                counter.incrementAndGet();
                BigInteger height = trees.get(i);
                if (height.compareTo(currentTreeHeight) >= 0) {
                    break;
                }
            }
        } else {
            for (BigInteger height : trees) {
                counter.incrementAndGet();
                if (height.compareTo(currentTreeHeight) >= 0) {
                    break;
                }
            }
        }
        return BigInteger.valueOf(counter.get());
    }

    private boolean isTreeVisible(BigInteger treeHeight, BigInteger indexOfTree, BigInteger rowIndex, Map<BigInteger, List<Pair<BigInteger, BigInteger>>> entriesByColumn, BigInteger columnLength) {
        if (indexOfTree.equals(BigInteger.ZERO)) {
            return true;
        }

        if (indexOfTree.add(BigInteger.ONE).equals(columnLength)) {
            return true;
        }

        if (rowIndex.equals(BigInteger.ZERO)) {
            return true;
        }

        if (rowIndex.add(BigInteger.ONE).equals(columnLength)) {
            return true;
        }

        List<Pair<BigInteger, BigInteger>> currentRow = entriesByColumn.get(rowIndex);

        boolean treeLeft = currentRow.stream()
                .filter(treeByIndex -> treeByIndex.getLeft().compareTo(indexOfTree) < 0)
                .map(Pair::getRight)
                .allMatch(height -> height.compareTo(treeHeight) < 0);

        boolean treeRight = currentRow.stream()
                .filter(treeByIndex -> treeByIndex.getLeft().compareTo(indexOfTree) > 0)
                .map(Pair::getRight)
                .allMatch(height -> height.compareTo(treeHeight) < 0);

        boolean treesAbove = entriesByColumn.entrySet().stream()
                .filter(rowEntries -> rowEntries.getKey().compareTo(rowIndex) < 0)
                .map(Map.Entry::getValue)
                .flatMap(Collection::stream)
                .filter(treeByIndex -> treeByIndex.getLeft().equals(indexOfTree))
                .map(Pair::getRight)
                .allMatch(height -> height.compareTo(treeHeight) < 0);

        boolean treesUnder = entriesByColumn.entrySet().stream()
                .filter(rowEntries -> rowEntries.getKey().compareTo(rowIndex) > 0)
                .map(Map.Entry::getValue)
                .flatMap(Collection::stream)
                .filter(treeByIndex -> treeByIndex.getLeft().equals(indexOfTree))
                .map(Pair::getRight)
                .allMatch(height -> height.compareTo(treeHeight) < 0);

        return treeRight || treeLeft || treesUnder || treesAbove;
    }

    private Map<BigInteger, List<Pair<BigInteger, BigInteger>>> getEntriesByColumn(List<String> columns) {
        Map<BigInteger, List<Pair<BigInteger, BigInteger>>> entriesByColumn = new HashMap<>();

        for (int i = 0; i < columns.size(); i++) {
            String column = columns.get(i);
            List<Pair<BigInteger, BigInteger>> entries = new ArrayList<>();

            for (int index = 0; index < column.length(); index++) {
                String character = Character.toString(column.charAt(index));
                Pair<BigInteger, BigInteger> pair = Pair.of(BigInteger.valueOf(index), new BigInteger(character));
                entries.add(pair);
            }
            entriesByColumn.put(BigInteger.valueOf(i), entries);
        }
        return entriesByColumn;
    }
}
