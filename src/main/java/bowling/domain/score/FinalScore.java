package bowling.domain.score;

import bowling.domain.frame.Frame;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static bowling.common.Pin.MAX;

public class FinalScore extends BaseScore {
    private final int third;

    private FinalScore(int first, int second, int third) {
        super(first, second, -1);
        this.third = third;
    }

    public static FinalScore first(int score) {
        validateScore(score);
        return new FinalScore(score, NONE, NONE);
    }

    @Override
    public int sum() {
        return super.sum() + third;
    }

    @Override
    protected int calculateWith(int base, Frame now) {
        if (!now.isLast()) return NONE;
        return getAll().stream().filter(score -> score != NONE).reduce(base, Integer::sum);
    }

    @Override
    public Score accumulate(int score) {
        if (getSecond() == -1) {
            return second(score);
        }
        return third(score);
    }

    private FinalScore second(int score) {
        validateScore(score);
        validateCombinedScores(score);
        return new FinalScore(getFirst(), score, this.third);
    }

    private FinalScore third(int score) {
        validateScore(score);
        return new FinalScore(getFirst(), getSecond(), score);
    }

    @Override
    public List<Integer> getAll() {
        return Collections.unmodifiableList(Arrays.asList(getFirst(), getSecond(), third));
    }

    @Override
    protected void validateCombinedScores(int score) {
        if (!isStrike() && getFirst() + score > MAX.value()) {
            throw new IllegalArgumentException(
                    "마지막 프레임의 첫시도가 스트라이크가 아닐 때, 1차시도와 2차시도의 합계는 10점을 넘을 수 없습니다.");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FinalScore that = (FinalScore) o;
        return third == that.third;
    }

    @Override
    public int hashCode() {
        return Objects.hash(third);
    }
}