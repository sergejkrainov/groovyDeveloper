package todoList

class CheckOutIntervalException extends Exception{

    String getMessageInfo() {
        return "Time interval is out of task time interval. Please input time interval in task time interval"
    }
}
