package todolist

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.ObjectMapper
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonElement
import com.google.gson.JsonParser
import groovy.util.logging.Slf4j
import jakarta.persistence.Column
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import java.util.Objects;
import groovy.json.*

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Slf4j
@Entity
@Table(name = "ACTION")
class Action {

    //private static final Logger log = LoggerFactory.getLogger(Action.class);

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
    @Column(name = "INDEXOFTASK")
    private Long indexOfTask

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "INDEXOFTASK", referencedColumnName = "ID", insertable=false, updatable=false)
    private Task taskEntity



    Action() {}

    Action(String title, String startTime, String endTime, Long indexOfTask) {


        this.title = title;
        this.startTime = startTime;
        this.endTime = endTime;
        this.indexOfTask = indexOfTask;
        log.info("Add Action complete!")
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

    public Long getIndexOfTask() {
        return this.indexOfTask;
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

    public void setIndexOfTask(Long indexOfTask) {
        this.indexOfTask = indexOfTask;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o)
            return true;
        if (!(o instanceof Action))
            return false;
        Action action = (Action) o;
        return Objects.equals(this.id, action.id) && Objects.equals(this.startTime, action.startTime)
                && Objects.equals(this.endTime, action.endTime) && Objects.equals(this.indexOfTask, action.indexOfTask)
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.startTime, this.endTime, this.indexOfTask);
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