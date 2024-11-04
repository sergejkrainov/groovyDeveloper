package todolist

class Action {

    String title;
    int indexOfTask
    String startTime;
    String endTime;
    boolean isCorrect

    static constraints = {

        title minSize:1
        indexOfTask min:1
        startTime matches:"\\d{2}:\\d{2}"
        endTime matches:"\\d{2}:\\d{2}"
        isCorrect validator : { val -> val == true}

    }
}
