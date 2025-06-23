package sailing.bootcamp.spring.board.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BoardDeleteRequest {

    private Long boardId;

}
