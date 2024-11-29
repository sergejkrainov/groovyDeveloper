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

    def "checkWrongInputFormatStartTime"(String id, String title, String startTime, String endTime, String dueDate) {
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
        String format = "hh:mm"
        expect:
        responseStr  == "value  " + startTime + " not of format " + format

        where:
        id| title | startTime | endTime | dueDate
        1 | "task1" | "test" | "23:00" | "2024-11-27"
        2 | "task2" | "054:00" | "22:00" | "2024-11-28"
    }

    def "checkWrongInputFormatEndTime"(String id, String title, String startTime, String endTime, String dueDate) {
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
        String format = "hh:mm"
        expect:
        responseStr  == "value  " + endTime + " not of format " + format

        where:
        id| title | startTime | endTime | dueDate
        1 | "task1" | "08:00" | "test" | "2024-11-27"
        2 | "task2" | "05:00" | "2223:00" | "2024-11-28"
    }

    def "checkWrongInputFormatDueDate"(String id, String title, String startTime, String endTime, String dueDate) {
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
        String format = "yyyy-mm-dd"
        expect:
        responseStr  == "value  " + dueDate + " not of format " + format

        where:
        id| title | startTime | endTime | dueDate
        1 | "task1" | "08:00" | "22:00" | "test"
        2 | "task2" | "05:00" | "23:00" | "20244545-11-28"
    }

}
