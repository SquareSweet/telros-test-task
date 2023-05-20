package prj.sqsw.telrostest.user.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Long id) {
        super("User does not exist: " + id);
    }
}
