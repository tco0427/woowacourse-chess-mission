package chess.domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import chess.domain.position.File;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class FileTest {
    @DisplayName("파일은 1~8 까지를 갖는다.")
    @Test
    public void file() {
        //given
        File file = File.ONE;

        //when
        int fileName = file.getFile();

        //then
        assertThat(fileName).isEqualTo(1);
    }
}
