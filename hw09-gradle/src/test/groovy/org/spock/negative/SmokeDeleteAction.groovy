package org.spock.negative

import groovy.json.JsonBuilder
import groovy.json.JsonSlurper
import groovy.util.logging.Log
import io.restassured.RestAssured
import io.restassured.response.Response
import spock.lang.Specification

@Log
class SmokeDeleteAction extends Specification{

    static Map<String, String> headersMap = new HashMap<>()

    def setupSpec() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8090;
        headersMap.put("Content-Type", "application/json")
    }


    def "delete action"() {
        given:

        when:
        Response response =
                RestAssured.delete("/actions/4")

        String responseStr = response.asString()
        log.info("Response is:" + responseStr)


        response =
                RestAssured.get("/actions")
        responseStr = response.asString()
        def jsnBld = new JsonBuilder(new JsonSlurper().parseText(responseStr))

        then:
        jsnBld.getContent().size() == 3

    }


}
