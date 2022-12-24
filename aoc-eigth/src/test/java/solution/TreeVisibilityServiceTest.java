package solution;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;

class TreeVisibilityServiceTest {

    private TreeVisibilityService testee;

    @BeforeEach
    void init() {
        testee = new TreeVisibilityService();
    }

    @Test
    void thatCountVisibleTreesWorks() {
        //given
        String data = """
                30373
                25512
                65332
                33549
                35390
                """;

        //when
        BigInteger result = testee.countVisibleTrees(data);

        //then
        Assertions.assertThat(result).isEqualByComparingTo(BigInteger.valueOf(21));
    }

    @Test
    void thatDetermineHighestScenicScoreWorks() {
        //given
        String data = """
                30373
                25512
                65332
                33549
                35390
                """;

        //when
        BigInteger result = testee.determineHighestScenicScore(data);

        //then
        Assertions.assertThat(result).isEqualByComparingTo(BigInteger.valueOf(8));
    }
}