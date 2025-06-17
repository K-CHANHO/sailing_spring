package sailing.bootcamp.spring.board.service;

import lombok.RequiredArgsConstructor;
import sailing.bootcamp.spring.board.entity.Board;
import sailing.bootcamp.spring.board.repository.BoardRepository;
import sailing.bootcamp.spring.exception.BoardException;

@RequiredArgsConstructor
public class BoardService {

        private final BoardRepository boardRepository;

        public Board save(Board board) {
                if(board.getTitle() == null || board.getContent() == null) {
                        throw new BoardException();
                }

                return boardRepository.save(board);
        }

}
