package sailing.bootcamp.spring.board.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import sailing.bootcamp.spring.board.dto.BoardSaveRequest;
import sailing.bootcamp.spring.board.dto.BoardSaveResponse;

@RestController
public class BoardController {

    @PostMapping("/api/v1/board")
    public ResponseEntity<BoardSaveResponse> boardSave(@Valid @RequestBody BoardSaveRequest boardSaveRequest){

        return new ResponseEntity<BoardSaveResponse>(HttpStatus.CREATED);
    }
}
