package sailing.bootcamp.spring.board.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sailing.bootcamp.spring.board.dto.BoardDeleteRequest;
import sailing.bootcamp.spring.board.dto.BoardGetResponse;
import sailing.bootcamp.spring.board.dto.BoardSaveRequest;
import sailing.bootcamp.spring.board.dto.BoardSaveResponse;
import sailing.bootcamp.spring.board.entity.Board;
import sailing.bootcamp.spring.board.repository.BoardRepository;
import sailing.bootcamp.spring.exception.BoardException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
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
        List<BoardGetResponse> boardGetResponseList = boardService.getAllBoard();

        // then
        assertThat(boardGetResponseList).isNotNull();
        assertThat(boardGetResponseList.size()).isEqualTo(3);
    }

    private List<Board> makeBoardList() {
        List<Board> boardList = new ArrayList<Board>();
        for(int i=0; i<3; i++){
            boardList.add(Board.builder().title("test" + i).content(i + "번 게시글 내용입니당").build());
        }

        return boardList;

    }

    @Test
    @DisplayName("게시물 삭제 테스트")
    public void deleteBoard(){
        // given
        lenient().doReturn(savedBoard()).when(boardRepository).save(any());
        BoardSaveRequest boardSaveRequest= BoardSaveRequest.builder()
                .title("테스트 게시글입니당")
                .content("초록 체크가 나왔으면 좋겠당")
                .build();
        BoardSaveResponse savedBoard = boardService.saveBoard(boardSaveRequest);

        BoardDeleteRequest boardDeleteRequest = BoardDeleteRequest.builder()
                .boardId(savedBoard.getBoardId())
                .build();

        // when, then
        assertThat(boardService.deleteBoard(boardDeleteRequest).isStatus()).isTrue();
    }

    @Test
    @DisplayName("게시글 조회 테스트")
    public void getBoard(){
        // given
        doReturn(Optional.of(savedBoard())).when(boardRepository).findById(any());

        // when
        BoardGetResponse boardDto = boardService.getBoard(1L);

        // then
        assertThat(boardDto.getBoardId()).isNotNull();
        assertThat(boardDto.getTitle()).isEqualTo("테스트 게시글입니당");
        assertThat(boardDto.getContent()).isEqualTo("초록 체크가 나왔으면 좋겠당");

    }

}
