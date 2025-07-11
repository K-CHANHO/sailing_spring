package sailing.bootcamp.spring.board.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import sailing.bootcamp.spring.board.dto.BoardGetResponse;
import sailing.bootcamp.spring.board.entity.Board;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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
    public void saveBoard(){
        // given
        Board boardWithNull = Board.builder()
                .title(null)
                .content(null)
                .userName("김찬호")
                .password("password")
                .build(); // 제목, 내용이 둘 다 null 인 경우

        Board boardWithTitleNull = Board.builder()
                .title(null)
                .content("초록색 체크를 원합니당")
                .userName("김찬호")
                .password("password")
                .build(); // 제목이 null 인 경우

        Board boardWithContentNull = Board.builder()
                .title("게시글 제목입니당")
                .content(null)
                .userName("김찬호")
                .password("password")
                .build(); // 내용이 null 인 경우

        Board boardWithoutNull = Board.builder()
                .title("게시글 제목입니당")
                .content("초록색 체크를 원합니당")
                .userName("김찬호")
                .password("password")
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

    @Test
    @DisplayName("게시물 전체 조회 테스트")
    public void getAllBoard(){
        // given
        ArrayList<Board> boardList = new ArrayList<>();
        Board testBoard1 = Board.builder().title("test1").content("테스트1").userName("김찬호").password("password").build();
        Board testBoard2 = Board.builder().title("test2").content("테스트2").userName("김찬호").password("password").build();
        Board testBoard3 = Board.builder().title("test3").content("테스트3").userName("김찬호").password("password").build();

        // when
        boardList.add(testBoard1); boardList.add(testBoard2); boardList.add(testBoard3);
        boardList.stream().forEach(board->boardRepository.save(board));

        // then
        assertThat(boardRepository.findAll().size()).isEqualTo(3);
    }

    @Test
    @DisplayName("게시글 수정 테스트")
    public void updateBoard(){
        // given
        Board board = Board.builder().title("수정전 제목").content("수정전 내용").userName("김찬호").password("password").build();
        Board savedBoard = boardRepository.save(board);

        // when
        Board updateBoard = Board.builder()
                .boardId(savedBoard.getBoardId()).title("수정후 제목").content("수정후 내용").build();
        Board updatedBoard = boardRepository.save(updateBoard);

        // then
        assertThat(updatedBoard.getBoardId()).isEqualTo(savedBoard.getBoardId());
        assertThat(updatedBoard.getTitle()).isEqualTo("수정후 제목");
        assertThat(updatedBoard.getContent()).isEqualTo("수정후 내용");
    }

    @Test
    @DisplayName("게시물 삭제 테스트")
    public void deleteBoard(){
        // given
        Board board = Board.builder().title("테스트 게시글").content("테스트 내용").build();
        Board savedBoard = boardRepository.save(board);

        // when
        boardRepository.deleteById(savedBoard.getBoardId());

        // then
        assertThat(boardRepository.findById(savedBoard.getBoardId()).isEmpty()).isTrue();

    }

    @Test
    @DisplayName("게시글 조회 테스트")
    public void getBoard(){
        // given
        Board board = Board.builder()
                .title("테스트지롱").content("내용이지롱").build();

        // when
        Board savedBoard = boardRepository.save(board);
        BoardGetResponse boardGetResponse = BoardGetResponse.toDto(savedBoard);

        // then
        assertThat(boardGetResponse.getBoardId()).isNotNull();
        assertThat(boardGetResponse.getTitle()).isEqualTo("테스트지롱");
        assertThat(boardGetResponse.getContent()).isEqualTo("내용이지롱");
    }

}
