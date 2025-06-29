package sailing.bootcamp.spring.board.dto;

import lombok.Builder;
import lombok.Data;
import sailing.bootcamp.spring.board.entity.Board;

import java.time.LocalDateTime;

@Data
@Builder
public class BoardUpdateResponse {

    private Long boardId;
    private String title;
    private String content;

    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    public static BoardUpdateResponse toDto(Board updatedBoard) {
        return BoardUpdateResponse.builder()
                .boardId(updatedBoard.getBoardId())
                .title(updatedBoard.getTitle())
                .content(updatedBoard.getContent())
                .createDate(updatedBoard.getCreateDate())
                .updateDate(updatedBoard.getUpdateDate())
                .build();
    }
}
