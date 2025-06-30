package sailing.bootcamp.spring.user.repository;

import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import sailing.bootcamp.spring.user.entity.User;

@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("유저생성 테스트")
    public void saveUser(){
        // given
        User user = User.builder()
                .username("cksgh1565")
                .password("test1")
                .build();

        // when
        User savedUser = userRepository.save(user);

        // then
        Assertions.assertThat(savedUser).isNotNull();
    }
}
