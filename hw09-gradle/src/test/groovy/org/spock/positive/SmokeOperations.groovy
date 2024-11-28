package org.spock.positive

import groovy.json.JsonBuilder
import groovy.json.JsonSlurper
import groovy.util.logging.Log
import io.restassured.RestAssured
import io.restassured.response.Response
import spock.lang.Specification

@Log
class SmokeOperations extends Specification {

    static Map<String, String> headersMap = new HashMap<>()

    def setupSpec() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8090;
        headersMap.put("Content-Type", "application/json")
    }

    def "check getBusyTime"() {
        given:

        when:
        Response response =
                RestAssured.get("/tasks/getbusytime?dueDate=2024-11-27")

        String responseStr = response.asString()
        log.info("Response is:" + responseStr)
        def jsnBld = new JsonBuilder(new JsonSlurper().parseText(responseStr))

        then:

        jsnBld.getContent() == 420
    }

    def "check getCounttasksByDate"() {
        given:

        when:
        Response response =
                RestAssured.get("/tasks/getcounttasksbydate?dueDate=2024-11-27")

        String responseStr = response.asString()
        log.info("Response is:" + responseStr)
        def jsnBld = new JsonBuilder(new JsonSlurper().parseText(responseStr))

        then:

        jsnBld.getContent() == 1
    }

    def "check getTasksByTime"() {
        given:

        when:
        Response response =
                RestAssured.get("/tasks/gettasksbydate?dueDate=2024-11-27")

        String responseStr = response.asString()
        log.info("Response is:" + responseStr)
        def jsnBld = new JsonBuilder(new JsonSlurper().parseText(responseStr))

        then:

        String.valueOf(jsnBld.getContent()[0].get("id")) == "1"
        jsnBld.getContent()[0].get("title") == "task1"
        jsnBld.getContent()[0].get("startTime") == "06:00"
        jsnBld.getContent()[0].get("endTime") == "23:00"
        jsnBld.getContent()[0].get("dueDate") == "2024-11-27"

    }
}
