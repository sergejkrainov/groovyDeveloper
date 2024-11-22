package todolist

class ActionForInputInTimeTaskIntervalException extends RuntimeException {

    ActionForInputInTimeTaskIntervalException() {
        super("action InputTimeInterval out of task interval, enter another interval ");
    }
}
