package org.spock.positive

import groovy.json.JsonBuilder
import groovy.json.JsonSlurper
import groovy.util.logging.Log
import io.restassured.RestAssured
import io.restassured.response.Response
import spock.lang.*

@Log
class SmokeAddTask extends Specification{

    static Map<String, String> headersMap = new HashMap<>()

    def setupSpec() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8090;
        headersMap.put("Content-Type", "application/json")
    }

    def "create tasks"(String id, String title, String startTime, String endTime, String dueDate) {
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
        def jsnBld = new JsonBuilder(new JsonSlurper().parseText(responseStr))
        expect:
            String.valueOf(jsnBld.getContent().get("id")) == id
            jsnBld.getContent().get("title") == title
            jsnBld.getContent().get("startTime") == startTime
            jsnBld.getContent().get("endTime") == endTime
            jsnBld.getContent().get("dueDate") == dueDate

        where:
        id| title | startTime | endTime | dueDate
        1 | "task1" | "06:00" | "23:00" | "2024-11-27"
        2 | "task2" | "05:00" | "22:00" | "2024-11-28"

        /*when:
        Response response =
                RestAssured
        .with()
        .headers(headersMap)
        .body(jsonBody)
        .post("/tasks")

        String responseStr = response.asString()
        log.info("Response is:" + responseStr)
        def jsnBld = new JsonBuilder(new JsonSlurper().parseText(responseStr))
        then:
        jsnBld.getContent().get("title") == "task1"
        jsnBld.getContent().get("startTime") == "06:00"
        jsnBld.getContent().get("endTime") == "22:00"
        jsnBld.getContent().get("dueDate") == "2024-11-27"*/

    }

    def "check action list"() {
        given:

        when:
        Response response =
                RestAssured.get("/tasks")

        String responseStr = response.asString()
        log.info("Response is:" + responseStr)
        def jsnBld = new JsonBuilder(new JsonSlurper().parseText(responseStr))

        then:
        //--------------Task1,Action1-------------------------------------------------
        String.valueOf(jsnBld.getContent()[0].get("actionList")[0].get("id")) == "1"
        jsnBld.getContent()[0].get("actionList")[0].get("title") == "action1"
        jsnBld.getContent()[0].get("actionList")[0].get("startTime") == "07:00"
        jsnBld.getContent()[0].get("actionList")[0].get("endTime") == "10:00"
        String.valueOf(jsnBld.getContent()[0].get("actionList")[0].get("indexOfTask")) == "1"
        //--------------Task1,Action2-------------------------------------------------
        String.valueOf(jsnBld.getContent()[0].get("actionList")[1].get("id")) == "2"
        jsnBld.getContent()[0].get("actionList")[1].get("title") == "action2"
        jsnBld.getContent()[0].get("actionList")[1].get("startTime") == "12:00"
        jsnBld.getContent()[0].get("actionList")[1].get("endTime") == "16:00"
        String.valueOf(jsnBld.getContent()[0].get("actionList")[1].get("indexOfTask")) == "1"

        //--------------Task2,Action3-------------------------------------------------
        String.valueOf(jsnBld.getContent()[1].get("actionList")[0].get("id")) == "3"
        jsnBld.getContent()[1].get("actionList")[0].get("title") == "action3"
        jsnBld.getContent()[1].get("actionList")[0].get("startTime") == "08:00"
        jsnBld.getContent()[1].get("actionList")[0].get("endTime") == "11:00"
        String.valueOf(jsnBld.getContent()[1].get("actionList")[0].get("indexOfTask")) == "2"
        //--------------Task2,Action4-------------------------------------------------
        String.valueOf(jsnBld.getContent()[1].get("actionList")[1].get("id")) == "4"
        jsnBld.getContent()[1].get("actionList")[1].get("title") == "action4"
        jsnBld.getContent()[1].get("actionList")[1].get("startTime") == "13:00"
        jsnBld.getContent()[1].get("actionList")[1].get("endTime") == "15:00"
        String.valueOf(jsnBld.getContent()[1].get("actionList")[1].get("indexOfTask")) == "2"


    }


}
