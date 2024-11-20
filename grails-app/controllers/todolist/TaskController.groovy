package todolist

import grails.rest.RestfulController
import grails.validation.ValidationException

import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

import static org.springframework.http.HttpStatus.*

class TaskController extends RestfulController{

    int index = 1
    TaskService taskService

    static ArrayList<TaskModel> taskList = new ArrayList<TaskModel>()

    TaskController(Class resource) {
        super(resource)
    }

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond taskService.list(params), model:[taskCount: taskService.count()]
    }

    def indexMy(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond taskService.list(params), model:[taskCount: taskService.count()]
    }

    def show(Long id) {
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
            if(!this.checkTaskForInputTimeInterval(dueDate, startTime, endTime)){
                task.setIsCorrect(false)
            } else {
                taskList << new TaskModel(task.getTitle(), dueDate, startTime, endTime)
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
}
