package todolist

class TimeFieldException extends RuntimeException {

    TimeFieldException(def format, def value) {
        super("value  " + value + " not of format " + format);
    }
}
