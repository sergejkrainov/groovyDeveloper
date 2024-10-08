import org.gradle.api.Plugin
import org.gradle.api.Project

class LicensePlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {
        def licenseExt = project.extensions.create("license", LicensePluginExtension)
        licenseExt.pattern.convention(".java")
        println(licenseExt.pattern.get())
        project.tasks.register("license", LicenseTask.class)
    }
}