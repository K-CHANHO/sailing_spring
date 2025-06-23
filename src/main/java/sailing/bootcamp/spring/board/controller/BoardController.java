package sailing.bootcamp.spring.board.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sailing.bootcamp.spring.board.dto.*;
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

    @DeleteMapping("/api/v1/board")
    public ResponseEntity<BoardDeleteResponse> deleteBoard(@RequestBody BoardDeleteRequest boardDeleteRequest){
        BoardDeleteResponse boardDeleteResponse = boardService.deleteBoard(boardDeleteRequest);

        if(boardDeleteResponse.isStatus())
            return new ResponseEntity<>(boardDeleteResponse, HttpStatus.OK);
        else
            return new ResponseEntity<>(boardDeleteResponse, HttpStatus.BAD_REQUEST);
    }
}
