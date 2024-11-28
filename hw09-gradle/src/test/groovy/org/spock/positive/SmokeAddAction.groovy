package org.spock.positive

import groovy.json.JsonBuilder
import groovy.json.JsonSlurper
import groovy.util.logging.Log
import io.restassured.RestAssured
import io.restassured.response.Response
import spock.lang.*

@Log
class SmokeAddAction  extends Specification{

    static Map<String, String> headersMap = new HashMap<>()

    def setupSpec() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8090;
        headersMap.put("Content-Type", "application/json")
    }

    def "create actions"(String id, String title, String startTime, String endTime, String indexOfTask) {
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
        def jsnBld = new JsonBuilder(new JsonSlurper().parseText(responseStr))
        expect:
        String.valueOf(jsnBld.getContent().get("id")) == id
        jsnBld.getContent().get("title") == title
        jsnBld.getContent().get("startTime") == startTime
        jsnBld.getContent().get("endTime") == endTime
        String.valueOf(jsnBld.getContent().get("indexOfTask")) == indexOfTask

        where:
        id| title | startTime | endTime | indexOfTask
        1 | "action1" | "07:00" | "10:00" | 1
        2 | "action2" | "12:00" | "16:00" | 1
        3 | "action3" | "08:00" | "11:00" | 2
        4 | "action4" | "13:00" | "15:00" | 2
    }

}
