package todolist

class Task {


    int index
    String title;
    String startTime;
    String endTime;
    String dueDate;

    Long countOfTasksByDate

    int busyTime

    boolean isCorrect
    List<Action> actionList

    List<Task> taskListByDate

    static constraints = {

        title minSize:1
        startTime matches:"\\d{2}:\\d{2}"
        endTime matches:"\\d{2}:\\d{2}"
        dueDate matches:"\\d{4}-\\d{2}-\\d{2}"
        isCorrect validator : { val -> val == true}

    }

    static hasMany = Action
}
