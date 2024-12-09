package todolist

class TaskNotFoundException extends RuntimeException {

    TaskNotFoundException(def id) {
        super("Could not find task " + id);
    }
}
