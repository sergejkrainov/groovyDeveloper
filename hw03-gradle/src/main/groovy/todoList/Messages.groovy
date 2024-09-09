package todoList

class Messages {

    //A public constant field to hold code to RESET the text font color
    static final def RESET_TEXT = "\u001B[0m";
    //A public constant field to hold code to change the text font color to RED
    static final def RED_TEXT = "\u001B[31m";
    //A public constant field to hold code to change the text font color to GREEN
    static final def GREEN_TEXT = "\u001B[32m";

    /**
     * This method will display the main menu (top level menu) on standard output (terminal)
     * to display all options for user selection.
     * @param incompleteTaskCount takes the number of incomplete tasks (int) to show in main menu
     * @param completedTaskCount takes the number of complete tasks (int) to show in main menu
     */
    static void mainMenu(def incompleteTaskCount, def completedTaskCount) {
        println("""        MAIN MENU
        ===========
        You have ${Messages.RED_TEXT}
                ${incompleteTaskCount}  task(s) todo "
                ${Messages.RESET_TEXT}  and  ${Messages.GREEN_TEXT}
                ${completedTaskCount}  completed task(s) ${Messages.RESET_TEXT}
        Pick an option:
        (1) Show All Task List (by date or title)
        (2) Show All Actions in selected Task
        (3) Add New Task
        (4) Add New Action to Task
        (5) Edit Action (update, mark as done, remove)
        (6) Remove Task
        (7) Show Task List by date
        (8) Show count of Tasks
        (9) Show busy-time of Task by date
        (10) Save and Quit
        Please enter your choice [1-10]: """);
    }

    /**
     * This method displays the menu to standard output (terminal) to show the options to display all tasks
     * for user selection
     */
    static void listAllTasksMenu() {
        println("""      Display All Tasks
        ===================
        Pick an option:
        (1) Show Task List by date
                ${Messages.RED_TEXT}  [default choice, just press ENTER key]  ${Messages.RESET_TEXT}
        (2) Show Task List by title
        Please enter your choice [1-2]: """)
    }

    /**
     * This method displays the menu to standard output (terminal) to show the options to display all actions
     * for user selection
     */
    static void listAllActionsMenu() {
        println("""      Display All Actions
        ===================
        Pick an option:
        (1) Show Action List by Start Time
                ${Messages.RED_TEXT}  [default choice, just press ENTER key]  ${Messages.RESET_TEXT}
        (2) Show Task List by title
        Please enter your choice [1-2]: """)
    }

    /**
     * This method will display a prompt to user for typing the task number to EDIT
     */
    static void taskSelection() {
        println(GREEN_TEXT);
        print(">>> Type a task number and press ENTER key: ");
        print(RESET_TEXT);
    }

    /**
     * This method will display a prompt to user for typing the action number to EDIT
     */
    static void editActionSelection() {
        println(GREEN_TEXT);
        print(">>> Type a action number to EDIT and press ENTER key: ");
        print(RESET_TEXT);
    }

    /**
     * This method will display a prompt to user for typing the task number to EDIT
     */
    static void editTaskSelection() {
        println(GREEN_TEXT);
        print(">>> Type a task number to EDIT and press ENTER key: ");
        print(RESET_TEXT);
    }

    /**
     * This method will display the Edit menu options on standard output (terminal)
     * for user selection
     */
    public static void editTaskMenu() {
        println("""      Task Edit Options
        ======================
        Pick an option:
        (1) Modify selected task
        (2) Delete selected task")
        (3) Return to main menu 
                 ${Messages.RED_TEXT}  [default choice, just press ENTER]  ${Messages.RESET_TEXT}
        Please enter your choice [1-3]: """)
    }

    /**
     * This method will display the Edit menu options on standard output (terminal)
     * for user selection
     */
    public static void editActionMenu() {
        println("""      Action Edit Options
        ======================
        Pick an option:
        (1) Modify selected action
        (2) Mark selected action as COMPLETED
        (3) Delete selected action
        (4) Return to main menu 
                 ${Messages.RED_TEXT}  [default choice, just press ENTER]  ${Messages.RESET_TEXT}
        Please enter your choice [1-4]: """)
    }

    /**
     * This method will display the bye message while ending the program
     */
    public static void byeMessage() {
        println(GREEN_TEXT);
        println(">>> All tasks are saved to data file");
        println(">>> Good Bye");
        println(RESET_TEXT);
    }

    /**
     * This method will display the error message if a user input an option which is not
     * from the choices given in main menu
     */
    static void unknownMessage() {
        println(RED_TEXT);
        println(">>> Incorrect choice: Please type a number from given choices ");
        print(RESET_TEXT);
    }

    /**
     * This message will display any given message in RED or GREEN text on standard output (terminal)
     * @param message a text message as String
     * @param warning a boolean value, true for printing warning with RED text, and false
     *                for printing message in GREEN text on standard output (terminal)
     */
    public static void showMessage(String message, boolean warning) {
        println(warning ? RED_TEXT : GREEN_TEXT);
        println(">>> " + message);
        println(RESET_TEXT);
    }

    /**
     * This message will print the given character on standard output (terminal) to given number of times
     * @param charToPrint a character given in single quote to print, i.e., '='
     * @param times an integer to repeat printing the given character
     */
    def static void separator(def charToPrint, def times) {
        for (def index = 0; index < times; index++) print(charToPrint);
        println("");
    }
}
