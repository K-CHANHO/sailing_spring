package sailing.bootcamp.spring.user.service;

import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Slf4j
class JwtServiceTest {

    @Autowired
    JwtService jwtService;

    @Test
    @DisplayName("토큰 발급 테스트")
    public void createToken(){
        String token = jwtService.createToken("chanho");

        log.info("token : {}", token);
    }

    @Test
    @DisplayName("토큰 검증 테스트")
    public void validToken(){
        boolean validateToken = jwtService.validateToken("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJjaGFuaG8iLCJleHAiOjE3NTEyOTE4ODYsImlhdCI6MTc1MTI4ODI4Nn0.i9-6hSWOI6ab1YWt6gAlgntwNHFJLHu5o0sECYw7G0EYLsoxLooiiEFLq_VSbWZYikJqcGgF_8B2pG03l4uKvw");
        assertThat(validateToken).isTrue();
    }

    @Test
    @DisplayName("토큰 유저정보 테스트")
    public void getUserInfoFromToken(){
        Claims claims = jwtService.getUserInfoFromToken("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJjaGFuaG8iLCJleHAiOjE3NTEyOTE4ODYsImlhdCI6MTc1MTI4ODI4Nn0.i9-6hSWOI6ab1YWt6gAlgntwNHFJLHu5o0sECYw7G0EYLsoxLooiiEFLq_VSbWZYikJqcGgF_8B2pG03l4uKvw");
        assertThat(claims.getSubject()).isEqualTo("chanho");
    }

}