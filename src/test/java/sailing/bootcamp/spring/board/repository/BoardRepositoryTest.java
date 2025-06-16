package sailing.bootcamp.spring.board.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import sailing.bootcamp.spring.board.entity.Board;

import static org.assertj.core.api.Assertions.*;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BoardRepositoryTest {

    @Autowired
    private BoardRepository boardRepository;

    @Test
    @DisplayName("게시글 레포지토리 의존성 주입 확인")
    public void BoardRepositoryIsNotNull(){
        assertThat(boardRepository).isNotNull();
    }

    @Test
    @DisplayName("게시글 등록 테스트")
    public void BoardSave(){
        // given
        Board boardWithNull = Board.builder()
                .title(null)
                .content(null)
                .build(); // 제목, 내용이 둘 다 null 인 경우

        Board boardWithTitleNull = Board.builder()
                .title(null)
                .content("초록색 체크를 원합니당")
                .build(); // 제목이 null 인 경우

        Board boardWithContentNull = Board.builder()
                .title("게시글 제목입니당")
                .content(null)
                .build(); // 내용이 null 인 경우

        Board boardWithoutNull = Board.builder()
                .title("게시글 제목입니당")
                .content("초록색 체크를 원합니당")
                .build(); // 제목, 내용 둘 다 null이 아닌 경우

        // when
        Board result = boardRepository.save(boardWithoutNull);

        // then
        assertThatThrownBy(() -> boardRepository.save(boardWithNull)).isInstanceOf(Exception.class);
        assertThatThrownBy(() -> boardRepository.save(boardWithTitleNull)).isInstanceOf(Exception.class);
        assertThatThrownBy(() -> boardRepository.save(boardWithContentNull)).isInstanceOf(Exception.class);

        assertThat(result.getBoardId()).isNotNull();
        assertThat(result.getTitle()).isEqualTo("게시글 제목입니당");
        assertThat(result.getContent()).isEqualTo("초록색 체크를 원합니당");
    }

}
