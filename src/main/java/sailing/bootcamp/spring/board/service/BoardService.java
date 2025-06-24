package sailing.bootcamp.spring.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sailing.bootcamp.spring.board.dto.*;
import sailing.bootcamp.spring.board.entity.Board;
import sailing.bootcamp.spring.board.repository.BoardRepository;
import sailing.bootcamp.spring.exception.BoardException;

import java.util.List;
import java.util.Optional;
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

    public List<BoardGetResponse> getAllBoard() {

        List<Board> boardList = boardRepository.findAll();
        List<BoardGetResponse> boardGetResponseList = boardList.stream()
                .map(board -> BoardGetResponse.toDto(board))
                .collect(Collectors.toList());

        return boardGetResponseList;
    }

    public BoardDeleteResponse deleteBoard(BoardDeleteRequest boardDeleteRequest) {
        boolean status = true;
        try {
            boardRepository.deleteById(boardDeleteRequest.getBoardId());
            return new BoardDeleteResponse(true);
        } catch (Exception e){
            return new BoardDeleteResponse(false);
        }
    }

    public BoardGetResponse getBoard(Long boardId) {
        Board findBoard = boardRepository.findById(boardId).orElseThrow(() -> new BoardException("존재하지 않는 게시글입니다."));

        return BoardGetResponse.toDto(findBoard);
    }
}
