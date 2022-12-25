package solution;

import org.apache.commons.collections4.ListUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import utils.FileUtil;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class CircleServiceTest {

    private CircleService testee;

    private static Stream<Arguments> parameterFor_thatGetSignalStrengthWorks_BigData() {
        return Stream.of(
                Arguments.of(BigInteger.valueOf(20), BigInteger.valueOf(420)),
                Arguments.of(BigInteger.valueOf(60), BigInteger.valueOf(1140)),
                Arguments.of(BigInteger.valueOf(100), BigInteger.valueOf(1800)),
                Arguments.of(BigInteger.valueOf(140), BigInteger.valueOf(2940)),
                Arguments.of(BigInteger.valueOf(180), BigInteger.valueOf(2880)),
                Arguments.of(BigInteger.valueOf(220), BigInteger.valueOf(3960))
        );
    }

    @BeforeEach
    void init() {
        testee = new CircleService();
    }

    @Test
    void thatGetSignalStrengthWorks() {
        //given
        String data = """
                noop
                addx 3
                addx -5""";

        //when
        BigInteger result = testee.getSignalStrength(data, BigInteger.valueOf(5));

        //then
        assertThat(result).isEqualByComparingTo(BigInteger.valueOf(-5));
    }

    @ParameterizedTest
    @MethodSource("parameterFor_thatGetSignalStrengthWorks_BigData")
    void thatGetSignalStrengthWorks_BigData(BigInteger position, BigInteger expectedSignalStrength) throws IOException {
        //given
        String data = FileUtil.loadData("test.txt");

        //when
        BigInteger result = testee.getSignalStrength(data, position);

        //then
        assertThat(result).isEqualByComparingTo(expectedSignalStrength);
    }

    @Test
    void thatGetImageWorks() throws IOException {
        //given
        String data = FileUtil.loadData("test.txt");
        String expectedResult = """
                ##..##..##..##..##..##..##..##..##..##..
                ###...###...###...###...###...###...###.
                ####....####....####....####....####....
                #####.....#####.....#####.....#####.....
                ######......######......######......####
                #######.......#######.......#######.....""";

        //when
        List<String> result = testee.getImage(data);
        String formattedResult = ListUtils.partition(result, 40).stream()
                .map(strings -> String.join("", strings))
                .collect(Collectors.joining(System.getProperty("line.separator")));

        //then
        assertThat(formattedResult).isEqualTo(expectedResult);
    }
}