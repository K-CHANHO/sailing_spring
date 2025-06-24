package sailing.bootcamp.spring.board.controller;

import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import sailing.bootcamp.spring.board.dto.*;
import sailing.bootcamp.spring.board.service.BoardService;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.lenient;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class BoardControllerTest {

    @InjectMocks
    private BoardController boardController;
    private MockMvc mockMvc;
    private Gson gson;

    @Mock
    private BoardService boardService;

    @BeforeEach
    public void mockMvcInit(){
        gson = new Gson();
        mockMvc = MockMvcBuilders.standaloneSetup(boardController)
                .build();
    }

    @Test
    @DisplayName("mockMvc가 Null이 아님")
    public void mockMvcIsNotNull(){

        assertThat(boardController).isNotNull();
        assertThat(mockMvc).isNotNull();

    }

    @Test
    @DisplayName("제목 또는 내용이 NULL이면 게시물 등록 실패")
    public void boardSaveFail() throws Exception {
        // given
        final String url = "/api/v1/board";

        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post(url)
                        .content(gson.toJson(boardSaveRequest(null, null)))
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        resultActions.andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("게시물 등록 성공")
    public void saveBoard() throws Exception {
        // given
        final String url = "/api/v1/board";
        lenient().doReturn(makeBoard()).when(boardService).saveBoard(any());

        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post(url)
                        .content(gson.toJson(boardSaveRequest("테스트 게시글입니당", "초록 체크가 보고싶당")))
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        resultActions.andExpect(status().isCreated())
                .andExpect(jsonPath("$.boardId").value(1))
                .andExpect(jsonPath("$.title").value("테스트 게시글입니당"))
                .andExpect(jsonPath("$.content").value("초록 체크가 나왔으면 좋겠당"));
    }

    @Test
    @DisplayName("전체 게시글 조회")
    public void getAllBoard() throws Exception {
        // given
        final String url = "/api/v1/board";
        doReturn(makeBoardList()).when(boardService).getAllBoard();

        // when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get(url)
        );

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3));
    }

    @Test
    @DisplayName("게시글 삭제 테스트")
    public void deleteBoard() throws Exception {
        // given
        final String url = "/api/v1/board";
        doReturn(new BoardDeleteResponse(true)).when(boardService).deleteBoard(any());

        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.delete(url)
                        .content(gson.toJson(boardDeleteRequest(1L)))
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(true));
    }

    private BoardDeleteRequest boardDeleteRequest(Long boardId) {
        return BoardDeleteRequest.builder().boardId(boardId).build();
    }

    private List<BoardGetResponse> makeBoardList() {
        List<BoardGetResponse> boardGetResponseList = new ArrayList<>();
        for(int i=0; i<3; i++){
            boardGetResponseList.add(BoardGetResponse.builder()
                    .boardId(Long.valueOf(i))
                    .title("게시물 " + i)
                    .content(i+" 번째 내용")
                    .build());
        }
        return boardGetResponseList;
    }

    private BoardSaveRequest boardSaveRequest(String title, String content) {
        return BoardSaveRequest.builder()
                .title(title)
                .content(content)
                .build();
    }

    private BoardSaveResponse makeBoard() {
        return BoardSaveResponse.builder()
                .boardId(1L)
                .title("테스트 게시글입니당")
                .content("초록 체크가 나왔으면 좋겠당")
                .build();
    }
}
