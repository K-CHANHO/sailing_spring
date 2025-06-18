package sailing.bootcamp.spring.board.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import sailing.bootcamp.spring.board.entity.Board;

@Builder
@Data
public class BoardSaveRequest {

    @NotNull
    private String title;

    @NotNull
    private String content;

    public static Board toEntity(BoardSaveRequest dto){
        Board entity = Board.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .build();

        return entity;
    }

}
