package sailing.bootcamp.spring.board.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sailing.bootcamp.spring.board.dto.BoardDto;
import sailing.bootcamp.spring.board.dto.BoardSaveRequest;
import sailing.bootcamp.spring.board.dto.BoardSaveResponse;
import sailing.bootcamp.spring.board.entity.Board;
import sailing.bootcamp.spring.board.repository.BoardRepository;
import sailing.bootcamp.spring.exception.BoardException;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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
        BoardSaveRequest board = BoardSaveRequest.builder()
                .title(null)
                .content(null)
                .build();

        // when, then
        assertThatThrownBy(() -> boardService.saveBoard(board)).isInstanceOf(BoardException.class);

    }

    @Test
    @DisplayName("게시글 등록 성공")
    public void BoardSave(){
        // given
        Board board = Board.builder()
                .title("테스트 게시글입니당")
                .content("초록 체크가 나왔으면 좋겠당")
                .build();
        lenient().doReturn(savedBoard()).when(boardRepository).save(any());

        BoardSaveRequest boardSaveRequest= BoardSaveRequest.builder()
                .title("테스트 게시글입니당")
                .content("초록 체크가 나왔으면 좋겠당")
                .build();

        // when
        BoardSaveResponse result = boardService.saveBoard(boardSaveRequest);

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

        BoardSaveRequest boardSaveRequest= BoardSaveRequest.builder()
                .title(null)
                .content("초록 체크가 나왔으면 좋겠당")
                .build();

        // when, then
        assertThatThrownBy(() -> boardService.saveBoard(boardSaveRequest)).isInstanceOf(BoardException.class);

    }

    @Test
    @DisplayName("내용이 NULL인 경우 게시글 등록 실패")
    public void BoardSaveFailByContentNull(){
        // given
        BoardSaveRequest board = BoardSaveRequest.builder()
                .title("테스트 게시글입니당")
                .content(null)
                .build();

        // when, then
        assertThatThrownBy(() -> boardService.saveBoard(board)).isInstanceOf(BoardException.class);

    }

    @Test
    @DisplayName("게시글 전체 조회")
    public void getAllBoards(){
        // given
        doReturn(makeBoardList()).when(boardRepository).findAll();

        // when
        List<BoardDto> boardDtoList = boardService.getAllBoard();

        // then
        assertThat(boardDtoList).isNotNull();
        assertThat(boardDtoList.size()).isEqualTo(3);
    }

    private List<Board> makeBoardList() {
        List<Board> boardList = new ArrayList<Board>();
        for(int i=0; i<3; i++){
            boardList.add(Board.builder().title("test" + i).content(i + "번 게시글 내용입니당").build());
        }

        return boardList;

    }

}
