package todolist

class CheckFields {

    static boolean checkDateField(String checkValue) {
        def digitPattern = ~/\d{4}-\d{2}-\d{2}/
        return digitPattern.matcher(checkValue).matches()
    }

    static boolean checkTimeField(String checkValue) {
        def digitPattern = ~/\d{2}:\d{2}/
        return digitPattern.matcher(checkValue).matches()
    }
}
