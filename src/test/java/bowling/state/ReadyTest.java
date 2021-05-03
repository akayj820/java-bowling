package bowling.state;

import bowling.domain.FrameScore;
import bowling.domain.Pins;
import bowling.domain.exception.PinsCountException;
import bowling.domain.state.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ReadyTest {
    private State ready;

    @BeforeEach
    void setUp() {
        ready = Ready.create();
    }

    @Test
    @DisplayName("턴이 끝났는지 확인 테스트")
    void isFinishedTest() {
        assertThat(ready.isFinished()).isFalse();
    }

    @Test
    @DisplayName("Continue 상태로 변경 확인 테스트")
    void bowlToContinueTest() {
        Pins pins = Pins.of(7);
        assertThat(ready.stateAfterPitch(Pins.of(7))).isEqualTo(Continue.of(pins));
    }

    @Test
    @DisplayName("Strike 상태로 변경 확인 테스트")
    void bowlToStrikeTest() {
        Pins pins = Pins.of(10);
        assertThat(ready.stateAfterPitch(pins)).isEqualTo(Strike.of(pins));
    }

    @Test
    @DisplayName("추가 점수가 1개 일 때 보너스 점수 테스트")
    void OneBonusFrameScoreTest() {
        FrameScore prevFrameScore = FrameScore.of(10,1);
        assertThat(ready.frameScoreWithBonus(prevFrameScore)).isEqualTo(FrameScore.of(10,-1));
    }

    @Test
    @DisplayName("추가 점수가 2개 일 때 보너스 점수 테스트")
    void TwoBonusFrameScoreTest() {
        FrameScore prevFrameScore = FrameScore.of(10,2);
        assertThat(ready.frameScoreWithBonus(prevFrameScore)).isEqualTo(FrameScore.of(10,-1));
    }

    @ParameterizedTest(name = "넘어뜨린 핀 개수 예외 테스트")
    @ValueSource(ints = {11, -1, 15})
    void pinsCountExceptionTest(int pitch) {
        assertThatThrownBy(() -> ready.stateAfterPitch(Pins.of(pitch)))
                .isInstanceOf(PinsCountException.class)
                .hasMessage("넘어뜨린 핀수는 0에서 10 사이의 정수여야 합니다.");
    }
}