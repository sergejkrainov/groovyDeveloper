package org.spock.negative

import io.restassured.RestAssured
import spock.lang.*

class SmokeAddTask extends Specification {

    def setupSpec() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8090;
    }

    def "create task1"() {

    }

}
