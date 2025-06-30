package sailing.bootcamp.spring.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sailing.bootcamp.spring.user.dto.UserSaveRequest;
import sailing.bootcamp.spring.user.dto.UserSaveResponse;
import sailing.bootcamp.spring.user.entity.User;
import sailing.bootcamp.spring.user.repository.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserSaveResponse saveUser(UserSaveRequest userSaveRequest) {

        try {
            Optional<User> validUser = userRepository.findByUsername(userSaveRequest.getUsername());
            if(validUser.isPresent()) throw new Exception();

            User user = userSaveRequest.toEntity(userSaveRequest);
            userRepository.save(user);

            return UserSaveResponse.builder()
                    .statusCode(200)
                    .message("회원가입에 성공하였습니다")
                    .build();
        } catch (Exception e){
            e.printStackTrace();
            return UserSaveResponse.builder()
                    .statusCode(400)
                    .message("회원가입에 실패하였습니다")
                    .build();
        }

    }

}
