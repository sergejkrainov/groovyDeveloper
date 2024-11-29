package todolist

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class TimeFieldAdvice {

    @ExceptionHandler(TimeFieldException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String taskNotFoundHandler(TimeFieldException ex) {
        return ex.getMessage();
    }
}
