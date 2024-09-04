package todoList


import java.time.DateTimeException
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class Action implements Serializable {

    // A String that holds the title of a action and it cannot be empty or null.
    private def title;
    private LocalTime startTime;
    private LocalTime endTime;
    // A boolean value, if true: the task is completed, otherwise false.
    private boolean complete;


    Action(def title, LocalTime startTime, LocalTime endTime) {

        this.setTitle(title);
        this.setStartTime(startTime);
        this.setEndTime(endTime);
        this.complete = false;
    }

    /**
     * A method to get the task title
     * @return a String containing the title of a task
     */
    def getTitle() {
        return this.title;
    }

    def getComplete() {
        return this.complete;
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

    def getStartTime() {
        return this.startTime;
    }

    def getEndTime() {
        return this.endTime;
    }

    void setStartTime(def startTime) {
        this.startTime = startTime
    }

    void setEndTime(def endTime) {
        this.endTime = endTime
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
     * A method to get the task data as formatted string to display in multiple lines
     * @return formatted string of all fields of a task
     */
    def formattedStringOfAction() {
        return (
                "\nTitle     : " + title +
                        "\nStartTime   : " + startTime +
                        "\nEndTime   : " + endTime +
                        "\nStatus    : " + (complete ? "Completed" : "NOT COMPLETED") +
                        "\n");
    }
}
