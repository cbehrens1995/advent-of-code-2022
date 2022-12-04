package solution;

import java.math.BigDecimal;

public enum Outcome {
    WIN(new BigDecimal(6)),
    DRAW(new BigDecimal(3)),
    LOOSE(new BigDecimal(0));

    private final BigDecimal matchScore;

    Outcome(BigDecimal matchScore) {
        this.matchScore = matchScore;
    }

    public static Outcome of(String guideString) {
        return switch (guideString) {
            case "X" -> LOOSE;
            case "Y" -> DRAW;
            case "Z" -> WIN;
            default -> throw new IllegalArgumentException("Unmapped guide string for own hand");
        };
    }

    public BigDecimal getMatchScore() {
        return matchScore;
    }
}
