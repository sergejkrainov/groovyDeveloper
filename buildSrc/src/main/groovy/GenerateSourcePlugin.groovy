import org.gradle.api.Plugin
import org.gradle.api.Project

class GenerateSourcePlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {
        def generateSourceExt = project.extensions.create("generateSource", GenerateSourcePluginExtension)
        generateSourceExt.className.convention("myClassName")
        generateSourceExt.fieldName.convention("myFieldName")
        generateSourceExt.fieldValue.convention("MyFieldValue")
        generateSourceExt.packageName.convention("mypackagename")
        generateSourceExt.rootPath.convention("rootPath")
        println("className from plugin = " + generateSourceExt.className.get())
        println("fieldName from plugin = " + generateSourceExt.fieldName.get())
        println("fieldValue from plugin = " + generateSourceExt.fieldValue.get())
        println("packageName from plugin = " + generateSourceExt.packageName.get())
        println("rootPath from plugin = " + generateSourceExt.rootPath.get())
        project.tasks.register("generateSource", GenerateSourceTask.class)
    }

}