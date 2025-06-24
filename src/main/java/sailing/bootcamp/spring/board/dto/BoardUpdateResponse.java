package sailing.bootcamp.spring.board.dto;

import lombok.Builder;
import lombok.Data;
import sailing.bootcamp.spring.board.entity.Board;

@Data
@Builder
public class BoardUpdateResponse {

    private Long boardId;
    private String title;
    private String content;

    public static BoardUpdateResponse toDto(Board updatedBoard) {
        return BoardUpdateResponse.builder()
                .boardId(updatedBoard.getBoardId())
                .title(updatedBoard.getTitle())
                .content(updatedBoard.getContent())
                .build();
    }
}
