package org.spock.negative

import groovy.util.logging.Log
import io.restassured.RestAssured
import io.restassured.response.Response
import spock.lang.*

@Log
class SmokeAddAction  extends Specification {

    static Map<String, String> headersMap = new HashMap<>()

    def setupSpec() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8090;
        headersMap.put("Content-Type", "application/json")
    }

    def "checkTimeDateInterval"(String id, String title, String startTime, String endTime, String indexOfTask) {
        given:
        String jsonBody = """
            {
              "id": ${id},
              "title": "${title}",
              "startTime": "${startTime}",
              "endTime": "${endTime}",
              "indexOfTask": "${indexOfTask}"
            }
        """
        Response response =
                RestAssured
                        .with()

                        .headers(headersMap)
                        .body(jsonBody)
                        .post("/actions")

        String responseStr = response.asString()
        log.info("Response is:" + responseStr)
        expect:
        responseStr  == "Wrong action InputTimeInterval , enter another interval "

        where:
        id| title | startTime | endTime | indexOfTask
        1 | "action1" | "07:00" | "10:00" | 1
        2 | "action2" | "12:00" | "16:00" | 1
        3 | "action3" | "08:00" | "11:00" | 2
        4 | "action4" | "13:00" | "15:00" | 2
    }

    def "checkTimeDateTaskInterval"(String id, String title, String startTime, String endTime, String indexOfTask) {
        given:
        String jsonBody = """
            {
              "id": ${id},
              "title": "${title}",
              "startTime": "${startTime}",
              "endTime": "${endTime}",
              "indexOfTask": "${indexOfTask}"
            }
        """
        Response response =
                RestAssured
                        .with()

                        .headers(headersMap)
                        .body(jsonBody)
                        .post("/actions")

        String responseStr = response.asString()
        log.info("Response is:" + responseStr)
        expect:
        responseStr  == "action InputTimeInterval out of task interval, enter another interval "

        where:
        id| title | startTime | endTime | indexOfTask
        1 | "action1" | "01:00" | "02:00" | 1
        2 | "action2" | "03:00" | "10:00" | 1
        3 | "action3" | "01:00" | "02:00" | 2
        4 | "action4" | "03:00" | "10:00" | 2

    }

    def "checkWrongInputFormatStartTime"(String id, String title, String startTime, String endTime, String indexOfTask) {
        given:
        String jsonBody = """
            {
              "id": ${id},
              "title": "${title}",
              "startTime": "${startTime}",
              "endTime": "${endTime}",
              "indexOfTask": "${indexOfTask}"
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
        id| title | startTime | endTime | indexOfTask
        1 | "action1" | "test" | "23:00" | 1
        2 | "action2" | "054:00" | "22:00" | 1
    }

    def "checkWrongInputFormatEndTime"(String id, String title, String startTime, String endTime, String indexOfTask) {
        given:
        String jsonBody = """
            {
              "id": ${id},
              "title": "${title}",
              "startTime": "${startTime}",
              "endTime": "${endTime}",
              "indexOfTask": "${indexOfTask}"
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
        id| title | startTime | endTime | indexOfTask
        1 | "task1" | "08:00" | "test" | 1
        2 | "task2" | "05:00" | "2223:00" | 1
    }
}
