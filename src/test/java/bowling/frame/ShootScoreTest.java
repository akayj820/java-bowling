package bowling.frame;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class ShootScoreTest {

    public static final int STRIKE = 10;

    @Test
    @DisplayName("사용자의 투구를 담당할 ShootScore 생성")
    void create() {
        assertThat(ShootScore.from(STRIKE)).isEqualTo(ShootScore.from(STRIKE));
    }

    @Test
    @DisplayName("한 투구는 0점 이상, 10점 이하의 숫자가 유효함")
    void invalidShootScore() {
        assertAll(
                () -> {
                    assertThatThrownBy(() -> ShootScore.from(-1))
                            .isInstanceOf(IllegalArgumentException.class);
                    assertThatThrownBy(() -> ShootScore.from(11))
                            .isInstanceOf(IllegalArgumentException.class);
                }
        );
    }

    @Test
    @DisplayName("첫 투구가 10점이라면 그것은 스트라이크")
    void shootIsStrike() {
        assertThat(ShootScore.from(STRIKE).isStrike()).isTrue();
    }

    @Test
    @DisplayName("첫 투구 이후에 두 번째 투구를 했을 때 두 투구의 합이 10이라면 스페어")
    void firstShootAndSecondShootIsSpare() {
        ShootScore firstShoot = ShootScore.from(5);
        assertThat(firstShoot.isSpare(ShootScore.from(5))).isTrue();
    }

}