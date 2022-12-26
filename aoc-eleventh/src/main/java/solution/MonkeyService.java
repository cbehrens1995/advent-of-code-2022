package solution;

import org.apache.commons.lang3.StringUtils;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

public class MonkeyService {

    private static UnaryOperator<BigInteger> getWorryFunction(String observation, Operation operation) {
        List<String> parts = Arrays.stream(StringUtils.split(observation)).toList();
        int indexOfOperation = parts.indexOf(operation.getValue());
        String valueAfterOperation = parts.get(indexOfOperation + 1);
        if (valueAfterOperation.equals("old")) {
            return (a) -> operation.getFunction().apply(a, a);
        } else {
            BigInteger integerValue = new BigInteger(valueAfterOperation);
            return (a) -> operation.getFunction().apply(a, integerValue);
        }
    }

    private static Operation determineOperation(String observation) {
        List<Operation> operations = Arrays.stream(Operation.values())
                .map(Operation::getValue)
                .filter(observation::contains)
                .map(Operation::of)
                .toList();
        if (operations.size() > 1) {
            throw new IllegalStateException();
        }
        return operations.get(0);
    }

    public List<Monkey> getMonkeysAfterRounds(List<Monkey> monkeys, int round, boolean divideWorryLevel) {
        for (int i = 0; i < round; i++) {
            monkeys.stream()
                    .sorted(Comparator.comparing(Monkey::getIndex))
                    .forEach(monkey -> processMonkey(monkey, monkeys, divideWorryLevel));
        }

        return monkeys;
    }

    private void processMonkey(Monkey currentMonkey, List<Monkey> monkeys, boolean divideWorryLevel) {
        for (BigInteger item : currentMonkey.getItems()) {
            BigInteger commonDivisor = monkeys.stream()
                    .map(Monkey::getDivisor)
                    .reduce(BigInteger::multiply)
                    .orElseThrow();
            BigInteger newItemValue = determineNewItemValue(currentMonkey, item, divideWorryLevel, commonDivisor);

            BigInteger newIndex = determineNewIndexOfMonkey(currentMonkey, newItemValue);

            Monkey newMonkey = monkeys.stream()
                    .filter(monkey -> monkey.getIndex().equals(newIndex))
                    .toList()
                    .get(0);
            newMonkey.addItem(newItemValue);
            currentMonkey.increaseInspectedItemCount();
        }
        currentMonkey.clearItems();
    }

    private BigInteger determineNewIndexOfMonkey(Monkey currentMonkey, BigInteger newItemValue) {
        Predicate<BigInteger> testFunction = currentMonkey.getTestFunction();
        if (testFunction.test(newItemValue)) {
            return currentMonkey.getPositiveTestIndex();
        } else {
            return currentMonkey.getNegativeTestIndex();
        }
    }

    private BigInteger determineNewItemValue(Monkey currentMonkey, BigInteger item, boolean divideWorryLevel, BigInteger commonDivisor) {
        BigInteger newItemWorry = currentMonkey.getNewWorryLevelFunction().apply(item);
        if (divideWorryLevel) {
            return newItemWorry.divide(BigInteger.valueOf(3));
        } else {
            return newItemWorry.mod(commonDivisor).add(commonDivisor);
        }
    }

    public List<Monkey> getMonkeys(String data) {
        List<String> observations = data.lines().toList();

        List<Monkey> monkeys = new ArrayList<>();
        Monkey currentMonkey = null;
        for (String observation : observations) {
            String[] observationParts = StringUtils.split(observation);
            if (observation.contains("Monkey")) {
                String[] parts = observationParts;
                currentMonkey = new Monkey(new BigInteger(parts[1].replace(":", "")));
                monkeys.add(currentMonkey);
            }

            if (observation.contains("Starting items:")) {
                Arrays.stream(observationParts)
                        .skip(2)
                        .map(part -> part.replace(",", ""))
                        .map(BigInteger::new)
                        .forEach(currentMonkey::addItem);
            }

            if (observation.contains("Operation:")) {
                Operation operation = determineOperation(observation);

                UnaryOperator<BigInteger> function = getWorryFunction(observation, operation);
                currentMonkey.setNewWorryLevelFunction(function);
            }

            if (observation.contains("Test:")) {
                BigInteger divisorValue = new BigInteger(observationParts[observationParts.length - 1]);
                Predicate<BigInteger> testFunction = a -> a.mod(divisorValue).equals(BigInteger.ZERO);
                currentMonkey.setTestFunction(testFunction);
                currentMonkey.setDivisor(divisorValue);
            }

            if (observation.contains("true")) {
                BigInteger positiveMonkeyIndex = new BigInteger(observationParts[observationParts.length - 1]);
                currentMonkey.setPositiveTestIndex(positiveMonkeyIndex);
            }

            if (observation.contains("false")) {
                BigInteger negativeMonkeyIndex = new BigInteger(observationParts[observationParts.length - 1]);
                currentMonkey.setNegativeTestIndex(negativeMonkeyIndex);
            }
        }
        return monkeys;
    }

    enum Operation {
        PLUS("+", BigInteger::add),
        MULTIPLE("*", BigInteger::multiply);

        private final String value;
        private final BinaryOperator<BigInteger> function;

        Operation(String value, BinaryOperator<BigInteger> function) {
            this.value = value;
            this.function = function;
        }

        public static Operation of(String value) {
            if (value.equals("*")) {
                return MULTIPLE;
            }

            if (value.equals("+")) {
                return PLUS;
            }

            throw new IllegalArgumentException();
        }

        public String getValue() {
            return value;
        }

        public BinaryOperator<BigInteger> getFunction() {
            return function;
        }
    }

    class Monkey {
        private BigInteger index;
        private List<BigInteger> items = new ArrayList<>();
        private UnaryOperator<BigInteger> newWorryLevelFunction;
        private Predicate<BigInteger> testFunction;
        private BigInteger divisor;
        private BigInteger positiveTestIndex;
        private BigInteger negativeTestIndex;
        private BigInteger inspectedItemCount = BigInteger.ZERO;

        public Monkey(BigInteger index) {
            this.index = index;
        }

        public void addItem(BigInteger item) {
            items.add(item);
        }

        public void clearItems() {
            items.clear();
        }

        public BigInteger getIndex() {
            return index;
        }

        public List<BigInteger> getItems() {
            return items;
        }

        public UnaryOperator<BigInteger> getNewWorryLevelFunction() {
            return newWorryLevelFunction;
        }

        public void setNewWorryLevelFunction(UnaryOperator<BigInteger> newWorryLevelFunction) {
            this.newWorryLevelFunction = newWorryLevelFunction;
        }

        public Predicate<BigInteger> getTestFunction() {
            return testFunction;
        }

        public void setTestFunction(Predicate<BigInteger> testFunction) {
            this.testFunction = testFunction;
        }

        public BigInteger getDivisor() {
            return divisor;
        }

        public void setDivisor(BigInteger divisor) {
            this.divisor = divisor;
        }

        public BigInteger getPositiveTestIndex() {
            return positiveTestIndex;
        }

        public void setPositiveTestIndex(BigInteger positiveTestIndex) {
            this.positiveTestIndex = positiveTestIndex;
        }

        public BigInteger getNegativeTestIndex() {
            return negativeTestIndex;
        }

        public void setNegativeTestIndex(BigInteger negativeTestIndex) {
            this.negativeTestIndex = negativeTestIndex;
        }

        public BigInteger getInspectedItemCount() {
            return inspectedItemCount;
        }

        public void increaseInspectedItemCount() {
            inspectedItemCount = inspectedItemCount.add(BigInteger.ONE);
        }
    }
}
