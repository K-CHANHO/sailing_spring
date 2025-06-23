package sailing.bootcamp.spring.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class BoardDeleteResponse {

    private boolean status;

}
