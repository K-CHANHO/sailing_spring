package sailing.bootcamp.spring.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sailing.bootcamp.spring.board.dto.BoardSaveRequest;
import sailing.bootcamp.spring.board.dto.BoardSaveResponse;
import sailing.bootcamp.spring.board.entity.Board;
import sailing.bootcamp.spring.board.repository.BoardRepository;
import sailing.bootcamp.spring.exception.BoardException;

@Service
@RequiredArgsConstructor
public class BoardService {

        private final BoardRepository boardRepository;

        public BoardSaveResponse save(BoardSaveRequest boardDto) {

                if(boardDto.getTitle() == null || boardDto.getContent() == null) {
                        throw new BoardException();
                }

                Board board = BoardSaveRequest.toEntity(boardDto);
                Board savedBoard = boardRepository.save(board);

                return BoardSaveResponse.toDto(savedBoard);
        }

}
