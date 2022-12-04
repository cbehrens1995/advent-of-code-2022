package solution;

import java.math.BigDecimal;

public enum Hand {
    ROCK(new BigDecimal(1)),
    PAPER(new BigDecimal(2)),
    SCISSOR(new BigDecimal(3));

    private final BigDecimal handScore;

    Hand(BigDecimal handScore) {
        this.handScore = handScore;
    }

    public static Hand of(String guideString) {
        return switch (guideString) {
            case "A", "X" -> ROCK;
            case "B", "Y" -> PAPER;
            case "C", "Z" -> SCISSOR;
            default ->
                    throw new IllegalArgumentException(String.format("Unmapped guide string for opponent's hand %s", guideString));
        };
    }

    public BigDecimal getHandScore() {
        return handScore;
    }
}
