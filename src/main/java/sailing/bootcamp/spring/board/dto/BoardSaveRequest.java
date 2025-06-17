package sailing.bootcamp.spring.board.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class BoardSaveRequest {

    @NotNull
    private String title;

    @NotNull
    private String content;
}
