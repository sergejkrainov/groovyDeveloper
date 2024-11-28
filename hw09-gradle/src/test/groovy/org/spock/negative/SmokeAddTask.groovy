package org.spock.negative

import groovy.json.JsonBuilder
import groovy.json.JsonSlurper
import groovy.util.logging.Log
import io.restassured.RestAssured
import io.restassured.response.Response
import spock.lang.*

@Log
class SmokeAddTask extends Specification {

    static Map<String, String> headersMap = new HashMap<>()

    def setupSpec() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8090;
        headersMap.put("Content-Type", "application/json")
    }

    def "checkTimeDateInterval"(String id, String title, String startTime, String endTime, String dueDate) {
        given:
        String jsonBody = """
            {
              "id": ${id},
              "title": "${title}",
              "startTime": "${startTime}",
              "endTime": "${endTime}",
              "dueDate": "${dueDate}"
            }
        """
        Response response =
                RestAssured
                        .with()

                        .headers(headersMap)
                        .body(jsonBody)
                        .post("/tasks")

        String responseStr = response.asString()
        log.info("Response is:" + responseStr)
        expect:
        responseStr  == "Task InputTimeInterval is busy, enter another interval "

        where:
        id| title | startTime | endTime | dueDate
        1 | "task1" | "06:00" | "23:00" | "2024-11-27"
        2 | "task2" | "05:00" | "22:00" | "2024-11-28"

    }

}
