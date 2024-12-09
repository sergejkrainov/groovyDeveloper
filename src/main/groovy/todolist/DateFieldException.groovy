package todolist

class DateFieldException extends RuntimeException {

    DateFieldException(def format, def value) {
        super("value  " + value + " not of format " + format);
    }
}
