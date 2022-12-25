package solution;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigInteger;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class RopePositionServiceTest {

    private RopePositionService testee;

    private static Stream<Arguments> parameterFor_thatCountDistinctPositionsForNineKnotWorks() {
        String data1 = """
                R 4
                U 4
                L 3
                D 1
                R 4
                D 1
                L 5
                R 2""";
        String data2 = """
                R 5
                U 8
                L 8
                D 3
                R 17
                D 10
                L 25
                U 20""";
        return Stream.of(
                Arguments.of(data1, BigInteger.ONE),
                Arguments.of(data2, BigInteger.valueOf(36))
        );
    }

    @BeforeEach
    void init() {
        testee = new RopePositionService();
    }

    @Test
    void thatCountDistinctPositionsForOneKnotWorks() {
        //given
        String data = """
                R 4
                U 4
                L 3
                D 1
                R 4
                D 1
                L 5
                R 2""";

        //when
        BigInteger result = testee.countDistinctPositionsForOneKnot(data);

        //then
        assertThat(result).isEqualByComparingTo(BigInteger.valueOf(13));
    }

    @ParameterizedTest
    @MethodSource("parameterFor_thatCountDistinctPositionsForNineKnotWorks")
    void thatCountDistinctPositionsForNineKnotWorks(String data, BigInteger expectedCount) {
        //when
        BigInteger result = testee.countDistinctPositionsForNineKnots(data);

        //then
        assertThat(result).isEqualByComparingTo(expectedCount);
    }
}