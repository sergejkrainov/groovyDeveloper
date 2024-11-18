package todolist

import grails.boot.GrailsApp
import grails.boot.config.GrailsAutoConfiguration

import groovy.transform.CompileStatic

@CompileStatic
class Application extends GrailsAutoConfiguration {
    static void main(String[] args) {
        System.setProperty("server.port", "8090")
        GrailsApp.run(Application, args)
    }
}