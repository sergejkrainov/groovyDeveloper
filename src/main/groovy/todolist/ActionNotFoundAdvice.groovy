package todolist

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
class ActionNotFoundAdvice {

    @ExceptionHandler(ActionNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String actionNotFoundHandler(ActionNotFoundException ex) {
        return ex.getMessage();
    }
}
