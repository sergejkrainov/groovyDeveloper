package todolist

import java.time.LocalTime
import java.time.format.DateTimeFormatter

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
class ActionController {

    private final ActionRepository repositoryAct;
    private final TaskRepository repositoryTask;


    ActionController(ActionRepository repositoryAct, TaskRepository repositoryTask) {
        this.repositoryAct = repositoryAct;
        this.repositoryTask = repositoryTask;
    }


    // Aggregate root
    // tag::get-aggregate-root[]
    @GetMapping("/actions")
    List<Action> all() {
        return repositoryAct.findAll();
    }
    // end::get-aggregate-root[]

    @PostMapping("/actions")
    Action newAction(@RequestBody Action newAction) {
        if(!CheckFields.checkTimeField(newAction.getStartTime())){
            throw new TimeFieldException("hh:mm", newAction.getStartTime())
        }
        if(!CheckFields.checkTimeField(newAction.getEndTime())){
            throw new TimeFieldException("hh:mm", newAction.getEndTime())
        }

        Task tsk = repositoryTask.findById(newAction.getIndexOfTask()).get()
        if(!this.checkActionForInputInTimeTaskInterval(newAction.getStartTime(), newAction.getEndTime(), tsk)){
            throw new ActionForInputInTimeTaskIntervalException()
        }else if(tsk.getActionList().size() > 0) {
            if (!this.checkActionForInputTimeInterval(newAction.getStartTime(), newAction.getEndTime(),  tsk)) {
                throw new ActionForInputTimeIntervalException()
            }
        }
        return repositoryAct.save(newAction);
    }

    // Single item

    @GetMapping("/actions/{id}")
    Action one(@PathVariable(name = "id") Long id) {

        /*if(id == 1){
            throw new ActionNotFoundException(id)
        } else*/
        return repositoryAct.findById(id)
                .orElseThrow(() -> new ActionNotFoundException(id));
    }

    @PutMapping("/actions/{id}")
    Action replaceAction(@RequestBody Action newAction, @PathVariable(name = "id") Long id) {

        return repositoryAct.findById(id)
                .map(action -> {
                    action.setTitle(newAction.getTitle());
                    action.setStartTime(newAction.getStartTime());
                    action.setEndTime(newAction.getEndTime());
                    action.setIndexOfTask(newAction.getIndexOfTask());
                    action.setIsCorrect(newAction.getIsCorrect());
                    if(!CheckFields.checkTimeField(newAction.getStartTime())){
                        throw new TimeFieldException("hh:mm", newAction.getStartTime())
                    }
                    if(!CheckFields.checkTimeField(newAction.getEndTime())){
                        throw new TimeFieldException("hh:mm", newAction.getEndTime())
                    }
                    Task tsk = repositoryTask.findById(newAction.getIndexOfTask()).get()
                    if(!this.checkActionForInputInTimeTaskInterval(newAction.getStartTime(), newAction.getEndTime(), tsk)){
                        ActionForInputInTimeTaskIntervalException()
                    }else if(tsk.getActionList().size() > 0) {
                        if (!this.checkActionForInputTimeInterval(newAction.getStartTime(), newAction.getEndTime(),  tsk)) {
                            throw new ActionForInputTimeIntervalException()
                        }
                    }
                    return repositoryAct.save(action);
                })
                .orElseGet(() -> {
                    if(!CheckFields.checkTimeField(newAction.getStartTime())){
                        throw new TimeFieldException("hh:mm", newAction.getStartTime())
                    }
                    if(!CheckFields.checkTimeField(newAction.getEndTime())){
                        throw new TimeFieldException("hh:mm", newAction.getEndTime())
                    }
                    Task tsk = repositoryTask.findById(newAction.getIndexOfTask()).get()
                    if(!this.checkActionForInputInTimeTaskInterval(newAction.getStartTime(), newAction.getEndTime(), tsk)){
                        ActionForInputInTimeTaskIntervalException()
                    }else if(tsk.getActionList().size() > 0) {
                        if (!this.checkActionForInputTimeInterval(newAction.getStartTime(), newAction.getEndTime(),  tsk)) {
                            throw new ActionForInputTimeIntervalException()
                        }
                    }
                    return repositoryAct.save(newAction);
                });
    }

    @DeleteMapping("/actions/{id}")
    void deleteAction(@PathVariable(name = "id") Long id) {
        repositoryAct.deleteById(id);
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
