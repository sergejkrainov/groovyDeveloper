import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

abstract class GreetingTask extends DefaultTask {

    @TaskAction
    void action() {
        println("Привет от сборки")
    }
}