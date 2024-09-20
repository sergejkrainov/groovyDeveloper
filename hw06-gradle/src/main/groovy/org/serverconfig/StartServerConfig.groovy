package org.serverconfig

import org.codehaus.groovy.control.CompilerConfiguration

class StartServerConfig {

    static void main(String[] args) {

        def binding = new Binding()
        CompilerConfiguration conf = new CompilerConfiguration()
        conf.setScriptBaseClass("org.serverconfig.ScriptRun")
        //binding.setProperty('name', 'Petr')
        def shell = new GroovyShell(binding, conf)
        shell.evaluate(new File('./parent_config.conf'))
        /*println(ServerConfigBuilder.build{
        name = "MyTest"
        description = "Apache Tomcat"*/
        Script scr = shell.parse(new File("./parent_config.conf"))
        def result = scr.run()

        ServerConfig srConf = new ServerConfig();
        Binding bnd = shell.getContext()

        def clArr = bnd.getProperty("mappings")
        def cl = clArr.getAt(0)
        def resultCl = cl.call()
        srConf.setProperty("name", bnd.getProperty("name"))
        srConf.setProperty("description", bnd.getProperty("description"))
        srConf.setProperty("mappings", bnd.getProperty("mappings"))
        println(srConf)
    }


}