package org.serverconfig

abstract class ScriptRun extends groovy.lang.Script{
    def include(def path) {
        ServerConfig.getInstance().path = path;
    }

    def http(Closure cl) {
        HttpConf httpConf = new HttpConf()
        cl.resolveStrategy = Closure.DELEGATE_FIRST
        cl.delegate = httpConf
        cl.call()
        ServerConfig.getInstance().httpConf = httpConf
    }

    def https(Closure cl) {
        HttpsConf httpsConf = new HttpsConf()
        cl.resolveStrategy = Closure.DELEGATE_FIRST
        cl.delegate = httpsConf
        cl.call()
        ServerConfig.getInstance().httpsConf = httpsConf
    }
}
