package todolist

class ActionNotFoundException extends RuntimeException {

    ActionNotFoundException(Long id) {
        super("Could not find action " + id);
    }
}
