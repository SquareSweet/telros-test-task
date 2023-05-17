package prj.sqsw.telrostest.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import prj.sqsw.telrostest.user.exception.UserNotFoundException;

import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiError> handleNotFoundException(RuntimeException exception) {
        log.error(exception.getMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ApiError.builder()
                        .errors(exception.getStackTrace())
                        .message(exception.getMessage())
                        .reason(exception.getLocalizedMessage())
                        .status(HttpStatus.NOT_FOUND)
                        .timestamp(LocalDateTime.now())
                        .build());
    }

    @ExceptionHandler({MethodArgumentNotValidException.class, MissingServletRequestParameterException.class})
    public ResponseEntity<ApiError> handleBadRequest(Exception exception) {
        log.error(exception.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiError.builder()
                        .errors(exception.getStackTrace())
                        .message(exception.getMessage())
                        .reason(exception.getLocalizedMessage())
                        .status(HttpStatus.BAD_REQUEST)
                        .timestamp(LocalDateTime.now())
                        .build());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiError> handleConflict(DataIntegrityViolationException exception) {
        log.error(exception.getMessage());
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(ApiError.builder()
                        .errors(exception.getStackTrace())
                        .message(exception.getMessage())
                        .reason(exception.getLocalizedMessage())
                        .status(HttpStatus.CONFLICT)
                        .timestamp(LocalDateTime.now())
                        .build());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiError> handleException(Exception exception) {
        log.error(exception.getMessage());
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiError.builder()
                        .errors(exception.getStackTrace())
                        .message(exception.getMessage())
                        .reason(exception.getLocalizedMessage())
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .timestamp(LocalDateTime.now())
                        .build());
    }
}
