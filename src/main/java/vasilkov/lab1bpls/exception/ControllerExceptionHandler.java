package vasilkov.lab1bpls.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(value = {ResourceNotFoundException.class})
    public ResponseEntity<ErrorMessage> resourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {

        ErrorMessage message2 = new ErrorMessage(
                HttpStatus.NOT_FOUND.value(),
                new Date(),
                "ExpiredJwtException",
                request.getDescription(false)
        );
        return new ResponseEntity<ErrorMessage>(message2, HttpStatus.NOT_FOUND);
    }

    //todo
//    @ExceptionHandler(value = {ExpiredJwtException.class})
//    @ResponseStatus(value = HttpStatus.NOT_FOUND)
//    public  ResponseEntity<ErrorMessage>  resourceNotFoundException(ExpiredJwtException ex, WebRequest request) {
//
//        ErrorMessage message2 = new ErrorMessage(
//                HttpStatus.NOT_FOUND.value(),
//                new Date(),
//                "ExpiredJwtException",
//                request.getDescription(false)
//        );
//
//        return new ResponseEntity<ErrorMessage>( message2, HttpStatus.NOT_FOUND);
//    }
}