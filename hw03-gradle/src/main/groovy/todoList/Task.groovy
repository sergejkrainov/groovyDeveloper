package todoList


import java.time.DateTimeException
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class Task implements Serializable {

    // A String that holds the title of a action and it cannot be empty or null.
    private def title;
    private LocalTime startTime;
    private LocalTime endTime;
    private LocalDate dueDate;
    // A boolean value, if true: the task is completed, otherwise false.
    private boolean complete;
    def actionList = new ArrayList<Action>()



    Task(def title, LocalTime startTime, LocalTime endTime, LocalDate dueDate) {

        this.setTitle(title);
        this.setStartTime(startTime);
        this.setEndTime(endTime);
        this.setDueDate(dueDate);
        this.complete = false;
    }

    void addAction(def title, LocalTime startTime, LocalTime endTime) {
        this.actionList << new Action(title, startTime, endTime)
    }

    /**
     * A method to select a particular Action object from ArrayList and perform editing operations
     * @param selectedAction number that is selected by user from given list to perform editing operations
     * @throws NullPointerException if action number of given as empty string or null
     * @throws ArrayIndexOutOfBoundsException if action number does not fall in index range of ArrayList
     */
    void editAction(def selectedAction) throws NullPointerException {
        try {
            // checking if the task number is given and empty string or null
            if (selectedAction.trim().equals("") || selectedAction == null) {
                throw new NullPointerException("EMPTY/NULL ACTION NUM: Returning to Main Menu");
            }

            int actionIndex = Integer.parseInt(selectedAction) - 1;
            if (actionIndex < 0 || actionIndex > actionList.size()) {
                throw new ArrayIndexOutOfBoundsException("ACTION NUM NOT GIVEN FROM ACTION LIST: Returning to Main Menu");
            }

            Action action = actionList[actionIndex]

            Messages.showMessage("Action Num " + selectedAction + "  is selected:" + action.formattedStringOfAction(), false);
            Messages.editActionMenu();
            Scanner scan = new Scanner(System.in);
            String editChoice = scan.nextLine();
            switch (editChoice) {
                case "1":
                    readActionFromUserToUpdate(action);
                    break;
                case "2":
                    action.markCompleted();
                    Messages.showMessage("Action Num " + selectedAction + " is marked as Completed: Returning to Main Menu", false);
                    calculateCompleteField()
                    break;
                case "3":
                    actionList -= action
                    Messages.showMessage("Action Num " + selectedAction + " is Deleted: Returning to Main Menu", true);
                    calculateCompleteField()
                    break;
                default:
                    Messages.showMessage("Returning to Main Menu", true);
            }
        } catch (Exception e) {
            Messages.showMessage(e.getMessage(), true);
        }
    }

    /**
     * A method to display the contents of ArrayList
     * @param sortBy a string holding a number, "2" for sorting by title, otherwise it will sorty by start time
     */
    public void listAllActions(def sortBy) {
        Messages.separator('=', 75);
        println(
                "Total Actions = " + actionList.size() +
                        "\t\t (Completed = " + completedActionsCount() + "\t\t" +
                        Messages.RED_TEXT + " Not Compeleted = " + notCompletedActionsCount() + Messages.RESET_TEXT +
                        " )");
        Messages.separator('=', 75);

        if (sortBy.equals("2")) {
            def displayFormat = "%-20s %-35s %-10s %-10s";

            if (actionList.size() > 0) {
                println(String.format(displayFormat, "TITLE", "START TIME", "END TIME", "COMPLETED"));
                println(String.format(displayFormat, "=====", "==========", "========", "========="));
            } else {
                println(Messages.RED_TEXT + "No actions to show" + Messages.RESET_TEXT);
            }

            actionList.sort(Action::getTitle)
                    .each {
                        println(String.format(displayFormat,
                                it.getTitle(),
                                it.getStartTime(),
                                it.getEndTime(),
                                (it.isComplete() ? "YES" : "NO")
                        ))
                    }

        } else {
            def displayFormat = "%-10s %-35s %-20s %-10s";

            if (actionList.size() > 0) {
                println(String.format(displayFormat, "START TIME", "END TIME", "TITLE", "COMPLETED"));
                println(String.format(displayFormat,  "==========", "========", "=====", "========="));
            } else {
                println(Messages.RED_TEXT + "No actions to show" + Messages.RESET_TEXT);
            }

            actionList.sort(Action::getStartTime)
                    .each {
                        println(String.format(displayFormat,
                                it.getStartTime(),
                                it.getEndTime(),
                                it.getTitle(),
                                (it.isComplete() ? "YES" : "NO")
                        ))
                    }

        }
    }

    /**
     * A method to display the contents of ArrayList with first column as action number
     */
    void listAllActionsWithIndex() {
        def displayFormat = "%-4s%-35s %-20s %-10s %-10s";

        if (actionList.size() > 0) {
            println(String.format(displayFormat, "NUM", "TITLE", "START TIME", "END TIME", "COMPLETED"));
            println(String.format(displayFormat, "===", "=====", "==========", "========", "========="));
        } else {
            println(Messages.RED_TEXT + "No actions to show" + Messages.RESET_TEXT);
        }

        actionList.eachWithIndex{ it, i ->
            println(String.format(displayFormat,
                    i + 1,
                    it.getTitle(),
                    it.getStartTime(),
                    it.getEndTime(),
                    (it.isComplete() ? "YES" : "NO")
            ))
        }
    }

    /**
     * A method to read the value from user (standard input, i.e., terminal)
     * to create a Action object and to add in the ArrayList of Actions
     * @return true, if the Actions object is created and added to ArrayList, otherwise false
     */
    boolean readActionFromUser() {
        Scanner scan = new Scanner(System.in);

        try {
            println(Messages.GREEN_TEXT + "Please enter the following details to add a action:" + Messages.RESET_TEXT);
            print(">>> Action Title  : ");
            def title = scan.nextLine();
            print(">>> Start Time [example: 10:25](HH:mm) : ");
            def formatTime = "HH:mm"
            DateTimeFormatter dtFrm = DateTimeFormatter.ofPattern(formatTime)
            LocalTime startTime = LocalTime.parse(scan.nextLine(), dtFrm)
            print(">>> End Time [example: 10:25](HH:mm) : ");
            LocalTime endTime = LocalTime.parse(scan.nextLine(), dtFrm)
            if(!this.checkActionForInputInTimeTaskInterval(startTime, endTime)){
                throw new CheckOutIntervalException();
            }
            if(this.actionList.size() > 0) {
                if (!this.checkActionForInputTimeInterval(startTime, endTime, null)) {
                    throw new CheckIntervalException();
                }
            }
            this.actionList << new Action(title, startTime, endTime)
            Messages.showMessage("Action is added successfully", false);

            return true;
        }catch (CheckOutIntervalException e) {
            Messages.showMessage(e.getMessageInfo(), true);
            return false;
        }catch (CheckIntervalException e) {
            Messages.showMessage(e.getMessageInfo(), true);
            return false;
        } catch (Exception e) {
            Messages.showMessage(e.getMessage(), true);
            return false;
        }

    }

    void calculateCompleteField() {
        int counter = 0
        this.actionList.
            each{
                if(it.getComplete()) {
                    counter++
                }
            }
        if(counter == this.actionList.size()) {
            this.markCompleted();
        }
    }

    boolean checkActionForInputInTimeTaskInterval(LocalTime startTime, LocalTime endTime) {

        boolean correctTimes = false
        boolean isIntervalBeforeEndTask = endTime.isBefore(this.getEndTime()) || endTime.equals(this.getEndTime())
        boolean isIntervalAfterStartTask = startTime.isAfter(this.getStartTime()) || startTime.equals(this.getStartTime())
        if(isIntervalBeforeEndTask && isIntervalAfterStartTask){
            correctTimes = true
        } else{
            correctTimes = false
            return correctTimes
        }
        return correctTimes;
    }

    boolean checkActionForInputTimeInterval(LocalTime startTime, LocalTime endTime, Action action) {

        boolean correctTimes = false
        this.actionList
            .sort(Action::getStartTime)
            .each{
                if(!it.equals(action)) {
                    boolean isIntervalBefore = endTime.isBefore(it.getStartTime())
                    boolean isIntervalAfter = startTime.isAfter(it.getEndTime())
                    if (isIntervalBefore || isIntervalAfter) {
                        correctTimes = true
                    } else {
                        correctTimes = false
                        return correctTimes
                    }
                }
            }
        return correctTimes;
    }

    /**
     * A method to read the value from user (standard input, i.e., terminal)
     * and update the given Action object in the ArrayList of Actions
     * @param action the action object whose value need to be updated with user input
     * @return true, if the Actions object is updated in ArrayList, otherwise false
     */
    public boolean readActionFromUserToUpdate(Action action) {
        Scanner scan = new Scanner(System.in);
        boolean isActionUpdated = false;

        try {
            println(Messages.GREEN_TEXT + "Please enter the following details to update a action:"
                    + "\nIf you do not want to change any field, just press ENTER key!" + Messages.RESET_TEXT);
            print(">>> Action Title  : ");
            def title = scan.nextLine();
            if (!(title.trim().equals("") || title == null)) {
                action.setTitle(title);
                isActionUpdated = true;
            }

            print(">>> Start time [example: 12:30](HH:mm) : ");
            def startTimeStr = scan.nextLine();

            print(">>> End time [example: 12:30](HH:mm) : ");
            def endTimeStr = scan.nextLine();

            def formatTime = "HH:mm"
            DateTimeFormatter dtFrm = DateTimeFormatter.ofPattern(formatTime)

            LocalTime startTime
            LocalTime endTime

            boolean checkInputStartTime = !(startTimeStr.trim().equals("") || startTimeStr == null)
            boolean checkInputEndTime = !(endTimeStr.trim().equals("") || endTimeStr == null)

            if (checkInputStartTime && checkInputEndTime) {
                startTime = LocalTime.parse(startTimeStr, dtFrm)
                endTime = LocalTime.parse(endTimeStr, dtFrm)
                if(!this.checkActionForInputInTimeTaskInterval(startTime, endTime)){
                    throw new CheckOutIntervalException();
                }
                if(!this.checkActionForInputTimeInterval(startTime, endTime, action)){
                    throw new CheckIntervalException();
                }
                action.setStartTime(startTime)
                action.setEndTime(endTime);
                isActionUpdated = true;
            }

            Messages.showMessage("Action is " + (isActionUpdated ? "updated successfully" : "NOT modified") + ": Returning to Main Menu", false);

            return true;
        }catch (CheckOutIntervalException e) {
            Messages.showMessage(e.getMessageInfo(), true);
            return false;
        }catch (CheckIntervalException e) {
            Messages.showMessage(e.getMessageInfo(), true);
            return false;
        } catch (Exception e) {
            Messages.showMessage(e.getMessage(), true);
            return false;
        }
    }


    /**
     * A method to count the number of tasks with completed status
     * @return number of tasks with completed status
     */
    int completedActionsCount() {

        return actionList.findAll {
            it -> it.isComplete()
        }.size()
    }

    /**
     * A method to count the number of tasks with incomplete status
     * @return number of tasks with incomplete status
     */
    public int notCompletedActionsCount() {

        return actionList.findAll {
            it -> !it.isComplete()
        }.size()

        /*return (int) taskList.stream()
                .filter(task -> !task.isComplete())
                .count();*/
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

        this.dueDate = dueDate
    }

    /**
     * A method to get the task data as formatted string to display in multiple lines
     * @return formatted string of all fields of a task
     */
    def formattedStringOfTask() {
        return (
                "\nTitle     : " + title +
                        "\nStartTime   : " + startTime +
                        "\nEndTime   : " + endTime +
                        "\nStatus    : " + (complete ? "Completed" : "NOT COMPLETED") +
                        "\nDue Date  : " + dueDate +
                        "\n");
    }
}
