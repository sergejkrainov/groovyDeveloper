package todolist

class Task {


    int index
    String title;
    String startTime;
    String endTime;
    String dueDate;
    boolean isCorrect
    List<Action> actionList

    static constraints = {

        title minSize:1
        startTime matches:"\\d{2}:\\d{2}"
        endTime matches:"\\d{2}:\\d{2}"
        dueDate matches:"\\d{4}-\\d{2}-\\d{2}"
        isCorrect validator : { val -> val == true}

    }

    static hasMany = Action
}
