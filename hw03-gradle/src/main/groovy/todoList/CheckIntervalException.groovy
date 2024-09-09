package todoList

class CheckIntervalException extends Exception{

    String getMessageInfo() {
        return "Time interval is busy. Please input free time interval..."
    }
}
