package todolist

class DateFieldException extends RuntimeException {

    DateFieldException(String format, String value) {
        super("value  " + value + " not of format " + format);
    }
}
