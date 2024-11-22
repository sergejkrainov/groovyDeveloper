package todolist


import com.fasterxml.jackson.databind.ObjectMapper
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonElement
import com.google.gson.JsonParser
import groovy.util.logging.Slf4j
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import org.hibernate.annotations.LazyCollection
import org.hibernate.annotations.LazyCollectionOption

@Slf4j
@Entity
@Table(name = "TASK")
class Task {

    private @Id
    @GeneratedValue
    @Column(name = "ID")
    Long id;
    @Column(name = "TITLE")
    private String title;
    @Column(name = "STARTTIME")
    private String startTime;
    @Column(name = "ENDTIME")
    private String endTime;
    @Column(name = "DUEDATE")
    private String dueDate;

    @OneToMany(mappedBy = "taskEntity")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Action> actionList


    Task() {}

    Task(String title, String dueDate, String startTime, String endTime) {


        this.title = title;
        this.dueDate = dueDate;
        this.startTime = startTime;
        this.endTime = endTime;
        log.info("Add Task complete!")
    }

    public Long getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public String getStartTime() {
        return this.startTime;
    }

    public String getEndTime() {
        return this.endTime;
    }

    public String getDueDate() {
        return this.dueDate;
    }

    public List<Action> getActionList() {
        return this.actionList;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    /*public void setActionList(List<Action> actionList) {
        this.actionList = actionList;
    }*/

    @Override
    public boolean equals(Object o) {

        if (this == o)
            return true;
        if (!(o instanceof Task))
            return false;
        Task task = (Task) o;
        return Objects.equals(this.id, task.id) && Objects.equals(this.startTime, task.startTime)
                && Objects.equals(this.endTime, task.endTime) && Objects.equals(this.dueDate, task.dueDate)
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.startTime, this.endTime, this.dueDate);
    }

    @Override
    public String toString() {

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(this);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonParser jp = new JsonParser();
        JsonElement je = jp.parse(json);
        String prettyJsonString = gson.toJson(je);

        return prettyJsonString

        /*return "Action{" + "id=" + this.id + ", startTime='" + this.startTime + '\'' + ", endTime='" + this.endTime
        + ", indexOfTask='" + this.indexOfTask + ", isCorrect='" + this.isCorrect + '\'' + '}';*/
    }
}