package sailing.bootcamp.spring.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sailing.bootcamp.spring.exception.LoginException;
import sailing.bootcamp.spring.user.dto.LoginRequest;
import sailing.bootcamp.spring.user.entity.User;
import sailing.bootcamp.spring.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final UserRepository userRepository;
    private final JwtService jwtService;

    public String login(LoginRequest loginRequest) {
        User user = userRepository.findByUsername(loginRequest.getUsername()).orElseThrow(() -> new LoginException("존재하지 않는 사용자입니다."));

        String token = "";
        if(user.getPassword().equals(loginRequest.getPassword())){
            token = jwtService.createToken(user.getUsername());
        } else {
            throw new LoginException("비밀번호가 일치하지 않습니다.");
        }

        return token;

    }
}
