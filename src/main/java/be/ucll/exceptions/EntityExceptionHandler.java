package be.ucll.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletResponse;

//Todo change HttpStatus -> have to be more specific

@ControllerAdvice
public class EntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UsernameNotValid.class)
    public ResponseEntity usernameNotValid(HttpServletResponse response, UsernameNotValid usernameNotValid) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(usernameNotValid.getMessage());
    }

    @ExceptionHandler(UsernameAlreadyExists.class)
    public ResponseEntity usernameAlreadyExists(HttpServletResponse response, UsernameAlreadyExists usernameAlreadyExists) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(usernameAlreadyExists.getMessage());
    }

}
