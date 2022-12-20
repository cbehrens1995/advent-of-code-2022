package solution;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import utils.FileUtil;

import java.io.IOException;
import java.math.BigInteger;

import static org.assertj.core.api.Assertions.assertThat;

class SideDeterminerTest {

    private SideDeterminer testee;

    @BeforeEach
    void init() {
        testee = new SideDeterminer();
    }

    @ParameterizedTest
    @CsvSource({"example.txt, 10", "example_2.txt, 60", "test.txt, 64"})
    void thatDetermineWorks(String fileName, long expectedResult) throws IOException {
        //given
        String data = FileUtil.loadData(fileName);

        //when
        BigInteger result = testee.countNonConnectedSides(data);

        //then
        assertThat(result).isEqualTo(BigInteger.valueOf(expectedResult));
    }

    @ParameterizedTest
    @CsvSource({"example_2.txt, 54", "test.txt, 58"})
    void thatCountExteriorSidesWorks(String fileName, long expectedResult) throws IOException {
        //given
        String data = FileUtil.loadData(fileName);

        //when
        BigInteger result = testee.countExteriorSides(data);

        //then
        assertThat(result).isEqualTo(BigInteger.valueOf(expectedResult));
    }
}