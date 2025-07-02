package sailing.bootcamp.spring.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import sailing.bootcamp.spring.exception.LoginException;
import sailing.bootcamp.spring.user.dto.LoginRequest;
import sailing.bootcamp.spring.user.dto.LoginResponse;
import sailing.bootcamp.spring.user.service.LoginService;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @PostMapping("/api/v1/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginrequest){

        try {
            String token = loginService.login(loginrequest);

            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", token);

            LoginResponse loginResponse = LoginResponse.builder()
                    .status(200)
                    .message("로그인 성공")
                    .build();
            return new ResponseEntity<LoginResponse>(loginResponse, headers, HttpStatus.OK);
        } catch (LoginException e){
            LoginResponse loginResponse = LoginResponse.builder()
                    .status(400)
                    .message(e.getMessage())
                    .build();
            return new ResponseEntity<>(loginResponse, HttpStatus.BAD_REQUEST);
        }


    }

}
