package sailing.bootcamp.spring.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sailing.bootcamp.spring.board.entity.Board;

public interface BoardRepository extends JpaRepository<Board, Long> {
}
