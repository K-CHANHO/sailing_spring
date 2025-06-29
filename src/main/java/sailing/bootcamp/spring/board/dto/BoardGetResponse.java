package sailing.bootcamp.spring.board.dto;

import lombok.Builder;
import lombok.Data;
import sailing.bootcamp.spring.board.entity.Board;

import java.time.LocalDateTime;

@Data
@Builder
public class BoardGetResponse {

    private Long boardId;
    private String title;
    private String content;
    private String userName;

    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    public static BoardGetResponse toDto(Board board) {
        BoardGetResponse dto = BoardGetResponse.builder()
                .boardId(board.getBoardId())
                .title(board.getTitle())
                .content(board.getContent())
                .userName(board.getUserName())
                .createDate(board.getCreateDate())
                .updateDate(board.getUpdateDate())
                .build();

        return dto;
    }
}
