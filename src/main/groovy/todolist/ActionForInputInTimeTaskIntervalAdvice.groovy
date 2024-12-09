package todolist

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ActionForInputInTimeTaskIntervalAdvice {

    @ExceptionHandler(ActionForInputInTimeTaskIntervalException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    def taskNotFoundHandler(ActionForInputInTimeTaskIntervalException ex) {
        return ex.getMessage();
    }
}
