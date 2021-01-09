package be.ucll.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

//Todo change HttpStatus -> have to be more specific

// afhandelen van de custom exceptions
// deze exceptions moeten nog speciefieker gemaakt worden. bv. status: 409 i.p.v. bad_request
@ControllerAdvice
public class EntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity summonerNotFound(HttpClientErrorException exception){
        return ResponseEntity.status(exception.getStatusCode()).body(exception.getMessage());
    }

    // deze exceptions gebruiken
    // TODO: De anderen exceptions met deze vervangen

    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity alreadyExistsException(AlreadyExistsException exception){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(exception.getMessage());
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity notFoundException(NotFoundException exception){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }

    @ExceptionHandler(ParameterInvalidException.class)
    public ResponseEntity ParameterInvalidException(ParameterInvalidException exception){
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(exception.getMessage());
    }

    @ExceptionHandler(TooManyActivePlayersException.class)
    public ResponseEntity ParameterInvalidException(TooManyActivePlayersException exception){
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(exception.getMessage());
    }
    @ExceptionHandler(UnauthorizedException .class)
    public ResponseEntity UnauthorizedException(UnauthorizedException exception){
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(exception.getMessage());
    }

}
