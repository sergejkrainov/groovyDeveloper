package todoList


import java.nio.file.Files
import java.nio.file.Paths
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class ToDoList {

    // An array list of task objects
    private ArrayList<Task> taskList
    private def taskMapGroupByTitle

    /**
     * creating an TodoList object
     */
    ToDoList() {
        taskList = new ArrayList<Task>()
    }

    def getTaskMapGroupByTitle() {
        return this.taskMapGroupByTitle;
    }

    ArrayList<Task> getTaskList() {
        return this.taskList;
    }


    void addTask(def title, LocalTime startTime, LocalTime endTime, LocalDate dueDate) {
        this.taskList << new Task(title, startTime, endTime, dueDate)
    }

    /**
     * A method to read the value from user (standard input, i.e., terminal)
     * to create a Task object and to add in the ArrayList of Tasks
     * @return true, if the Tasks object is created and added to ArrayList, otherwise false
     */
    boolean readTaskFromfUser() {
        Scanner scan = new Scanner(System.in);

        try {
            println(Messages.GREEN_TEXT + "Please enter the following details to add a task:" + Messages.RESET_TEXT);
            print(">>> Task Title  : ");
            def title = scan.nextLine();
            print(">>> Due Date [example: 2019-12-31] : ");
            LocalDate dueDate = LocalDate.parse(scan.nextLine());
            print(">>> Start Time [example: 10:25] : ");
            def formatTime = "HH:mm"
            DateTimeFormatter dtFrm = DateTimeFormatter.ofPattern(formatTime)
            LocalTime startTime = LocalTime.parse(scan.nextLine(), dtFrm)
            print(">>> End Time [example: 10:25] : ");
            LocalTime endTime = LocalTime.parse(scan.nextLine(), dtFrm)
            this.taskList << new Task(title, dueDate, startTime, endTime)
            Messages.showMessage("Task is added successfully", false);

            taskMapGroupByTitle = this.taskList.groupBy{it.title}

            return true;
        } catch (Exception e) {
            Messages.showMessage(e.getMessage(), true);
            return false;
        }

    }


    /**
     * A method to read the value from user (standard input, i.e., terminal)
     * to create a Task object and to add in the ArrayList of Actions
     * @return true, if the Actions object is created and added to ArrayList, otherwise false
     */
    boolean readTaskFromUser() {
        Scanner scan = new Scanner(System.in);

        try {
            println(Messages.GREEN_TEXT + "Please enter the following details to add a action:" + Messages.RESET_TEXT);
            print(">>> Task Title  : ");
            def title = scan.nextLine();
            print(">>> Due Date  [example: 2019-12-31] : ");
            def dueDate = scan.nextLine();
            LocalDate dueDateToSet
            if (!(dueDate.trim().equals("") || dueDate == null)) {
                dueDateToSet = LocalDate.parse(dueDate);
            }
            print(">>> Start Time [example: 10:25(HH:mm)] : ");
            def formatTime = "HH:mm"
            DateTimeFormatter dtFrm = DateTimeFormatter.ofPattern(formatTime)
            LocalTime startTime = LocalTime.parse(scan.nextLine(), dtFrm)
            print(">>> End Time [example: 10:25(HH:mm)] : ");
            LocalTime endTime = LocalTime.parse(scan.nextLine(), dtFrm)
            if(!this.checkTaskForInputTimeInterval(dueDateToSet, startTime, endTime)){
                throw new CheckIntervalException();
            }
            this.taskList << new Task(title, startTime, endTime, dueDateToSet)
            Messages.showMessage("Task is added successfully", false);
            return true;
        }catch (CheckIntervalException e) {
            Messages.showMessage(e.getMessageInfo(), true);
            return false;
        } catch (Exception e) {
            Messages.showMessage(e.getMessage(), true);
            return false;
        }

    }

    boolean checkTaskForInputTimeInterval(LocalDate dueDate, LocalTime startTime, LocalTime endTime) {

        boolean correctTimes = false
        def searchtaskList = this.taskList
            .findAll {
                it.getDueDate().equals(dueDate)
            }
        if(searchtaskList.size() == 0){
            correctTimes = true
        } else {
                    searchtaskList
                    .sort(Task::getStartTime)
                    .each{
                        boolean isIntervalBefore = endTime.isBefore(it.getStartTime())
                        boolean isIntervalAfter = startTime.isAfter(it.getEndTime())
                        if(isIntervalBefore || isIntervalAfter){
                            correctTimes = true
                        }
                    }
        }
        return correctTimes;
    }

    /**
     * A method to display the contents of ArrayList with first column as task number
     */
    void listAllTasksWithIndex() {
        def displayFormat = "%-4s%-35s %-10s %-10s %-10s %-10s";

        if (taskList.size() > 0) {
            println(String.format(displayFormat, "NUM", "TITLE", "START TIME", "END TIME", "DUE DATE", "COMPLETED"));
            println(String.format(displayFormat, "===", "=====", "==========", "========", "========", "========="));
        } else {
            println(Messages.RED_TEXT + "No tasks to show" + Messages.RESET_TEXT);
        }

        taskList.eachWithIndex{ it, i ->
            println(String.format(displayFormat,
                    i + 1,
                    it.getTitle(),
                    it.getStartTime(),
                    it.getEndTime(),
                    it.getDueDate(),
                    (it.isComplete() ? "YES" : "NO")
            ))
        }
    }

    /**
     * A method to display the contents of ArrayList
     * @param sortBy a string holding a number, "2" for sorting by title, otherwise it will sorty by date
     */
    public void listAllTasks(def sortBy) {
        Messages.separator('=', 75);
        println(
                "Total Tasks = " + taskList.size() +
                        "\t\t (Completed = " + completedCount() + "\t\t" +
                        Messages.RED_TEXT + " Not Compeleted = " + notCompletedCount() + Messages.RESET_TEXT +
                        " )");
        Messages.separator('=', 75);

        if (sortBy.equals("2")) {
            def displayFormat = "%-35s %-10s %-10s %-10s %-10s";

            if (taskList.size() > 0) {
                println(String.format(displayFormat, "TITLE", "START TIME", "END TIME", "DUE DATE", "COMPLETED"));
                println(String.format(displayFormat, "=====", "==========", "========", "========", "========="));
            } else {
                println(Messages.RED_TEXT + "No tasks to show" + Messages.RESET_TEXT);
            }

            taskList.sort(Task::getTitle)
                    .each {
                        println(String.format(displayFormat,
                                it.getTitle(),
                                it.getStartTime(),
                                it.getEndTime(),
                                it.getDueDate(),
                                (it.isComplete() ? "YES" : "NO")
                        ))
                    }

        } else {
            def displayFormat = "%-10s %-10s %-10s %-35s %-10s";

            if (taskList.size() > 0) {
                println(String.format(displayFormat, "DUE DATE", "START TIME", "END TIME", "TITLE", "COMPLETED"));
                println(String.format(displayFormat, "========", "==========", "========", "=====", "========="));
            } else {
                println(Messages.RED_TEXT + "No tasks to show" + Messages.RESET_TEXT);
            }

            taskList.sort(Task::getDueDate)
                    .each {
                        println(String.format(displayFormat, it.getDueDate(),
                                it.getStartTime(),
                                it.getEndTime(),
                                it.getTitle(),
                                (it.isComplete() ? "YES" : "NO")
                        ))
                    }

        }
    }

    /**
     * A method to display the contents of ArrayList
     * @param sortBy a string holding a number, "2" for sorting by title, otherwise it will sorty by date
     */
    public void listAllTasksByDate(String dueDate) {
        def displayFormat = "%-35s %-10s %-10s %-10s %-10s";
        Messages.separator('=', 75);

        LocalDate dueDateToSearch
        if (!(dueDate.trim().equals("") || dueDate == null)) {
            dueDateToSearch = LocalDate.parse(dueDate);
        }


        def taskListFound =  taskList.findAll {
            it.getDueDate().equals(dueDateToSearch)
        }
        if (taskListFound.size() > 0) {
            println(String.format(displayFormat, "TITLE", "START TIME", "END TIME", "DUE DATE", "COMPLETED"));
            println(String.format(displayFormat, "=====", "==========", "========", "========", "========="));
            taskListFound.sort(Task::getTitle)
                    .each {
                        println(String.format(displayFormat,
                                it.getTitle(),
                                it.getStartTime(),
                                it.getEndTime(),
                                it.getDueDate(),
                                (it.isComplete() ? "YES" : "NO")
                        ))
                    }
        } else {
            println(Messages.RED_TEXT + "No tasks to show" + Messages.RESET_TEXT);
        }

    }


    public void showBusyTimeByDate(String dueDate) {
        Messages.separator('=', 75);

        LocalDate dueDateToSearch
        if (!(dueDate.trim().equals("") || dueDate == null)) {
            dueDateToSearch = LocalDate.parse(dueDate);
        }
        def taskListFound =  taskList.findAll {
            it.getDueDate().equals(dueDateToSearch)
        }
        int busyTime = 0;
        if (taskListFound.size() > 0) {
            taskListFound.sort(Task::getStartTime)
                    .each {
                        it.getActionList()
                        .each{
                            busyTime += ((it.getEndTime().hour * 60 + it.getEndTime().minute)
                            - (it.getStartTime().hour * 60 + it.getStartTime().minute))
                        }
                    }
            Messages.showMessage("Busy time in selected date is ${busyTime} minutes", false)
        } else {
            println(Messages.RED_TEXT + "No tasks to show" + Messages.RESET_TEXT);
        }

    }

    /**
     * A method to select a particular Task object from ArrayList and perform remove operation
     * @param selectedTask number that is selected by user from given list to perform editing operations
     * @throws NullPointerException if task number of given as empty string or null
     * @throws ArrayIndexOutOfBoundsException if task number does not fall in index range of ArrayList
     */
    void removeTask(def selectedTask) throws NullPointerException {
        try {

            if (selectedTask < 0 || selectedTask > taskList.size()) {
                throw new ArrayIndexOutOfBoundsException("TASK NUM NOT GIVEN FROM TASK LIST: Returning to Main Menu");
            }

            Task task = taskList[selectedTask]

            Messages.showMessage("Task Num " + selectedTask + 1 + "  is selected:" + task.formattedStringOfTask(), false);
            taskList -= task
            Messages.showMessage("Task Num " + selectedTask + 1 + " is Deleted: Returning to Main Menu", true);

        } catch (Exception e) {
            Messages.showMessage(e.getMessage(), true);
        }
    }

    /**
     * A method to count the number of tasks with completed status
     * @return number of tasks with completed status
     */
     int completedCount() {

        return taskList.findAll {
            it -> it.isComplete()
        }.size()
    }

    /**
     * A method to count the number of tasks with incomplete status
     * @return number of tasks with incomplete status
     */
    public int notCompletedCount() {

        return taskList.findAll {
            it -> !it.isComplete()
        }.size()

        /*return (int) taskList.stream()
                .filter(task -> !task.isComplete())
                .count();*/
    }

    /**
     * This method will read the data file from disk which will contain the data of previously saved tasks
     * @param filename a string specifying the full path and extension of data file, for example,  "resources/tasks.obj"
     * @return true if the reading operation was successful, otherwise false
     */
    boolean readFromFile(def filename) {
        boolean status = false;

        try {
            if (!Files.isReadable(Paths.get(filename))) {
                Messages.showMessage("The data file, i.e., " + filename + " does not exists", true);
                return false;
            }

            FileInputStream fileInputStream = new FileInputStream(filename);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

            taskList = (ArrayList<Task>) objectInputStream.readObject();

            objectInputStream.close();
            fileInputStream.close();
            return true;

        } catch (Exception e) {
            Messages.showMessage(e.getMessage(), true);
            return false;
        }
    }

    /**
     * This method will write the data of Tasks from ArrayList to data file on disk, i.e., tasks.obj
     * @param filename a string specifying the full path and extension of data file, for example,  "resources/tasks.obj"
     * @return true if the reading operation was successful, otherwise false
     */
    boolean saveToFile(def filename) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(filename);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

            objectOutputStream.writeObject(taskList);

            objectOutputStream.close();
            fileOutputStream.close();
            return true;

        } catch (Exception e) {
            Messages.showMessage(e.getMessage(), true);
            return false;
        }
    }

}
