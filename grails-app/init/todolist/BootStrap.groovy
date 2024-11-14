package todolist

class BootStrap {

    def init = { servletContext ->

        Action action1 = new Action(isCorrect: true, indexOfTask:1, title: "action1",
                startTime: "10:00", endTime: "15:00").save()
        Action action2 = new Action(isCorrect: true, indexOfTask:1, title: "action1",
                startTime: "16:00", endTime: "20:00").save()
        Action action3 = new Action(isCorrect: true, indexOfTask:2, title: "action1",
                startTime: "08:00", endTime: "12:00").save()
        Action action4 = new Action(isCorrect: true, indexOfTask:2, title: "action1",
                startTime: "13:00", endTime: "20:00").save()

        /*List<Action> actionList1 = new ArrayList<>()
        actionList1.add(action1)
        actionList1.add(action2)
        List<Action> actionList2 = new ArrayList<>()
        actionList2.add(action3)
        actionList2.add(action4)*/
        Task task1 = new Task(isCorrect: true, index:1, title: "task1",
                startTime: "06:00", endTime: "22:00",
                dueDate : "2024-11-13", "actionList[0]": action1, "actionList[1]": action2)
        Task  task2 = new Task(isCorrect: true, index:2, title: "task2",
                startTime: "06:00", endTime: "22:00",
                dueDate: "2024-11-12", actionList: [action3, action4])
        task1.save()
        task2.save()



    }
    def destroy = {
    }
}
