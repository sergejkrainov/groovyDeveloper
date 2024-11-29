package todolist

class TaskForInputTimeIntervalException extends RuntimeException {

    TaskForInputTimeIntervalException() {
        super("Task InputTimeInterval is busy, enter another interval ");
    }
}
