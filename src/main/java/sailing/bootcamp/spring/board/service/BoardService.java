package sailing.bootcamp.spring.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sailing.bootcamp.spring.board.dto.BoardDto;
import sailing.bootcamp.spring.board.dto.BoardSaveRequest;
import sailing.bootcamp.spring.board.dto.BoardSaveResponse;
import sailing.bootcamp.spring.board.entity.Board;
import sailing.bootcamp.spring.board.repository.BoardRepository;
import sailing.bootcamp.spring.exception.BoardException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    public BoardSaveResponse saveBoard(BoardSaveRequest boardDto) {

        if(boardDto.getTitle() == null || boardDto.getContent() == null) {
                throw new BoardException();
        }

        Board board = BoardSaveRequest.toEntity(boardDto);
        Board savedBoard = boardRepository.save(board);

        return BoardSaveResponse.toDto(savedBoard);
    }

    public List<BoardDto> getAllBoard() {

        List<Board> boardList = boardRepository.findAll();
        List<BoardDto> boardDtoList = boardList.stream()
                .map(board -> BoardDto.toDto(board))
                .collect(Collectors.toList());

        return boardDtoList;
    }
}
