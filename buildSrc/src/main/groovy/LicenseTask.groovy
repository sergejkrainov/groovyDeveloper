import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

abstract class LicenseTask extends DefaultTask {

    @Input
    def fileName = project.rootDir.toString() + "/license.txt"



    @TaskAction
    void action(){
        def pattern = project.extensions.license.pattern
        print(pattern.get())
        def licenseText = new File(fileName).text

        new File(project.projectDir.toString())
                .eachFileRecurse { file ->
                    int lastIndexOf = file.getName().lastIndexOf('.')
                    if ((lastIndexOf != -1) && (file.getName().substring(lastIndexOf)) == pattern.get()) {
                        def content = file.getText()
                        //println(licenseText + '\n' + content)
                        // Write the license and the source code to the file
                        file.text = licenseText + '\n' + content
                    }
                }
    }
}
