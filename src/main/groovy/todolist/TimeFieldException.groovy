package todolist

class TimeFieldException extends RuntimeException {

    TimeFieldException(String format, String value) {
        super("value  " + value + " not of format " + format);
    }
}
