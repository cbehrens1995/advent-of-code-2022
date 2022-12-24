package solution;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class DecryptionServiceTest {

    private DecryptionService testee;

    private static Stream<Arguments> parameterFor_thatFindGroveCoordinateWorks() {

        List<BigInteger> decryptedCoordinates = List.of(BigInteger.valueOf(1), BigInteger.valueOf(2), BigInteger.valueOf(-3), BigInteger.valueOf(4), BigInteger.valueOf(0), BigInteger.valueOf(3), BigInteger.valueOf(-2));
        return Stream.of(
                Arguments.of(decryptedCoordinates, 1000, BigInteger.valueOf(4)),
                Arguments.of(decryptedCoordinates, 2000, BigInteger.valueOf(-3)),
                Arguments.of(decryptedCoordinates, 3000, BigInteger.valueOf(2))
        );
    }

    @BeforeEach
    void init() {
        testee = new DecryptionService();
    }

    @ParameterizedTest
    @MethodSource("parameterFor_thatFindGroveCoordinateWorks")
    void thatFindGroveCoordinateWorks(List<BigInteger> decryptedCoordinates,
                                      int position,
                                      BigInteger expectedCoordinate) {
        //when
        BigInteger result = testee.findGroveCoordinate(decryptedCoordinates, position);

        //then
        assertThat(result).isEqualByComparingTo(expectedCoordinate);
    }

    @Test
    void thatDecryptWorks() {
        //given
        List<BigInteger> coordinates = List.of(BigInteger.valueOf(1),
                BigInteger.valueOf(2),
                BigInteger.valueOf(-3),
                BigInteger.valueOf(3),
                BigInteger.valueOf(-2),
                BigInteger.valueOf(0),
                BigInteger.valueOf(4));

        //when
        List<BigInteger> result = testee.decrypt(coordinates);

        //then
        assertThat(result).containsExactly(
                BigInteger.valueOf(1),
                BigInteger.valueOf(2),
                BigInteger.valueOf(-3),
                BigInteger.valueOf(4),
                BigInteger.valueOf(0),
                BigInteger.valueOf(3),
                BigInteger.valueOf(-2)
        );
    }
}