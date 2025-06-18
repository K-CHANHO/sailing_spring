package sailing.bootcamp.spring.board.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import sailing.bootcamp.spring.board.dto.BoardSaveRequest;
import sailing.bootcamp.spring.board.dto.BoardSaveResponse;
import sailing.bootcamp.spring.board.entity.Board;
import sailing.bootcamp.spring.board.service.BoardService;

@RestController
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @PostMapping("/api/v1/board")
    public ResponseEntity<BoardSaveResponse> boardSave(@Valid @RequestBody BoardSaveRequest boardSaveRequest) {

        BoardSaveResponse savedBoard = boardService.save(boardSaveRequest);

        return new ResponseEntity<>(savedBoard, HttpStatus.CREATED);
    }
}
