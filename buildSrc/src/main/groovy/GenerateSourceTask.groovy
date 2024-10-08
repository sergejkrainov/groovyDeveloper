import com.helger.jcodemodel.IJExpression
import com.helger.jcodemodel.JCodeModel
import com.helger.jcodemodel.JDefinedClass
import com.helger.jcodemodel.JExpr
import com.helger.jcodemodel.JFieldVar
import com.helger.jcodemodel.JMethod
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

abstract class GenerateSourceTask extends DefaultTask {


    @TaskAction
    void action() {

        def className = project.extensions.generateSource.className
        println(className.get())

        def fieldName = project.extensions.generateSource.fieldName
        println(fieldName.get())

        def fieldValue = project.extensions.generateSource.fieldValue
        println(fieldValue.get())

        def packageName = project.extensions.generateSource.packageName
        println(packageName.get())

        def rootPath = project.extensions.generateSource.rootPath
        println(rootPath.get())

        JCodeModel cm = new JCodeModel();
        JDefinedClass dc = cm._class("${packageName.get()}.${className.get()}");
        /*JMethod m = dc.method(0, int.class, "foo");
        m.body()._return(JExpr.lit(5));*/
        JFieldVar f = dc.field(0, String.class, fieldName.get(), JExpr.lit(fieldValue.get()))

        File file = new File(project.rootDir.toString() + "/${rootPath.get()}");
        if(!file.exists()) {
            file.mkdirs()
        }
        cm.build(file);

    }
}
