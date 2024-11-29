package todolist

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class TaskForInputTimeIntervalAdvice {

    @ExceptionHandler(TaskForInputTimeIntervalException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String taskNotFoundHandler(TaskForInputTimeIntervalException ex) {
        return ex.getMessage();
    }
}
