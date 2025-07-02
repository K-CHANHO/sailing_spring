package sailing.bootcamp.spring.user.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {

    private int status;
    private String message;

}
