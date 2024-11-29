package org.spock.positive

import groovy.json.JsonBuilder
import groovy.json.JsonSlurper
import groovy.util.logging.Log
import io.restassured.RestAssured
import io.restassured.response.Response
import spock.lang.Specification

@Log
class SmokeEditTask extends Specification{

    static Map<String, String> headersMap = new HashMap<>()

    def setupSpec() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8090;
        headersMap.put("Content-Type", "application/json")
    }

    def "edit task by Id"() {
        given:
        String jsonBody = """
            {
              "id": 1,
              "title": "task1Edited",
              "startTime": "04:00",
              "endTime": "23:30",
              "dueDate": "2024-11-27"
            }
        """
        when:
        Response response =
                RestAssured
                        .with()
                        .headers(headersMap)
                        .body(jsonBody)
                        .put("/tasks/1")

        String responseStr = response.asString()
        log.info("Response is:" + responseStr)
        def jsnBld = new JsonBuilder(new JsonSlurper().parseText(responseStr))
        then:
            jsnBld.getContent().get("title") == "task1Edited"
            jsnBld.getContent().get("startTime") == "04:00"
            jsnBld.getContent().get("endTime") == "23:30"
            jsnBld.getContent().get("dueDate") == "2024-11-27"

    }


}
