package sailing.bootcamp.spring.user.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import sailing.bootcamp.spring.user.entity.User;

@Builder
@Data
public class UserSaveRequest {

    @Size(min = 4, max = 10)
    @Pattern(regexp = "^[a-z0-9]*$")
    private String username;

    @Size(min = 8, max = 15)
    @Pattern(regexp = "^[a-zA-Z0-9]*$")
    private String password;

    public User toEntity(UserSaveRequest userSaveRequest) {
        User entity = User.builder()
                .username(userSaveRequest.getUsername())
                .password(userSaveRequest.getPassword())
                .build();

        return entity;
    }
}
