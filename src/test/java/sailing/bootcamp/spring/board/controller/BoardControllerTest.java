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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import sailing.bootcamp.spring.board.dto.BoardSaveRequest;
import sailing.bootcamp.spring.board.dto.BoardSaveResponse;
import sailing.bootcamp.spring.board.entity.Board;
import sailing.bootcamp.spring.board.service.BoardService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;

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
        resultActions.andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("게시물 등록 성공")
    public void boardSave() throws Exception {
        // given
        final String url = "/api/v1/board";
        lenient().doReturn(savedBoard()).when(boardService).save(any());

        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post(url)
                        .content(gson.toJson(boardSaveRequest("테스트 게시글입니당", "초록 체크가 보고싶당")))
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        resultActions.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.boardId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("테스트 게시글입니당"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").value("초록 체크가 나왔으면 좋겠당"));
    }

    private BoardSaveRequest boardSaveRequest(String title, String content) {
        return BoardSaveRequest.builder()
                .title(title)
                .content(content)
                .build();
    }

    private BoardSaveResponse savedBoard() {
        return BoardSaveResponse.builder()
                .boardId(1L)
                .title("테스트 게시글입니당")
                .content("초록 체크가 나왔으면 좋겠당")
                .build();
    }
}
