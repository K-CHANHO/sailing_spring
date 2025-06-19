package sailing.bootcamp.spring.board.dto;

import lombok.Builder;
import lombok.Data;
import sailing.bootcamp.spring.board.entity.Board;

@Data
@Builder
public class BoardDto {

    private Long boardId;
    private String title;
    private String content;

    public static BoardDto toDto(Board board) {
        BoardDto dto = BoardDto.builder()
                .boardId(board.getBoardId())
                .title(board.getTitle())
                .content(board.getContent())
                .build();

        return dto;
    }
}
