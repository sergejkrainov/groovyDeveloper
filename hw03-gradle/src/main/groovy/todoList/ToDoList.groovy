package todoList


import java.nio.file.Files
import java.nio.file.Paths
import java.time.LocalDate

class ToDoList {

    // An array list of task objects
    private def taskList
    private def taskMapGroupByProject

    /**
     * creating an TodoList object
     */
    ToDoList() {
        taskList = []
    }

    def getTaskMapGroupByProject() {
        return this.taskMapGroupByProject;
    }

    /**
     * Adding a Task object in ArrayList
     * @param title A String that holds the title of a task and it cannot be empty or null.
     * @param project A String that holds the name of project associated with task, and it could be an empty string.
     * @param dueDate The due date of the task as yyyy-mm-dd format
     */
    void addTask(String title, String project, LocalDate dueDate) {
        this.taskList << new Task(title, project, dueDate)
    }

    /**
     * A method to read the value from user (standard input, i.e., terminal)
     * to create a Task object and to add in the ArrayList of Tasks
     * @return true, if the Tasks object is created and added to ArrayList, otherwise false
     */
    boolean readTaskFromUser() {
        Scanner scan = new Scanner(System.in);

        try {
            println(Messages.GREEN_TEXT + "Please enter the following details to add a task:" + Messages.RESET_TEXT);
            print(">>> Task Title  : ");
            def title = scan.nextLine();
            print(">>> Project Name: ");
            def project = scan.nextLine();
            print(">>> Due Date [example: 2019-12-31] : ");
            LocalDate dueDate = LocalDate.parse(scan.nextLine());

            this.taskList << new Task(title, project, dueDate)
            Messages.showMessage("Task is added successfully", false);

            taskMapGroupByProject = this.taskList.groupBy{it.project}

            return true;
        } catch (Exception e) {
            Messages.showMessage(e.getMessage(), true);
            return false;
        }

    }

    /**
     * A method to read the value from user (standard input, i.e., terminal)
     * and update the given Task object in the ArrayList of Tasks
     * @param task the task object whose value need to be updated with user input
     * @return true, if the Tasks object is updated in ArrayList, otherwise false
     */
    public boolean readTaskFromUserToUpdate(Task task) {
        Scanner scan = new Scanner(System.in);
        boolean isTaskUpdated = false;

        try {
            println(Messages.GREEN_TEXT + "Please enter the following details to update a task:"
                    + "\nIf you do not want to change any field, just press ENTER key!" + Messages.RESET_TEXT);
            print(">>> Task Title  : ");
            def title = scan.nextLine();
            if (!(title.trim().equals("") || title == null)) {
                task.setTitle(title);
                isTaskUpdated = true;
            }

            print(">>> Project Name: ");
            def project = scan.nextLine();
            if (!(project.trim().equals("") || project == null)) {
                task.setProject(project);
                isTaskUpdated = true;
            }

            print(">>> Due Date [example: 2019-12-31] : ");
            def dueDate = scan.nextLine();
            if (!(dueDate.trim().equals("") || dueDate == null)) {
                task.setDueDate(LocalDate.parse(dueDate));
                isTaskUpdated = true;
            }

            Messages.showMessage("Task is " + (isTaskUpdated ? "updated successfully" : "NOT modified") + ": Returning to Main Menu", false);

            return true;
        } catch (Exception e) {
            Messages.showMessage(e.getMessage(), true);
            return false;
        }
    }

    /**
     * A method to display the contents of ArrayList with first column as task number
     */
    void listAllTasksWithIndex() {
        def displayFormat = "%-4s%-35s %-20s %-10s %-10s";

        if (taskList.size() > 0) {
            println(String.format(displayFormat, "NUM", "TITLE", "PROJECT", "DUE DATE", "COMPLETED"));
            println(String.format(displayFormat, "===", "=====", "=======", "========", "========="));
        } else {
            println(Messages.RED_TEXT + "No tasks to show" + Messages.RESET_TEXT);
        }

        taskList.eachWithIndex{ it, i ->
            println(String.format(displayFormat,
                    i + 1,
                    it.getTitle(),
                    it.getProject(),
                    it.getDueDate(),
                    (it.isComplete() ? "YES" : "NO")
            ))
        }
        /*taskList.stream()
                .forEach(task -> println(String.format(displayFormat,
                        taskList.indexOf(task) + 1,
                        task.getTitle(),
                        task.getProject(),
                        task.getDueDate(),
                        (task.isComplete() ? "YES" : "NO")
                )));*/
    }

    /**
     * A method to display the contents of ArrayList
     * @param sortBy a string holding a number, "2" for sorting by project, otherwise it will sorty by date
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
            def displayFormat = "%-20s %-35s %-10s %-10s";

            if (taskList.size() > 0) {
                println(String.format(displayFormat, "PROJECT", "TITLE", "DUE DATE", "COMPLETED"));
                println(String.format(displayFormat, "=======", "=====", "========", "========="));
            } else {
                println(Messages.RED_TEXT + "No tasks to show" + Messages.RESET_TEXT);
            }

            taskList.sort(Task::getProject)
                    .each {
                        println(String.format(displayFormat, it.getProject(),
                                it.getTitle(),
                                it.getDueDate(),
                                (it.isComplete() ? "YES" : "NO")
                        ))
                    }

            /*taskList.stream()
                    .sorted(Comparator.comparing(Task::getProject))
                    .forEach(task -> println(String.format(displayFormat, task.getProject(),
                            task.getTitle(),
                            task.getDueDate(),
                            (task.isComplete() ? "YES" : "NO")
                    )));*/
        } else {
            def displayFormat = "%-10s %-35s %-20s %-10s";

            if (taskList.size() > 0) {
                println(String.format(displayFormat, "DUE DATE", "TITLE", "PROJECT", "COMPLETED"));
                println(String.format(displayFormat, "========", "=====", "=======", "========="));
            } else {
                println(Messages.RED_TEXT + "No tasks to show" + Messages.RESET_TEXT);
            }

            taskList.sort(Task::getDueDate)
                    .each {
                        println(String.format(displayFormat, it.getDueDate(),
                                it.getTitle(),
                                it.getProject(),
                                (it.isComplete() ? "YES" : "NO")
                        ))
                    }

            /*taskList.stream()
                    .sorted(Comparator.comparing(Task::getDueDate))
                    .forEach(task -> println(String.format(displayFormat, task.getDueDate(),
                            task.getTitle(),
                            task.getProject(),
                            (task.isComplete() ? "YES" : "NO")
                    )));*/
        }
    }

    /**
     * A method to select a particular Task object from ArrayList and perform editing operations
     * @param selectedTask Task number that is selected by user from given list to perform editing operations
     * @throws NullPointerException if task number of given as empty string or null
     * @throws ArrayIndexOutOfBoundsException if task number does not fall in index range of ArrayList
     */
    void editTask(def selectedTask) throws NullPointerException {
        try {
            // checking if the task number is given and empty string or null
            if (selectedTask.trim().equals("") || selectedTask == null) {
                throw new NullPointerException("EMPTY/NULL TASK NUM: Returning to Main Menu");
            }

            int taskIndex = Integer.parseInt(selectedTask) - 1;
            if (taskIndex < 0 || taskIndex > taskList.size()) {
                throw new ArrayIndexOutOfBoundsException("TASK NUM NOT GIVEN FROM TASK LIST: Returning to Main Menu");
            }

            Task task = taskList[taskIndex]

            Messages.showMessage("Task Num " + selectedTask + "  is selected:" + task.formattedStringOfTask(), false);

            Messages.editTaskMenu();
            Scanner scan = new Scanner(System.in);
            String editChoice = scan.nextLine();
            switch (editChoice) {
                case "1":
                    readTaskFromUserToUpdate(task);
                    break;
                case "2":
                    task.markCompleted();
                    Messages.showMessage("Task Num " + selectedTask + " is marked as Completed: Returning to Main Menu", false);
                    break;
                case "3":
                    taskList - task
                    Messages.showMessage("Task Num " + selectedTask + " is Deleted: Returning to Main Menu", true);
                    break;
                default:
                    Messages.showMessage("Returning to Main Menu", true);
            }
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
        /*return (int) taskList.stream()
                .filter(Task::isComplete)
                .count();*/
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
