package org.serverconfig

abstract class ScriptRun extends groovy.lang.Script{
    def path
    def include(def path) {
        this.path = path
        return path
    }
}
