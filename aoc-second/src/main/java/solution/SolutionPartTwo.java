package solution;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import utils.FileUtil;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public class SolutionPartTwo {

    public static final String INPUT = "input.txt";

    public static void main(String[] args) throws IOException {
        String data = FileUtil.loadData(INPUT);

        List<Pair<Hand, Outcome>> rounds = data.lines()
                .map(StringUtils::split)
                .map(strings -> Pair.of(Hand.of(strings[0]), Outcome.of(strings[1])))
                .toList();

        BigDecimal totalScore = BigDecimal.ZERO;
        for (Pair<Hand, Outcome> round : rounds) {
            Hand hand = round.getKey();
            Outcome outcome = round.getValue();

            BigDecimal handScore = determineHandScore(hand, outcome);
            BigDecimal matchScore = outcome.getMatchScore();

            totalScore = totalScore.add(handScore).add(matchScore);
        }

        System.out.printf("Your total score is %s%n", totalScore);

    }

    private static BigDecimal determineHandScore(Hand opponentsHand, Outcome outcome) {
        return switch (outcome) {
            case DRAW -> opponentsHand.getHandScore();
            case WIN -> switch (opponentsHand) {
                case ROCK -> Hand.PAPER.getHandScore();
                case PAPER -> Hand.SCISSOR.getHandScore();
                case SCISSOR -> Hand.ROCK.getHandScore();
            };
            case LOOSE -> switch (opponentsHand) {
                case ROCK -> Hand.SCISSOR.getHandScore();
                case PAPER -> Hand.ROCK.getHandScore();
                case SCISSOR -> Hand.PAPER.getHandScore();
            };
        };
    }
}
