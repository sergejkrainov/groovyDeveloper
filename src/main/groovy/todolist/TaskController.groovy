package todolist

import org.springframework.web.bind.annotation.*

import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@RestController
class TaskController {

    private final TaskRepository repository;

    TaskController(TaskRepository repository) {
        this.repository = repository;
    }


    // Aggregate root
    // tag::get-aggregate-root[]
    @GetMapping("/tasks")
    List<Task> all() {
        return repository.findAll();
    }
    // end::get-aggregate-root[]

    @PostMapping("/tasks")
    Task newTask(@RequestBody Task newTask) {
        if(!CheckFields.checkTimeField(newTask.getStartTime())){
            throw new TimeFieldException("hh:mm", newTask.getStartTime())
        }
        if(!CheckFields.checkTimeField(newTask.getEndTime())){
            throw new TimeFieldException("hh:mm", newTask.getEndTime())
        }
        if(!CheckFields.checkDateField(newTask.getDueDate())){
            throw new TimeFieldException("yyyy-mm-dd", newTask.getDueDate())
        }

        if(!this.checkTaskForInputTimeInterval(newTask.getDueDate(), newTask.getStartTime(), newTask.getEndTime())){
            throw new TaskForInputTimeIntervalException()
        }

        return repository.save(newTask);
    }

    // Single item

    @GetMapping("/tasks/{id}")
    Task one(@PathVariable(name = "id") Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
    }

    @PutMapping("/tasks/{id}")
    Task replaceTask(@RequestBody Task newTask, @PathVariable(name = "id") Long id) {

        return repository.findById(id)
                .map(task -> {
                    task.setTitle(newTask.getTitle());
                    task.setStartTime(newTask.getStartTime());
                    task.setEndTime(newTask.getEndTime());
                    task.setDueDate(newTask.getDueDate());
                    task.setActionList(newTask.getActionList());
                    task.setIsCorrect(newTask.getIsCorrect());
                    if(!CheckFields.checkTimeField(newTask.getStartTime())){
                        throw new TimeFieldException("hh:mm", newTask.getStartTime())
                    }
                    if(!CheckFields.checkTimeField(newTask.getEndTime())){
                        throw new TimeFieldException("hh:mm", newTask.getEndTime())
                    }
                    if(!CheckFields.checkDateField(newTask.getDueDate())){
                        throw new TimeFieldException("yyyy-mm-dd", newTask.getDueDate())
                    }
                    return repository.save(task);
                })
                .orElseGet(() -> {
                    if(!CheckFields.checkTimeField(newTask.getStartTime())){
                        throw new TimeFieldException("hh:mm", newTask.getStartTime())
                    }
                    if(!CheckFields.checkTimeField(newTask.getEndTime())){
                        throw new TimeFieldException("hh:mm", newTask.getEndTime())
                    }
                    if(!CheckFields.checkDateField(newTask.getDueDate())){
                        throw new TimeFieldException("yyyy-mm-dd", newTask.getDueDate())
                    }
                    return repository.save(newTask);
                });
    }

    @DeleteMapping("/tasks/{id}")
    void deleteTask(@PathVariable(name = "id") Long id) {
        repository.deleteById(id);
    }

    boolean checkTaskForInputTimeInterval(String dueDate, String startTime, String endTime) {

        def formatTime = "HH:mm"
        DateTimeFormatter dtFrm = DateTimeFormatter.ofPattern(formatTime)
        boolean correctTimes = false
        def searchtaskList = repository.findAllByDueDate(dueDate)
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

    @GetMapping("/tasks/getbusytime")
    @ResponseBody
    int getBusyTimeByDate(@RequestParam(name = "dueDate") String dueDate) {

        def formatTime = "HH:mm"
        DateTimeFormatter dtFrm = DateTimeFormatter.ofPattern(formatTime)

        List<Task> taskListFound = repository.findAllByDueDate(dueDate)
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

    @GetMapping("/tasks/gettasksbydate")
    List<Task> getTasksByDate(@RequestParam(name = "dueDate") String dueDate) {
        return repository.findAllByDueDate(dueDate)
    }

    @GetMapping("/tasks/getcounttasksbydate")
    @ResponseBody
    int getCountTasksByDate(@RequestParam(name = "dueDate") String dueDate) {
        return repository.findAllByDueDate(dueDate).size()
    }
}
