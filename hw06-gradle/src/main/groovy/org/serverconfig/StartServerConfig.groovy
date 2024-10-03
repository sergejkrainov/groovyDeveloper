package org.serverconfig

import org.codehaus.groovy.control.CompilerConfiguration

class StartServerConfig {

    static void main(String[] args) {

        def binding = new Binding()
        CompilerConfiguration conf = new CompilerConfiguration()
        conf.setScriptBaseClass("org.serverconfig.ScriptRun")
        //binding.setProperty('name', 'Petr')
        def shell = new GroovyShell(binding, conf)
        def prntConfPth = System.getProperty("user.dir") +
                "/hw06-gradle/src/main/" +  "resources/config/parent_config.conf"
        shell.evaluate(new File(prntConfPth))
        /*println(ServerConfigBuilder.build{
        name = "MyTest"
        description = "Apache Tomcat"*/

        Binding bnd = shell.getContext()

        ArrayList<Closure> clArr = bnd.getProperty("mappings")
        setMappings(clArr)
        ServerConfig.getInstance().setProperty("name", bnd.getProperty("name"))
        ServerConfig.getInstance().setProperty("description", bnd.getProperty("description"))

        def confPth = System.getProperty("user.dir") +
                "/hw06-gradle/src/main/" +  "resources/config/config.conf"
        shell.evaluate(new File(confPth))
        bnd = shell.getContext()
        clArr = bnd.getProperty("mappings")
        setMappings(clArr)

        def confStendPth = System.getProperty("user.dir") +
                "/hw06-gradle/src/main/" +  "resources/config/" + ServerConfig.getInstance().path
        shell.evaluate(new File(confStendPth))


        ServerConfig srvConf = ServerConfig.getInstance()
        srvConf.toString()
    }

    static void setMappings(List clArr){
        if(ServerConfig.getInstance().getMappings().size() > 1) {
            ServerConfig.getInstance().getMappings().clear()
        }
        for(Closure cl in clArr) {
            ServerProps srvPops = new ServerProps()
            cl.resolveStrategy = Closure.DELEGATE_FIRST
            cl.delegate = srvPops
            cl.call()
            ServerConfig.getInstance().getMappings().add(srvPops)
        }
    }


}