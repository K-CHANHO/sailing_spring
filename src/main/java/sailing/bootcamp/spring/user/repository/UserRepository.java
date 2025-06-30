package sailing.bootcamp.spring.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sailing.bootcamp.spring.user.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByUsername(String username);

}
