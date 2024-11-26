package org.spock.positive

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

    def "create task1"() {
        given:
        String jsonBdy = ""
        when:
        Response response =
                RestAssured.get("/tasks")
        /*with().body(jsonBdy)
                .when()
                .request("POST", "/tasks")
                .then()
                .statusCode(200);*/
        log.info(response.getBody())
        then:
        response.getBody().toString().size() > 0

    }

    def "create task2"() {

    }


}
