package sailing.bootcamp.spring.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import sailing.bootcamp.spring.board.entity.Board;

@Component
public interface BoardRepository extends JpaRepository<Board, Long>, BoardRepositoryCustom {

}
