package sailing.bootcamp.spring.board.dto;

import lombok.Builder;
import lombok.Data;
import sailing.bootcamp.spring.board.entity.Board;

@Builder
@Data
public class BoardSaveResponse {

    private Long boardId;
    private String title;
    private String content;

    public static BoardSaveResponse toDto(Board board){
        BoardSaveResponse dto = BoardSaveResponse.builder()
                .boardId(board.getBoardId())
                .title(board.getTitle())
                .content(board.getContent())
                .build();

        return dto;
    }
}
