package sailing.bootcamp.spring.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sailing.bootcamp.spring.board.dto.*;
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
                throw new BoardException("제목 또는 내용을 입력해주세요");
        }

        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        boardDto.setUserName(userName);

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

        Board board = boardRepository.findById(boardDeleteRequest.getBoardId())
                .orElseThrow(() -> new BoardException("존재하지 않는 게시글입니다"));

        if (board.getPassword().equals(boardDeleteRequest.getPassword())) {
            boolean status = true;
            try {
                boardRepository.deleteById(boardDeleteRequest.getBoardId());
                return new BoardDeleteResponse(true);
            } catch (Exception e) {
                return new BoardDeleteResponse(false);
            }
        } else throw new BoardException("비밀번호가 일치하지 않습니다");
    }

    public BoardGetResponse getBoard(Long boardId) {
        Board findBoard = boardRepository.findById(boardId).orElseThrow(() -> new BoardException("존재하지 않는 게시글입니다."));

        return BoardGetResponse.toDto(findBoard);
    }

    public BoardUpdateResponse updateBoard(BoardUpdateRequest boardUpdateRequest) {

        Board beforeBoard = boardRepository.findById(boardUpdateRequest.getBoardId())
                .orElseThrow(()-> new BoardException("존재하지 않는 게시글입니다"));

        if(beforeBoard.getPassword().equals(boardUpdateRequest.getPassword())){
            Board board = beforeBoard.toBuilder()
                    .title(boardUpdateRequest.getTitle())
                    .content(boardUpdateRequest.getContent())
                    .userName(boardUpdateRequest.getUserName())
                    .build();
            Board updatedBoard = boardRepository.save(board);
            return BoardUpdateResponse.toDto(updatedBoard);
        } else throw new BoardException("비밀번호가 일치하지 않습니다!");


    }
}
