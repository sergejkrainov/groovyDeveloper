package org.spock.negative

import groovy.json.JsonBuilder
import groovy.json.JsonSlurper
import groovy.util.logging.Log
import io.restassured.RestAssured
import io.restassured.response.Response
import spock.lang.Specification

@Log
class SmokeEditAction extends Specification{

    static Map<String, String> headersMap = new HashMap<>()

    def setupSpec() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8090;
        headersMap.put("Content-Type", "application/json")
    }

    def "edit action by Id"() {
        given:
        String jsonBody = """
            {
              "id": 1,
              "title": "action1Edited",
              "startTime": "07:30",
              "endTime": "10:30",
              "indexOfTask": 1
            }
        """
        when:
        Response response =
                RestAssured
                        .with()
                        .headers(headersMap)
                        .body(jsonBody)
                        .put("/actions/1")

        String responseStr = response.asString()
        log.info("Response is:" + responseStr)
        def jsnBld = new JsonBuilder(new JsonSlurper().parseText(responseStr))
        then:
            jsnBld.getContent().get("title") == "action1Edited"
            jsnBld.getContent().get("startTime") == "20:30"
            jsnBld.getContent().get("endTime") == "21:30"
            String.valueOf(jsnBld.getContent().get("indexOfTask")) == "1"

    }


}
