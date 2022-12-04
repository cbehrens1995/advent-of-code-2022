package solution;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class Solution {

    public static final String INPUT = "input.txt";

    public static void main(String[] args) throws IOException {
        String data = loadData();

        List<Pair<Hand, Hand>> rounds = data.lines()
                .map(StringUtils::split)
                .map(strings -> Pair.of(Hand.of(strings[0]), Hand.of(strings[1])))
                .toList();

        BigDecimal totalScore = BigDecimal.ZERO;
        for (Pair<Hand, Hand> round : rounds) {
            Hand opponentsHand = round.getKey();
            Hand ownHand = round.getValue();

            BigDecimal handScore = ownHand.getHandScore();
            BigDecimal matchScore = determineMatchScore(opponentsHand, ownHand);

            totalScore = totalScore.add(handScore).add(matchScore);
        }

        System.out.printf("Your total score is %s%n", totalScore);
    }

    private static BigDecimal determineMatchScore(Hand opponentsHand, Hand ownHand) {
        return switch (opponentsHand) {
            case ROCK -> switch (ownHand) {
                case SCISSOR -> Outcome.LOOSE.getMatchScore();
                case ROCK -> Outcome.DRAW.getMatchScore();
                case PAPER -> Outcome.WIN.getMatchScore();
            };
            case SCISSOR -> switch (ownHand) {
                case PAPER -> Outcome.LOOSE.getMatchScore();
                case SCISSOR -> Outcome.DRAW.getMatchScore();
                case ROCK -> Outcome.WIN.getMatchScore();
            };
            case PAPER -> switch (ownHand) {
                case ROCK -> Outcome.LOOSE.getMatchScore();
                case PAPER -> Outcome.DRAW.getMatchScore();
                case SCISSOR -> Outcome.WIN.getMatchScore();
            };
        };
    }

    private static String loadData() throws IOException {
        ClassLoader classLoader = Solution.class.getClassLoader();
        File file = new File(classLoader.getResource(INPUT).getFile());
        return FileUtils.readFileToString(file, StandardCharsets.UTF_8);
    }
}
