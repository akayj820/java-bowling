package bowling;

import java.util.List;
import java.util.Objects;

public class Score {
    private static final int MIN_SCORE = 0;
    private static final int MAX_SCORE = 10;

    private final int score;

    private Score(final int score) {
        validateScoreRange(score);
        this.score = score;
    }

    private static void validateScoreRange(final int score) {
        if (score < MIN_SCORE || score > MAX_SCORE) {
            throw new IllegalArgumentException("Score must be greater than zero and lower than 10.");
        }
    }

    public static Score of(final int score) {
        return new Score(score);
    }

    public static int sum(final List<Score> scores) {
        return scores.stream()
                .mapToInt(score -> score.score)
                .sum();
    }

    public boolean isEqualsTo(final int pinCount) {
        return score == pinCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Score)) return false;
        Score score1 = (Score) o;
        return score == score1.score;
    }

    @Override
    public int hashCode() {
        return Objects.hash(score);
    }

    @Override
    public String toString() {
        return String.valueOf(score);
    }

    public int getScore() {
        return score;
    }
}