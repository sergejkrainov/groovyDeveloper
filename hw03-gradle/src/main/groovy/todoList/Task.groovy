package todoList


import java.time.DateTimeException
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class Task implements Serializable {

    // A String that holds the title of a task and it cannot be empty or null.
    private def title;
    // A String that holds the name of project associated with task, and it could be an empty string.
    private def project;
    // A boolean value, if true: the task is completed, otherwise false.
    private boolean complete;
    // The due date of the task as yyyy-mm-dd format
    private LocalDate dueDate;


    /**
     * Creating an object of Task class
     * @param title A String that holds the title of a task and it cannot be empty or null.
     * @param project A String that holds the name of project associated with task, and it could be an empty string.
     * @param dueDate The due date of the task as yyyy-mm-dd format
     */
    public Task(def title, def project, LocalDate dueDate) {

        this.setTitle(title);
        this.setProject(project);
        this.complete = false;
        this.setDueDate(dueDate);
    }

    /**
     * A method to get the task title
     * @return a String containing the title of a task
     */
    def getTitle() {
        return this.title;
    }

    /**
     * A method to set the title of a Task object
     * @param title A String that holds the title of a task and it cannot be empty or null.
     * @throws NullPointerException if title is null or empty string
     */
    void setTitle(def title) throws NullPointerException {
        if (title.trim().equals("") || title == null) {
            throw new NullPointerException("REQUIRED: Title can not be empty.");
        }
        this.title = title.trim();
    }

    /**
     * A method to get the project name
     * @return a String containing the project name
     */
    def getProject() {
        return this.project;
    }

    /**
     * A method to set the project name
     * @param project A String that holds the name of project associated with task, and it could be an empty string.
     */
    void setProject(def project) {
        this.project = project.trim();
    }

    /**
     * A method to get the completed status of task
     * @return true: if the task is marked as completed, otherwise it will return false
     */
    boolean isComplete() {
        return this.complete;
    }

    /**
     * A method to mark a task as in complete
     * @return the updated value of the field complete
     */
    boolean markInComplete() {
        this.complete = false;
        return this.complete;
    }

    /**
     * A method to mark a task as completed
     * @return the updated value of the field complete
     */
    boolean markCompleted() {
        this.complete = true;
        return this.complete;
    }

    /**
     * A method to get the due date of the task
     * @return the due date of task as LocalDate object
     */
    LocalDate getDueDate() {
        return dueDate;
    }

    /**
     * A method to set the due date of a task
     * @param dueDate The due date of the task as yyyy-mm-dd format
     * @throws DateTimeException if given date is a past date
     */
    void setDueDate(LocalDate dueDate) throws DateTimeException {
        // Throw DateTimeException if past date is given
        if (dueDate.compareTo(LocalDate.now()) < 0) {
            throw new DateTimeException("Past Date not allowed");
        }

        //Ensure dueDate is saved as yyyy-MM-dd
        DateTimeFormatter formattedDate = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.dueDate = LocalDate.parse(dueDate.format(formattedDate));
    }

    /**
     * A method to get the task data as formatted string to display in multiple lines
     * @return formatted string of all fields of a task
     */
    def formattedStringOfTask() {
        return (
                "\nTitle     : " + title +
                        "\nProject   : " + project +
                        "\nStatus    : " + (complete ? "Completed" : "NOT COMPLETED") +
                        "\nDue Date  : " + dueDate +
                        "\n");
    }
}
