package sailing.bootcamp.spring.user.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import sailing.bootcamp.spring.exception.LoginException;
import sailing.bootcamp.spring.user.dto.LoginRequest;
import sailing.bootcamp.spring.user.entity.User;
import sailing.bootcamp.spring.user.repository.UserRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Slf4j
public class LoginServiceTest {

    @Autowired
    private LoginService loginService;

    @Autowired
    private UserRepository userRepository;

    @BeforeAll
    public void initUser(){
        User user = User.builder().username("chanho").password("1234").build();
        userRepository.save(user);
    }

    @Test
    @DisplayName("사용자 정보가 없는 경우 로그인 테스트")
    public void loginWithNoUser(){
        // given
        LoginRequest request = LoginRequest.builder()
                .username("jungwoo")
                .password("1234")
                .build();

        // when, then
        assertThatThrownBy(() -> loginService.login(request))
                .isInstanceOf(LoginException.class)
                .hasMessage("존재하지 않는 사용자입니다.");

    }

    @Test
    @DisplayName("비밀번호가 다를 경우 로그인 테스트")
    public void loginWithWrongPassword(){
        // given
        LoginRequest request = LoginRequest.builder()
                .username("chanho")
                .password("123")
                .build();

        // when, then
        assertThatThrownBy(() -> loginService.login(request))
                .isInstanceOf(LoginException.class)
                .hasMessage("비밀번호가 일치하지 않습니다.");
    }

    @Test
    @DisplayName("로그인 성공")
    public void loginSuccess(){
        // given
        LoginRequest request = LoginRequest.builder().username("chanho").password("1234").build();

        // when
        String token = loginService.login(request);

        // then
        assertThat(token).isNotNull();
        log.info("token = {}", token);
    }
}
