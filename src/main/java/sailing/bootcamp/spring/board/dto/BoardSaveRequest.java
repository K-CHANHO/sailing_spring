package sailing.bootcamp.spring.board.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import sailing.bootcamp.spring.board.entity.Board;

import java.time.LocalDateTime;

@Builder
@Data
public class BoardSaveRequest {

    @NotNull
    private String title;

    @NotNull
    private String content;

    private String userName;

    @NotNull
    private String password;

    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    public static Board toEntity(BoardSaveRequest dto){
        Board entity = Board.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .userName(dto.getUserName())
                .password(dto.getPassword())
                .build();

        return entity;
    }

}
