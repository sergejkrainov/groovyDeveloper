package todolist

import grails.validation.ValidationException

import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

import static org.springframework.http.HttpStatus.*

class TaskController {

    int index = 1
    TaskService taskService

    //static ArrayList<TaskModel> taskList = new ArrayList<TaskModel>()

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond taskService.list(params), model:[taskCount: taskService.count()]
    }

    def show(Long id) {

        def count = Task.executeQuery(
                "select count(tsk.id) from Task tsk where tsk.dueDate = ?0", ['2024-11-18'])
        taskService.get(id).setCountOfTasksByDate(count.get(0))

        List<Task> tskList = Task.findAllByDueDate("2024-11-18")
        taskService.get(id).setTaskListByDate(tskList)

        if(taskService.get(id).getActionList().size() > 0) {
            taskService.get(id).setBusyTime(getBusyTimeByDate("2024-11-18"))
        }


        respond taskService.get(id)
    }

    def create() {

        respond new Task(params)
    }

    def save(Task task) {
        if (task == null) {
            notFound()
            return
        }

        try {
            LocalDate dueDate = LocalDate.parse(task.getDueDate());
            def formatTime = "HH:mm"
            DateTimeFormatter dtFrm = DateTimeFormatter.ofPattern(formatTime)
            LocalTime startTime = LocalTime.parse(task.getStartTime(), dtFrm)
            LocalTime endTime = LocalTime.parse(task.getEndTime(), dtFrm)
            /*if(!this.checkTaskForInputTimeInterval(dueDate, startTime, endTime)){
                task.setIsCorrect(false)
            } else {
                taskList << new TaskModel(task.getTitle(), dueDate, startTime, endTime)
                task.setIsCorrect(true)
            }*/
            if(!this.checkTaskForInputTimeInterval(task.getDueDate(), task.getStartTime(), task.getEndTime())){
                task.setIsCorrect(false)
            } else {
                //taskList << new TaskModel(task.getTitle(), dueDate, startTime, endTime)
                task.setIsCorrect(true)
            }
            task.setIndex(index)
            taskService.save(task)
            index++
        } catch (ValidationException e) {
            respond task.errors, view:'create'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'task.label', default: 'Task'), task.id])
                redirect task
            }
            '*' { respond task, [status: CREATED] }
        }
    }

    def edit(Long id) {
        respond taskService.get(id)
    }

    def update(Task task) {
        if (task == null) {
            notFound()
            return
        }

        try {
            taskService.save(task)
        } catch (ValidationException e) {
            respond task.errors, view:'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'task.label', default: 'Task'), task.id])
                redirect task
            }
            '*'{ respond task, [status: OK] }
        }
    }

    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }

        taskService.delete(id)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'task.label', default: 'Task'), id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'task.label', default: 'Task'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }

    boolean checkTaskForInputTimeInterval(String dueDate, String startTime, String endTime) {

        def formatTime = "HH:mm"
        DateTimeFormatter dtFrm = DateTimeFormatter.ofPattern(formatTime)
        boolean correctTimes = false
        def searchtaskList = Task.findAllByDueDate(dueDate)
        if(searchtaskList.size() == 0){
            correctTimes = true
        } else {
            searchtaskList
                    .sort(Task::getStartTime)
                    .each{
                        boolean isIntervalBefore = LocalTime.parse(endTime, dtFrm).isBefore(LocalTime.parse(it.getStartTime(), dtFrm))
                        boolean isIntervalAfter = LocalTime.parse(startTime, dtFrm).isAfter(LocalTime.parse(it.getEndTime(), dtFrm))
                        if(isIntervalBefore || isIntervalAfter){
                            correctTimes = true
                        }
                    }
        }
        return correctTimes;
    }

    int getBusyTimeByDate(String dueDate) {

        LocalDate dueDateToSearch
        if (!(dueDate.trim().equals("") || dueDate == null)) {
            dueDateToSearch = LocalDate.parse(dueDate);
        }

        def formatTime = "HH:mm"
        DateTimeFormatter dtFrm = DateTimeFormatter.ofPattern(formatTime)

        List<Task> taskListFound = Task.findAllByDueDate("2024-11-18")
        int busyTime = 0;
        if (taskListFound.size() > 0) {
            taskListFound.sort(Task::getStartTime)
                    .each {
                        it.getActionList()
                                .each{
                                    busyTime += ((LocalTime.parse(it.getEndTime(), dtFrm).hour * 60 + LocalTime.parse(it.getEndTime(), dtFrm).minute)
                                            - (LocalTime.parse(it.getStartTime(), dtFrm).hour * 60 + LocalTime.parse(it.getStartTime(), dtFrm).minute))
                                }


                    }
        }

        return busyTime

    }

}
