package org.spock.positive

import groovy.json.JsonBuilder
import groovy.json.JsonSlurper
import groovy.util.logging.Log
import io.restassured.RestAssured
import io.restassured.response.Response
import spock.lang.Specification

@Log
class SmokeGetTask extends Specification{

    static Map<String, String> headersMap = new HashMap<>()

    def setupSpec() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8090;
        headersMap.put("Content-Type", "application/json")
    }


    def "get task by Id"() {
        given:

        when:
        Response response =
                RestAssured.get("/tasks/1")

        String responseStr = response.asString()
        log.info("Response is:" + responseStr)
        def jsnBld = new JsonBuilder(new JsonSlurper().parseText(responseStr))

        then:
        String.valueOf(jsnBld.getContent().get("id")) == "1"
        jsnBld.getContent().get("title") == "task1"
        jsnBld.getContent().get("startTime") == "06:00"
        jsnBld.getContent().get("endTime") == "23:00"
        jsnBld.getContent().get("dueDate") == "2024-11-27"

    }


}
