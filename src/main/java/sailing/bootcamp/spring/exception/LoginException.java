package sailing.bootcamp.spring.exception;

public class LoginException extends RuntimeException{
    public LoginException(String message) {
        super(message);
    }
}
