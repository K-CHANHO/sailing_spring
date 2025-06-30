package sailing.bootcamp.spring.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import sailing.bootcamp.spring.user.dto.UserSaveRequest;
import sailing.bootcamp.spring.user.dto.UserSaveResponse;
import sailing.bootcamp.spring.user.service.UserService;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/api/v1/user")
    public ResponseEntity saveUser(@Valid @RequestBody UserSaveRequest userSaveRequest){
        UserSaveResponse userSaveResponse = userService.saveUser(userSaveRequest);

        return new ResponseEntity(userSaveResponse, HttpStatus.OK);
    }

}
