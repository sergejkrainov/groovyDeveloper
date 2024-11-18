package todolist

import grails.validation.ValidationException

import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

import static org.springframework.http.HttpStatus.*

class ActionController {

    ActionService actionService
    TaskService taskService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond actionService.list(params), model:[actionCount: actionService.count()]
    }

    def show(Long id) {
        respond actionService.get(id)
    }

    def create() {
        respond new Action(params)
    }

    def save(Action action) {
        if (action == null) {
            notFound()
            return
        }

        try {
            //Task tsk = taskService.get(action.getIndexOfTask())
            Task tsk = Task.findByIndex(action.getIndexOfTask())
            //TaskModel tskMod = TaskController.taskList.get(action.getIndexOfTask() - 1)
            def formatTime = "HH:mm"
            DateTimeFormatter dtFrm = DateTimeFormatter.ofPattern(formatTime)
            LocalTime startTime = LocalTime.parse(action.getStartTime(), dtFrm)
            LocalTime endTime = LocalTime.parse(action.getEndTime(), dtFrm)
            if(!this.checkActionForInputInTimeTaskInterval(action.getStartTime(), action.getEndTime(), tsk)){
                action.setIsCorrect(false)
            }else if(tsk.getActionList().size() > 0) {
                if (!this.checkActionForInputTimeInterval(action.getStartTime(), action.getEndTime(),  tsk)) {
                    action.setIsCorrect(false)
                } else {
                    //tskMod.actionList << new ActionModel(action.getTitle(), action.getStartTime(), action.getEndTime())
                    action.setIsCorrect(true)
                }
            } else {
                //tskMod.actionList << new ActionModel(action.getTitle(), action.getStartTime(), action.getEndTime())
                action.setIsCorrect(true)
            }
            List<Action> lst = tsk.getActionList()
            lst.add(action)
            tsk.setActionList(lst)
            taskService.save(tsk)
            actionService.save(action)
        } catch (ValidationException e) {
            respond action.errors, view:'create'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'action.label', default: 'Action'), action.id])
                redirect action
            }
            '*' { respond action, [status: CREATED] }
        }
    }

    def edit(Long id) {
        respond actionService.get(id)
    }

    def update(Action action) {
        if (action == null) {
            notFound()
            return
        }

        try {
            actionService.save(action)
        } catch (ValidationException e) {
            respond action.errors, view:'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'action.label', default: 'Action'), action.id])
                redirect action
            }
            '*'{ respond action, [status: OK] }
        }
    }

    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }

        actionService.delete(id)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'action.label', default: 'Action'), id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'action.label', default: 'Action'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }

    boolean checkActionForInputInTimeTaskInterval(String startTimeStr, String endTimeStr, Task task) {

        def formatTime = "HH:mm"
        DateTimeFormatter dtFrm = DateTimeFormatter.ofPattern(formatTime)
        LocalTime startTime = LocalTime.parse(startTimeStr, dtFrm)
        LocalTime endTime = LocalTime.parse(endTimeStr, dtFrm)

        boolean correctTimes = false
        boolean isIntervalBeforeEndTask = endTime.isBefore(LocalTime.parse(task.getEndTime(), dtFrm)) || endTime.equals(LocalTime.parse(task.getEndTime(), dtFrm))
        boolean isIntervalAfterStartTask = startTime.isAfter(LocalTime.parse(task.getStartTime(), dtFrm)) || startTime.equals(LocalTime.parse(task.getStartTime(), dtFrm))
        if(isIntervalBeforeEndTask && isIntervalAfterStartTask){
            correctTimes = true
        } else{
            correctTimes = false
            return correctTimes
        }
        return correctTimes;
    }

    boolean checkActionForInputTimeInterval(String startTimeStr, String endTimeStr, Task tsk) {
        def formatTime = "HH:mm"
        DateTimeFormatter dtFrm = DateTimeFormatter.ofPattern(formatTime)
        LocalTime startTime = LocalTime.parse(startTimeStr, dtFrm)
        LocalTime endTime = LocalTime.parse(endTimeStr, dtFrm)
        def result = Person.withCriteria {
            if(false) order("salary", "desc")
            if(true) like("name", "%i%")
        }
        boolean correctTimes = false
        tsk.getActionList()
                .sort(Action::getStartTime)
                .each{
                        boolean isIntervalBefore = endTime.isBefore(LocalTime.parse(it.getStartTime(), dtFrm))
                        boolean isIntervalAfter = startTime.isAfter(LocalTime.parse(it.getEndTime(), dtFrm))
                        if (isIntervalBefore || isIntervalAfter) {
                            correctTimes = true
                        } else {
                            correctTimes = false
                            return correctTimes
                        }
                }
        return correctTimes;
    }
}
