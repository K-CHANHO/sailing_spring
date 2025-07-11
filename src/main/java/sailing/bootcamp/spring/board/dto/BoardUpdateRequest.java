package sailing.bootcamp.spring.board.dto;

import lombok.Builder;
import lombok.Data;
import sailing.bootcamp.spring.board.entity.Board;

@Data
@Builder
public class BoardUpdateRequest {

    private Long boardId;
    private String title;
    private String content;
    private String userName;
    private String password;

}
