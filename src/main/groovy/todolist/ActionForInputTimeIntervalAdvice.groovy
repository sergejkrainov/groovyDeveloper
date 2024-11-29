package todolist

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ActionForInputTimeIntervalAdvice {

    @ExceptionHandler(ActionForInputTimeIntervalException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String taskNotFoundHandler(ActionForInputTimeIntervalException ex) {
        return ex.getMessage();
    }
}
