package solution;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import utils.FileUtil;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Stream;

class MonkeyServiceTest {

    private MonkeyService testee;

    private static Stream<Arguments> parameterFor_thatGetMonkeysAfterRoundsWorks() {
        return Stream.of(
                Arguments.of(1, List.of(BigInteger.valueOf(20), BigInteger.valueOf(23), BigInteger.valueOf(27), BigInteger.valueOf(26)),
                        List.of(BigInteger.valueOf(2080), BigInteger.valueOf(25), BigInteger.valueOf(167), BigInteger.valueOf(207), BigInteger.valueOf(401), BigInteger.valueOf(1046))),

                Arguments.of(15, List.of(BigInteger.valueOf(83), BigInteger.valueOf(44), BigInteger.valueOf(8), BigInteger.valueOf(184), BigInteger.valueOf(9), BigInteger.valueOf(20), BigInteger.valueOf(26), BigInteger.valueOf(102)),
                        List.of(BigInteger.valueOf(110), BigInteger.valueOf(36))),

                Arguments.of(20, List.of(BigInteger.valueOf(10), BigInteger.valueOf(12), BigInteger.valueOf(14), BigInteger.valueOf(26), BigInteger.valueOf(34)),
                        List.of(BigInteger.valueOf(245), BigInteger.valueOf(93), BigInteger.valueOf(53), BigInteger.valueOf(199), BigInteger.valueOf(115)))
        );
    }

    private static Stream<Arguments> parameterFor_thatGetMonkeysAfterRoundsWorks_PartTwo() {
        return Stream.of(
                Arguments.of(1, BigInteger.valueOf(2), BigInteger.valueOf(4), BigInteger.valueOf(3), BigInteger.valueOf(6)),
                Arguments.of(20, BigInteger.valueOf(99), BigInteger.valueOf(97), BigInteger.valueOf(8), BigInteger.valueOf(103)),
                Arguments.of(1000, BigInteger.valueOf(5204), BigInteger.valueOf(4792), BigInteger.valueOf(199), BigInteger.valueOf(5192))
        );
    }

    @BeforeEach
    void init() {
        testee = new MonkeyService();
    }

    @ParameterizedTest
    @MethodSource("parameterFor_thatGetMonkeysAfterRoundsWorks")
    void thatGetMonkeysAfterRoundsWorks(int round, List<BigInteger> expectedItemsMonkey0, List<BigInteger> expectedItemsMonkey1) throws IOException {
        //given
        String data = FileUtil.loadData("test.txt");
        List<MonkeyService.Monkey> monkeys = testee.getMonkeys(data);

        //when
        List<MonkeyService.Monkey> result = testee.getMonkeysAfterRounds(monkeys, round, true);

        //then
        List<BigInteger> resultItemsMonkey0 = result.stream()
                .filter(monkey -> monkey.getIndex().equals(BigInteger.ZERO))
                .map(MonkeyService.Monkey::getItems)
                .toList().get(0);
        Assertions.assertThat(resultItemsMonkey0).containsAll(expectedItemsMonkey0);

        List<BigInteger> resultItemsMonkey1 = result.stream()
                .filter(monkey -> monkey.getIndex().equals(BigInteger.ONE))
                .map(MonkeyService.Monkey::getItems)
                .toList().get(0);
        Assertions.assertThat(resultItemsMonkey1).containsAll(expectedItemsMonkey1);
    }

    @ParameterizedTest
    @MethodSource("parameterFor_thatGetMonkeysAfterRoundsWorks_PartTwo")
    void thatGetMonkeysAfterRoundsWorks_PartTwo(int round, BigInteger expectedCountMonkey0, BigInteger expectedCountMonkey1, BigInteger expectedCountMonkey2, BigInteger expectedCountMonkey3) throws IOException {
        //given
        String data = FileUtil.loadData("test.txt");
        List<MonkeyService.Monkey> monkeys = testee.getMonkeys(data);

        //when
        List<MonkeyService.Monkey> result = testee.getMonkeysAfterRounds(monkeys, round, false);

        //then
        BigInteger resultCountMonkey0 = result.stream()
                .filter(monkey -> monkey.getIndex().equals(BigInteger.ZERO))
                .map(MonkeyService.Monkey::getInspectedItemCount)
                .toList().get(0);
        Assertions.assertThat(resultCountMonkey0).isEqualByComparingTo(expectedCountMonkey0);

        BigInteger resultCountMonkey1 = result.stream()
                .filter(monkey -> monkey.getIndex().equals(BigInteger.ONE))
                .map(MonkeyService.Monkey::getInspectedItemCount)
                .toList().get(0);
        Assertions.assertThat(resultCountMonkey1).isEqualByComparingTo(expectedCountMonkey1);

        BigInteger resultCountMonkey2 = result.stream()
                .filter(monkey -> monkey.getIndex().equals(BigInteger.TWO))
                .map(MonkeyService.Monkey::getInspectedItemCount)
                .toList().get(0);
        Assertions.assertThat(resultCountMonkey2).isEqualByComparingTo(expectedCountMonkey2);

        BigInteger resultCountMonkey3 = result.stream()
                .filter(monkey -> monkey.getIndex().equals(BigInteger.valueOf(3)))
                .map(MonkeyService.Monkey::getInspectedItemCount)
                .toList().get(0);
        Assertions.assertThat(resultCountMonkey3).isEqualByComparingTo(expectedCountMonkey3);
    }
}