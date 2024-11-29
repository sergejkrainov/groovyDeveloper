package todolist

class TaskNotFoundException extends RuntimeException {

    TaskNotFoundException(Long id) {
        super("Could not find task " + id);
    }
}
