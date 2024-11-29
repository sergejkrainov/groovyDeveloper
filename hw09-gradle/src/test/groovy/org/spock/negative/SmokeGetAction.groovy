package org.spock.negative

import groovy.json.JsonBuilder
import groovy.json.JsonSlurper
import groovy.util.logging.Log
import io.restassured.RestAssured
import io.restassured.response.Response
import spock.lang.Specification

@Log
class SmokeGetAction extends Specification{

    static Map<String, String> headersMap = new HashMap<>()

    def setupSpec() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8090;
        headersMap.put("Content-Type", "application/json")
    }


    def "get action by Id"() {
        given:

        when:
        Response response =
                RestAssured.get("/actions/1")

        String responseStr = response.asString()
        log.info("Response is:" + responseStr)
        def jsnBld = new JsonBuilder(new JsonSlurper().parseText(responseStr))

        then:
        String.valueOf(jsnBld.getContent().get("id")) == "1"
        jsnBld.getContent().get("title") == "action1"
        jsnBld.getContent().get("startTime") == "07:00"
        jsnBld.getContent().get("endTime") == "10:00"
        String.valueOf(jsnBld.getContent().get("indexOfTask")) == "1"
    }


}
