package todolist

class ActionNotFoundException extends RuntimeException {

    ActionNotFoundException(def id) {
        super("Could not find action " + id);
    }
}
