package sailing.bootcamp.spring.board.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sailing.bootcamp.spring.board.entity.Board;
import sailing.bootcamp.spring.board.repository.BoardRepository;
import sailing.bootcamp.spring.exception.BoardException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
public class BoardServiceTest {

    @InjectMocks
    private BoardService boardService;

    @Mock
    private BoardRepository boardRepository;

    private Board savedBoard() {
        return Board.builder()
                .boardId(1L)
                .title("테스트 게시글입니당")
                .content("초록 체크가 나왔으면 좋겠당")
                .build();
    }

    @Test
    @DisplayName("제목, 내용이 NULL인 경우 게시글 등록 실패")
    public void BoardSaveFailByNull(){
        // given
        Board board = Board.builder()
                .title(null)
                .content(null)
                .build();

        // when, then
        assertThatThrownBy(() -> boardService.save(board)).isInstanceOf(BoardException.class);

    }

    @Test
    @DisplayName("게시글 등록 성공")
    public void BoardSave(){
        // given
        Board board = Board.builder()
                .title("테스트 게시글입니당")
                .content("초록 체크가 나왔으면 좋겠당")
                .build();
        lenient().doReturn(savedBoard()).when(boardRepository).save(board);

        // when
        Board result = boardService.save(board);

        // then
        assertThat(result.getBoardId()).isNotNull();
        assertThat(result.getTitle()).isEqualTo("테스트 게시글입니당");
        assertThat(result.getContent()).isEqualTo("초록 체크가 나왔으면 좋겠당");
    }

    @Test
    @DisplayName("제목이 NULL인 경우 게시글 등록 실패")
    public void BoardSaveFailByTitleNull(){
        // given
        Board board = Board.builder()
                .title(null)
                .content("초록 체크가 나왔으면 좋겠당")
                .build();
        lenient().doThrow(BoardException.class).when(boardRepository).save(board);

        // when, then
        assertThatThrownBy(() -> boardService.save(board)).isInstanceOf(BoardException.class);

    }

    @Test
    @DisplayName("내용이 NULL인 경우 게시글 등록 실패")
    public void BoardSaveFailByContentNull(){
        // given
        Board board = Board.builder()
                .title("테스트 게시글입니당")
                .content(null)
                .build();

        // when, then
        assertThatThrownBy(() -> boardService.save(board)).isInstanceOf(BoardException.class);

    }

}
