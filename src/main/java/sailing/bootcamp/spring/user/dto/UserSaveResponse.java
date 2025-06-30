package sailing.bootcamp.spring.user.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserSaveResponse {

    private int statusCode;
    private String message;
}
