package sailing.bootcamp.spring.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sailing.bootcamp.spring.exception.LoginException;
import sailing.bootcamp.spring.user.dto.LoginRequest;
import sailing.bootcamp.spring.user.dto.LoginResponse;
import sailing.bootcamp.spring.user.entity.User;
import sailing.bootcamp.spring.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final UserRepository userRepository;
    private final JwtService jwtService;

    public LoginResponse login(LoginRequest loginRequest) {
        User user = userRepository.findByUsername(loginRequest.getUsername()).orElseThrow(() -> new LoginException("존재하지 않는 사용자입니다."));

        if(user.getPassword().equals(loginRequest.getPassword())){
            String accessToken = jwtService.createAccessToken(user);
            String refreshToken = jwtService.createRefreshToken(user);

            // TODO : REDIS에 Refresh Token 정보 저장하기

            return LoginResponse.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .build();
        } else {
            throw new LoginException("비밀번호가 일치하지 않습니다.");
        }

    }
}
