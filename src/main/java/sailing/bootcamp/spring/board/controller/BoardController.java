package sailing.bootcamp.spring.board.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import sailing.bootcamp.spring.board.dto.BoardDto;
import sailing.bootcamp.spring.board.dto.BoardSaveRequest;
import sailing.bootcamp.spring.board.dto.BoardSaveResponse;
import sailing.bootcamp.spring.board.service.BoardService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @PostMapping("/api/v1/board")
    public ResponseEntity<BoardSaveResponse> saveBoard(@Valid @RequestBody BoardSaveRequest boardSaveRequest) {

        BoardSaveResponse savedBoard = boardService.saveBoard(boardSaveRequest);

        return new ResponseEntity<>(savedBoard, HttpStatus.CREATED);
    }

    @GetMapping("/api/v1/board")
    public ResponseEntity<List<BoardDto>> getAllBoard(){
        List<BoardDto> boardList = boardService.getAllBoard();

        return new ResponseEntity<>(boardList, HttpStatus.OK);
    }
}
